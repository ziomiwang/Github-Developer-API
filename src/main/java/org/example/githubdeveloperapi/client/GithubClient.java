package org.example.githubdeveloperapi.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubClient {

    RestClient restClient = RestClient.create();

    @Value("${user-client.url}")
    private String baseUrl;

    public List<GithubRepositoryDTO> fetchUserRepos(final String username) {
        String url = baseUrl + "/users/{username}/repos";
        log.info("url {}", url);

        List<GithubRepositoryDTO> repositories = restClient.get()
                .uri(baseUrl + "/users/{username}/repos", username)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        log.info("response, {}", repositories);

        return repositories;
    }

    public List<BranchDTO> fetchRepoBranches(final String branchesUrl) {
        List<BranchDTO> branches = restClient.get()
                .uri(branchesUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        log.info("branches, {}", branches);

        return branches;
    }

}
