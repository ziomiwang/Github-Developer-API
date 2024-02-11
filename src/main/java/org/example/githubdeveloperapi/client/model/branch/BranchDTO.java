package org.example.githubdeveloperapi.client.model.branch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BranchDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private CommitDTO commit;
}
