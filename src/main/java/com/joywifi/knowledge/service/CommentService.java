package com.joywifi.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joywifi.knowledge.entity.Comment;
import com.joywifi.knowledge.repository.CommentDao;

@Service
public class CommentService extends BaseService<Comment, String> {

    @Autowired
    private CommentDao commentDao;
}
