package org.example.githubdeveloperapi.service;

import lombok.RequiredArgsConstructor;
import org.example.githubdeveloperapi.client.GithubClient;
import org.example.githubdeveloperapi.exception.GithubException;
import org.example.githubdeveloperapi.client.mapper.GithubMapper;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient githubClient;

    public List<GithubRepository> fetchUserRepos(final String username) {

        List<GithubRepositoryDTO> githubRepositoryDTOS;

        try {
            githubRepositoryDTOS = githubClient.fetchUserRepos(username);
        } catch (HttpClientErrorException ex) {
            throw new GithubException();
        }

        List<GithubRepository> list = githubRepositoryDTOS.stream().map(repositoryDTO -> {
                    String branchesUrl = repositoryDTO.getBranchesUrl().replace("{/branch}", "");
                    List<BranchDTO> branchDTOS = githubClient.fetchRepoBranches(branchesUrl);
                    return GithubMapper.map(repositoryDTO, branchDTOS);
                })
                .toList();

        return list;
    }
}
