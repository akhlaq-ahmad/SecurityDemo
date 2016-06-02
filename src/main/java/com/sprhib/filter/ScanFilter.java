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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.multipart.FilePart;
/**
 * @author kiwitech
 *
 */
import com.oreilly.servlet.multipart.MultipartParser;
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

   public void init(FilterConfig filterConfig)
      throws ServletException {
      this.filterConfig = filterConfig;
   }

   //Process the request/response pair
   public void doFilter(ServletRequest request,
       ServletResponse response,
      FilterChain filterChain) {
   try {
      String contentType = request.getContentType();
      // Only filter this request if it is multipart encoding
      if (contentType.startsWith("multipart/form-data")) {

         MultipartParser mp = new MultipartParser((HttpServletRequest) request,10*1024*1024);
         Part part;
         while ((part = mp.readNextPart()) != null) {
            String name = part.getName();
         if (part.isFile()) {
            // it's a file part
            FilePart filePart = (FilePart) part;
            if (filePart != null) {
               // scan it for viruses
            }
         }
      }
   }    // end if

   filterChain.doFilter(request, response);
   } catch (ServletException sx) {
      filterConfig.getServletContext().log(sx.getMessage());
   } catch (IOException iox) {
      filterConfig.getServletContext().log(iox.getMessage());
   }
}}