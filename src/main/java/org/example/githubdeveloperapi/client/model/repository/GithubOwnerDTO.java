package org.example.githubdeveloperapi.client.model.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GithubOwnerDTO {

    @JsonProperty("login")
    private String login;
}
