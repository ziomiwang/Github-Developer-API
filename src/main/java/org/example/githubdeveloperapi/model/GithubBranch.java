package org.example.githubdeveloperapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubBranch (@JsonProperty("branchName") String branchName, @JsonProperty("sha") String sha) {
}
