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

    public List<GithubRepositoryDTO> fetchUserRepos() {
        List<GithubRepositoryDTO> repositories = restClient.get()
                .uri("https://api.github.com/users/ziomiwang/repos")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        log.info("response, {}", repositories);

        return repositories;
    }

    public List<BranchDTO> fetchRepoBranches(String branchesUrl) {
        List<BranchDTO> branches = restClient.get()
                .uri(branchesUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        log.info("branches, {}", branches);

        return branches;
    }

}
