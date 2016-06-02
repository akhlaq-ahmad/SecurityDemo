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

import javax.servlet.http.HttpServletRequest;

/**
 * Interface to be implemented by classes (usually HTTP request handlers) that
 * provides a {@link CorsConfiguration} instance based on the provided request.
 *
 * @author Arvind
 * @since 4.2
 */
public interface CorsConfigurationSource {

	/**
	 * Return a {@link CorsConfiguration} based on the incoming request.
	 */
	CorsConfiguration getCorsConfiguration(HttpServletRequest request);

}
