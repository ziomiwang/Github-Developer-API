package org.example.githubdeveloperapi.client.model.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubOwnerDTO(@JsonProperty("login") String login) {
}
