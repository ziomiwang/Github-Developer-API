package org.example.githubdeveloperapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestGithubDeveloperApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(GithubDeveloperApiApplication::main).with(TestGithubDeveloperApiApplication.class).run(args);
    }

}
