package org.example.githubdeveloperapi.model;

import lombok.Builder;

@Builder
public record GithubBranch (String branchName, String sha) {
}
