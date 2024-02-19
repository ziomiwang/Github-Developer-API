package org.example.githubdeveloperapi.exception;

import lombok.Getter;
import org.example.githubdeveloperapi.client.exception.GithubException;

@Getter
public class GithubWebException extends RuntimeException {

    private int status = 400;
    private String message = "Something went wrong";

    public GithubWebException(){

    }
    public GithubWebException(final int status){
        super();
        this.status = status;
    }

    public GithubWebException(String message, int status) {
        super();
        this.status = status;
        this.message = message;
    }
    public GithubWebException(GithubException githubException, int status) {
        super();
        this.status = status;
        this.message = githubException.getMessage();
    }
}
