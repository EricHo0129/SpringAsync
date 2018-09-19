package com.eric.springasync.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GraphQLService {

	private Logger log = LoggerFactory.getLogger(GraphQLService.class);
	
	//Spring的RestClient
	private RestTemplate restTemplate;
	
	public GraphQLService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	/**
	 * 查詢GraphQL的API
	 * @return
	 * @throws Exception
	 */
	public Object getAllUsers() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String querySyntax = "query { allUsers {id name email createdAt posts(last: 3, orderBy: id_ASC) {id title text}}}";
		URI url = new URI("https://api.graph.cool/simple/v1/ciyz901en4j590185wkmexyex");
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("query", querySyntax);
		String request = new ObjectMapper().writeValueAsString(queryMap);
		HttpEntity<String> entity = new HttpEntity<String>(request ,headers);
		String result = restTemplate.postForObject(url, entity, String.class);
		log.info("result:"+result);
		return new ObjectMapper().readTree(result);
	}
}
