package org.example.githubdeveloperapi.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.exception.GithubWebException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubClientHandler {

    private final RestClient restClient;

    @Value("${github-client.url}")
    private String baseUrl;

    public List<GithubRepositoryDTO> fetchUserRepos(final String username) {
        List<GithubRepositoryDTO> repositories;
        try {
            repositories = restClient.get()
                    .uri(baseUrl + "/users/{username}/repos", username)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.info("response, {}", repositories);

        } catch (HttpClientErrorException.NotFound ex) {
            log.warn("Error while fetching repositories for given github name, {}", username);
            throw new GithubWebException("Account not found for given username", 404, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException generalEx) {
            log.error("Error while executing client call", generalEx);
            throw new GithubWebException();
        }

        return repositories;
    }

    public List<BranchDTO> fetchRepoBranches(final String branchesUrl) {
        List<BranchDTO> branches;
        try {
            branches = restClient.get()
                    .uri(branchesUrl)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.info("branches, {}", branches);
        } catch (HttpClientErrorException.NotFound ex) {
            log.warn("Error while fetching branches for given github name");
            throw new GithubWebException("Account not found for given username", 404);
        } catch (HttpClientErrorException generalEx) {
            log.error("Error while executing client call", generalEx);
            throw new GithubWebException();
        }

        return branches;
    }
}
