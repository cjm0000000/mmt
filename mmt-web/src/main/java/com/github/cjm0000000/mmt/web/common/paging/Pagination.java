package com.github.cjm0000000.mmt.web.common.paging;

/**
 * 分页帮助类
 * @author lemon
 * @version 1.1
 *
 */
public class Pagination {
	/** 当前页 */
	private int currentPage;
	/** 每页记录数 */
	private int pageSize;
	/** 总记录数 */
	private int resCount;
	/** 分页查询条件 */
	private String filters;
	
	public Pagination(int currentPage, int pageSize, int resCount){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.resCount = resCount;
	}
	
	public Pagination(int currentPage, int pageSize, int resCount, String filters){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.resCount = resCount;
		this.filters = filters;
	}

	/**
	 * 获取当前页
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 获取每页记录数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取记录数
	 * @return
	 */
	public int getResCount() {
		return resCount;
	}
	
	/**
	 * 获取分页查询条件
	 * @return
	 */
	public String getFilters() {
		return filters;
	}

	/**
	 * 获取最后一页页码
	 * @return
	 */
	public int getLastPage(){
		int lastPage = resCount/pageSize;
		if(resCount % pageSize != 0)
			lastPage ++;
		if(lastPage <= 0)
			lastPage = 1;
		return lastPage;
	}
}
