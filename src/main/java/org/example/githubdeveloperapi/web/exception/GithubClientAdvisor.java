package org.example.githubdeveloperapi.web.exception;

import org.example.githubdeveloperapi.exception.GithubWebException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GithubClientAdvisor {

    @ExceptionHandler
    protected WebException handleGithubUsernameNotFound(final GithubWebException ex) {
        return WebException.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .build();
    }
}
