package org.example.githubdeveloperapi.client.model.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommitDTO(@JsonProperty("sha") String sha) {
}
