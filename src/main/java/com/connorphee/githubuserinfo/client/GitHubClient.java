package com.connorphee.githubuserinfo.client;

import com.connorphee.githubuserinfo.model.User;
import com.connorphee.githubuserinfo.model.Repository;

public interface GitHubClient {

    User getUserInfo(String username);

    Repository[] getUserRepositories(String username);
}
