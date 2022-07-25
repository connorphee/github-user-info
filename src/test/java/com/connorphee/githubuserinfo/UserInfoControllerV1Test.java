package com.connorphee.githubuserinfo;

import com.connorphee.githubuserinfo.model.Repository;
import com.connorphee.githubuserinfo.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoControllerV1Test {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    private final String USERS_TEST_URL = "https://api.github.com/users/octocat";
    private final String REPOS_TEST_URL = "https://api.github.com/users/octocat/repos";
    private final String TEST_TARGET_URL = "http://localhost:%d/v1/user-info/octocat";

    @Test
    public void returnsExpectedResponse() {
        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).bufferContent().build();

        mockServer.expect(ExpectedCount.once(), requestTo(USERS_TEST_URL)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(userResponse(), MediaType.APPLICATION_JSON));

        mockServer.expect(ExpectedCount.once(), requestTo(REPOS_TEST_URL)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(reposResponse(), MediaType.APPLICATION_JSON));

        assertThat(new RestTemplate().getForObject(String.format(TEST_TARGET_URL, port), UserInfo.class)).isEqualTo(expectedUserInfo());
    }

    @Test
    public void returnsErrorWhenUserNotFound() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo(USERS_TEST_URL)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.NOT_FOUND));

        try {
            new RestTemplate().getForObject(String.format(TEST_TARGET_URL, port), UserInfo.class);
            fail("Exception should be thrown when user not found");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void returnsEmptyRepoListWhenNoReposFound() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        mockServer.expect(requestTo(USERS_TEST_URL)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK).body(userResponse()).headers(headers));

        mockServer.expect(requestTo(REPOS_TEST_URL)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK).body("[]").headers(headers));

        assertThat(new RestTemplate().getForObject(String.format(TEST_TARGET_URL, port), UserInfo.class)).isEqualTo(expectedUserInfoNoRepos());
    }

    @Test
    public void returnsEmptyRepoListWhenRepoRequestFails() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        mockServer.expect(requestTo(USERS_TEST_URL)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK).body(userResponse()).headers(headers));

        mockServer.expect(requestTo(REPOS_TEST_URL)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThat(new RestTemplate().getForObject(String.format(TEST_TARGET_URL, port), UserInfo.class)).isEqualTo(expectedUserInfoNoRepos());
    }

    @Test
    public void returnsErrorWhenUserRequestFails() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo(USERS_TEST_URL)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        try {
            new RestTemplate().getForObject(String.format(TEST_TARGET_URL, port), UserInfo.class);
            fail("Should throw exception when user request fails");
        } catch (HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String userResponse() {
        return "{\n" + "  \"login\": \"octocat\",\n" + "  \"avatar_url\": \"https://avatars.githubusercontent.com/u/583231?v=4\",\n" + "  \"html_url\": \"https://github.com/octocat\",\n" + "  \"name\": \"The Octocat\",\n" + "  \"location\": \"San Francisco\",\n" + "  \"email\": null,\n" + "  \"created_at\": \"2011-01-25T18:44:36Z\"\n" + "}";
    }

    private String reposResponse() {
        return "[{\"name\": \"boysenberry-repo-1\", \"html_url\": \"https://github.com/octocat/boysenberry-repo-1\"}]";
    }

    private UserInfo expectedUserInfo() {
        Repository repo = new Repository("boysenberry-repo-1", "https://github.com/octocat/boysenberry-repo-1");

        return UserInfo.builder()
                .avatar("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt("2011-01-25 18:44:36")
                .displayName("The Octocat")
                .email(null)
                .geolocation("San Francisco")
                .repositories(new Repository[]{repo})
                .url("https://github.com/octocat")
                .username("octocat")
                .build();
    }

    private UserInfo expectedUserInfoNoRepos() {
        return UserInfo.builder()
                .avatar("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt("2011-01-25 18:44:36")
                .displayName("The Octocat")
                .email(null)
                .geolocation("San Francisco")
                .repositories(new Repository[]{})
                .url("https://github.com/octocat")
                .username("octocat")
                .build();
    }
}
