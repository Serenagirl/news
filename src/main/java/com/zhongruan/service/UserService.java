package com.zhongruan.service;

import com.zhongruan.entity.User;

public interface UserService {
    User checkUser(String username, String password);
}
