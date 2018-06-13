package com.video.dao;

import org.octopus.spring_utils.jpa.GenericServiceI;

import com.video.model.LiveHub;



public interface LiveHubDao extends GenericServiceI<LiveHub> {

	public LiveHub getHubNameByHubType(Integer hubType);
}
