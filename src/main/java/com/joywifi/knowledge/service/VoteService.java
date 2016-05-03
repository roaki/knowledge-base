package com.joywifi.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joywifi.knowledge.entity.Vote;
import com.joywifi.knowledge.repository.VoteDao;

@Service
public class VoteService extends BaseService<Vote, String> {
	
	@Autowired
	private VoteDao laudDao;

}
