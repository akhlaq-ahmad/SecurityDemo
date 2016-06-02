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

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provide a per request {@link CorsConfiguration} instance based on a
 * collection of {@link CorsConfiguration} mapped on path patterns.
 *
 * <p>Exact path mapping URIs (such as {@code "/admin"}) are supported
 * as well as Ant-style path patterns (such as {@code "/admin/**"}).
 *
 * @author Arvind.
 * @since 4.2
 */
public class UrlBasedCorsConfigurationSource implements CorsConfigurationSource {

	private final Map<String, CorsConfiguration> corsConfigurations = new LinkedHashMap<String, CorsConfiguration>();

	private PathMatcher pathMatcher = new AntPathMatcher();

	private UrlPathHelper urlPathHelper = new UrlPathHelper();


	/**
	 * Set the PathMatcher implementation to use for matching URL paths
	 * against registered URL patterns. Default is AntPathMatcher.
	 * @see AntPathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}

	/**
	 * Set if URL lookup should always use the full path within the current servlet
	 * context. Else, the path within the current servlet mapping is used if applicable
	 * (that is, in the case of a ".../*" servlet mapping in web.xml).
	 * <p>Default is "false".
	 * @see UrlPathHelper#setAlwaysUseFullPath
	 */
	public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
		this.urlPathHelper.setAlwaysUseFullPath(alwaysUseFullPath);
	}

	/**
	 * Set if context path and request URI should be URL-decoded. Both are returned
	 * <i>undecoded</i> by the Servlet API, in contrast to the servlet path.
	 * <p>Uses either the request encoding or the default encoding according
	 * to the Servlet spec (ISO-8859-1).
	 * @see UrlPathHelper#setUrlDecode
	 */
	public void setUrlDecode(boolean urlDecode) {
		this.urlPathHelper.setUrlDecode(urlDecode);
	}

	/**
	 * Set if ";" (semicolon) content should be stripped from the request URI.
	 * <p>The default value is {@code true}.
	 * @see UrlPathHelper#setRemoveSemicolonContent(boolean)
	 */
	public void setRemoveSemicolonContent(boolean removeSemicolonContent) {
		this.urlPathHelper.setRemoveSemicolonContent(removeSemicolonContent);
	}

	/**
	 * Set the UrlPathHelper to use for resolution of lookup paths.
	 * <p>Use this to override the default UrlPathHelper with a custom subclass.
	 */
	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}

	/**
	 * Set CORS configuration based on URL patterns.
	 */
	public void setCorsConfigurations(Map<String, CorsConfiguration> corsConfigurations) {
		this.corsConfigurations.clear();
		if (corsConfigurations != null) {
			this.corsConfigurations.putAll(corsConfigurations);
		}
	}

	/**
	 * Get the CORS configuration.
	 */
	public Map<String, CorsConfiguration> getCorsConfigurations() {
		return Collections.unmodifiableMap(this.corsConfigurations);
	}

	/**
	 * Register a {@link CorsConfiguration} for the specified path pattern.
	 */
	public void registerCorsConfiguration(String path, CorsConfiguration config) {
		this.corsConfigurations.put(path, config);
	}


	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		for(Map.Entry<String, CorsConfiguration> entry : this.corsConfigurations.entrySet()) {
			if (this.pathMatcher.match(entry.getKey(), lookupPath)) {
				return entry.getValue();
			}
		}
		return null;
	}

}
