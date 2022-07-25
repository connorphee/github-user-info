package com.connorphee.githubuserinfo.controller;

import com.connorphee.githubuserinfo.model.UserInfo;
import com.connorphee.githubuserinfo.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/user-info")
public class UserInfoControllerV1 {

    @Autowired
    private UserInfoService service;

    Logger logger = LoggerFactory.getLogger(UserInfoControllerV1.class);

    @GetMapping("{username}")
    @Cacheable("user-info")
    public UserInfo getUserInfo(@PathVariable String username) {
        logger.info("Request received for {}", username);
        return service.get(username);
    }
}
