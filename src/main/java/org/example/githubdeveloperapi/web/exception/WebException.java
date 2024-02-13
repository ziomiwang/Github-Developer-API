package org.example.githubdeveloperapi.web.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebException {

    private int status;
    private String message;
}
