package com.joywifi.knowledge.entity;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Blog extends BaseEntity {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String summary;

    private String level;//级别(初级,中级,高级)
    private Long collectNum;  // 收藏数量
    private Long voteNum;  // 赞数量
    private Float score;// 评分
    private Long readNum;// 阅读数
    private Category category;
    private List<Tag> tags;
    private List<Comment> comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Long collectNum) {
        this.collectNum = collectNum;
    }

    public Long getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Long voteNum) {
        this.voteNum = voteNum;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
