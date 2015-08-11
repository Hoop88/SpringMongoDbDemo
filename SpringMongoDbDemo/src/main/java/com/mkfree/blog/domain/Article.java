package com.mkfree.blog.domain;

/**
 * 博客实体类
 * 
 * @author hk 2012-11-1 下午10:55:38
 */
public class Article {
	private String id;// 博客ID
	private String title;// 博客标题
	private String content;// 博客内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

}
