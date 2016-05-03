package com.joywifi.knowledge.repository;

import com.joywifi.knowledge.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User, String> {
}
