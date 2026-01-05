package com.example.githubproxy;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"repositoryName", "ownerLogin", "branches"})
public record RepositoryResponse(

        @JsonAlias("name")
        String repositoryName,

        @JsonProperty(value = "owner", access = JsonProperty.Access.WRITE_ONLY)
        Owner owner,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        boolean fork,

        List<BranchResponse> branches
) {

    public String getOwnerLogin() {
        return owner.login();
    }

    public record Owner(String login) {}
}