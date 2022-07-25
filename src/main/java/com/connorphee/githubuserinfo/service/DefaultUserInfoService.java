package com.connorphee.githubuserinfo.service;

import com.connorphee.githubuserinfo.client.GitHubClient;
import com.connorphee.githubuserinfo.converter.DateConverter;
import com.connorphee.githubuserinfo.model.Repository;
import com.connorphee.githubuserinfo.model.User;
import com.connorphee.githubuserinfo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserInfoService implements UserInfoService {

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private DateConverter dateConverter;

    public UserInfo get(String username) {
        User user = gitHubClient.getUserInfo(username);
        Repository[] userRepos = gitHubClient.getUserRepositories(username);

        return UserInfo.builder()
                .avatar(user.avatar)
                .username(user.login)
                .displayName(user.name)
                .email(user.email)
                .geolocation(user.location)
                .createdAt(dateConverter.fromISO8601(user.createdAt))
                .repositories(userRepos)
                .url(user.url)
                .build();
    }
}
