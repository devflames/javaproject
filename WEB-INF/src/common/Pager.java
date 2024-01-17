package common;

public class Pager {

	private int list_count;
	private int now_page;
	private int limit;

	public Pager(int limit, int count) {
		this.limit = limit;
	    this.list_count = count;
		this.now_page = 1;
	}

	public int getList_count() { return list_count; }
	public void setList_count(int list_count) { this.list_count = list_count; }

	public int getNow_page() { return now_page; }
	public void setNow_page(int now_page) {
		this.now_page = ( now_page > 0 ) ? now_page : 1;
	}

	public int getPage_max() {
		int page_max;
		page_max = (this.list_count / this.limit);
		if ( this.list_count % this.limit > 0 ) page_max++;
	    return page_max;
	}

	public int getPage_st() {
		int page_st;
		page_st = ((this.now_page - 1) * this.limit) + 1;
	    return page_st;
	}

	public int getPage_ed() {
		int page_ed;
		page_ed = (this.list_count < (this.now_page * this.limit)) ? this.list_count : (this.now_page * this.limit);
	    return page_ed;
	}

	public String getStart() { return util.toStr(this.getPage_st() - 1); }
	public String getLimit() { return util.toStr(this.limit); }

}
