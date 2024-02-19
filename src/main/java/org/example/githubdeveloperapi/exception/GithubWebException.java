package org.example.githubdeveloperapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GithubWebException extends RuntimeException {

    private int status = 400;
    private String message = "Something went wrong";
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public GithubWebException() {

    }

    public GithubWebException(String message, int status) {
        super();
        this.status = status;
        this.message = message;
    }

    public GithubWebException(String message, int status, HttpStatus httpStatus) {
        super();
        this.status = status;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
