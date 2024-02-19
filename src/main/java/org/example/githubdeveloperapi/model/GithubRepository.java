package org.example.githubdeveloperapi.model;

import lombok.Builder;

import java.util.List;

@Builder
public record GithubRepository(String repositoryName,
                               String ownerLogin,
                               List<GithubBranch> branches) {
}
