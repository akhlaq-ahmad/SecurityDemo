/*******************************************************************************
 * Copyright (c) 2016 kiwitech Corporation and others.
 * <h5>author: @author Arvind</h5>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiwitech Corporation - initial CMS Web Portal, Services  and implementation
 *******************************************************************************/

package com.sprhib.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;

/**
 * The default implementation of {@link CorsProcessor}, as defined by the
 * <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>.
 *
 * <p>Note that when input {@link CorsConfiguration} is {@code null}, this
 * implementation does not reject simple or actual requests outright but simply
 * avoid adding CORS headers to the response. CORS processing is also skipped
 * if the response already contains CORS headers, or if the request is detected
 * as a same-origin one.
 *
 * @author Arvind.
 * @since 4.2
 */
public class DefaultCorsProcessor implements CorsProcessor {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	private static final Log logger = LogFactory.getLog(DefaultCorsProcessor.class);


	@Override
	public boolean processRequest(CorsConfiguration config, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (!CorsUtils.isCorsRequest(request)) {
			return true;
		}

		ServletServerHttpResponse serverResponse = new ServletServerHttpResponse(response);
		ServletServerHttpRequest serverRequest = new ServletServerHttpRequest(request);

		if (CorsUtils.isSameOrigin(serverRequest)) {
			logger.debug("Skip CORS processing, request is a same-origin one");
			return true;
		}
		if (responseHasCors(serverResponse)) {
			logger.debug("Skip CORS processing, response already contains \"Access-Control-Allow-Origin\" header");
			return true;
		}

		boolean preFlightRequest = CorsUtils.isPreFlightRequest(request);
		if (config == null) {
			if (preFlightRequest) {
				rejectRequest(serverResponse);
				return false;
			}
			else {
				return true;
			}
		}

		return handleInternal(serverRequest, serverResponse, config, preFlightRequest);
	}

	private boolean responseHasCors(ServerHttpResponse response) {
		boolean hasAllowOrigin = false;
		try {
			hasAllowOrigin = (CorsUtils.getAccessControlAllowOrigin(response.getHeaders()) != null);
		}
		catch (NullPointerException npe) {
			// SPR-11919 and https://issues.jboss.org/browse/WFLY-3474
		}
		return hasAllowOrigin;
	}

	/**
	 * Invoked when one of the CORS checks failed.
	 * The default implementation sets the response status to 403 and writes
	 * "Invalid CORS request" to the response.
	 */
	protected void rejectRequest(ServerHttpResponse response) throws IOException {
		response.setStatusCode(HttpStatus.FORBIDDEN);
		response.getBody().write("Invalid CORS request".getBytes(UTF8_CHARSET));
	}

	/**
	 * Handle the given request.
	 */
	protected boolean handleInternal(ServerHttpRequest request, ServerHttpResponse response,
	                                 CorsConfiguration config, boolean preFlightRequest) throws IOException {

		String requestOrigin = CorsUtils.getOrigin(request.getHeaders());
		String allowOrigin = checkOrigin(config, requestOrigin);

		HttpMethod requestMethod = getMethodToUse(request, preFlightRequest);
		List<HttpMethod> allowMethods = checkMethods(config, requestMethod);

		List<String> requestHeaders = getHeadersToUse(request, preFlightRequest);
		List<String> allowHeaders = checkHeaders(config, requestHeaders);

		if (allowOrigin == null || allowMethods == null || (preFlightRequest && allowHeaders == null)) {
			rejectRequest(response);
			return false;
		}

		HttpHeaders responseHeaders = response.getHeaders();
		CorsUtils.setAccessControlAllowOrigin(responseHeaders, allowOrigin);
		responseHeaders.add(CorsUtils.VARY, CorsUtils.ORIGIN);

		if (preFlightRequest) {
			CorsUtils.setAccessControlAllowMethods(responseHeaders, allowMethods);
		}

		if (preFlightRequest && !allowHeaders.isEmpty()) {
			CorsUtils.setAccessControlAllowHeaders(responseHeaders, allowHeaders);
		}

		if (!CollectionUtils.isEmpty(config.getExposedHeaders())) {
			CorsUtils.setAccessControlExposeHeaders(responseHeaders, config.getExposedHeaders());
		}

		if (Boolean.TRUE.equals(config.getAllowCredentials())) {
			CorsUtils.setAccessControlAllowCredentials(responseHeaders, true);
		}

		if (preFlightRequest && config.getMaxAge() != null) {
			CorsUtils.setAccessControlMaxAge(responseHeaders, config.getMaxAge());
		}

		response.close();
		return true;
	}

	/**
	 * Check the origin and determine the origin for the response. The default
	 * implementation simply delegates to
	 * {@link com.qc.spring.cors.CorsConfiguration#checkOrigin(String)}
	 */
	protected String checkOrigin(CorsConfiguration config, String requestOrigin) {
		return config.checkOrigin(requestOrigin);
	}

	/**
	 * Check the HTTP method and determine the methods for the response of a
	 * pre-flight request. The default implementation simply delegates to
	 * {@link com.qc.spring.cors.CorsConfiguration#checkOrigin(String)}
	 */
	protected List<HttpMethod> checkMethods(CorsConfiguration config, HttpMethod requestMethod) {
		return config.checkHttpMethod(requestMethod);
	}

	private HttpMethod getMethodToUse(ServerHttpRequest request, boolean isPreFlight) {
		return (isPreFlight ? CorsUtils.getAccessControlRequestMethod(request.getHeaders()) : request.getMethod());
	}

	/**
	 * Check the headers and determine the headers for the response of a
	 * pre-flight request. The default implementation simply delegates to
	 * {@link com.qc.spring.cors.CorsConfiguration#checkOrigin(String)}
	 */
	protected List<String> checkHeaders(CorsConfiguration config, List<String> requestHeaders) {
		return config.checkHeaders(requestHeaders);
	}

	private List<String> getHeadersToUse(ServerHttpRequest request, boolean isPreFlight) {
		HttpHeaders headers = request.getHeaders();
		return (isPreFlight ? CorsUtils.getAccessControlRequestHeaders(headers) : new ArrayList<>(headers.keySet()));
	}

}
