$(function () {
  initEditor();
  initDatetimepicker();
  initSubMenu();
  initColorPikcer();
  initGraph();
  initSortable();
  selectEditor();
  tableScroll();
  tabAlert();
  checkToOpenCollapse();
  initEditLabelSwitcher();
  initTagsSuggest();
  initTagsListEdit();
  initEmojiPicker();
  initTextAreaAutoHeight();
});
var winW = $(window).width(); //チェックでコンテンツオープン

var checkToOpenCollapse = function () {
  if ($('#checkToOpenCollapse').length > 0) {
    var _check = $('#checkToOpenCollapse');

    _check.on('change', function () {
      if (_check.prop('checked')) {
        $('#checkToOpenCollapseContent').collapse('show');
      } else {
        $('#checkToOpenCollapseContent').collapse('hide');
      }
    });
  }
}; //スマホ用テーブルスクロール


var tableScroll = function () {
  if ($('.table-scroll-sm').length > 0 && winW < 576) {
    new ScrollHint('.table-scroll-sm', {
      suggestiveShadow: true,
      i18n: {
        scrollable: 'スクロールできます'
      }
    });
  }
}; //タブアラート


var tabAlert = function () {
  $('#editTab').on('click', function () {
    $('#templateTab').tab('show');
    $('#editModal').modal('show');
    $('#editModalButton').on('click', function () {
      $('#editModal').modal('hide');
      $('#editTab').tab('show');
    });
  });
  $('#templateTab').on('click', function () {
    $('#editTab').tab('show');
    $('#templateModal').modal('show');
    $('#templateModalButton').on('click', function () {
      $('#templateModal').modal('hide');
      $('#templateTab').tab('show');
    });
  });
}; //エディタ選択


var selectEditor = function () {
  if ($('.select-editor').length > 0) {
    $('.select-editor').each(function () {
      var _this = $(this);

      var _common = _this.find('.is-common');

      var _separate = _this.find('.is-separate');

      var _check = _this.find('input[name="select-editor"]');

      _separate.hide();

      _check.on('change', function () {
        if (_check.prop('checked')) {
          _check.prop('checked', true);

          _common.hide();

          _separate.show();
        } else {
          _check.prop('checked', false);

          _common.show();

          _separate.hide();
        }
      });
    });
  }
}; //CKエディタ


var initEditor = function () {
  if ($('.ckeditor').length > 0) {
    var allEditors = document.querySelectorAll('.ckeditor');
    for (var i = 0; i < allEditors.length; ++i) {
      ClassicEditor.create(allEditors[i], {
    	plugins: [ Bold, Italic, Underline, Strikethrought],
        toolbar: {
            items: [ 'bold', 'italic', 'underline', 'strikethrough']
        },
        language: 'ja'
      });
    }
  }
}; //日付カレンダー


var initDatetimepicker = function () {
	  if ($('.datetimepicker').length > 0) {
	    jQuery.datetimepicker.setLocale('ja');
	    var _input = $('.datetimepicker');
	    _input.each(function(){
	        var _this = $(this);
	        if( _this.hasClass('is-noTime') ){
	            var _time = false;
	        }else{
	            var _time = true;
	        }
	        _this.datetimepicker({
	            timepicker: _time,
	            mask: true
	        });
	    })
	  }
	} //チェックでサイドメニュー表示


var initSubMenu = function () {
  if ($('.tr-check').length > 0) {
    $('.tr-check').on('change', function () {
      var count = $('.tr-check:checked').length;

      if (count > 0) {
        $('#check_count').empty().append(count + '件');

        if ($(this).prop('checked')) {
          $(this).closest('tr').addClass('table-active');
        } else {
          $(this).closest('tr').removeClass('table-active');
        }

        $('#aside').addClass('c-sidebar-show');
        //$('#sidebar').removeClass('c-sidebar-lg-show');
        $('.c-sidebar-close').on('click', function () {
          //$('#sidebar').addClass('c-sidebar-lg-show');
        });
      } else {
        $(this).closest('tr').removeClass('table-active');
        $('#aside').removeClass('c-sidebar-show');
        //$('#sidebar').addClass('c-sidebar-lg-show');
      }
    });
  }
}; //色選択


var initColorPikcer = function () {
  if ($('.input-color').length > 0) {
    const inputElement = document.querySelectorAll('.input-color');
    inputElement.forEach(elm => {
      const pickr = new Pickr({
        el: elm,
        useAsButton: true,
        default: '#666666',
        theme: 'nano',
        strings: {
          save: '保存'
        },
        components: {
          preview: true,
          opacity: false,
          hue: true,
          interaction: {
            //hex: true,
            input: true,
            save: true
          }
        }
      }).on('init', pickr => {
        elm.value = pickr.getSelectedColor().toHEXA().toString(0);
      }).on('save', color => {
        elm.value = color.toHEXA().toString(0);
        pickr.hide();
      });
    });
  }
}; //テーブルソート


initSortable = function () {
  //table scrollと兼ねると操作できないのを解消
  if ($('.sortable').length > 0 && winW > 576) {
    var target = $('.sortable')[0];
    new Sortable(target, {
      group: 'sortA',
      chosenClass: 'sortable-tr',
      animation: 100
    });
    var inner = $('.sortable-inner');
    inner.each(function (index, element) {
      new Sortable(inner[index], {
        group: 'sortB',
        chosenClass: 'sortable-tr',
        animation: 100
      });
    });
  }
}; //グラフ


var initGraph = function () {
  if ($('#click-chart').length > 0) {
    Chart.defaults.global.pointHitDetectionRadius = 1;
    Chart.defaults.global.tooltips.enabled = true;
    Chart.defaults.global.tooltips.mode = 'index';
    Chart.defaults.global.tooltips.position = 'nearest';
    Chart.defaults.global.tooltips.custom = coreui.ChartJS.customTooltips;
    Chart.defaults.global.defaultFontColor = coreui.Utils.getStyle('--color', document.getElementsByClassName('c-app')[0]);
    const mainChart = new Chart(document.getElementById('click-chart'), {
      type: 'line',
      data: {
        labels: ['8/1', '8/2', '8/3', '8/4', '8/5', '8/6', '8/7', '8/8', '8/9', '8/10', '8/11', '8/12', '8/13', '8/14', '8/15', '8/16', '8/17', '8/18', '8/19', '8/20', '8/21', '8/22', '8/23', '8/24', '8/25', '8/26', '8/27', '8/28'],
        datasets: [{
          label: 'クリックURL-A',
          backgroundColor: coreui.Utils.hexToRgba(coreui.Utils.getStyle('--info', document.getElementsByClassName('c-app')[0]), 10),
          borderColor: coreui.Utils.getStyle('--info', document.getElementsByClassName('c-app')[0]),
          pointHoverBackgroundColor: '#fff',
          borderWidth: 2,
          data: [165, 180, 70, 69, 77, 57, 125, 165, 172, 91, 173, 138, 155, 89, 50, 161, 65, 163, 160, 103, 114, 185, 125, 196, 183, 64, 137, 95, 112, 175]
        }, {
          label: 'クリックURL-B',
          backgroundColor: 'transparent',
          borderColor: coreui.Utils.getStyle('--success', document.getElementsByClassName('c-app')[0]),
          pointHoverBackgroundColor: '#fff',
          borderWidth: 2,
          data: [92, 97, 80, 100, 86, 97, 83, 98, 87, 98, 93, 83, 87, 98, 96, 84, 91, 97, 88, 86, 94, 86, 95, 91, 98, 91, 92, 80, 83, 82]
        }, {
          label: 'しきい値',
          backgroundColor: 'transparent',
          borderColor: coreui.Utils.getStyle('--danger', document.getElementsByClassName('c-app')[0]),
          pointHoverBackgroundColor: '#fff',
          borderWidth: 1,
          borderDash: [8, 5],
          data: [65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65]
        }]
      },
      options: {
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        scales: {
          xAxes: [{
            gridLines: {
              drawOnChartArea: false
            }
          }],
          yAxes: [{
            ticks: {
              beginAtZero: true,
              maxTicksLimit: 5,
              stepSize: Math.ceil(250 / 5),
              max: 250
            }
          }]
        },
        elements: {
          point: {
            radius: 0,
            hitRadius: 10,
            hoverRadius: 4,
            hoverBorderWidth: 3
          }
        }
      }
    });
  }

  if ($('#time-graph').length > 0) {
    // eslint-disable-next-line no-unused-vars
    const barChart = new Chart(document.getElementById('time-graph'), {
      type: 'bar',
      data: {
        labels: ['0:00', '1:00', '2:00', '2:00', '3:00', '4:00', '5:00', '6:00', '7:00', '8:00', '9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'],
        datasets: [{
          backgroundColor: coreui.Utils.getStyle('--info', document.getElementsByClassName('c-app')[0]),
          data: [5, 6, 8, 9, 10, 13, 17, 18, 9, 10, 5, 7, 9, 3, 8, 42, 41, 25, 30, 5, 80, 12, 3, 2, 1]
        }]
      },
      options: {
        responsive: true
      }
    });
  }

  if ($('#week-graph').length > 0) {
    // eslint-disable-next-line no-unused-vars
    const doughnutChart = new Chart(document.getElementById('week-graph'), {
      type: 'doughnut',
      data: {
        labels: ['月', '火', '水', '木', '金', '土', '日'],
        datasets: [{
          data: [550, 80, 62, 23, 45, 56, 69],
          backgroundColor: ['#E91E63', '#673AB7', '#2196F3', '#00BCD4', '#4CAF50', '#CDDC39', '#FFEB3B']
        }]
      },
      options: {
        responsive: true
      }
    });
  }
};
//# sourceMappingURL=main.js.map

//名前変更UI
var initEditLabelSwitcher = function () {
  if ($('.editLabelSwitcher').length > 0) {
    var target = $('.editLabelSwitcher');
    target.each(function (index, element) {
      var icon = $(this).find('.editLabelSwitcher__icon');
      var label = $(this).find('.editLabelSwitcher__label');
      var form = $(this).find('.editLabelSwitcher__form');
      var button = $(this).find('.editLabelSwitcher__form button');
      icon.on('click', function (e) {
        e.stopPropagation();
        e.preventDefault();
        label.hide();
        icon.hide();
        form.show();
        return false;
      })
      button.on('click', function (e) {
        e.stopPropagation();
        e.preventDefault();
        label.show();
        icon.show();
        form.hide();
      })
    })
  }
}


//タグサジェスト
var initTagsSuggest = function () {

  function escapeHTML(string) {
    if (typeof string !== 'string') {
      return string;
    }
    return string.replace(/[&'`"<>]/g, function (match) {
      return {
        '&': '&amp;',
        "'": '&#x27;',
        '`': '&#x60;',
        '"': '&quot;',
        '<': '&lt;',
        '>': '&gt;',
      }[match]
    });
  }

  var target = $('input.tag-suggest');

  target.each(function () {
    var tagify = new Tagify(this, {
      maxTags: 5,
      whitelist: [
        {
          value: 'タグ1',
          id: 1,
          category: 'フォルダA'
        },
        {
          value: 'タグ2',
          id: 2,
          category: 'フォルダA'
        },
        {
          value: 'タグ3',
          id: 3,
          category: 'フォルダB'
        },
      ],
      dropdown: {
        maxItems: Infinity,
        enabled: 0,
        closeOnSelect: false,
        searchKeys: ['name']
      },
    })

    tagify.dropdown.createListHTML = suggestionsList => {
      const allTagData = suggestionsList.reduce((item, suggestion) => {
        const category = suggestion.category || 'Not Assigned';
        if (!item[category])
          item[category] = [suggestion]
        else
          item[category].push(suggestion)
        return item
      }, {})

      const getUsersSuggestionsHTML = tagData => tagData.map((suggestion, idx) => {

        if (typeof suggestion == 'string' || typeof suggestion == 'number')
          suggestion = { value: suggestion }
        var value = tagify.dropdown.getMappedValue.call(tagify, suggestion)
        suggestion.value = value && typeof value == 'string' ? escapeHTML(value) : value
        return tagify.settings.templates.dropdownItem.apply(tagify, [suggestion]);
      }).join("")

      // assign the user to a group
      return Object.entries(allTagData).map(([category, tagData]) => {
        return `<div class="tagify__dropdown__itemsGroup">
        <div class="groupHeader"><i class="cil-folder-open mr-1"></i>${category}</div>
        <div class="groupItem">
          ${getUsersSuggestionsHTML(tagData)}
        </div>
        </div>`
      }).join("")
    }
  })
}


//タグ編集
var initTagsListEdit = function () {
  //新規フォルダ追加処理
  $('#addFolderButton').on('click', function (e) {
    var addFolderItem = `
                    <li class="list-group-item list-group-item-action d-flex align-items-center">
                      <i class="h5 cil-folder mr-2"></i>
                      <div class="editLabelSwitcher mode-edit">
                        <div class="editLabelSwitcher__label">
                          新しいフォルダ
                        </div>
                        <div class="editLabelSwitcher__form">
                          <div class="input-group input-group-sm">
                            <input type="text" class="form-control" value="" placeholder="新しいフォルダ名">
                            <div class="input-group-append">
                              <button class="btn btn-secondary">決定</button>
                            </div>
                          </div>
                        </div>
                        <div class="editLabelSwitcher__icon">
                          <i class="cil-color-border"></i>
                        </div>
                      </div>
                      <i class="cil-trash ml-auto"></i>
                      <i class="cil-resize-height drag-handle ml-2"></i>
                    </li>`;
    $('#folderList').prepend(addFolderItem).find('input').focus();
    $('#folderList li').not(':first').find('.mode-edit').removeClass('mode-edit');
    initEditLabelSwitcher();
  });

  //新規タグ追加処理
  $('#addTagButton').on('click', function () {
    var addTagItem = `
                    <tr>
                      <td>
                        <div class="editLabelSwitcher mode-edit">
                          <div class="editLabelSwitcher__label">
                            新しいタグ
                          </div>
                          <div class="editLabelSwitcher__form">
                            <div class="input-group input-group-sm">
                              <input type="text" class="form-control" value="" placeholder="新しいタグ名">
                              <div class="input-group-append">
                                <button class="btn btn-secondary">決定</button>
                              </div>
                            </div>
                          </div>
                          <div class="editLabelSwitcher__icon">
                            <i class="cil-color-border"></i>
                          </div>
                        </div>
                      </td>
                      <td>
                        ユーザ属性
                      </td>
                      <td>
                        2020/05/15 08:00
                      </td>
                      <td>
                        <a href="line_friend_talk.html">
                          <button type="button" class="btn btn-pill btn-sm btn-light">
                            <i class="cil-trash"></i> 削除
                          </button>
                        </a>
                      </td>
                    </tr>`;
    $('#tagTable tbody').prepend(addTagItem).find('input').focus();
    $('#tagTable tbody tr').not(':first').find('.mode-edit').removeClass('mode-edit');
    initEditLabelSwitcher();
  });
}


//絵文字ピッカー
var initEmojiPicker = function () {
  if ($('.line-form').length > 0) {
    var target = $('.line-form');
    target.each(function (index, element) {
      var _this = $(this);
      var _emojiButton = _this.find('.emoji-button');
      var _emojiPicker = _this.find('emoji-picker');
      var _emojiPickerWrapper = _this.find('.emoji-picker');
      var _textarea = this.querySelector('.line-message');
      _emojiButton.on('click', function () {
        _emojiPickerWrapper.show(function () {
          //検索窓を消す
          var pickerRoot = this.querySelector('emoji-picker');
          var searchForm = pickerRoot.shadowRoot.querySelector('.search-row');
          if (searchForm) {
            searchForm.remove();
          }
          _emojiPicker.on('emoji-click', function (e) {
            //カーソルの位置を基準に前後を分割して、その間に文字列を挿入
            _textarea.value = _textarea.value.substr(0, _textarea.selectionStart)
              + e.detail.unicode
              + _textarea.value.substr(_textarea.selectionStart);

              _emojiPicker.off();
              _emojiPickerWrapper.hide();
              $(document).off();

          });
          $(document).on('click', function (e) {
            if (!$(e.target).closest('.emoji-picker').length) {
              _emojiPickerWrapper.hide();
              $(this).off();
              _emojiPicker.off();
            }
          })
        });
      })
    })
  }
}


//テキストエリア自動拡張
var initTextAreaAutoHeight = function () {
  if ($('textarea.auto-height').length > 0) {
    var $textarea = $('textarea.auto-height');
    $textarea.each(function () {
      var lineHeight = parseInt($(this).css('lineHeight'));
      $(this).on('input', function (evt) {
        var lines = ($(this).val() + '\n').match(/\n/g).length;
        if ($(this).outerHeight() < $(this).get(0).scrollHeight) {
          $(this).height(lineHeight * lines + lines);
        }
      });
    })
  }
}





