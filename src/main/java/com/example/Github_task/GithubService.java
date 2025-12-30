package com.example.Github_task;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

    private final GithubClient client;

    public GithubService(GithubClient client) {
        this.client = client;
    }

    public List<RepositoryResponse> getRepositories(String username) {

        List<RepositoryResponse> repositories = client.getRepositories(username);
        List<RepositoryResponse> result = new ArrayList<>();

        for (RepositoryResponse repo : repositories) {
            if (!repo.isFork()) {
                repo.setBranches(
                        client.getBranches(username, repo.getRepositoryName())
                );
                result.add(repo);
            }
        }

        return result;
    }
}
