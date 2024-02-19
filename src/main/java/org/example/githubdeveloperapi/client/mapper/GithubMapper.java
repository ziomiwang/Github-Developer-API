package org.example.githubdeveloperapi.client.mapper;

import org.example.githubdeveloperapi.client.model.branch.BranchDTO;
import org.example.githubdeveloperapi.client.model.repository.GithubRepositoryDTO;
import org.example.githubdeveloperapi.model.GithubBranch;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubMapper {

    public GithubRepository map(GithubRepositoryDTO repositoryDTO, List<BranchDTO> branchesDTO) {
        return GithubRepository.builder()
                .repositoryName(repositoryDTO.name())
                .ownerLogin(repositoryDTO
                        .owner()
                        .login())
                .branches(map(branchesDTO))
                .build();
    }

    private List<GithubBranch> map(List<BranchDTO> branchesDTO) {
        return branchesDTO.stream()
                .map(this::map)
                .toList();
    }

    private GithubBranch map(BranchDTO branchDTO) {
        return GithubBranch.builder()
                .branchName(branchDTO.name())
                .sha(branchDTO
                        .commit()
                        .sha())
                .build();
    }
}

