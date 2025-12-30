package com.example.Github_task;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GithubClient {

    private final RestClient restClient;

    public GithubClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.github.com")
                .build();
    }

    public List<RepositoryResponse> getRepositories(String username) {
        try {
            return restClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException.NotFound e) {
            throw new GithubUserNotFoundException(username);
        }
    }

    public List<BranchResponse> getBranches(String username, String repoName) {
        return restClient.get()
                .uri("/repos/{username}/{repo}/branches", username, repoName)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}

class GithubUserNotFoundException extends RuntimeException {
    GithubUserNotFoundException(String username) {
        super("User " + username + " not found");
    }
}
