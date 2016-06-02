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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * {@link javax.servlet.Filter} that handles CORS preflight requests and intercepts CORS
 * simple and actual requests thanks to a {@link CorsProcessor} implementation
 * ({@link DefaultCorsProcessor} by default) in order to add the relevant CORS response
 * headers (like {@code Access-Control-Allow-Origin}) using the provided
 * {@link CorsConfigurationSource} (for example an {@link UrlBasedCorsConfigurationSource}
 * instance.
 *
 * <p>This is an alternative to Spring MVC Java config and XML namespace CORS configuration,
 * useful for applications depending only on spring-web (not on spring-webmvc) or for
 * security constraints requiring CORS checks to be performed at {@link javax.servlet.Filter}
 * level.
 *
 * <p>This filter could be used in conjunction with {@link DelegatingFilterProxy} in order
 * to help with its initialization.
 *
 * @author Arvind.
 * @since 4.2
 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 */
public class CorsFilter extends OncePerRequestFilter {

	private CorsProcessor processor = new DefaultCorsProcessor();

	private final CorsConfigurationSource configSource;
	



	/**
	 * Constructor accepting a {@link CorsConfigurationSource} used by the filter to find
	 * the {@link CorsConfiguration} to use for each incoming request.
	 * @see UrlBasedCorsConfigurationSource
	 */
	public CorsFilter(CorsConfigurationSource configSource) {
		this.configSource = configSource;
	}

	/**
	 * Configure a custom {@link CorsProcessor} to use to apply the matched
	 * {@link CorsConfiguration} for a request.
	 * <p>By default {@link DefaultCorsProcessor} is used.
	 */
	public void setCorsProcessor(CorsProcessor processor) {
		Assert.notNull(processor, "CorsProcessor must not be null");
		this.processor = processor;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		
		if (CorsUtils.isCorsRequest(request)) {
			CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(request);
			if (corsConfiguration != null) {
				boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
				if (!isValid || CorsUtils.isPreFlightRequest(request)) {
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
