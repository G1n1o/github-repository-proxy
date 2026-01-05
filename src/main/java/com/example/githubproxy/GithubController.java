package com.example.githubproxy;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private final GithubService service;

    public GithubController(final GithubService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public List<RepositoryResponse> getRepositories(@PathVariable final String username) {
        return service.getRepositories(username);
    }
}
