package com.joywifi.knowledge.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.joywifi.knowledge.security.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.entity.Category;
import com.joywifi.knowledge.repository.CategoryDao;

@Service
public class CategoryService extends BaseService<Category, String> {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BlogService blogService;

    public void updateCategory(String id, Category category) {
        Category dbCategory = get(id);
        String title = dbCategory.getTitle();
        update(id, category);
        List<Blog> blogs = blogService.findBy("category.title", SearchFilter.Operator.EQ, title);
        Category newCategory = get(id);
        for (Blog blog : blogs) {
            blog.setCategory(newCategory);
            blogService.save(blog);
        }

    }

    public Map<String, SearchFilter> baseFilter(String categotyId) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        System.out.println("当前用户：" + shiroUser.getUsername());
        filters.put("creator._id",new SearchFilter("creator._id", SearchFilter.Operator.EQ, shiroUser.getId()));
        filters.put("categoryId",new SearchFilter("categoryId", SearchFilter.Operator.EQ, categotyId));
        return filters;
    }
}
