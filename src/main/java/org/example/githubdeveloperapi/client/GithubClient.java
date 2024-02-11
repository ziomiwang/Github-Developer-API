package org.example.githubdeveloperapi.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubClient {

    RestClient restClient = RestClient.create();

    final String uriBase = "https://api.github.com/users/";
    final String uriEnd = "/repos";

    public List<GithubRepositoryDTO> fetchUserRepos(final String username) {
        List<GithubRepositoryDTO> repositories = restClient.get()
                .uri(uriBase + username + uriEnd)
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
