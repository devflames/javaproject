      <img src="anchor.php?$!{TODAY.MilliSecond}" width="0" height="0" border="0" alt="" id="anchor">
      <div id="LOADING_FRAME" style="display:block;" class="loading">Loading&#8230;</div>
      <header class="c-header c-header-light c-header-fixed">
        <button class="c-header-toggler c-class-toggler d-lg-none mfe-auto" type="button" data-target="#sidebar" data-class="c-sidebar-show">
          <svg class="c-icon c-icon-lg">
            <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-menu"></use>
          </svg>
        </button>
        <a class="c-header-brand d-lg-none c-header-brand-sm-up-center" href="#">
          <img src="/-ui/assets/brand/autobiz_logo.svg" width="" height="24" alt="AutoBiz">
        </a>
        <button class="c-header-toggler c-class-toggler mfs-3 d-md-down-none" type="button" data-target="#sidebar" data-class="c-sidebar-lg-show" responsive="true">
          <svg class="c-icon c-icon-lg">
            <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-menu"></use>
          </svg>
        </button>
        <div class="col-lg-5 d-md-down-none p-2">
        #if( $!{ASP_TYPE} == 2 )
        	#set( $action = "pro" )
        #else
        	#set( $action ="basic" )
        #end
        <form id="SEARCH_FORM" action="https://autobiz.help/$!{action}/" method="get" target="_BLANK">
          <div class="input-group">
            <input class="form-control bg-light" name="s" type="text" placeholder="オンラインヘルプを検索">
            <span class="input-group-append">
              <button class="btn btn-secondary" type="button" onClick="document.getElementById('SEARCH_FORM').submit();">
                <svg class="c-icon">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-magnifying-glass"></use>
                </svg>
              </button>
            </span>
          </div>
        </form>
        </div>
        <ul class="c-header-nav d-md-down-none mfs-auto">
          <li class="c-header-nav-item mr-2">
            <a class="c-header-nav-link" href="https://autobiz.help/$!{action}/" target="_blank">
              オンラインヘルプ
            </a>
          </li>
          <li class="c-header-nav-item mr-2">
            <a class="c-header-nav-link" href="https://autobiz.help/$!{action}/inquiry/" target="_blank">
              お問い合わせ
            </a>
          </li>
          <li class="c-header-nav-item dropdown d-md-down-none mr-3">
            <a class="c-header-nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <svg class="c-icon mfe-2">
                <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-user"></use>
              </svg> $!{ACCOUNT}
            </a>
            <div class="dropdown-menu dropdown-menu-right dropdown-menu-lg">
              <a class="dropdown-item" href="https://ssl24.net/~abuser/user/index.php" target="_blank">
                <svg class="c-icon mfe-2">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-notes"></use>
                </svg> 契約更新・変更・各種手続き
              </a>
              <a class="dropdown-item" href="logout.php">
                <svg class="c-icon mfe-2">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-account-logout"></use>
                </svg> ログアウト
              </a>
            </div>
          </li>
        </ul>
        <ul class="c-header-nav d-lg-none mfs-auto">
          <li class="c-header-nav-item dropdown mr-2">
            <a class="c-header-nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <svg class="c-icon">
                <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-user"></use>
              </svg>
            </a>
            <div class="dropdown-menu dropdown-menu-right">
              <a class="dropdown-item" href=""https://autobiz.help/basic/" target="_blank">
                <svg class="c-icon mfe-2">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-lightbulb"></use>
                </svg> オンラインヘルプ
              </a>
              <a class="dropdown-item" href="https://autobiz.help/basic/inquiry/" target="_blank">
                <svg class="c-icon mfe-2">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-comment-bubble"></use>
                </svg> お問い合わせ
              </a>
              <a class="dropdown-item" href="https://ssl24.net/~abuser/user/index.php" target="_blank">
                <svg class="c-icon mfe-2">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-notes"></use>
                </svg> 契約更新・変更・各種手続き
              </a>
              <a class="dropdown-item" href="logout.php">
                <svg class="c-icon mfe-2">
                  <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-account-logout"></use>
                </svg> ログアウト
              </a>
            </div>
          </li>
        </ul>
        <div class="c-subheader d-none d-md-block d-lg-none p-2">
          <div class="col-lg-6">
            <div class="input-group">
              <input class="form-control bg-light" type="text" placeholder="オンラインヘルプを検索可能です。">
              <span class="input-group-append">
                <button class="btn btn-secondary" type="button">
                  <svg class="c-icon">
                    <use xlink:href="/-ui/vendors/@coreui/icons/svg/free.svg#cil-magnifying-glass"></use>
                  </svg>
                </button>
              </span>
            </div>
          </div>
        </div>
      </header>