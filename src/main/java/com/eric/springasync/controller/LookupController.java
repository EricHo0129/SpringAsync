package com.eric.springasync.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eric.springasync.model.Profile;
import com.eric.springasync.model.Response;
import com.eric.springasync.model.User;
import com.eric.springasync.service.GithubLookupService;
import com.eric.springasync.service.ProfileLookupService;

@RestController
public class LookupController {

	@Autowired
	private GithubLookupService githubLookupService;
	@Autowired
	private ProfileLookupService profileLookupService;
	
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
		
		Set<CompletableFuture<Response<Profile>>> resultSet = new HashSet<>();
		for (String pid: pidSet) {
			CompletableFuture<Response<Profile>> plusUser = profileLookupService.findUser(pid);
			resultSet.add(plusUser);
		}
		//全部回來之後
		CompletableFuture.allOf(resultSet.toArray(new CompletableFuture[0])).join();
		for (CompletableFuture<Response<Profile>> p: resultSet) {
			result.put(String.valueOf(p.get().getResponse().getPid()), p.get().getResponse());
		}
		return result;
	}
}
