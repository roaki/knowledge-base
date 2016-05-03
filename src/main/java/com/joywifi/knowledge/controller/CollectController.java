package com.joywifi.knowledge.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import com.joywifi.knowledge.annotation.CurrentUserQuery;
import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.entity.Collect;
import com.joywifi.knowledge.service.BlogService;
import com.joywifi.knowledge.service.CollectService;
import com.joywifi.knowledge.util.Constants;

@Controller
@RequestMapping("collect")
public class CollectController {

	@Autowired
	private CollectService collectService;
	@Autowired
	private BlogService blogService;

	@CurrentUserQuery
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request,
				Constants.SEARCH_PREFIX));
		Page<Collect> collects = collectService.findPage(filters, pageable);

		for (Collect collect : collects) {
			Blog blog = blogService.get(collect.getBlogId());
			collect.setBlog(blog);
		}

		model.addAttribute("collects", collects);
		return "collect/list";
	}

	@ResponseBody
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(String blogId) {
		Map<String, SearchFilter> filters = blogService.baseFilter(blogId);
		List<Collect> collects = collectService.findBy(filters);

		if (collects.isEmpty() == true) {
			Collect collect = new Collect();
			collect.setBlogId(blogId);
			collectService.save(collect);
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(String blogId) {
		Map<String, SearchFilter> filters = blogService.baseFilter(blogId);
		List<Collect> collects = collectService.findBy(filters);
		collectService.delete(collects);
		return "success";
	}
}
