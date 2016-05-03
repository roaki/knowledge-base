package com.joywifi.knowledge.test;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springside.modules.mapper.JsonMapper;

import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.search.BlogSearch;
import com.joywifi.knowledge.service.BlogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-config.xml")
public class ElasticSearchTest {
    @Autowired
    BlogSearch blogSearch;
    @Autowired
    BlogService blogService;

    @Test
    public void test_add_index() {
        List<Blog> blogs = blogService.findAll();
        for (Blog blog : blogs) {
            blogSearch.add(blog);
        }
    }

    @Test
    public void test_search_blog() {
        String content = "elasticSearch";
        SearchResponse searchResponse = blogSearch.search(content);
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> map = searchHit.getSource();
            System.out.println(JsonMapper.nonDefaultMapper().toJson(map));
        }
    }
}
