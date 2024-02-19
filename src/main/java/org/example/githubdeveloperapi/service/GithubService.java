package org.example.githubdeveloperapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.githubdeveloperapi.client.GithubClientHandler;
import org.example.githubdeveloperapi.client.mapper.GithubMapper;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClientHandler githubClient;
    private final GithubMapper githubMapper;

    public List<GithubRepository> fetchUserRepos(final String username) {
        List<GithubRepositoryDTO> githubRepositoryDTOS = githubClient.fetchUserRepos(username);

        return githubRepositoryDTOS.stream().map(repositoryDTO -> {
                    String branchesUrl = repositoryDTO.branchesUrl().replace("{/branch}", "");
                    List<BranchDTO> branchDTOS = githubClient.fetchRepoBranches(branchesUrl);
                    return githubMapper.map(repositoryDTO, branchDTOS);
                })
                .toList();
    }
}
