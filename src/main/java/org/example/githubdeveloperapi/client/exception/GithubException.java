package org.example.githubdeveloperapi.client.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GithubException {

    @JsonProperty("message")
    private String message;

    @JsonProperty("documentation_url")
    private String documentationUrl;

}
