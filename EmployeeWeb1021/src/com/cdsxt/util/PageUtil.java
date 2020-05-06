package com.cdsxt.util;

public class PageUtil {
	private int dataCount;
	private int curPage;
	private int startRow;
	private int dataStep=10;
	private int pageCount;
	private int prePage;
	private int nextPage;
	private int homePage=1;
	private int endPage;
	private int leftNav;
	private int rightNav;
	
	public PageUtil(int dataCount, int curPage) {
		this.dataCount = dataCount;
		this.curPage = curPage;
		this.startRow=(curPage-1)*dataStep;
		this.pageCount=dataCount%dataStep!=0?(dataCount/dataStep+1):dataCount/dataStep;
		this.prePage=curPage==1?1:curPage-1;
		this.nextPage=curPage==pageCount?pageCount:curPage+1;
		this.endPage=pageCount;
		if(pageCount<10){
			leftNav=1;
			rightNav=pageCount;
		}else{ 
			if(curPage<=6){
				leftNav=1;
				rightNav=10;
			}else if(curPage>=pageCount-4){
				leftNav=pageCount-dataStep-1;
				rightNav=pageCount;
			}else{
				leftNav=curPage-5;
				rightNav=curPage+4;
			}
		}
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getDataStep() {
		return dataStep;
	}

	public void setDataStep(int dataStep) {
		this.dataStep = dataStep;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getHomePage() {
		return homePage;
	}

	public void setHomePage(int homePage) {
		this.homePage = homePage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getLeftNav() {
		return leftNav;
	}

	public void setLeftNav(int leftNav) {
		this.leftNav = leftNav;
	}

	public int getRightNav() {
		return rightNav;
	}

	public void setRightNav(int rightNav) {
		this.rightNav = rightNav;
	}
	

}
