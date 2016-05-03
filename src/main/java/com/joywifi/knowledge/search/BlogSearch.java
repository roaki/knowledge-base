package com.joywifi.knowledge.search;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import javax.annotation.PreDestroy;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.mapper.JsonMapper;

import com.joywifi.knowledge.entity.Blog;
import com.joywifi.knowledge.exception.CustomException;

public class BlogSearch {

    private String index = "knowledge";
    private String type = "blog";
    private Client client;

    @Autowired
    public BlogSearch(String searchCluster) {
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(searchCluster), 9300));
        } catch (UnknownHostException e) {
            throw new CustomException("主机名不正确");
        }
    }

    public IndexResponse add(Blog blog) {
        return client.prepareIndex(index, type, blog.getId())
                .setSource(JsonMapper.nonDefaultMapper().toJson(blog)).get();
    }

    public GetResponse get(String blogId) {
        return client.prepareGet(index, type, blogId).get();
    }

    public DeleteResponse delete(String blogId) {
        return client.prepareDelete(index, type, blogId).get();
    }

    public UpdateResponse update(Blog blog) {
        UpdateRequest updateRequest = new UpdateRequest(index, type, blog.getId()).doc(JsonMapper.nonDefaultMapper().toJson(blog));

        try {
            return client.update(updateRequest).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SearchResponse search(String content) {
        return client.prepareSearch(index)
                .setTypes(type).setQuery(QueryBuilders.matchQuery("content", content))
                .setFrom(0).setSize(20).execute().actionGet();
    }

    @PreDestroy
    public void destory() {
        if (client != null) {
            client.close();
        }
    }
}
