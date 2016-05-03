package com.joywifi.knowledge.repository;

import org.springframework.stereotype.Repository;

import com.joywifi.knowledge.entity.Tag;

@Repository
public interface TagDao extends BaseDao<Tag, String> {
}
