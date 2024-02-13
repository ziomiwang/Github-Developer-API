package org.example.githubdeveloperapi.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.example.githubdeveloperapi.service.GithubService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Github", description = "github repositories API")
@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @Operation(
            summary = "Fetch all github repositories",
            description = "Fetches all github repositories and their branches for provided username"
    )
    @GetMapping(value = "/repos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GithubRepository>> getUserRepos(@RequestParam("username") String username) {
        return ResponseEntity.ok(githubService.fetchUserRepos(username));
    }

}
