package org.example.githubdeveloperapi.client.model.branch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommitDTO {

    @JsonProperty("sha")
    private String sha;
}
