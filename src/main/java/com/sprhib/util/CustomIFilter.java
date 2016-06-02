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

package com.sprhib.util;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * A container for CORS configuration that also provides methods to check
 * the actual or requested origin, HTTP methods, and headers.
 *
 * @author Arvind.
 * @since 4.2
 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 */
public class CustomIFilter extends HttpServletRequestWrapper implements CustomFilterInterface {
    public CustomIFilter(HttpServletRequest request) {
        super(request);
    }

    public void execute(HttpServletRequest request) {
        //do something with the request here...
    }

}
