package com.tudog.graphqldemo01.service;

import java.util.Optional;

import com.tudog.graphqldemo01.entity.User;
import com.tudog.graphqldemo01.repository.UserRepository;
import com.tudog.graphqldemo01.tools.BaseService;
import com.tudog.graphqldemo01.tools.HttpUserTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends BaseService<User,Long>{
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByAccount(String account){
        return userRepository.findByAccount(account);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return userRepository.findByAccount(HttpUserTool.getName());
    }
}
