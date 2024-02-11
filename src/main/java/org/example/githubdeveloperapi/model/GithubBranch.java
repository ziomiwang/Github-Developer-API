package org.example.githubdeveloperapi.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubBranch {

    private String branchName;
    private String sha;
}
