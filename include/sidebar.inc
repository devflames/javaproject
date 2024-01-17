
    <div class="c-sidebar c-sidebar-dark c-sidebar-fixed c-sidebar-lg-show $!{MINIMIZE_CLASS}" id="sidebar">
    #if( $!{ASP_TYPE} == 2 )
			<div class="c-sidebar-brand d-md-down-none">
				<img class="c-sidebar-brand-full" src="/-ui/assets/brand/autobiz_logo_white.svg" width="" height="24" alt="AutoBiz">
				<img class="c-sidebar-brand-minimized" src="/-ui/assets/brand/autobiz_logo_mini.svg" width="" height="30" alt="AutoBiz">&nbsp;Pro
			</div>
			<ul class="c-sidebar-nav">
				<li class="c-sidebar-nav-item">
					<a class="c-sidebar-nav-link" href="top.php">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-home"></use>
						</svg> TOP
					</a>
				</li>
				<li class="c-sidebar-nav-dropdown">
					<a class="c-sidebar-nav-dropdown-toggle" href="#">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-grid"></use>
						</svg> オートレスポンダー
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ar_edit_thread.php">スレッド一覧</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ar_page_make.php">登録ページ・フォーム</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ar_pageC_make.php">登録内容確認ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a	class="c-sidebar-nav-link" href="ar_pageU_make.php">登録完了ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ar_reply_frame.php">自動返信メール作成</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ar_schedule_group.php">時間帯別挿入文</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ar_phrase_kwd.php">項目別挿入文</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="ar_data_mng.php">リスト一覧</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="ar_option_mng.php"> 決済サービス連携</a>
						</li>
					</ul>
				</li>
				<li class="c-sidebar-nav-dropdown">
					<a class="c-sidebar-nav-dropdown-toggle" href="#">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-list-numbered"></use>
						</svg> ステップメール
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="st_edit_scenario.php">シナリオ一覧</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_page_make.php"> 登録ページ・フォーム</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_pageC_make.php"> 登録内容確認ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_pageU_make.php"> 登録完了ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_pageK_make.php"> 配信解除完了ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_plan_frame.php"> ステップメール作成</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="st_plan_monitor.php"> 配信管理</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_data_mng.php">リスト一覧</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link " href="st_option_mng.php"> 決済サービス連携</a>
						</li>
					</ul>
				</li>
    #else
			<div class="c-sidebar-brand d-md-down-none">
				<img class="c-sidebar-brand-full" src="/-ui/assets/brand/autobiz_logo_white.svg" width="" height="24" alt="AutoBiz">
				<img class="c-sidebar-brand-minimized" src="/-ui/assets/brand/autobiz_logo_mini.svg" width="" height="30" alt="AutoBiz">
			</div>
			<ul class="c-sidebar-nav">
				<li class="c-sidebar-nav-item">
					<a class="c-sidebar-nav-link" href="top.php">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-home"></use>
						</svg> TOP
					</a>
				</li>
				<li class="c-sidebar-nav-item">
					<a class="c-sidebar-nav-link" href="edit_scenario.php">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-spreadsheet"></use>
						</svg> シナリオ一覧
					</a>
				</li>
				<li class="c-sidebar-nav-dropdown">
					<a class="c-sidebar-nav-dropdown-toggle" href="javascript:void(0);">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-grid-slash"></use>
						</svg> フォーム/ページ
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="page_make.php"> 登録ページ・フォーム</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="pageC_make.php"> 登録内容確認ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="pageU_make.php"> 登録完了ページ</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="pageK_make.php"> 配信解除完了ページ</a>
						</li>
					</ul>
				</li>
				<li class="c-sidebar-nav-dropdown">
					<a class="c-sidebar-nav-dropdown-toggle" href="javascript:void(0);">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-list-numbered"></use>
						</svg> ステップメール
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="plan_frame.php"> ステップメール作成</a>
						</li>
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="plan_monitor.php"> 配信管理</a>
						</li>
					</ul>
				</li>
				<li class="c-sidebar-nav-item">
					<a class="c-sidebar-nav-link" href="data_mng.php">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-address-book"></use>
						</svg> リスト一覧
					</a>
				</li>
    #end
    #if( $!{ASP_PLAN} !=  5 )
        #if( $!{PAGE_PATH} == "/NMH0010.htm" || $!{PAGE_PATH} == "/NMH0011.htm" || $!{PAGE_PATH} == "/NMH0012.htm" || $!{PAGE_PATH} == "/NMH0011K.htm"  || $!{PAGE_PATH} == "/NMH0012K.htm")
        	#set( $show_class3 = "c-show" )
            #set( $active_class1 = "c-active" )
        #elseif(
        	$!{PAGE_PATH} == "/NMH0020.htm" || $!{PAGE_PATH} == "/NMH0021.htm" ||
        	$!{PAGE_PATH} == "/NMH0022.htm" || $!{PAGE_PATH} == "/NMH0021K.htm" ||
        	$!{PAGE_PATH} == "/NMH0022K.htm" || $!{PAGE_PATH} == "/NMH0021S.htm" ||
        	$!{PAGE_PATH} == "/NMH0022S.htm" || $!{PAGE_PATH} == "/NMH0041.htm" ||
        	$!{PAGE_PATH} == "/NMH0041EX.htm" || $!{PAGE_PATH} == "/NMH0042.htm"
        	)
        	#set( $show_class3 = "c-show" )
        	#set( $active_class2 = "c-active" )
        #elseif( $!{PAGE_PATH} == "/NMH0030.htm" || $!{PAGE_PATH} == "/NMH0031.htm" || $!{PAGE_PATH} == "/NMH0032.htm")
        	#set( $show_class3 = "c-show" )
        	#set( $active_class3 = "c-active" )
        #elseif( $!{PAGE_PATH} == "/NMH0040EX.htm" || $!{PAGE_PATH} == "/NMH0050.htm")
        	#set( $show_class3 = "c-show" )
        	#set( $active_class4 = "c-active" )
        #end
				<li class="c-sidebar-nav-dropdown  $!{show_class3}">
					<a class="c-sidebar-nav-dropdown-toggle" href="">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-envelope-closed"></use>
						</svg> メルマガ配信
					</a>


					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
						  #if( $!{SERVER_ID} == 6 )
						  	<a class="c-sidebar-nav-link $!{active_class1}" href="/admin/php2java.php?file=NMH0010"> メルマガ一覧</a>
						  #else
						  	<a class="c-sidebar-nav-link $!{active_class1}" href="/$!{ACCOUNT}/admin/php2java.php?file=NMH0010"> メルマガ一覧</a>
						  #end
						</li>
						<li class="c-sidebar-nav-item">
						#if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class2}" href="/admin/php2java.php?file=NMH0020"> メール一覧</a>
						#else
							<a class="c-sidebar-nav-link $!{active_class2}" href="/$!{ACCOUNT}/admin/php2java.php?file=NMH0020"> メール一覧</a>
						#end
						</li>
						<li class="c-sidebar-nav-item">
						#if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class3}" href="/admin/php2java.php?file=NMH0030"> 購読者設定一覧</a>
						#else
							<a class="c-sidebar-nav-link $!{active_class3}" href="/$!{ACCOUNT}/admin/php2java.php?file=NMH0030"> 購読者設定一覧</a>
						#end
						</li>
						<li class="c-sidebar-nav-item">
						#if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class4}" href="/admin/php2java.php?file=NMH0040EX&TYPE=1"> 予約/配信結果一覧</a>
						#else
							<a class="c-sidebar-nav-link $!{active_class4}" href="/$!{ACCOUNT}/admin/php2java.php?file=NMH0040EX&TYPE=1"> 予約/配信結果一覧</a>
						#end
						</li>
					</ul>
				</li>
				<li class="c-sidebar-nav-item">
        #if( $!{SERVER_ID} == 6 )
					<a class="c-sidebar-nav-link" href="/admin/php2java.php?file=NMH0070&if=2">
        #else
					<a class="c-sidebar-nav-link" href="/$!{ACCOUNT}/admin/php2java.php?file=NMH0070&if=2">
        #end
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-mouse"></use>
						</svg> クリック測定
					</a>
				</li>
    #end
    #if( $!{PAGE_PATH} == "/NMH0062.htm")
        #set( $show_class = "c-show" )
        #set( $active_class2 = "c-active" )
    #end
				<li class="c-sidebar-nav-dropdown $!{show_class}">
					<a class="c-sidebar-nav-dropdown-toggle" href="option_mng.php">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-applications"></use>
						</svg> その他の機能
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
    #if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class2}" href="/admin/php2java.php?file=NMH0062"> メディア管理</a>
    #else
							<a class="c-sidebar-nav-link $!{active_class2}" href="/$!{ACCOUNT}/admin/php2java.php?file=NMH0062"> メディア管理</a>
    #end
						</li>
    #if( $!{ASP_TYPE} != 2 )
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="option_mng.php"> 決済サービス連携</a>
						</li>
    #end
					</ul>
				</li>

    #if( $!{SERVER_ID} == 6 )
				<li class="c-sidebar-nav-dropdown">
					<a class="c-sidebar-nav-dropdown-toggle" href="#">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-at"></use>
						</svg> メ―ルアドレス作成
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
							<a class="c-sidebar-nav-link" href="ad_email_mng.php">メールアドレス一覧</a>
						</li>
					</ul>
				</li>
    #end
	#if( $!{ASP_PLAN} !=  5  && $!{ASP_PLAN} != 3 )

        #if( $!{PAGE_PATH} == "/LIN0000.htm")
            #set( $show_class2 = "c-show" )
            #set( $active_class3 = "c-active" )
        #elseif( $!{PAGE_PATH} == "/LIN0012.htm" )
            #set( $show_class2 = "c-show" )
            #set( $active_class4 = "c-active" )
        #elseif( $!{PAGE_PATH} == "/LIN0021.htm" )
            #set( $show_class2 = "c-show" )
            #set( $active_class5 = "c-active" )
        #elseif( $!{PAGE_PATH} == "/LIN0020.htm" )
            #set( $show_class2 = "c-show" )
            #set( $active_class6 = "c-active" )
        #elseif( $!{PAGE_PATH} == "/LIN0030.htm" )
            #set( $show_class2 = "c-show" )
            #set( $active_class7 = "c-active" )
        #end
				<li class="c-sidebar-nav-dropdown $!{show_class2}">
					<a class="c-sidebar-nav-dropdown-toggle" href="#">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/sprites/brand.svg#cib-line"></use>
						</svg> LINE連携
					</a>
					<ul class="c-sidebar-nav-dropdown-items">
						<li class="c-sidebar-nav-item">
        #if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class3}" href="/admin/php2java.php?file=LIN0000&if=3"> ダッシュボード</a>
        #else
							<a class="c-sidebar-nav-link $!{active_class3}" href="/$!{ACCOUNT}/admin/php2java.php?file=LIN0000&if=3"> ダッシュボード</a>
        #end
						</li>
						<li class="c-sidebar-nav-item">
        #if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class4}" href="/admin/php2java.php?file=LIN0011ST1&if=3"> 連携設定</a>
        #else
							<a class="c-sidebar-nav-link $!{active_class4}" href="/$!{ACCOUNT}/admin/php2java.php?file=LIN0011ST1&if=3"> 連携設定</a>
        #end
						</li>
						<li class="c-sidebar-nav-item">
        #if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class5}" href="/admin/php2java.php?file=LIN0021&if=3"> 個別トーク</a>
        #else
							<a class="c-sidebar-nav-link $!{active_class5}" href="/$!{ACCOUNT}/admin/php2java.php?file=LIN0021&if=3"> 個別トーク</a>
        #end
						</li>
						<li class="c-sidebar-nav-item">
        #if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class6}" href="/admin/php2java.php?file=LIN0020&if=3"> 友だちリスト一覧</a>
        #else
							<a class="c-sidebar-nav-link $!{active_class6}" href="/$!{ACCOUNT}/admin/php2java.php?file=LIN0020&if=3"> 友だちリスト一覧</a>
        #end
						</li>
						<li class="c-sidebar-nav-item">
        #if( $!{SERVER_ID} == 6 )
							<a class="c-sidebar-nav-link $!{active_class7}" href="/admin/php2java.php?file=LIN0030&if=3"> 一斉配信メッセージ一覧</a>
        #else
							<a class="c-sidebar-nav-link $!{active_class7}" href="/$!{ACCOUNT}/admin/php2java.php?file=LIN0030&if=3"> 一斉配信メッセージ一覧</a>
        #end
						</li>
					</ul>
				</li>
    #end
    #if( $!{ASP_PLAN} !=  5 )
				<li class="c-sidebar-nav-item mt-auto">
					<a class="c-sidebar-nav-link c-sidebar-nav-link-info" href="https://03auto.biz/LimitList/Login.htm" target="_blank">
						<svg class="c-sidebar-nav-icon">
							<use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-exit-to-app"></use>
						</svg> 簡易メルマガ
					</a>
				</li>
    #end
			</ul>
      <button class="c-sidebar-minimizer c-class-toggler" type="button" data-target="_parent" data-class="c-sidebar-minimized" id="minimizer"></button>
    </div>