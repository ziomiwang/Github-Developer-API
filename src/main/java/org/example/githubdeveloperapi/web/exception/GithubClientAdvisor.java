package org.example.githubdeveloperapi.web.exception;

import org.example.githubdeveloperapi.exception.GithubWebException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GithubClientAdvisor {

    @ExceptionHandler
    protected ResponseEntity<WebException> handleGithubUsernameNotFound(final GithubWebException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body( WebException.builder()
                .status(ex.getStatus())
                .message(ex.getMessage())
                .build());
    }
}
