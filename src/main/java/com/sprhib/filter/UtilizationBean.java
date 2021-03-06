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

import org.springframework.beans.factory.InitializingBean;


/**
 * A container for CORS configuration that also provides methods to check
 * the actual or requested origin, HTTP methods, and headers.
 *
 * @author Arvind.
 * @since 4.2
 * @see <a href="http://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 */
public class UtilizationBean implements InitializingBean {
    
    /** The properties. */
    //public PropertiesConfiguration properties;
    
    /**
     * Instantiates a new configuration.
     */
    public UtilizationBean(){
    }
/*
    *//**
     * Sets the properties.
     *
     * @param properties the new properties
     *//*
    public void setProperties(PropertiesConfiguration properties) {
        this.properties = properties;
    }
    
   
    @Override
    public void afterPropertiesSet() throws Exception {
    }

    *//**
     * Gets the.
     *
     * @param key the key
     * @return the string
     *//*
    public String get(String key) {
        return properties.getString(key);
    }
    
   
    @Override
    public String toString() {
    	return "comming from Configuration";
    }*/

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}