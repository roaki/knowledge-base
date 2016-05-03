package com.joywifi.knowledge.repository;

import org.springframework.stereotype.Repository;

import com.joywifi.knowledge.entity.Blog;

@Repository
public interface BlogDao extends BaseDao<Blog, String> {
}
