package org.example.githubdeveloperapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record GithubRepository(@JsonProperty("repositoryName") String repositoryName,
                               @JsonProperty("ownerLogin") String ownerLogin,
                               @JsonProperty("branches") List<GithubBranch> branches) {
}
