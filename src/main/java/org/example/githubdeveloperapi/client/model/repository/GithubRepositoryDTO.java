package org.example.githubdeveloperapi.client.model.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class GithubRepositoryDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private GithubOwnerDTO owner;

    @JsonProperty("branches_url")
    private String branchesUrl;
}
