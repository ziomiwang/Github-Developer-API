package org.example.githubdeveloperapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.githubdeveloperapi.client.exception.GithubException;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.exception.GithubWebException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubClient {

    RestClient restClient = RestClient.create();

    @Value("${github-client.url}")
    private String baseUrl;

    public List<GithubRepositoryDTO> fetchUserRepos(final String username) {
        String url = baseUrl + "/users/{username}/repos";
        log.info("url {}", url);

        List<GithubRepositoryDTO> repositories = new ArrayList<>();
        try {
            repositories = restClient.get()
                    .uri(baseUrl + "/users/{username}/repos", username)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.info("response, {}", repositories);

        } catch (HttpClientErrorException ex) {
            exceptionHandling(ex);
        }

        return repositories;
    }

    public List<BranchDTO> fetchRepoBranches(final String branchesUrl) {
        List<BranchDTO> branches = new ArrayList<>();
        try {
            branches = restClient.get()
                    .uri(branchesUrl)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.info("branches, {}", branches);
        } catch (HttpClientErrorException ex) {
            exceptionHandling(ex);
        }

        return branches;
    }

    private void exceptionHandling(HttpClientErrorException ex) {
        Optional<GithubException> githubException = mapGithubException(ex);
        if (githubException.isEmpty()) {
            log.error("Error while executing client call", ex);
            throw new GithubWebException(ex.getStatusCode().value());
        }
        log.warn("Error while executing client call, parsed exception: {}", githubException);
        throw new GithubWebException(githubException.get(), ex.getStatusCode().value());
    }

    private Optional<GithubException> mapGithubException(HttpClientErrorException ex) {
        try {
            return Optional.of(new ObjectMapper().readValue(ex.getResponseBodyAsByteArray(), GithubException.class));
        } catch (IOException e) {
            log.warn("Cannot serialize github exception", e);
            return Optional.empty();
        }
    }
}
