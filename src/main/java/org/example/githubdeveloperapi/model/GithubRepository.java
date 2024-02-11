package org.example.githubdeveloperapi.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepository {

    private String repositoryName;
    private String ownerLogin;
    private List<GithubBranch> branches;
}
