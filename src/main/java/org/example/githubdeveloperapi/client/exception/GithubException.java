package org.example.githubdeveloperapi.client.exception;

import lombok.Getter;

@Getter
public class GithubException extends RuntimeException {

    private String status = "NOT_FOUND";
    private String message = "Repositories for user with given name not found";

    public GithubException(){

    }
    public GithubException(final String message){
        super();
        this.message = message;
    }
}
