package org.example.githubdeveloperapi.web;

import lombok.RequiredArgsConstructor;
import org.example.githubdeveloperapi.model.GithubRepository;
import org.example.githubdeveloperapi.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/repos")
    public ResponseEntity<List<GithubRepository>> getUserRepos(@RequestParam("username") String username) {
        List<GithubRepository> githubRepositories = githubService.fetchUserRepos(username);

        return ResponseEntity.ok(githubRepositories);
    }

}
