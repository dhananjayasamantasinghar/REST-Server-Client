package com.client.util;

import java.io.IOException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class HttpClientLogInterceptor implements ClientHttpRequestInterceptor {

	Logger logger = LoggerFactory.getLogger(HttpClientLogInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		logger.info("--------------------------------------------------------------------------------------");
		logger.info("Request URL: " + request.getURI().toString());
		logger.info("Method Type: " + request.getMethod().toString());
		if (body.length > 0)
			logger.info("Request Body: " + new String(body));
		long startTimeMillis = Instant.now().toEpochMilli();
		ClientHttpResponse response = execution.execute(request, body);
		long endTimeMillis = Instant.now().toEpochMilli();
		logger.info("Response time: " + (endTimeMillis - startTimeMillis) + " Mili Seconds");
		logger.info("-------------------------------------------------------------------------------------");
		return response;
	}

}
