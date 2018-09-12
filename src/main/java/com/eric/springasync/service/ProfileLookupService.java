package com.eric.springasync.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eric.springasync.model.Profile;
import com.eric.springasync.model.Response;
import com.eric.springasync.model.User;

@Service
public class ProfileLookupService {

		private Logger log = LoggerFactory.getLogger(ProfileLookupService.class);
	
		//Spring的RestClient
		private RestTemplate restTemplate;
		
		public ProfileLookupService(RestTemplateBuilder restTemplateBuilder) {
			this.restTemplate = restTemplateBuilder.build();
		}
		
		@Async
		public CompletableFuture<Response<Profile>> findUser(String pid) throws Exception {
			String url = String.format("https://profile.fp.104dc-dev.com/users/%s/profile", pid);
			log.info("Look up user: "+pid);
			long start = System.currentTimeMillis();
			
			Response<Map> resultMap = restTemplate.getForObject(url, Response.class);
			Profile p = new Profile();
			p.setPid(MapUtils.getLong(resultMap.getResponse(), "pid"));
			p.setAvatarFileId(MapUtils.getString(resultMap.getResponse(), "avatarFileId"));
			p.setIntroduction(MapUtils.getString(resultMap.getResponse(), "introduction"));
			p.setUserName(MapUtils.getString(resultMap.getResponse(), "userName"));
			Response<Profile> result = new Response<>();
			result.setResponse(p);
			log.info("Spent time: "+ (System.currentTimeMillis()-start)/1000.0+" seconds");
			return CompletableFuture.completedFuture(result);
		}
}
