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
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Arvind
 *
 */
public class EscapeUtils  implements Filter{

	public static final HashMap m = new HashMap();
	static {
		m.put(34, "&quot;"); 
		m.put(60, "&lt;");   
		m.put(62, "&gt;");  
              }

	public static String escapeHtml() {
		String str = "<script>alert(\"abc\")</script>";
		try {
			StringWriter writer = new StringWriter((int) 
                           (str.length() * 1.5));
			escape(writer, str);
			System.out.println("encoded string is " + writer.toString() );
			return writer.toString();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		                                            }
	                                                 }

	public static void escape(Writer writer, String str) throws IOException {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			int ascii = (int) c;
			String entityName = (String) m.get(ascii);
			if (entityName == null) {
				if (c > 0x7F) {
					writer.write("&#");
					writer.write(Integer.toString(c, 10));
					writer.write(';');
				} else {
					writer.write(c);
				}
			} else {
                     writer.write(entityName);
			}
		}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		escapeHtml();
		chain.doFilter(new XssDemoRequestWrapper((HttpServletRequest) arg0), arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
