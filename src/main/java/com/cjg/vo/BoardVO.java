package com.cjg.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class BoardVO {
	
	private int level;
	private int articleNO;
	private int parentNO;
	private String title;
	private String content;
	private Date writeDate;
	private String id;
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getArticleNO() {
		return articleNO;
	}
	public void setArticleNO(int articleNO) {
		this.articleNO = articleNO;
	}
	public int getParentNO() {
		return parentNO;
	}
	public void setParentNO(int parentNO) {
		this.parentNO = parentNO;
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
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "BoardVO [articleNO=" + articleNO + ", parentNO=" + parentNO + ", title=" + title + ", content="
				+ content + ", writeDate=" + writeDate + ", id=" + id + "]";
	}
	
	
	
	
	

}
