package org.example.githubdeveloperapi;

import org.example.githubdeveloperapi.client.GithubClientHandler;
import org.example.githubdeveloperapi.client.mapper.GithubMapper;
import org.example.githubdeveloperapi.service.GithubService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

@Configuration
@ActiveProfiles({"test"})
public class GithubTestConfiguration {

    @Bean
    public GithubMapper githubMapper() {
        return new GithubMapper();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public GithubClientHandler githubClientHandler() {
        return new GithubClientHandler(restClient());
    }

    @Bean
    public GithubService githubService() {
        return new GithubService(githubClientHandler(), githubMapper());
    }
}
