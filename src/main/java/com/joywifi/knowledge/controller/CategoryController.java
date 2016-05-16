package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.entity.Category;
import com.joywifi.knowledge.service.CategoryService;
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
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<Category> categories = categoryService.findPage(filters, pageable);

        model.addAttribute("categories", categories);
        return "category/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "category/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new() {
        return "category/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "category/new";
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("msg", "新增分类成功");
        return "redirect:/category/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("category", category);

        return "category/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") String id, Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "category/edit";
        }

        categoryService.updateCategory(id, category);

        redirectAttributes.addFlashAttribute("msg", "更新分类成功");
        return "redirect:/category/";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
//        Map<String, SearchFilter> filters = categoryService.baseFilter(id);
//        List<Category> categories = categoryService.findBy(filters);
//        categoryService.delete(categories);

        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功!");
        return "redirect:/category/";
    }

}
