    <script src="/-ui/-dl/ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="/-ui/vendors/@coreui/coreui-pro/js/coreui.bundle.min.js"></script>
    <script src="/jslib/ckeditor/ckeditor.js"></script>
<!--    <script src="/-ui/js/AutoSave.js"></script> -->
<!--   <script src="/-ui/-dl/cdn.ckeditor.com/ckeditor5/custom/build/translations/ja.js"></script> -->
    <script src="/-ui/js/tooltips.js"></script>
    <script src="/-ui/js/jquery.datetimepicker.full.min.js"></script>
	<script src="/-ui/js/imagesloaded.pkgd.min.js"></script>
	<script src="/-ui/vendors/datatables.net/js/jquery.dataTables.js"></script>
	<script src="/-ui/vendors/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>

<!--	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.8.7/chosen.min.css"> -->
<!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.8.7/chosen.jquery.min.js"></script> -->

  	<script src="/-ui/vendors/chosen/chosen.jquery.js"></script>
	<script src="/-ui/-dl/cdn.datatables.net/fixedheader/3.1.7/js/dataTables.fixedHeader.min.js"></script>
  	<script src="/-ui/js/datatables.js"></script>
	<script src="/-ui/-dl/cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.min.js"></script>
    <script src="/-ui/-dl/cdn.jsdelivr.net/npm/@yaireo/tagify.min.js"></script>
    <script src="/-ui/-dl/cdn.jsdelivr.net/npm/@yaireo/tagify.polyfills.min.js"></script>
    <script src="/-ui/-dl/unpkg.com/scroll-hint@latest/js/scroll-hint.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/emoji-picker-element@^1/index.js" type="module"></script>
    <script src="/-ui/js/line_main.js"></script>
    <script src="/-ui/-dl/js.pusher.com/7.2/pusher.min.js"></script>

    <!--[if IE]-->
    <script src="/-ui/vendors/@coreui/icons/js/svgxuse.min.js"></script>
    <!--<![endif]-->
    <script language='javascript'>
    		window.onload = function(){applyMask('none');}

			// マスキングの設定
			function applyMask( mode ){
				$("#LOADING_FRAME").css("display", mode );
			}

			const RELOAD_SPAN = 120000; // 2分
//			const RELOAD_SPAN = 1200000; // 20 分
			function reload(){
				document.getElementById('anchor').src ='anchor.php' + '?' + Date.now();
			}
			$( document ).ready(function() {
				setInterval(reload, RELOAD_SPAN);

				// メニューのミニマイザー
				$("#minimizer").on('mouseout',function(){
					let sbc;
					let msg;
					let cval = document.getElementById('sidebar').className;
					if( cval.includes('c-sidebar-minimized') ) {
						sbc='minimized';
					} else {
						sbc='none';
					}
					jQuery.ajax({
						type: 'POST',
						url: 'menu.php',
						data: {'sbcls': sbc },
						async : false,
					})
				});
			});

	</script>