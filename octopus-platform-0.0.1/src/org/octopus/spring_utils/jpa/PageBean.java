/**
 * 
 */
package org.octopus.spring_utils.jpa;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengjiecai
 * @create time 2010-3-23
 */
public class PageBean<T> {
	private String actionUrl;

	private int totalRows;

	private int pageSize;// 条数

	private int currentPage; // 当前页

	private int totalPages;// 页数总数

	private List<T> dispList;

	private String countHQL;

	private String listHQL;

	public PageBean() {

	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public PageBean(int totalRows, int pageSize, int currentPage) {
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		this.currentPage = currentPage;
	}

	public PageBean(int pageSize, String countHQL, String listHQL) {
		this.pageSize = pageSize;
		this.countHQL = countHQL;
		this.listHQL = listHQL;
	}

	public boolean isValidPage() {
		if (this.totalPages > 0
				&& (this.currentPage < 1 || this.currentPage > this.totalPages)) {
			return false;
		} else {
			return true;
		}
	}

	public int getStartRow() {
		return (currentPage - 1) * pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public boolean isHasNextPage() {
		if (this.currentPage < this.totalPages) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isHasPreviousPage() {
		if (currentPage > 1) {
			return true;
		} else {
			return false;
		}
	}

	public void first() {
		currentPage = 1;
	}

	public void previous() {
		if (currentPage == 1) {
			return;
		}
		currentPage--;
	}

	public void next() {
		if (currentPage < totalPages) {
			currentPage++;
		}
	}

	public void last() {
		currentPage = totalPages;
	}

	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		if (currentPage > totalPages) {
			last();
		}
	}

	public List<T> getDispList() {
		if (null == this.dispList) {
			dispList = new ArrayList();
		}
		return dispList;
	}

	public void setDispList(List<T> dispList) {
		if (dispList != null)
			this.dispList = dispList;
		else
			this.dispList = new ArrayList<T>();
	}

	public int getNextPage() {
		return this.currentPage + 1;
	}

	public int getPreviousPage() {
		return this.currentPage - 1;
	}

	public String getCountHQL() {
		return countHQL;
	}

	public void setCountHQL(String countHQL) {
		this.countHQL = countHQL;
	}

	public String getListHQL() {
		return listHQL;
	}

	public void setListHQL(String listHQL) {
		this.listHQL = listHQL;
	}

}
