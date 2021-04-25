
package com.poetry.admin.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.poetry.admin.exception.BusinessException;
import com.poetry.admin.exception.SystemException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Service基类实现
 *
 * @author NEGI
 * @version 2018-11
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<SaveVO extends BaseVO, QueryVO extends PaginationVO, DTO extends BaseDTO, Entity extends LongIdEntity, Criteria> implements BaseService<SaveVO, QueryVO, DTO> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private enum Operation {
        INSERT,
        UPDATE,
        SAVE
    }

    @Autowired
    private BaseDAO<Entity, Criteria> baseDAO;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public long insert(SaveVO vo) {
        Entity entity = covert2Entity(vo);
//        entity.setCreatorId(entity.getOperId());
        baseDAO.insertSelective(entity);
        return entity.getId();
    }

    @Override
    public long update(SaveVO vo) {
        Entity entity = covert2Entity(vo);
        if (entity.getId() == null) {
            throw new BusinessException("id不能为空");
        }
//        entity.setOperTime(new Date());
        baseDAO.updateByPrimaryKey(entity);
        return entity.getId();
    }

    @Override
    public long updateSelective(SaveVO vo) {
        Entity entity = covert2Entity(vo);
        if (entity.getId() == null) {
            throw new BusinessException("id不能为空");
        }
//        entity.setCreatorId(null);
//        entity.setCreateTime(null);
//        entity.setOperTime(new Date());
        baseDAO.updateByPrimaryKeySelective(entity);
        return entity.getId();
    }

    @Override
    public DTO queryById(long id) {
        Entity entity = baseDAO.selectByPrimaryKey(id);
        return entity == null ? null : covert2DTO(entity);
    }

    @Override
    public void deleteById(long id) {
        baseDAO.deleteByPrimaryKey(id);
    }

    @Override
    public void insertBatch(List<SaveVO> dataList) {
        batch(dataList, Operation.INSERT);
    }

    @Override
    public void updateBatch(List<SaveVO> dataList) {
        batch(dataList, Operation.UPDATE);
    }

    @Override
    public void saveBatch(List<SaveVO> dataList) {
        batch(dataList, Operation.SAVE);
    }

    @Override
    public List<DTO> queryAll() {
        List<Entity> entityList = baseDAO.selectByCriteria(null);
        List<DTO> dtoList = new ArrayList<>();
        for (Entity entity : entityList) {
//            if(entity.getValidity()){
                DTO newDto = newInstanceDTO();
                BeanUtils.copyProperties(entity, newDto);
                dtoList.add(newDto);
//            }
        }
        return dtoList;
    }

    private Criteria getCriteria(){
        Criteria criteria;
        try {
            criteria = newInstanceCriteria();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
        return criteria;
    }

    private List<DTO> queryList(Criteria criteria){
        List<Entity> entityList = baseDAO.selectByCriteria(criteria);
        List<DTO> dtoList = new ArrayList<>(entityList.size());
        for (Entity entity : entityList) {
            DTO newDto = newInstanceDTO();
            BeanUtils.copyProperties(entity, newDto);
            dtoList.add(newDto);
        }
        return dtoList;
    }

    @Override
    public List<DTO> queryList(QueryVO vo) {
        Criteria criteria = getCriteria();
        setCriteriaForQuery(vo, criteria);
        return queryList(criteria);
    }

    @Override
    public long count(QueryVO vo) {
        Criteria criteria = getCriteria();
        setCriteriaForQuery(vo, criteria);
        return baseDAO.countByCriteria(criteria);
    }

    @Override
    public Pagination<DTO> queryPagination(QueryVO vo) {
        Criteria criteria = getCriteria();
        setCriteriaForQuery(vo, criteria);
        Page page = PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
        List<DTO> dtoList = queryList(criteria);
//        long total = baseDAO.countByCriteria(criteria);
        return new Pagination<>(dtoList, page.getTotal(), vo.getPageSize());
    }

    //根据dto设置查询条件criteria
    protected abstract void setCriteriaForQuery(QueryVO vo, Criteria criteria);

    private void batch(List<SaveVO> dataList, Operation operation) {
        boolean realBatchProcess = dataList.size() > 100;
        if(realBatchProcess){
            batchProcess(dataList,operation);
        }else{
            for(SaveVO vo : dataList){
                Entity entity = newInstanceEntity();
                BeanUtils.copyProperties(vo, entity);
                if (Operation.INSERT.equals(operation)) {
                    this.baseDAO.insertSelective(entity);
                } else if (Operation.UPDATE.equals(operation)) {
                    this.baseDAO.updateByPrimaryKeySelective(entity);
                } else {
                    if (entity.getId() == null) {
                        this.baseDAO.insertSelective(entity);
                    } else {
                        this.baseDAO.updateByPrimaryKeySelective(entity);
                    }
                }
            }
        }
    }

    private void batchProcess(List<SaveVO> dataList, Operation operation) {
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);// 第二个参数表示取消自动提交
        BaseDAO baseDAO = session.getMapper(BaseDAO.class);

        Entity entity;
        int size = dataList.size();
        try {
            for (int i = 0; i < size; i++) {
                entity = newInstanceEntity();
                BeanUtils.copyProperties(dataList.get(i), entity);
                if (Operation.INSERT.equals(operation)) {
                    baseDAO.insertSelective(entity);
                } else if (Operation.UPDATE.equals(operation)) {
                    baseDAO.updateByPrimaryKeySelective(entity);
                } else {
                    if (entity.getId() == null) {
                        baseDAO.insertSelective(entity);
                    } else {
                        baseDAO.updateByPrimaryKeySelective(entity);
                    }
                }
                if (i % 1000 == 0 || i == size - 1) {
                    session.commit();
                    session.clearCache();
                }
            }
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
    }

    private DTO newInstanceDTO() {
        Class<DTO> entityClass = (Class<DTO>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    private Entity newInstanceEntity() {
        Class<Entity> entityClass = (Class<Entity>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[3];
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    private Criteria newInstanceCriteria() {
        Class<Criteria> entityClass = (Class<Criteria>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[4];
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    //vo转entity
    private Entity covert2Entity(SaveVO vo) {
        try {
            Entity entity = newInstanceEntity();
            BeanUtils.copyProperties(vo, entity);
            return entity;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }

    //entity转dto
    private DTO covert2DTO(Entity entity) {
        try {
            DTO dto = newInstanceDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(e);
        }
    }
}