package com.poetry.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.poetry.admin.base.*;
import com.poetry.admin.exception.BusinessException;
import com.poetry.admin.exception.SystemException;
import org.springframework.beans.BeanUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 业务层Service基类实现
 *
 * @author Negi
 * @version 2019-04
 */
@SuppressWarnings("unchecked")
public abstract class BaseBizServiceImpl<SaveVO extends BaseVO, QueryVO extends PaginationVO, DTO extends BaseDTO, Entity extends BizEntity, IBaseService extends IService<Entity>> implements BaseService<SaveVO, QueryVO, DTO> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected IBaseService baseService;

    @Override
    public long insert(SaveVO vo) {
        Entity entity = convert2Entity(vo);
        entity.setCreated(new Date());
        baseService.save(entity);
        return entity.getId();
    }

    /**
     * 默认不提供全量更新避免误操作，如需将字段设置为null，在对应实体属性中加入注解@TableField(strategy = FieldStrategy.IGNORED)
     *
     * @see com.baomidou.mybatisplus.annotation.FieldStrategy
     */
    @Override
    public long update(SaveVO vo) {
        return updateSelective(vo);
    }

    @Override
    public long updateSelective(SaveVO vo) {
        Entity entity = convert2Entity(vo);
        if (entity.getId() == null) {
            throw new BusinessException("id不能为空");
        }
        entity.setCreated(null);
        entity.setUpdated(new Date());
        baseService.updateById(entity);
        return entity.getId();
    }

    @Override
    public DTO queryById(long id) {
        Entity entity = baseService.getById(id);
        return entity == null ? null : convert2DTO(entity);
    }

    @Override
    public void deleteById(long id) {
        if (BizConstant.REAL_DELETE) {
            baseService.removeById(id);
        } else {
            throw new BusinessException("系统已禁止物理删除！");
        }
    }

    @Override
    public void insertBatch(List<SaveVO> dataList) {
        List<Entity> entityList = convertVo2EntityList(dataList);
        baseService.saveBatch(entityList);
    }

    @Override
    public void updateBatch(List<SaveVO> dataList) {
        List<Entity> entityList = convertVo2EntityList(dataList);
        baseService.updateBatchById(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<SaveVO> dataList) {
        List<Entity> insertList = new ArrayList<>(dataList.size());
        List<Entity> updateList = new ArrayList<>(dataList.size());
        for (SaveVO vo : dataList) {
            Entity entity = newInstanceEntity();
            BeanUtils.copyProperties(vo, entity);
            if (Objects.isNull(entity.getId())) {
                insertList.add(entity);
            } else {
                updateList.add(entity);
            }
        }
        if (insertList.size() > 0) {
            baseService.saveBatch(insertList);
        }
        if (updateList.size() > 0) {
            baseService.updateBatchById(updateList);
        }
    }

    @Override
    public List<DTO> queryAll() {
        long total = baseService.count();
        checkQueryLimit(total);
        List<Entity> entityList = baseService.list();
        List<DTO> dtoList = new ArrayList<>();
        for (Entity entity : entityList) {
            DTO newDto = newInstanceDTO();
            BeanUtils.copyProperties(entity, newDto);
            dtoList.add(newDto);
        }
        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DTO> queryList(QueryVO vo) {
        QueryWrapper criteria = new QueryWrapper();
        setCriteriaForQuery(vo, criteria);
        long total = baseService.count(criteria);
        checkQueryLimit(total);
        return convertEntity2DtoList(baseService.list(criteria));
    }

    /**
     * 根据id查询数据集合
     *
     * @param ids
     * @return
     */
    public List<DTO> queryListByIds(List<Long> ids) {
        List<Entity> list = (List<Entity>) baseService.listByIds(ids);
        return convertEntity2DtoList(list);
    }

    /**
     * 查询限制校验
     */
    private void checkQueryLimit(long total) {
        if (total > BizConstant.QUERY_LIMIT) {
            throw new BusinessException("查询数据量" + total + "已超过系统限制" + BizConstant.QUERY_LIMIT);
        }
    }

    private List<DTO> queryList(IPage<Entity> page, QueryWrapper<Entity> criteria) {
        List<Entity> entityList = baseService.page(page, criteria).getRecords();
        return convertEntity2DtoList(entityList);
    }

    @Override
    public long count(QueryVO vo) {
        QueryWrapper criteria = new QueryWrapper();
        setCriteriaForQuery(vo, criteria);
        return baseService.count(criteria);
    }

    @Override
    public Pagination<DTO> queryPagination(QueryVO vo) {
        QueryWrapper criteria = new QueryWrapper();
        setCriteriaForQuery(vo, criteria);
        long total = baseService.count(criteria);
        IPage<Entity> page = new Page<>(vo.getPageIndex(), vo.getPageSize(), total, false);
        List<DTO> dtoList = queryList(page, criteria);
        return new Pagination<>(dtoList, page.getTotal(), vo.getPageSize());
    }

    // 设置查询条件criteria
    protected abstract void setCriteriaForQuery(QueryVO vo, QueryWrapper<Entity> criteria);

    private DTO newInstanceDTO() {
        Class<DTO> entityClass = (Class<DTO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    private Entity newInstanceEntity() {
        Class<Entity> entityClass = (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[3];
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    private SaveVO newInstanceVO() {
        Class<SaveVO> entityClass = (Class<SaveVO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    //vo转entity
    protected Entity convert2Entity(SaveVO vo) {
        try {
            Entity entity = newInstanceEntity();
            BeanUtils.copyProperties(vo, entity);
            return entity;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    //entity转vo
    protected SaveVO convert2SaveVO(Entity entity) {
        try {
            SaveVO saveVO = newInstanceVO();
            BeanUtils.copyProperties(entity, saveVO);
            return saveVO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    //entity转dto
    protected DTO convert2DTO(Entity entity) {
        try {
            DTO dto = newInstanceDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    //entity集合转dto集合
    protected List<DTO> convertEntity2DtoList(List<Entity> entityList) {
        List<DTO> dtoList = new ArrayList<>(entityList.size());
        for (Entity entity : entityList) {
            DTO newDto = newInstanceDTO();
            BeanUtils.copyProperties(entity, newDto);
            dtoList.add(newDto);
        }
        return dtoList;
    }

    protected List<Entity> convertVo2EntityList(List<SaveVO> dataList) {
        List<Entity> entityList = new ArrayList<>(dataList.size());
        for (SaveVO vo : dataList) {
            Entity entity = newInstanceEntity();
            BeanUtils.copyProperties(vo, entity);
            entityList.add(entity);
        }
        return entityList;
    }

    /**
     * 对象转Map集合
     *
     * @param obj 查询vo对象
     * @return 对象属性为key，属性值为value，不能添加新的key
     */
    protected Map beanToMap(QueryVO obj) {
        return beanToMap(obj, false);
    }

    /**
     * 对象转Map集合
     *
     * @param obj         查询对象
     * @param allowAddKey 返回的map对象是否允许添加新的key
     */
    protected Map beanToMap(Object obj, boolean allowAddKey) {
        if (obj == null) {
            return null;
        }
        Map beanMap = new BeanMap(obj);
        return allowAddKey ? new HashMap() {{
            putAll(beanMap);
        }} : beanMap;
    }
}