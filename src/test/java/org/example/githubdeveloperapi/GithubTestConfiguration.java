package org.example.githubdeveloperapi;

import org.example.githubdeveloperapi.client.GithubClientHandler;
import org.example.githubdeveloperapi.client.mapper.GithubMapper;
import org.example.githubdeveloperapi.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

@Configuration
@ActiveProfiles({"test"})
public class GithubTestConfiguration {

    @Autowired
    private RestClient restClient;

    @Autowired
    private GithubMapper githubMapper;


    @Bean
    public GithubClientHandler githubClientHandler(){
        return new GithubClientHandler(restClient);
    }
    @Bean
    public GithubService githubService(){
        return new GithubService(githubClientHandler(), githubMapper);
    }
}
