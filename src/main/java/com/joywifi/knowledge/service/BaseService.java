package com.joywifi.knowledge.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.utils.Reflections;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.joywifi.knowledge.entity.BaseEntity;
import com.joywifi.knowledge.repository.BaseDao;

public abstract class BaseService<M extends BaseEntity, ID extends Serializable> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected BaseDao<M, ID> baseDao;

    protected final Class<M> entityClass;

    protected BaseService() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    public M get(ID id) {
        return baseDao.findOne(id);
    }

    public List<M> get(Collection<ID> ids) {
        Iterable<M> elements = baseDao.findAll(ids);
        List<M> models = new ArrayList<>();
        Iterators.addAll(models, elements.iterator());
        return models;
    }

    public Page<M> findPage(Map<String, SearchFilter> filters, Pageable pageable) {
        Query query = buildQuery(filters);
        long count = mongoTemplate.count(query, entityClass);
        query.with(pageable);
        query.with(new Sort(Sort.Direction.DESC, "modifiedTime"));
        return new PageImpl(mongoTemplate.find(query, entityClass), pageable, count);
    }
    
    public List<M> findBy(Map<String, SearchFilter> filters) {
    	Query query = buildQuery(filters);
    	List<M> list = mongoTemplate.find(query, entityClass);
    	return list;
    }

    public Page<M> findPage(Map<String, SearchFilter> filters, Pageable pageable, Sort sort) {
        Query query = buildQuery(filters);
        long count = mongoTemplate.count(query, entityClass);
        query.with(pageable);
        query.with(sort);
        return new PageImpl(mongoTemplate.find(query, entityClass), pageable, count);
    }

    public List<M> findBy(String key, SearchFilter.Operator operator, Object value) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        SearchFilter sf = new SearchFilter(key, operator, value);
        filters.put(key, sf);
        return findBySearchFilter(filters);
    }

    public M findOne(String key, SearchFilter.Operator operator, Object value) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        filters.put(key, new SearchFilter(key, operator, value));
        Query query = buildQuery(filters);
        query.limit(1);
        return mongoTemplate.findOne(query, entityClass);
    }

    public Page<M> findAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    public List<M> findAll() {
        return baseDao.findAll();
    }

    public Long count() {
        return baseDao.count();
    }

    public boolean exist(ID id) {
        return baseDao.exists(id);
    }

    public M save(M entity) {
        return baseDao.save(entity);
    }

    public Collection<M> save(Collection<M> entites) {
        return baseDao.save(entites);
    }

    public M update(ID id, M entity) {
        M existM = get(id);
        copyNonNullProperties(entity, existM);
        baseDao.save(existM);
        return existM;
    }

    public void delete(M entity) {
        baseDao.delete(entity);
    }

    public void delete(Collection<M> entitys) {
        baseDao.delete(entitys);
    }

    public void delete(ID id) {
        baseDao.delete(id);
    }

    public void delete(ID[] ids) {
        for (ID id : ids) {
            delete(id);
        }
    }

    protected Query buildQuery(Map<String, SearchFilter> filters) {
        Query query = new Query();
        for (Map.Entry<String, SearchFilter> filter : filters.entrySet()) {
            SearchFilter sf = filter.getValue();
            String fieldName = sf.fieldName;
            Object value = sf.value;

            Criteria criteria = Criteria.where(fieldName);
            switch (sf.operator) {
                case EQ:
                    criteria.is(value);
                    break;
                case GT:
                    criteria.gt(value);
                    break;
                case LT:
                    criteria.lt(value);
                    break;
                case GTE:
                    criteria.gte(value);
                    break;
                case LTE:
                    criteria.lte(value);
                    break;
                case LIKE:
                    criteria.regex((String) value, "i");
                    break;
                default:
                    break;
            }
            query.addCriteria(criteria);
        }
        return query;
    }

    public List<M> findBySearchFilter(Map<String, SearchFilter> filters) {
        Query query = buildQuery(filters);
        return mongoTemplate.find(query, entityClass);
    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    
}
