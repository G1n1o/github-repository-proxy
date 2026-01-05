package com.example.githubproxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "lastCommitSha"})
public record BranchResponse(
        String name,

        @JsonProperty(value = "commit", access = JsonProperty.Access.WRITE_ONLY)
        Commit commit
) {

    public String getLastCommitSha() {
        return commit.sha();
    }

    public record Commit(String sha) {}
}
