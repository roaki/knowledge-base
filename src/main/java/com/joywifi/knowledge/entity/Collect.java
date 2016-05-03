package com.joywifi.knowledge.entity;

import org.springframework.data.annotation.Transient;

public class Collect extends BaseEntity {

	private String blogId;
	
	@Transient
	private Blog blog;

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	
}
