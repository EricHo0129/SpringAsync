package com.eric.springasync.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.springasync.model.Profile;
import com.eric.springasync.model.Response;
import com.eric.springasync.model.User;
import com.eric.springasync.service.GithubLookupService;
import com.eric.springasync.service.GraphQLService;
import com.eric.springasync.service.ProfileLookupService;

@RestController
public class LookupController {
	
	private Logger log = LoggerFactory.getLogger(LookupController.class);

	@Autowired
	private GithubLookupService githubLookupService;
	@Autowired
	private ProfileLookupService profileLookupService;
	@Autowired
	private GraphQLService graphQLService;
	
	@GetMapping("/lookup")
	public Object lookup() throws Exception {
		Map<String, Object> result = new HashMap<>();
		Set<String> userNameSet = new HashSet<>();
		userNameSet.add("chonpin");
		userNameSet.add("odetocode");
		userNameSet.add("EricHo0129");
		userNameSet.add("aldy120");

		Set<CompletableFuture<User>> resultSet = new HashSet<>();
		for (String name: userNameSet) {
			CompletableFuture<User> gitUser = githubLookupService.findUser(name);
			result.put(name, gitUser.get());
		}
		//全部回來之後
		CompletableFuture.allOf(resultSet.toArray(new CompletableFuture[0])).join();
		
		return result;
	}
	
	@GetMapping("/find")
	public Object find() throws Exception {
		Map<String, Object> result = new HashMap<>();
		Set<String> pidSet = Stream.of("100024","100008","100080","100604","108196").collect(Collectors.toSet());
		
		long start = System.currentTimeMillis();
		Set<CompletableFuture<Response<Profile>>> resultSet = new HashSet<>();
		for (String pid: pidSet) {
			CompletableFuture<Response<Profile>> plusUser = profileLookupService.findUser(pid);
			resultSet.add(plusUser);
			result.put(pid, null);
		}
		//全部回來之後
		CompletableFuture<Void> allFuture = CompletableFuture.allOf(resultSet.toArray(new CompletableFuture[0]));
		log.info("阻塞");
		allFuture.join();
		log.info("阻塞結束");
		for (CompletableFuture<Response<Profile>> p: resultSet) {
			if (p.get()!=null) {				
				result.put(String.valueOf(p.get().getResponse().getPid()), p.get().getResponse());
			}
		}
		log.info("Total spent time: "+ (System.currentTimeMillis()-start)/1000.0+" seconds");
		return result;
	}
	
	@GetMapping("/query")
	public Object query() throws Exception {
		Object obj = graphQLService.getAllUsers();
		
		return obj;
	}
}
