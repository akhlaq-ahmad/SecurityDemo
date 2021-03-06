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

package com.sprhib.service;

import java.util.List;

import com.sprhib.model.Team;
/** 
 * @author Arvind.
 * */
public interface TeamService {
	
	public void addTeam(Team team);
	public void updateTeam(Team team);
	public Team getTeam(Long id);
	public void deleteTeam(Long id);
	public List<Team> getTeams();

}
