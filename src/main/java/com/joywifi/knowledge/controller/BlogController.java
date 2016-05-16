package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.annotation.CurrentUserQuery;
import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.entity.Category;
import com.joywifi.knowledge.entity.Collect;
import com.joywifi.knowledge.entity.Vote;
import com.joywifi.knowledge.security.ShiroDbRealm.ShiroUser;
import com.joywifi.knowledge.service.BlogService;
import com.joywifi.knowledge.service.CategoryService;
import com.joywifi.knowledge.service.CollectService;
import com.joywifi.knowledge.service.VoteService;
import com.joywifi.knowledge.util.Constants;
import com.joywifi.knowledge.util.ShiroUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CollectService CollectService;
    @Autowired
    private VoteService voteService;

    @Autowired
    private RedisTemplate<String, Boolean> redisTemplate;

    @CurrentUserQuery
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request,
                Constants.SEARCH_PREFIX));
        Page<Blog> blogs = blogService.findPage(filters, pageable);

        model.addAttribute("blogs", blogs);
        return "blog/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, Model model) {
        Blog blog = blogService.get(id);

        ShiroUser currentUser = ShiroUtils.getCurrentUser();
        ValueOperations<String, Boolean> userOps = redisTemplate.opsForValue();
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
                curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        long cacheTime = tommorowDate.getTimeInMillis() - curDate.getTimeInMillis();
        if (userOps.get(currentUser + id) == null) {
            userOps.set(currentUser + id, true, cacheTime, TimeUnit.MILLISECONDS);
            if (blog.getReadNum() != null) {
                blog.setReadNum(blog.getReadNum() + 1);
            } else {
                blog.setReadNum((long) 1);
            }
        }

        Map<String, SearchFilter> filters = blogService.baseFilter(id);
        List<Collect> collect = CollectService.findBy(filters);
        if (collect.size() == 0) {
            model.addAttribute("collect", "收藏");
        } else {
            model.addAttribute("collect", "取消收藏");
        }

        List<Vote> votes = voteService.findBy("blogId", SearchFilter.Operator.EQ, blog.getId());
        blog.setVoteNum((long) votes.size());

        blogService.save(blog);
        model.addAttribute("blog", blog);
        return "blog/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new(Model model) {
        initCategories(model);
        return "blog/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Blog blog, BindingResult result, @NotBlank String categoryTitle,
                         @NotNull String tagTitles, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "blog/new";
        }

        blogService.createBlog(blog, categoryTitle, tagTitles);

        redirectAttributes.addFlashAttribute("msg", "新增博客成功");
        return "redirect:/blog/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model) {
        Blog blog = blogService.get(id);
        model.addAttribute("blog", blog);

        initCategories(model);

        return "blog/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") String id, Blog blog, BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "blog/edit";
        }

        blogService.update(id, blog);

        redirectAttributes.addFlashAttribute("msg", "更新博客成功");
        return "redirect:/blog/";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {

        blogService.delete(id);

        redirectAttributes.addFlashAttribute("msg", "删除用户成功");
        return "redirect:/blog/";
    }

    private void initCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
    }

}
