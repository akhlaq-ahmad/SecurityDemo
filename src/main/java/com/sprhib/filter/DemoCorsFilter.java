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

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class DemoCorsFilter extends CorsFilter {

	@Autowired
	public DemoCorsFilter(UtilizationBean utilizationBean) {
		super(corsConfigurationSource("allowed-origin"));
	}
	 HttpHeaders responseHeaders = new HttpHeaders();
	private static CorsConfigurationSource corsConfigurationSource(String allowedOrigin) {
		CorsConfiguration config = new CorsConfiguration();
		 /** The response headers. */
	    
	    
	    /**
	     * Instantiates a new application base controller.
	     */
	   
	    
		config.setAllowCredentials(true);
		config.addAllowedOrigin(allowedOrigin);
		config.addAllowedHeader("*");
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "HEAD", "OPTIONS"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
	
	 /*public ApplicationBaseController() {
	        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
	        responseHeaders.add("Content-Type", MediaType.APPLICATION_XML_VALUE);
	        responseHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
	        responseHeaders.add("Accept", MediaType.APPLICATION_XML_VALUE);
	        responseHeaders.add("Access-Control-Allow-Origin", "*");
	        responseHeaders.add("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
	        responseHeaders.add("Access-Control-Allow-Credentials","true");
	        responseHeaders.add("Access-Control-Allow-Headers", "Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control");
	        responseHeaders.add("Connection", "keep-alive, close");
	        
	    }*/
	
}
