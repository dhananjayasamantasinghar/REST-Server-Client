package com.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.client.util.RestServiceInvoker;

@RestController
@RequestMapping("/client")
@RefreshScope
public class TestClientController {

	@Autowired
	private Environment env;

	@Lookup
	public RestServiceInvoker getRestService() {
		return null;
	}

	@GetMapping(value = "/message")
	public String getMessage(@RequestParam(name = "name") String name) throws Exception {

		return getRestService()
				.get()
				.path(env.getProperty("git.config.url"))
				.queryParam("name", name)
				.invoke(String.class);

		/*
		 * UriComponentsBuilder builder =
		 * UriComponentsBuilder.fromHttpUrl(env.getProperty("git.config.url"));
		 * builder.queryParam("name", name); return
		 * restTemplate.exchange(URLDecoder.decode(builder.build().toString(),
		 * "UTF-8"), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),
		 * String.class).getBody();
		 */

	}
}
