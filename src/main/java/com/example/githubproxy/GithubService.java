package com.example.githubproxy;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {

    private final GithubClient client;

    public GithubService(final GithubClient client) {
        this.client = client;
    }

    public List<RepositoryResponse> getRepositories(final String username) {

        return client.getRepositories(username).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> new RepositoryResponse(
                        repo.repositoryName(),
                        repo.owner(),
                        false,
                        client.getBranches(username, repo.repositoryName())
                ))
                .toList();
    }

}
