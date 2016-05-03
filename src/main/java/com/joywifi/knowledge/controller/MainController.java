package com.joywifi.knowledge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.entity.Category;
import com.joywifi.knowledge.entity.Tag;
import com.joywifi.knowledge.entity.Vote;
import com.joywifi.knowledge.service.BlogService;
import com.joywifi.knowledge.service.CategoryService;
import com.joywifi.knowledge.service.TagService;
import com.joywifi.knowledge.service.VoteService;
import com.joywifi.knowledge.util.Constants;

@Controller
public class MainController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private VoteService voteService;

    /**
     * @param pageable
     * @param request  {criteria_EQ_tags.title=springmvc,criteria_EQ_category.title}
     * @param model
     * @return
     */
    @RequestMapping({"/", "main"})
    public String main(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<Blog> blogs = blogService.findPage(filters, pageable);
        
        for(Blog blog : blogs) {
        	List<Vote> votes = voteService.findBy("blogId", SearchFilter.Operator.EQ, blog.getId());
            blog.setVoteNum((long)votes.size());
        }
        model.addAttribute("blogs", blogs);

        return "main";
    }

    @ModelAttribute("lastBlogs")
    public Page<Blog> lastBlogs() {
        PageRequest pageRequest = new PageRequest(0, 10);
        return blogService.findPage(new HashMap<String, SearchFilter>(), pageRequest);
    }

    @ModelAttribute("hotBlogs")
    public Page<Blog> hotBlogs() {
        PageRequest pageRequest = new PageRequest(0, 10);
        return blogService.findPage(new HashMap<String, SearchFilter>(), pageRequest, new Sort(Sort.Direction.DESC, "readNum"));
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("tags")
    public List<Tag> tags() {
        return tagService.findAll();
    }
}
