package com.joywifi.knowledge.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter;

import com.joywifi.knowledge.entity.Vote;
import com.joywifi.knowledge.service.BlogService;
import com.joywifi.knowledge.service.VoteService;

@Controller
@RequestMapping("vote")
public class VoteController {

	@Autowired
	private VoteService voteService;
	@Autowired
	private BlogService blogService;

	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(String blogId, Model model) {
		Map<String, SearchFilter> filters = blogService.baseFilter(blogId);
		List<Vote> votes = voteService.findBy(filters);
		
		if (votes.isEmpty() == true) {
			Vote vote = new Vote();
			vote.setBlogId(blogId);
			voteService.save(vote);
			return "success";
		} else {
			return "fail";
		}
	}
}