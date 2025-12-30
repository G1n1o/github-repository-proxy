package com.example.Github_task;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "name", "commit" })
public class BranchResponse {

    private String name;
    private Commit commit;

    public BranchResponse() {
    }

    public String getName() {
        return name;
    }
    public String getCommit() {
        return commit.getSha();
    }

    public static class Commit {
        private String sha;
        public String getSha() {
            return sha;
        }
    }
}

