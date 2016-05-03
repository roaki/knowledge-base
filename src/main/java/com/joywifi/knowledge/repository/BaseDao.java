package com.joywifi.knowledge.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.joywifi.knowledge.entity.BaseEntity;

@NoRepositoryBean
public interface BaseDao<M extends BaseEntity, ID extends Serializable> extends MongoRepository<M, ID> {
}
