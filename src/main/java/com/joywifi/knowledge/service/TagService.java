package com.joywifi.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joywifi.knowledge.entity.Tag;
import com.joywifi.knowledge.repository.TagDao;

@Service
public class TagService extends BaseService<Tag, String> {

    @Autowired
    private TagDao tagDao;
}
