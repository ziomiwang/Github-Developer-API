package org.example.githubdeveloperapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.apache.commons.io.IOUtils;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.example.githubdeveloperapi.web.GithubController;
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

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {GithubController.class, GithubService.class})
@EnableWireMock({
        @ConfigureWireMock(name = "github-client", property = "github-client.url")
})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@WebFluxTest(controllers = GithubController.class)
@Import(GithubTestConfiguration.class)
public class GithubFetchIntegrationTestWithWebClient {

    private final static String USERNAME = "ziomiwang";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ApplicationContext context;

    @InjectWireMock("github-client")
    private WireMockServer wiremock;

    @Value("${github-client.url}")
    private String wiremockUrl;

    @Test
    public void integrationTest() throws Exception {
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
                .containsOnly("ziomiwang");

    }

    private String getDataFromFile(final String filename) throws IOException {
        try (var in = getClass().getClassLoader().getResourceAsStream(filename)) {
            assert in != null;
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        }
    }
}
