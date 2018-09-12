package com.eric.springasync.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eric.springasync.model.User;

@Service
public class GithubLookupService {

	private Logger log = LoggerFactory.getLogger(GithubLookupService.class);
	//Spring的RestClient
	private RestTemplate restTemplate;
	
	public GithubLookupService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@Async
	public CompletableFuture<User> findUser(String name) throws Exception {
		log.info("Look up user: "+name);
		String url = String.format("https://api.github.com/users/%s", name);
		User result = restTemplate.getForObject(url, User.class);
		return CompletableFuture.completedFuture(result);
	}
}
