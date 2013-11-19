package lemon.web.base.paging;

/**
 * 分页帮助类
 * @author lemon
 * @version 1.1
 *
 */
public class Pagination {
	private int currentPage;
	
	private int pageSize;
	
	private int resCount;
	
	public Pagination(int currentPage, int pageSize, int resCount){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.resCount = resCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getResCount() {
		return resCount;
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
