package com.example.Github_task;

import java.util.List;

public class RepositoryResponse {

    private String repositoryName;
    private String ownerLogin;
    private List<BranchResponse> branches;

    public RepositoryResponse() {}

    public RepositoryResponse(String repositoryName, String ownerLogin, List<BranchResponse> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchResponse> getBranches() {
        return branches;
    }
}
