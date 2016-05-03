package com.joywifi.knowledge.service;

import java.io.Serializable;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springside.modules.persistence.SearchFilter;

import com.google.common.collect.Maps;
import com.joywifi.knowledge.entity.BaseEntity;
import com.joywifi.knowledge.plugin.entity.LogicDeleteable;

public class LogicDeleteBaseService<M extends BaseEntity, ID extends Serializable> extends BaseService<M, ID> {

    @Override
    public void delete(M entity) {
        LogicDeleteable logicDeleteable = (LogicDeleteable) entity;
        if (logicDeleteable != null) {
            logicDeleteable.markDeleted();
            save(entity);
        }
    }

    @Override
    public void delete(Collection<M> entitys) {
        for (M entity : entitys) {
            delete(entity);
        }
    }

    @Override
    public void delete(ID id) {
        M m = get(id);
        delete(m);
    }

    @Override
    public void delete(ID[] ids) {
        for (ID id : ids) {
            delete(id);
        }
    }

    @Override
    public M get(ID id) {
        M m = baseDao.findOne(id);
        LogicDeleteable logicDeleteable = (LogicDeleteable) m;
        if (m != null && logicDeleteable.getDeleted()) {
            return null;
        }
        return m;
    }

    @Override
    public List<M> get(Collection<ID> ids) {
        Iterable<M> elements = baseDao.findAll(ids);
        List<M> models = new ArrayList<>();
        for (M m : elements) {
            if (!((LogicDeleteable) m).getDeleted()) {
                models.add(m);
            }
        }
        return models;
    }

    @Override
    public Page<M> findPage(Map<String, SearchFilter> filters, Pageable pageable) {
        Query query = buildQuery(filters);
        addDeletedCriteria(query);
        long count = mongoTemplate.count(query, entityClass);
        query.with(pageable);
        query.with(new Sort(Sort.Direction.DESC, "modifiedTime"));
        return new PageImpl(mongoTemplate.find(query, entityClass), pageable, count);
    }

    @Override
    public List<M> findBy(Map<String, SearchFilter> filters) {
        Query query = buildQuery(filters);
        addDeletedCriteria(query);
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public Page<M> findPage(Map<String, SearchFilter> filters, Pageable pageable, Sort sort) {
        Query query = buildQuery(filters);
        addDeletedCriteria(query);
        long count = mongoTemplate.count(query, entityClass);
        query.with(pageable);
        query.with(sort);
        return new PageImpl(mongoTemplate.find(query, entityClass), pageable, count);
    }

    @Override
    public List<M> findBy(String key, SearchFilter.Operator operator, Object value) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        SearchFilter sf = new SearchFilter(key, operator, value);
        filters.put(key, sf);
        Query query = buildQuery(filters);
        addDeletedCriteria(query);
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public M findOne(String key, SearchFilter.Operator operator, Object value) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        SearchFilter sf = new SearchFilter(key, operator, value);
        filters.put(key, sf);
        Query query = buildQuery(filters);
        addDeletedCriteria(query);
        query.limit(1);
        return mongoTemplate.findOne(query, entityClass);
    }

    @Override
    public Page<M> findAll(Pageable pageable) {
        return findPage(new HashMap<String, SearchFilter>(), pageable);
    }

    @Override
    public List<M> findAll() {
        Query query = new Query();
        addDeletedCriteria(query);
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public Long count() {
        Query query = new Query();
        addDeletedCriteria(query);
        return mongoTemplate.count(query, entityClass);
    }

    @Override
    public boolean exist(ID id) {
        M m = baseDao.findOne(id);
        LogicDeleteable logicDeleteable = (LogicDeleteable) m;
        return !(m != null && logicDeleteable.getDeleted()) && m != null;
    }

    private void addDeletedCriteria(Query query) {
        query.addCriteria(Criteria.where("deleted").is(false));
    }
}
