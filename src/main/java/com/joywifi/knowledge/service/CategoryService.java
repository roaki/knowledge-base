package com.joywifi.knowledge.service;

import java.util.List;

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
}
