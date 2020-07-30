package com.zhongruan.service.impl;

import com.zhongruan.entity.User;
import com.zhongruan.repository.UserRepository;
import com.zhongruan.service.UserService;
import com.zhongruan.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        System.out.println(user);
        return user;
    }
}
