/**
 * --------------------------------------------------------------------------
 * CoreUI Pro Boostrap Admin Template (3.2.0): datatables.js
 * Licensed under MIT (https://coreui.io/license)
 * --------------------------------------------------------------------------
 */
var winW = $(window).width();

if ($('.data-table.is-scrollX').length > 0 && winW > 576) {
  $('.data-table').addClass('nowrap');
  var _scrollX = true;
} else {
  var _scrollX = false;
}

$('.data-table').DataTable({
  language: {
    "sProcessing": "処理中...",
    "sLengthMenu": "_MENU_ 件表示",
    "sZeroRecords": "データはありません。",
    "sInfo": " _TOTAL_ 件中 _START_ から _END_ まで表示",
    "sInfoEmpty": " 0 件中 0 から 0 まで表示",
    "sInfoFiltered": "（全 _MAX_ 件より抽出）",
    "sInfoPostFix": "",
    "sSearch": "検索:",
    "sUrl": "",
    "oPaginate": {
      "sFirst": "先頭",
      "sPrevious": "前",
      "sNext": "次",
      "sLast": "最終"
    }
  },
  searching: false,
  lengthChange: false,
  paging: false,
  info: false,
  responsive: true,
  columnDefs: [{
    orderable: true,
    targets: 'is-sort'
  }, {
    orderable: false,
    targets: '_all'
  }],
  select: {
    style: 'os',
    selector: 'td:first-child'
  },
  autoFill: {
    columns: ':not(:first-child)'
  },
  order: [],
  fixedHeader: true,
  scrollX: _scrollX
}).fixedHeader.headerOffset(50);
$('.data-table').attr('style', 'border-collapse: collapse !important');
//# sourceMappingURL=datatables.js.map