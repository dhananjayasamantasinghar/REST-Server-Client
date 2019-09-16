package com.client.util;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class RestClient {

	private static final String ENCODING_UTF_8 = "UTF-8";

	@Autowired
	private RestTemplate restTemplate;
	private HttpHeaders headers;
	private HttpEntity<?> entity;
	private String path;
	private HttpMethod method;
	private UriComponentsBuilder builder;
	private Map<String, String> pathParams;
	private MultiValueMap<String, Object> multiValueparameters;

	public RestClient() {
		this.headers = new HttpHeaders();
		this.pathParams = new HashMap<>();
		this.entity = new HttpEntity<>(headers);
		this.multiValueparameters = new LinkedMultiValueMap<>();
	}

	public <R> R invoke(Class<R> outputClassType) throws Exception {
		this.validate();
		return restTemplate.exchange(URLDecoder.decode(builder.buildAndExpand(pathParams).toString(), ENCODING_UTF_8),
				method, entity, outputClassType).getBody();

	}

	public RestClient path(String url) {
		this.path = url;
		builder = UriComponentsBuilder.fromHttpUrl(this.path);
		return this;
	}

	public RestClient contentType(MediaType contentType) {
		headers.setContentType(contentType);
		return this;
	}

	public RestClient acceptType(MediaType acceptType) {
		headers.setAccept(Arrays.asList(acceptType));
		return this;
	}

	public RestClient headerParam(String key, Object value) {
		if (Optional.ofNullable(key).isPresent()) {
			headers.set(key, String.valueOf(value));
			entity = new HttpEntity<>(headers);
		}
		return this;
	}

	public RestClient headerParam(Map<String, String> headerParam) {
		if (Optional.ofNullable(headerParam).isPresent()) {
			headerParam.keySet().forEach(e -> headers.set(e, headerParam.get(e)));
			entity = new HttpEntity<>(headers);
		}
		return this;
	}

	public RestClient queryParam(Map<String, String> parameters) {
		MultiValueMap<String, String> multiValueparameters = new LinkedMultiValueMap<>();
		if (Optional.ofNullable(parameters).isPresent()) {
			parameters.keySet().forEach(e -> multiValueparameters.add(e, parameters.get(e)));
		}
		builder = builder.queryParams(multiValueparameters);
		return this;
	}

	public RestClient queryParam(String key, Object value) {
		MultiValueMap<String, String> multiValueparameters = new LinkedMultiValueMap<>();
		if (Optional.ofNullable(key).isPresent()) {
			multiValueparameters.add(key, String.valueOf(value));
		}
		builder = builder.queryParams(multiValueparameters);
		return this;
	}

	public RestClient formParam(Map<String, String> formParams) {
		MultiValueMap<String, String> multiValueparameters = new LinkedMultiValueMap<>();
		if (formParams != null && !formParams.isEmpty()) {
			formParams.keySet().forEach(e -> multiValueparameters.add(e, formParams.get(e)));
		}
		entity = new HttpEntity<>(multiValueparameters, headers);
		return this;
	}

	public RestClient formParam(String key, Object value) {
		multiValueparameters.add(key, value);
		entity = new HttpEntity<>(multiValueparameters, headers);
		return this;
	}

	public RestClient pathParam(Map<String, String> pathParam) {
		this.pathParams = pathParam;
		return this;
	}

	public RestClient pathParam(String key, String value) {
		if (value != null)
			pathParams.put(key, value);
		return this;
	}

	public RestClient body(Object body) {
		entity = new HttpEntity<>(body, headers);
		return this;
	}

	public RestClient get() {
		this.method = HttpMethod.GET;
		return this;
	}

	public RestClient post() {
		this.method = HttpMethod.POST;
		return this;
	}

	public RestClient put() {
		this.method = HttpMethod.PUT;
		return this;
	}

	public RestClient delete() {
		this.method = HttpMethod.DELETE;
		return this;
	}

	private void validate() throws Exception {
		if (!Optional.ofNullable(path).isPresent() || !Optional.ofNullable(method).isPresent())
			throw new RestClientException(method == null ? "Http method is required" : "Target URL is required ");
	}

}
