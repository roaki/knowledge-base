package com.joywifi.knowledge.repository;

import org.springframework.stereotype.Repository;

import com.joywifi.knowledge.entity.Category;

@Repository
public interface CategoryDao extends BaseDao<Category, String> {
}
