package com.example.Github_task;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubControllerIT {

    static WireMockServer wireMockServer;

    @Autowired
    private GithubService githubService;

    @LocalServerPort
    int port;

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
    void shouldReturnRepositoriesViaHttpEndpoint() {

        RestTestClient client = RestTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        stubReposWithForkAndNonFork();
        stubBranches();

        client.get()
                .uri("/github/testuser")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].repositoryName").isEqualTo("repo1")
                .jsonPath("$[0].ownerLogin").isEqualTo("testuser")
                .jsonPath("$[0].branches[0].lastCommitSha").isEqualTo("abc123");
    }

    @Test
    void shouldReturnRepositoriesWithBranchesAndIgnoreForks() {

        stubReposWithForkAndNonFork();
        stubBranches();

        List<RepositoryResponse> response = githubService.getRepositories("testuser");

        assertThat(response).hasSize(1);

        RepositoryResponse repo = response.getFirst();

        assertThat(repo.repositoryName()).isEqualTo("repo1");
        assertThat(repo.branches()).hasSize(1);
    }

    @Test
    void shouldReturn404WhenUserNotFound() {

        stubFor(get(urlEqualTo("/users/unknown/repos"))
                .willReturn(aResponse().withStatus(404)));


        try {
            githubService.getRepositories("unknown");
        } catch (GithubUserNotFoundException e) {
            assertThat(e.getMessage()).contains("unknown");
        }
    }


    // ---------- STUBS ----------
    private void stubReposWithForkAndNonFork() {
        stubFor(get(urlEqualTo("/users/testuser/repos"))
                .willReturn(okJson("""
                    [
                      { "name": "repo1", "fork": false, "owner": { "login": "testuser" } },
                      { "name": "repo2", "fork": true,  "owner": { "login": "testuser" } }
                    ]
                """)));
    }

    private void stubBranches() {
        stubFor(get(urlEqualTo("/repos/testuser/repo1/branches"))
                .willReturn(okJson("""
                    [
                      { "name": "main", "commit": { "sha": "abc123" } }
                    ]
                """)));
    }
}
