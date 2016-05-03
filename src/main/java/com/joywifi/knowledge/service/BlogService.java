package com.joywifi.knowledge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.entity.Category;
import com.joywifi.knowledge.entity.Comment;
import com.joywifi.knowledge.entity.Tag;
import com.joywifi.knowledge.repository.BlogDao;
import com.joywifi.knowledge.security.ShiroDbRealm.ShiroUser;

@Service
public class BlogService extends BaseService<Blog, String> {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    public void createBlog(Blog blog, String categoryTitle, String tagTitles) {
        Category category = categoryService.findOne("title", SearchFilter.Operator.EQ, categoryTitle);
        if (category == null) {
            category = new Category();
            category.setTitle(categoryTitle);
            categoryService.save(category);
        }

        List<Tag> tags = Lists.newArrayList();
        for (String tagTitle : StringUtils.split(tagTitles, ",")) {
            Tag tag = tagService.findOne("title", SearchFilter.Operator.EQ, tagTitle);
            if (tag == null) {
                tag = new Tag();
                tag.setTitle(tagTitle);
                tagService.save(tag);
            }
            tags.add(tag);
        }

        blog.setReadNum(0L);
        blog.setVoteNum(0L);
//        blog.setStarNum(0L);
        blog.setScore(0.0f);
        blog.setComments(new ArrayList<Comment>());
        blog.setCategory(category);
        blog.setTags(tags);

        blogDao.save(blog);
    }
    
    public Map<String, SearchFilter> baseFilter(String blogId) {
    	Map<String, SearchFilter> filters = Maps.newHashMap();
    	ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
    	System.out.println("当前用户：" + shiroUser.getUsername());
    	filters.put("creator._id",new SearchFilter("creator._id", SearchFilter.Operator.EQ, shiroUser.getId()));
    	filters.put("blogId",new SearchFilter("blogId", SearchFilter.Operator.EQ, blogId));
    	return filters;
	}
}
