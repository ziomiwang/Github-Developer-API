package org.example.githubdeveloperapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.apache.commons.io.IOUtils;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.example.githubdeveloperapi.web.GithubController;
import org.example.githubdeveloperapi.web.exception.WebException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@Import(GithubTestConfiguration.class)
@WebFluxTest(controllers = GithubController.class)
@EnableWireMock({
        @ConfigureWireMock(name = "github-client", property = "github-client.url")
})
public class GithubFetchIntegrationWebTest {

    private final static String USERNAME = "ziomiwang";
    private final static String USERNAME_NO_REPOS = "ziomiwantest";
    private final static String USERNAME_NOT_FOUND = "ziomiwanNotFound";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ApplicationContext context;

    @InjectWireMock("github-client")
    private WireMockServer wiremock;

    @Value("${github-client.url}")
    private String wiremockUrl;

    @Test
    public void githubClient_fetchRepositories_success() throws Exception {
        String responseMockBody = getDataFromFile("github-response.json").replaceAll("wiremock-url", wiremockUrl);
        String branchesResponseMockBody = getDataFromFile("github-branches-response.json");

        wiremock.stubFor(get("/users/" + USERNAME + "/repos")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseMockBody)));

        wiremock.stubFor(get(urlPathMatching("^/repos/[^/]+/([^/]+)/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(branchesResponseMockBody)));

        List<GithubRepository> body = webTestClient.get()
                .uri("/repos?username=" + USERNAME)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-type", MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GithubRepository.class)
                .returnResult()
                .getResponseBody();

        assertThat(body).extracting(GithubRepository::ownerLogin)
                .containsOnly(USERNAME);

        assertThat(body).extracting(GithubRepository::branches).extracting(data -> {
                    assertThat(data).hasSize(2);
                    return data;
                })
                .hasSize(5);
    }

    @Test
    public void githubClient_fetchRepositories_empty() throws Exception {
        String branchesResponseMockBody = getDataFromFile("github-branches-response.json");

        wiremock.stubFor(get("/users/" + USERNAME_NO_REPOS + "/repos")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        wiremock.stubFor(get(urlPathMatching("^/repos/[^/]+/([^/]+)/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(branchesResponseMockBody)));

        List<GithubRepository> body = webTestClient.get()
                .uri("/repos?username=" + USERNAME_NO_REPOS)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-type", MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GithubRepository.class)
                .returnResult()
                .getResponseBody();

        assertThat(body)
                .isEmpty();
    }

    @Test
    public void githubClient_fetchRepositories_failure() throws Exception {
        String userNotFoundResponseMockBody = getDataFromFile("github-not-found-response.json");

        wiremock.stubFor(get("/users/" + USERNAME_NOT_FOUND + "/repos")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(userNotFoundResponseMockBody)));

        WebException responseBody = webTestClient.get()
                .uri("/repos?username=" + USERNAME_NOT_FOUND)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-type", MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(WebException.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody)
                .extracting(WebException::getStatus)
                .isEqualTo(404);
    }

    private String getDataFromFile(final String filename) throws IOException {
        try (var in = getClass().getClassLoader().getResourceAsStream(filename)) {
            assert in != null;
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        }
    }
}
