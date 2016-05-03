package com.joywifi.knowledge.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Lists;
import com.joywifi.knowledge.entity.Tag;
import com.joywifi.knowledge.service.TagService;
import com.joywifi.knowledge.util.Constants;

@Controller
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<Tag> tags = tagService.findPage(filters, pageable);

        model.addAttribute("tags", tags);
        return "tag/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, Model model) {
        Tag tag = tagService.get(id);
        model.addAttribute("tag", tag);
        return "tag/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new() {
        return "tag/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tag/new";
        }

        tagService.save(tag);

        redirectAttributes.addFlashAttribute("msg", "新增标签成功");
        return "redirect:/tag/";
    }

    @RequestMapping("add-blog-tags")
    @ResponseBody
    public List<String> addTags(String term){
        List<Tag> tags = tagService.findBy("title", SearchFilter.Operator.LIKE, term);
        List<String> tagTitles = Lists.newArrayList();
        for (Tag tag : tags) {
            tagTitles.add(tag.getTitle());
        }
        return tagTitles;
    }
}
