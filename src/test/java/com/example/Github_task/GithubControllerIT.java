package com.example.Github_task;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubControllerIT {

    static WireMockServer wireMockServer;

    @Autowired
    private GithubService githubService;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(0);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("github.api.url",
                () -> "http://localhost:" + wireMockServer.port());
    }

    @BeforeEach
    void resetWireMock() {
        wireMockServer.resetAll();
    }

    @Test
    void shouldReturnRepositoriesWithBranches() {

        stubFor(get(urlEqualTo("/users/testuser/repos"))
                .willReturn(okJson("""
                    [
                      { "name": "repo1", "fork": false, "owner": { "login": "testuser" } }
                    ]
                """)));


        stubFor(get(urlEqualTo("/repos/testuser/repo1/branches"))
                .willReturn(okJson("""
                    [
                      { "name": "main", "commit": { "sha": "abc123" } }
                    ]
                """)));

        List<RepositoryResponse> response = githubService.getRepositories("testuser");

        assertThat(response).hasSize(1);

        RepositoryResponse repo = response.getFirst();
        BranchResponse branch = repo.branches().getFirst();

        assertThat(repo.repositoryName()).isEqualTo("repo1");
        assertThat(repo.getOwnerLogin()).isEqualTo("testuser");
        assertThat(repo.branches()).hasSize(1);
        assertThat(branch.name()).isEqualTo("main");
        assertThat(branch.getLastCommitSha()).isEqualTo("abc123");
    }

    @Test
    void shouldReturn404WhenUserNotFound() {

        stubFor(get(urlEqualTo("/users/unknown/repos"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("""
                            { "message": "Not Found" }
                        """)));


        try {
            githubService.getRepositories("unknown");
        } catch (GithubUserNotFoundException e) {
            assertThat(e.getMessage()).contains("unknown");
        }
    }
}
