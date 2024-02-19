package org.example.githubdeveloperapi.client.model.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepositoryDTO(@JsonProperty("name") String name,
                                  @JsonProperty("owner") GithubOwnerDTO owner,
                                  @JsonProperty("branches_url") String branchesUrl) {
}
