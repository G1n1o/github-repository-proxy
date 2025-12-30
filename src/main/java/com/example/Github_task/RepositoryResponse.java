package com.example.Github_task;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"repositoryName", "ownerLogin", "branches"})
public class RepositoryResponse {

    @JsonAlias("name")
    private String repositoryName;
    @JsonProperty(value = "owner", access = JsonProperty.Access.WRITE_ONLY)
    private Owner owner;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean fork;

    private List<BranchResponse> branches;

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return owner.getLogin();
    }

    public boolean isFork() {
        return fork;
    }

    public List<BranchResponse> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchResponse> branches) {
        this.branches = branches;
    }

    static class Owner {
        private String login;

        public String getLogin() {
            return login;
        }
    }
}
