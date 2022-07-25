package com.connorphee.githubuserinfo.client;

import com.connorphee.githubuserinfo.model.User;
import com.connorphee.githubuserinfo.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class HttpGitHubClient implements GitHubClient {

    private final Logger logger = LoggerFactory.getLogger(HttpGitHubClient.class);
    @Value("${github-url}")
    private String githubUrl;

    @Value("${github-user-info-path}")
    private String githubUserInfoPath;

    @Value("${github-user-repos-path}")
    private String githubUserReposPath;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public User getUserInfo(String username) {
        String userInfoUrl = String.format(githubUrl + githubUserInfoPath, username);
        try {
            logger.info("Making HTTP request to: {}", userInfoUrl);
            return restTemplate.getForObject(userInfoUrl, User.class);
        } catch (HttpClientErrorException e) {
            logger.error("Error with HTTP request. Error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        } catch (HttpServerErrorException e) {
            logger.error("Error with HTTP request. Error: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @Override
    public Repository[] getUserRepositories(String username) {
        String reposUrl = String.format(githubUrl + githubUserReposPath, username);
        try {
            logger.info("Making HTTP request to: {}", reposUrl);
            return restTemplate.getForEntity(reposUrl, Repository[].class).getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Error occurred with HTTP request. Error: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        } catch (HttpServerErrorException e) {
            logger.error("Error occurred with HTTP request. Error: {}", e.getMessage(), e);
            return new Repository[]{};
        }
    }
}
