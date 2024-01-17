package common;

public class Pager2 {

	// 現在のページ
	private int current_page;

	// 総レコード数
	private int total;

	// 1ページに表示するレコード数
	private int page_rec;

	// 総ページ数
	private int total_page;

	// 表示するナビゲーション数
	private int show_nav = 5;

	private int loop_start;
	private int loop_end;



	public Pager2(int current, int limit, int count) {

		this.current_page =  ( current > 0 ) ? current : 1;
		this.page_rec = limit;
		this.total = count;
		this.total_page = (int)Math.ceil((double)this.total/this.page_rec);

		// 総ページ数が表示するナビゲーション数より小さい場合は、総ページ数を表示するナビゲーション数にする
		if( this.total_page < this.show_nav ) {
			this.show_nav = this.total_page;
		}

	    //トータルページ数が1以下か、現在のページが総ページより大きい場合表示しない
	    if ( this.total_page <= 1 || this.total_page < this.current_page ) return;

		// 総ページの半分
		int show_navh = (int)Math.floor(this.show_nav/2);

		// 現在のページをナビゲーションの中心にする
		this.loop_start = this.current_page - show_navh;
		this.loop_end = this.current_page + show_navh;

		// 現在のページが両端だったら端にくるようにする
		if( this.loop_start <= 0 ) {
			this.loop_start = 1;
			this.loop_end = this.show_nav;
		}

		if( this.loop_end > this.total_page ) {
			this.loop_start = this.total_page - this.show_nav + 1;
			this.loop_end = this.total_page;
		}
	}



	// cureent_page
	public int getCurrent_page() { return current_page; }
	public void setCurrent_page(int current_page) { this.current_page = current_page; }

	// total
	public int getTotal() { return total; }
	public void setTotal(int total) { this.total = total; }

	// page_rec
	public int getPage_rec() { return page_rec; }
	public void setPage_rec(int page_rec) { this.page_rec = page_rec; }

	// total_page
	public int getTotal_page() { return total_page; }
	public void setTotal_page(int total_page) { this.total_page = total_page; }

	// show_nav
	public int getShow_nav() { return show_nav; }
	public void setShow_nav(int show_nav) { this.show_nav = show_nav; }

	// loop_start
	public int getLoop_start() { return loop_start; }
	public void setLoop_start(int loop_start) { this.loop_start = loop_start; }

	// loop_end
	public int getLoop_end() { return loop_end; }
	public void setLoop_end(int loop_end) { this.loop_end = loop_end; }



	public int getPage_st() {
		int page_st;
		page_st = ((this.current_page - 1) * this.page_rec) + 1;
	    return page_st;
	}

	public int getPage_ed() {
		int page_ed;
		page_ed = (this.total < (this.current_page * this.page_rec)) ? this.total : (this.current_page * this.page_rec);
	    return page_ed;
	}

	public String getStart() { return util.toStr(this.getPage_st() - 1); }
	public String getLimit() { return util.toStr(this.page_rec); }

	// 使い方
	/*
	 * 総ページ数が2以下か、現在のページが総ページ数より多い場合は表示しない
	 *
	 * this.current_pageが1以上ならprevを表示
	 * this.loop_startからthis.loop_endまでをfor文で回してthis.current_pageに等しいのが現在のページ
	 * this.current_page < this.total_page ならnextを表示
	 */




}
