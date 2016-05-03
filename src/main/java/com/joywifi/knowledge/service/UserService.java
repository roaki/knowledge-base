package com.joywifi.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.joywifi.knowledge.entity.User;
import com.joywifi.knowledge.enums.UserStatusEnum;
import com.joywifi.knowledge.repository.UserDao;
import com.joywifi.knowledge.util.Constants;

@Service
public class UserService extends LogicDeleteBaseService<User, String> {

    @Autowired
    private UserDao userDao;

    public void createUser(User user) {
        user.setStatus(UserStatusEnum.enable);

        entryptPassword(user);
        super.save(user);
    }

    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, Constants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

}
