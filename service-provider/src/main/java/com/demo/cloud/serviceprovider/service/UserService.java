package com.demo.cloud.serviceprovider.service;

import com.demo.cloud.serviceprovider.entity.User;
import com.demo.cloud.serviceprovider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserMapper userMapper;

    public User queryUserById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

}
