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

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

/**
 * A container for CORS configuration that also provides methods to check
 * the actual or requested origin, HTTP methods, and headers.
 *
 * @author Arvind.
 * @since 4.2
 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 */
public class FilterChain {

    private ArrayList myFilters = new ArrayList();

    public FilterChain(CustomFilterInterface filter) {
        addFilter(filter);
    }

    public void processFilter(HttpServletRequest request) throws
            ServletException, java.io.IOException {
        CustomFilterInterface filter;

        Iterator filters = myFilters.iterator();
        while (filters.hasNext()) {
            filter = (CustomFilterInterface) filters.next();
            filter.execute(request);
        }
    }

    public void addFilter(CustomFilterInterface filter) {
        myFilters.add(filter);
    }

}
