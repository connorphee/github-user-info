package com.connorphee.githubuserinfo.service;

import com.connorphee.githubuserinfo.model.UserInfo;

public interface UserInfoService {
    UserInfo get(String username);
}
