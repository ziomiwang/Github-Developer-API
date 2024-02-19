package org.example.githubdeveloperapi.client.model.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchDTO(@JsonProperty("name") String name,
                        @JsonProperty("commit") CommitDTO commit) {
}
