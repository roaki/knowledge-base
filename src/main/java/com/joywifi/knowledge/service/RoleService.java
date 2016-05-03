package com.joywifi.knowledge.service;

import com.joywifi.knowledge.entity.Role;
import com.joywifi.knowledge.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, String> {

    @Autowired
    private RoleDao roleDao;
}
