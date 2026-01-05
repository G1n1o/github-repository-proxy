package com.example.githubproxy;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GithubClient {

    private final RestClient restClient;

    public GithubClient( @Value("${github.api.url}") final String baseUrl,
                        final RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public List<RepositoryResponse> getRepositories(final String username) {

        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (_, _) -> { throw new GithubUserNotFoundException(username); }
                )
                .body(new ParameterizedTypeReference<List<RepositoryResponse>>() {});
    }

    public List<BranchResponse> getBranches(final String username, final String repoName) {
        return restClient.get()
                .uri("/repos/{username}/{repo}/branches", username, repoName)
                .retrieve()
                .body(new ParameterizedTypeReference<List<BranchResponse>>() {});
    }
}

class GithubUserNotFoundException extends RuntimeException {
    GithubUserNotFoundException(final String username) {
        super("User " + username + " not found");
    }
}
