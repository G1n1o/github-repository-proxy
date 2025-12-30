package com.example.Github_task;

public class GithubRepository {

    private String name;
    private Owner owner;
    private boolean fork;

    public GithubRepository() {}

    public String getName() {
        return name;
    }
    public Owner getOwner() {
        return owner;
    }
    public boolean isFork() {
        return fork;
    }


    public static class Owner {
        private String login;
        public String getLogin() {
            return login;
        }
    }
}
