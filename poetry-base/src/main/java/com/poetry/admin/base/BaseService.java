
package com.poetry.admin.base;

import java.util.List;

/**
 * Service基类
 *
 * @author NEGI
 * @version 2018-11
 */
@SuppressWarnings("unused")
public interface BaseService<SaveVO extends BaseVO,QueryVO extends PaginationVO,DTO extends BaseDTO> {
    /**
     * 新增数据
     *
     * @param vo 数据vo
     * @return 生成的id
     */
    long insert(SaveVO vo);

    /**
     * 更新全部数据
     *
     * @param vo 数据vo
     * @return 更新的id
     */
    long update(SaveVO vo);

    /**
     * 更新部分数据
     *
     * @param vo 数据vo
     * @return 更新的id
     */
    long updateSelective(SaveVO vo);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return dto实体
     */
    DTO queryById(long id);

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    void deleteById(long id);

    /**
     * 批量插入(不支持外部事务)
     *
     * @param dataList 数据集合
     */
    void insertBatch(List<SaveVO> dataList);

    /**
     * 批量更新(不支持外部事务)
     *
     * @param dataList 数据集合
     */
    void updateBatch(List<SaveVO> dataList);

    /**
     * 批量保存（新增或更新）
     *
     * @param dataList 数据集合
     */
    void saveBatch(List<SaveVO> dataList);

    /**
     * 条件所有结果集
     *
     * @return 数据集合
     */
    List<DTO> queryAll();

    /**
     * 条件查询结果集
     *
     * @param vo 查询条件
     * @return 数据集合
     */
    List<DTO> queryList(QueryVO vo);

    /**
     * 条件查询记录数
     *
     * @param vo 查询条件
     * @return 记录数
     */
    long count(QueryVO vo);

    /**
     * 分页查询
     *
     * @param vo 查询条件
     * @return 分页对象
     */
    Pagination<DTO> queryPagination(QueryVO vo);
}