package com.example.Github_task;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class GithubClient {

    private final RestClient restClient;

    public GithubClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.github.com")
                .build();
    }

    public List<GithubRepository> fetchRepositories(String username) {
        return Arrays.asList(
                restClient.get()
                        .uri("/users/{username}/repos", username)
                        .retrieve()
                        .body(GithubRepository[].class)
        );
    }

    public List<BranchResponse> fetchBranches(String owner, String repoName) {
        return Arrays.asList(
                restClient.get()
                        .uri("/repos/{owner}/{repo}/branches", owner, repoName)
                        .retrieve()
                        .body(BranchResponse[].class)
        );
    }
}
