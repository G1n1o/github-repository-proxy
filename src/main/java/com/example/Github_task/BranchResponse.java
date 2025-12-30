package com.example.Github_task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"name", "lastCommitSha"})
public class BranchResponse {

    private String name;
    @JsonProperty(value = "commit", access = JsonProperty.Access.WRITE_ONLY)
    private Commit commit;

    public BranchResponse() {
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return commit.getSha();
    }

    public static class Commit {
        private String sha;

        public String getSha() {
            return sha;
        }
    }
}

