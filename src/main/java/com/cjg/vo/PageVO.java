package com.cjg.vo;

import org.springframework.stereotype.Component;

@Component
public class PageVO {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	
	
	public String[] getTypeArr(){
		return type == null? new String[]{} : type.split("");
	}
	

	@Override
	public String toString() {
		return "PageVO [pageNum=" + pageNum + ", amount=" + amount + ", type=" + type + ", keyword=" + keyword
				+ ", startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + ", total="
				+ total + "]";
	}
	
	public PageVO(){
		this.pageNum=1;
		this.startPage=1;
		this.amount=20;
	}

	public PageVO(int pageNum, int amount, String type, String keyword, int total){
		this.pageNum = pageNum;
		this.amount = amount;
		this.type = type;
		this.keyword = keyword;
		this.total = total;
		
		process();

	}
	
	public void process(){
		//페이지를 10단위로 만듬.
		endPage = (int)(Math.ceil(pageNum/10.0))*10;
		startPage = endPage-9;
		
		//만약에 실제 페이지가 15까지인 경우 페이지를 20까지 표시하는것 방지.
		//위에 endPage로 사용할 경우 20까지 표시가 된다.
		int realEnd = (int)(Math.ceil(total*1.0/amount));
		
		if(realEnd<endPage){
			this.endPage = realEnd;
		}
		prev = startPage>1;
		next = endPage<realEnd;
	};
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
