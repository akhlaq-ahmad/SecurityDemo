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

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

import com.oreilly.servlet.multipart.*;
import com.oreilly.servlet.multipart.Part;

/**
 * A container for CORS configuration that also provides methods to check
 * the actual or requested origin, HTTP methods, and headers.
 *
 * @author Arvind.
 * @since 4.2
 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 */
public class ScanFilter extends HttpServlet implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    //Process the request/response pair
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) {
        try {
            String contentType = request.getContentType();
            // Only filter this request if it is multipart
            // encoding
            if (contentType.startsWith("multipart/form-data")) {

                    MultipartParser mp = new MultipartParser((HttpServletRequest) request, 2*4*1024);
                    Part part;
                    while ((part = mp.readNextPart()) != null) {
                        String name = part.getName();
                   if (part.isFile()) {
                            // it's a file part
                            FilePart filePart = (FilePart) part;
                            String fileName = filePart.getFileName();
                            if (fileName != null) {
                              // scan it for viruses
                            }
                        }
                    }
            } // end if

            ((javax.servlet.FilterChain) filterChain).doFilter(request, response);

        } catch (ServletException sx) {
            filterConfig.getServletContext().log(sx.getMessage());
        } catch (IOException iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }

   @Override
    public void destroy() {
    }

@Override
public void doFilter(ServletRequest arg0, ServletResponse arg1,
		javax.servlet.FilterChain arg2) throws IOException, ServletException {
	// TODO Auto-generated method stub
	
}
}
