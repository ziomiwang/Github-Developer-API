package org.example.githubdeveloperapi.service;

import lombok.RequiredArgsConstructor;
import org.example.githubdeveloperapi.client.GithubClient;
import org.example.githubdeveloperapi.client.mapper.GithubMapper;
import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient githubClient;

    public List<GithubRepository> fetchUserRepos() {
        List<GithubRepositoryDTO> githubRepositoryDTOS = githubClient.fetchUserRepos();

        List<GithubRepository> list = githubRepositoryDTOS.stream().map(repositoryDTO -> {
                    String branchesUrl = repositoryDTO.getBranchesUrl().replace("{/branch}", "");
                    List<BranchDTO> branchDTOS = githubClient.fetchRepoBranches(branchesUrl);
                    return GithubMapper.map(repositoryDTO, branchDTOS);
                })
                .toList();

        return list;
    }
}
