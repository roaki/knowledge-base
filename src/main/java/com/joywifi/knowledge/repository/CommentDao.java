package com.joywifi.knowledge.repository;

import org.springframework.stereotype.Repository;

import com.joywifi.knowledge.entity.Comment;

@Repository
public interface CommentDao extends BaseDao<Comment, String> {
}
