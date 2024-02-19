package org.example.githubdeveloperapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.apache.commons.io.IOUtils;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(name = "github-client", property = "github-client.url")
})
@ExtendWith(SpringExtension.class)
public class GithubFetchIntegrationTest {

    private final static String USERNAME = "ziomiwang";

    @Autowired
    private TestRestTemplate testRestTemplate;

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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<List<GithubRepository>> response = testRestTemplate.exchange("/repos?username=" + USERNAME, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {
        });


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<GithubRepository> body = response.getBody();

        assertThat(body).extracting(GithubRepository::ownerLogin)
                .containsOnly("ziomiwang");

        assertThat(body).extracting(GithubRepository::branches).extracting(data -> {
                    assertThat(data).hasSize(2);
                    return data;
                })
                .hasSize(5);

    }

    private String getDataFromFile(final String filename) throws IOException {
        try (var in = getClass().getClassLoader().getResourceAsStream(filename)) {
            assert in != null;
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        }
    }
}
