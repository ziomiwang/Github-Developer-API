package org.example.githubdeveloperapi.client.mapper;

import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.model.GithubBranch;
import org.example.githubdeveloperapi.model.GithubRepository;

import java.util.List;

public class GithubMapper {

    public static GithubRepository map(GithubRepositoryDTO repositoryDTO, List<BranchDTO> branchesDTO) {
        return GithubRepository.builder()
                .repositoryName(repositoryDTO.getName())
                .ownerLogin(repositoryDTO
                        .getOwner()
                        .getLogin())
                .branches(map(branchesDTO))
                .build();
    }

    private static List<GithubBranch> map(List<BranchDTO> branchesDTO) {
        return branchesDTO.stream()
                .map(GithubMapper::map)
                .toList();
    }

    private static GithubBranch map(BranchDTO branchDTO) {
        return GithubBranch.builder()
                .branchName(branchDTO.getName())
                .sha(branchDTO
                        .getCommit()
                        .getSha())
                .build();
    }
}

