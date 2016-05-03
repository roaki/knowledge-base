package com.joywifi.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joywifi.knowledge.entity.Collect;
import com.joywifi.knowledge.repository.CollectDao;

@Service
public class CollectService extends BaseService<Collect, String> {

	@Autowired
	private CollectDao collectDao;
	
	
	
}
