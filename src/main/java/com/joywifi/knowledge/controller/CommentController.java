package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.entity.Comment;
import com.joywifi.knowledge.service.CommentService;
import com.joywifi.knowledge.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<Comment> comments = commentService.findPage(filters, pageable);

        model.addAttribute("comments", comments);
        return "comment/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.get(id);
        model.addAttribute("comment", comment);
        return "comment/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new(Model model) {
        return "comment/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Comment comment, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "comment/new";
        }

        commentService.save(comment);

        redirectAttributes.addFlashAttribute("msg", "新增评论成功");
        return "redirect:/comment/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.get(id);
        model.addAttribute("comment", comment);

        return "comment/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") String id, Comment comment, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "comment/edit";
        }

        commentService.update(id, comment);

        redirectAttributes.addFlashAttribute("msg", "更新评论成功");
        return "redirect:/comment/";
    }

}
