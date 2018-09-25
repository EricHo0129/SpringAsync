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
import com.github.k0kubun.builder.query.graphql.GraphQL;
import com.github.k0kubun.builder.query.graphql.model.GraphQLObject;
import com.google.common.collect.ImmutableMap;

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
	
	public Object getAllUsersByBuilder() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> postsMap = new HashMap<>();
		postsMap.put("last", 3);
		postsMap.put("orderBy", "id_ASC"); //列舉還不知道怎麼丟

		String querySyntax = GraphQL.createQueryBuilder()
				.object("allUsers", GraphQL.createObjectBuilder()
						.field("id")
						.field("name")
						.field("email")
						.field("createdAt")
						.object("posts", postsMap, GraphQL.createObjectBuilder()
								.field("id")
								.field("title")
								.field("text")
								.build())
						.build())
				.build();
		URI url = new URI("https://api.graph.cool/simple/v1/ciyz901en4j590185wkmexyex");
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("query", String.format("query { %s }",querySyntax.replaceAll("\"", "")));
		String request = new ObjectMapper().writeValueAsString(queryMap);
		HttpEntity<String> entity = new HttpEntity<String>(request ,headers);
		String result = restTemplate.postForObject(url, entity, String.class);
		return new ObjectMapper().readTree(result);
	}
	
	public static void main(String[] args) {
		System.out.println(String.format("aaa %s %s", "b"));
	}
}
