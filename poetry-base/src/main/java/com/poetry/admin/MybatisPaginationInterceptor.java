/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.poetry.admin;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * ???????????????
 *
 * @author Negi
 * @version 2019-05
 */
@Slf4j
@Setter
@Accessors(chain = true)
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisPaginationInterceptor extends AbstractSqlParserHandler implements Interceptor {

    /**
     * COUNT SQL ??????
     */
    private ISqlParser countSqlParser;
    /**
     * ?????????????????????????????????
     */
    private boolean overflow = false;
    /**
     * ???????????? 500 ???????????? 0 ??? -1 ????????????
     */
    private long limit = 500L;
    /**
     * ????????????
     */
    private String dialectType;
    /**
     * ???????????????
     */
    private String dialectClazz;

    /**
     * ??????SQL??????Order By
     *
     * @param originalSql ???????????????SQL
     * @param page        page??????
     * @param orderBy     ??????????????????Order By
     * @return ignore
     */
    public static String concatOrderBy(String originalSql, IPage<?> page, boolean orderBy) {
        if (orderBy && (ArrayUtils.isNotEmpty(page.ascs())
            || ArrayUtils.isNotEmpty(page.descs()))) {
            StringBuilder buildSql = new StringBuilder(originalSql);
            String ascStr = concatOrderBuilder(page.ascs(), " ASC");
            String descStr = concatOrderBuilder(page.descs(), " DESC");
            if (StringUtils.isNotEmpty(ascStr) && StringUtils.isNotEmpty(descStr)) {
                ascStr += ", ";
            }
            if (StringUtils.isNotEmpty(ascStr) || StringUtils.isNotEmpty(descStr)) {
                buildSql.append(" ORDER BY ").append(ascStr).append(descStr);
            }
            return buildSql.toString();
        }
        return originalSql;
    }

    /**
     * ????????????????????????
     *
     * @param columns   ignore
     * @param orderWord ignore
     */
    private static String concatOrderBuilder(String[] columns, String orderWord) {
        if (ArrayUtils.isNotEmpty(columns)) {
            return Arrays.stream(columns).filter(StringUtils::isNotEmpty)
                .map(i -> i + orderWord).collect(joining(StringPool.COMMA));
        }
        return StringUtils.EMPTY;
    }

    /**
     * Physical Page Interceptor for all the queries with parameter {@link RowBounds}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // SQL ??????
        this.sqlParser(metaObject);

        // ??????????????????SELECT??????  (??????????????????)
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()
            || StatementType.CALLABLE == mappedStatement.getStatementType()) {
            return invocation.proceed();
        }

        // ???????????????rowBounds?????????mapper?????????????????????
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object paramObj = boundSql.getParameterObject();

        // ????????????????????????page??????
        IPage<?> page = null;
        if (paramObj instanceof IPage) {
            page = (IPage<?>) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map<?, ?>) paramObj).values()) {
                if (arg instanceof IPage) {
                    page = (IPage<?>) arg;
                    break;
                }
            }
        }

        /*
         * ????????????????????????????????? size ?????? 0 ???????????????
         */
        if (null == page || page.getSize() < 0) {
            return invocation.proceed();
        }

        /*
         * ????????????????????????
         */
        if (limit > 0 && limit <= page.getSize()) {
            page.setSize(limit);
        }

        String originalSql = boundSql.getSql();
        Connection connection = (Connection) invocation.getArgs()[0];
        DbType dbType = StringUtils.isNotEmpty(dialectType) ? DbType.getDbType(dialectType)
            : JdbcUtils.getDbType(connection.getMetaData().getURL());

        boolean orderBy = true;
        if (page.isSearchCount()) {
            SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(page.optimizeCountSql(), countSqlParser, originalSql);
            orderBy = sqlInfo.isOrderBy();
            this.queryTotal(overflow, sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
            if (page.getTotal() < 0) {
                return null;
            }
        }

        String buildSql = concatOrderBy(originalSql, page, orderBy);
        DialectModel model = DialectFactory.buildPaginationSql(page, buildSql, dbType, dialectClazz);
        Configuration configuration = mappedStatement.getConfiguration();
        List<ParameterMapping> mappings = new ArrayList<>(boundSql.getParameterMappings());
        Map<String, Object> additionalParameters = (Map<String, Object>) metaObject.getValue("delegate.boundSql.additionalParameters");
        model.consumers(mappings, configuration, additionalParameters);
        if (log.isDebugEnabled()) {
            showSql(mappedStatement, paramObj, dbType, page);
        }
        metaObject.setValue("delegate.boundSql.sql", model.getDialectSql());
        metaObject.setValue("delegate.boundSql.parameterMappings", mappings);
        return invocation.proceed();
    }

    /**
     * ?????????????????????
     *
     * @param sql             count sql
     * @param mappedStatement MappedStatement
     * @param boundSql        BoundSql
     * @param page            IPage
     * @param connection      Connection
     */
    protected void queryTotal(boolean overflowCurrent, String sql, MappedStatement mappedStatement, BoundSql boundSql, IPage<?> page, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            long total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            page.setTotal(total);
            /*
             * ?????????????????????????????????
             */
            long pages = page.getPages();
            if (overflowCurrent && page.getCurrent() > pages) {
                // ??????????????????
                page.setCurrent(1);
            }
        } catch (Exception e) {
            throw ExceptionUtils.mpe("Error: Method queryTotal execution error of sql : \n %s \n", e, sql);
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
    }


    private void showSql(MappedStatement mappedStatement, Object paramObj, DbType dbType, IPage page) {
        String sql = showSql(mappedStatement.getConfiguration(), mappedStatement.getBoundSql(paramObj));
        if (dialectType == null || DbType.MYSQL.equals(dbType)) {
            long startIndex = page.getCurrent() > 0 ? page.getCurrent() - 1 : 0;
            sql = sql + String.format(" limit %d,%d", startIndex, page.getSize());
        }
        log.debug("\n" + sql);
    }

    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    private static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
}
