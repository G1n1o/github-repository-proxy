package com.example.Github_task;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    private final GithubClient client;

    public GithubService(GithubClient client) {
        this.client = client;
    }

    public List<RepositoryResponse> getRepositories(String username) {

        return client.fetchRepositories(username)
                .stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> {
                    List<BranchResponse> branches = client.fetchBranches(
                            repo.getOwner().getLogin(),
                            repo.getName()
                    );

                    return new RepositoryResponse(
                            repo.getName(),
                            repo.getOwner().getLogin(),
                            branches
                    );
                })
                .collect(Collectors.toList());
    }
}
