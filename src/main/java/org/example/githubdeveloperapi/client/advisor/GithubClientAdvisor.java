package org.example.githubdeveloperapi.client.advisor;

import org.example.githubdeveloperapi.client.exception.GithubException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GithubClientAdvisor {

    @ExceptionHandler
    protected ProblemDetail handleGithubUsernameNotFound(final GithubException ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getStatus());
        problemDetail.setTitle(ex.getMessage());
        return problemDetail;
    }
}
