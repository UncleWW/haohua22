$(document).ready(function() {

	/*-----------------------------------/
	/*	TOP NAVIGATION AND LAYOUT
	/*----------------------------------*/

	$('.btn-toggle-fullwidth').on('click', function() {
		if(!$('body').hasClass('layout-fullwidth')) {
			$('body').addClass('layout-fullwidth');
		} else {
			$('body').removeClass('layout-fullwidth');
			$('body').removeClass('layout-default'); // also remove default behaviour if set
		}
		$(this).find('.lnr').toggleClass('lnr-arrow-left-circle lnr-arrow-right-circle');

		if($(window).innerWidth() < 1025) {
			if(!$('body').hasClass('offcanvas-active')) {
				$('body').addClass('offcanvas-active');
			} else {
				$('body').removeClass('offcanvas-active');
			}
		}
	});

	$(window).on('load', function() {
		if($(window).innerWidth() < 1025) {
			$('.btn-toggle-fullwidth').find('.icon-arrows')
			.removeClass('icon-arrows-move-left')
			.addClass('icon-arrows-move-right');
		}

		// adjust right sidebar top position
		$('.right-sidebar').css('top', $('.navbar').innerHeight());

		// if page has content-menu, set top padding of main-content
		if($('.has-content-menu').length > 0) {
			$('.navbar + .main-content').css('padding-top', $('.navbar').innerHeight());
		}

		// for shorter main content
		if($('.main').height() < $('#sidebar-nav').height()) {
			$('.main').css('min-height', $('#sidebar-nav').height());
		}
		
		$('.page-panel').css('min-height', $('.main').height());

	});


	/*-----------------------------------/
	/*	SIDEBAR NAVIGATION
	/*----------------------------------*/

	$('.sidebar a[data-toggle="collapse"]').on('click', function() {
		if($('.nav a').hasClass('active')) {
			$('.nav a').removeClass('active');
		}
		$('.collapse').collapse('hide');
		$('.collapse').removeClass('active');
//		if($(this).hasClass('collapsed')) {
//			$(this).addClass('active');
//		} else {
//			$(this).removeClass('active');
//		}
	});

	if( $('.sidebar-scroll').length > 0 ) {
		$('.sidebar-scroll').slimScroll({
			height: '95%',
			wheelStep: 2,
		});
	}


	/*-----------------------------------/
	/*	PANEL FUNCTIONS
	/*----------------------------------*/

	// panel remove
	$('.panel .btn-remove').click(function(e){

		e.preventDefault();
		$(this).parents('.panel').fadeOut(300, function(){
			$(this).remove();
		});
	});

	// panel collapse/expand
	var affectedElement = $('.panel-body');

	$('.panel .btn-toggle-collapse').clickToggle(
		function(e) {
			e.preventDefault();

			// if has scroll
			if( $(this).parents('.panel').find('.slimScrollDiv').length > 0 ) {
				affectedElement = $('.slimScrollDiv');
			}

			$(this).parents('.panel').find(affectedElement).slideUp(300);
			$(this).find('i.lnr-chevron-up').toggleClass('lnr-chevron-down');
		},
		function(e) {
			e.preventDefault();

			// if has scroll
			if( $(this).parents('.panel').find('.slimScrollDiv').length > 0 ) {
				affectedElement = $('.slimScrollDiv');
			}

			$(this).parents('.panel').find(affectedElement).slideDown(300);
			$(this).find('i.lnr-chevron-up').toggleClass('lnr-chevron-down');
		}
	);


	/*-----------------------------------/
	/*	PANEL SCROLLING
	/*----------------------------------*/

	if( $('.panel-scrolling').length > 0) {
		$('.panel-scrolling .panel-body').slimScroll({
			height: '430px',
			wheelStep: 2,
		});
	}

	if( $('#panel-scrolling-demo').length > 0) {
		$('#panel-scrolling-demo .panel-body').slimScroll({
			height: '175px',
			wheelStep: 2,
		});
	}

	/*-----------------------------------/
	/*	TODO LIST
	/*----------------------------------*/

	$('.todo-list input').change( function() {
		if( $(this).prop('checked') ) {
			$(this).parents('li').addClass('completed');
		}else {
			$(this).parents('li').removeClass('completed');
		}
	});


	/*-----------------------------------/
	/* TOASTR NOTIFICATION
	/*----------------------------------*/

	if($('#toastr-demo').length > 0) {
		toastr.options.timeOut = "false";
		toastr.options.closeButton = true;
		toastr['info']('Hi there, this is notification demo with HTML support. So, you can add HTML elements like <a href="#">this link</a>');

		$('.btn-toastr').on('click', function() {
			$context = $(this).data('context');
			$message = $(this).data('message');
			$position = $(this).data('position');

			if($context == '') {
				$context = 'info';
			}

			if($position == '') {
				$positionClass = 'toast-left-top';
			} else {
				$positionClass = 'toast-' + $position;
			}

			toastr.remove();
			toastr[$context]($message, '' , { positionClass: $positionClass });
		});

		$('#toastr-callback1').on('click', function() {
			$message = $(this).data('message');

			toastr.options = {
				"timeOut": "300",
				"onShown": function() { alert('onShown callback'); },
				"onHidden": function() { alert('onHidden callback'); }
			}

			toastr['info']($message);
		});

		$('#toastr-callback2').on('click', function() {
			$message = $(this).data('message');

			toastr.options = {
				"timeOut": "10000",
				"onclick": function() { alert('onclick callback'); },
			}

			toastr['info']($message);

		});

		$('#toastr-callback3').on('click', function() {
			$message = $(this).data('message');

			toastr.options = {
				"timeOut": "10000",
				"closeButton": true,
				"onCloseClick": function() { alert('onCloseClick callback'); }
			}

			toastr['info']($message);
		});
	}
});

// toggle function
$.fn.clickToggle = function( f1, f2 ) {
	return this.each( function() {
		var clicked = false;
		$(this).bind('click', function() {
			if(clicked) {
				clicked = false;
				return f2.apply(this, arguments);
			}

			clicked = true;
			return f1.apply(this, arguments);
		});
	});

}

/**
 * extend jQuery for common method
 */
jQuery.extend({
	/** application root path */
	contextPath: function() {
		var pathName = window.document.location.pathname;
		return pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	},
	/** ips change common method */
	addLineFeed: function(value, lineFeedChar, lineChar) {
		if (null == value || undefined == value) {
			return '';
		}
		if (null == lineFeedChar || undefined == lineFeedChar) {
			lineFeedChar = '\n';
		}
		if (null == lineChar || undefined == lineChar) {
			lineChar = /,/g;
		}
		return value.replace(lineChar, lineFeedChar);
	},
	removeLineFeed: function(values, lineChar, lineFeedChar) {
		if (null == values || undefined == values) {
			return '';
		}
		var newValue = '';
		var valueArray = values.split('\n');
		var len = valueArray.length;
		var value;
		for (var i = 0; i < len; i++) {
        	value = valueArray[i];
        	value = value.trim();
        	if(value == '') continue;
        	newValue = newValue + value + ',';
		}
		return newValue.substring(0, newValue.length - 1);
	}
});

/**
 * extend bootstrapValidation for ips validate
 */
;(function($) {
    $.fn.bootstrapValidator.i18n.ips = $.extend($.fn.bootstrapValidator.i18n.ips || {}, {
        'default': 'Please enter a valid IP address',
        ipv4: 'Please enter a valid IPv4 address',
        ipv6: 'Please enter a valid IPv6 address'
    });
    $.fn.bootstrapValidator.validators.ips = {
        html5Attributes: {
            message: 'message',
            ipv4: 'ipv4',
            ipv6: 'ipv6'
        },
        validate: function(validator, $field, options) {
            var values = $field.val();
            if (values === '') {
                return true;
            }
            options = $.extend({}, { ipv4: true, ipv6: true }, options);

            var ipv4Regex = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
                ipv6Regex = /^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/,
                valid     = true,
                message;

            var valueArray = values.split('\n');
            var len = valueArray.length;
            var value;
            for (var i = 0; i < len; i++) {
            	if (!valid) {
            		break;
            	}
            	value = valueArray[i];
            	value = value.trim();
            	if(value=="")continue;
	            switch (true) {
	                case (options.ipv4 && !options.ipv6):
	                	valid   = ipv4Regex.test(value);
	                    message = options.message || $.fn.bootstrapValidator.i18n.ips.ipv4;
	                    break;
	
	                case (!options.ipv4 && options.ipv6):
	                    valid   = ipv6Regex.test(value);
	                    message = options.message || $.fn.bootstrapValidator.i18n.ips.ipv6;
	                    break;
	
	                case (options.ipv4 && options.ipv6):
	                /* falls through */
	                default:
	                    valid   = ipv4Regex.test(value) || ipv6Regex.test(value);
	                    message = options.message || $.fn.bootstrapValidator.i18n.ips['default'];
	                    break;
	            }
            }
            return {
                valid: valid,
                message: message
            };
        }
    };
}(window.jQuery));

/**
 * extend bootstrapValidation for ips repeat valid
 */
;(function($) {
    $.fn.bootstrapValidator.i18n.ipsRepeat = $.extend($.fn.bootstrapValidator.i18n.ipsRepeat || {}, {
        'default': 'Repeat ips are found'
    });

	$.fn.bootstrapValidator.validators.ipsRepeat = {
		validate: function(validator, $field, options) {
			var values = $field.val();
			if (values === '') {
				return true;
			}
			var hash = {};
			var valueArray = values.split('\n');
			for(var i in valueArray) {
				valueArray[i] = valueArray[i].trim();
				if(valueArray[i]=="")continue;
				if (hash[valueArray[i]]) {
					return false;
				}
				hash[valueArray[i]] = true;
			}
            return true;
		}
	};
}(window.jQuery));

;(function($) {
    $.fn.bootstrapValidator.i18n.syncRemote = $.extend($.fn.bootstrapValidator.i18n.syncRemote || {}, {
        'default': 'Please enter a valid value'
    });

    $.fn.bootstrapValidator.validators.syncRemote = {
        html5Attributes: {
            message: 'message',
            name: 'name',
            type: 'type',
            url: 'url',
            data: 'data'
        },

        /**
         * Destroy the timer when destroying the bootstrapValidator (using validator.destroy() method)
         */
        destroy: function(validator, $field, options) {
            if ($field.data('bv.remote.timer')) {
                clearTimeout($field.data('bv.remote.timer'));
                $field.removeData('bv.remote.timer');
            }
        },

        /**
         * Request a remote server to check the input value
         *
         * @param {BootstrapValidator} validator Plugin instance
         * @param {jQuery} $field Field element
         * @param {Object} options Can consist of the following keys:
         * - url {String|Function}
         * - type {String} [optional] Can be GET or POST (default)
         * - data {Object|Function} [optional]: By default, it will take the value
         *  {
         *      <fieldName>: <fieldValue>
         *  }
         * - delay
         * - name {String} [optional]: Override the field name for the request.
         * - message: The invalid message
         * - headers: Additional headers
         * @returns {Deferred}
         */
        validate: function(validator, $field, options) {
            var value = $field.val(),
                dfd   = new $.Deferred();
            if (value === '') {
                dfd.resolve($field, 'syncRemote', { valid: true });
                return dfd;
            }

            var name    = $field.attr('data-bv-field'),
                data    = options.data || {},
                url     = options.url,
                type    = options.type || 'GET',
                headers = options.headers || {};

            // Support dynamic data
            if ('function' === typeof data) {
                data = data.call(this, validator);
            }

            // Parse string data from HTML5 attribute
            if ('string' === typeof data) {
                data = JSON.parse(data);
            }

            // Support dynamic url
            if ('function' === typeof url) {
                url = url.call(this, validator);
            }

            data[options.name || name] = value;
            function runCallback() {
                var xhr = $.ajax({
                    type: type,
                    headers: headers,
                    async: false,
                    url: url,
                    dataType: 'json',
                    data: data
                });
                xhr.then(function(response) {
                    response.valid = response.valid === true || response.valid === 'true';
                    dfd.resolve($field, 'syncRemote', response);
                });

                dfd.fail(function() {
                    xhr.abort();
                });

                return dfd;
            }
            
            return runCallback();
        }
    };
}(window.jQuery));

(function($) {
	var bootstrapTable = $.fn.bootstrapTable.Constructor;
	bootstrapTable.prototype.load = function (data) {
	    var fixedScroll = false;

        // #431: support pagination
        if (this.options.sidePagination === 'server') {
            this.options.totalRows = data[this.options.totalField];
            this.options.pageNumber = data['pageNumber'];
            fixedScroll = data.fixedScroll;
            data = data[this.options.dataField];
        } else if (!$.isArray(data)) { // support fixedScroll
            fixedScroll = data.fixedScroll;
            data = data.data;
        }

        this.initData(data);
        this.initSearch();
        this.initPagination();
        this.initBody(fixedScroll);
    };
}(window.jQuery));

function resultAjax(url, data) {
	var result;
	$.ajax({
		url: $.contextPath() + url,
		type: 'get',
		async: false,
		cache: false,
		contentType: 'application/json',
		data: data,
		dataType: 'json',
		success: function(data) {
			result = data.result;
			if ('fail' == data.resultFlag) {
				var errors = data.errors;
				if(errors!=null && errors.length>0){
					var a = document.createElement("div");
					a.classList.add('error');
					for(var i=0;i<errors.length;i++){
						var b = document.createElement("span");
						b.innerHTML = "错误信息: "+errors[i].errorMsg;
						//b.innerHTML = "错误信息: "+errors[i].errorMsg+" ["+errors[i].errorCode+"]";
						a.appendChild(b);
						var br =document.createElement("br");
						a.appendChild(br);
					}
					contentAlert(a,'error')
				}else{
					commonAlert('出错了','error');
				}
				//alert(JSON.stringify(data.errors));
			}
		},
		error: function(data) {
			ajaxErrorAlert(data);
		}
	});
	return result;
}
function ajaxErrorAlert(data) {
	var responseText = data.responseText;
	if(null == responseText || '' == responseText) {
		// commonAlert('操作过快，请稍后操作！','warning');
	} else if(responseText.indexOf('您没有权限访问该页面') >= 0){
		commonAlert('您的权限不足，请联系管理员！','error');
	}else{
		commonAlert('登录超时！','error');
	}
}
function alertAjax(url, data, successFunction, msg) {
	showMask("mask");
	$.ajax({
		url: $.contextPath() + url,
		type: 'post',
		async: true,
		//contentType: 'application/json',
		data: data,
		traditional:true,
		dataType: 'json',
		success: function(data) {
			hideMask("mask");
			if ('success' == data.resultFlag) {
				if (null == msg) {
					msg = '保存成功！';
				}
				swal({
					title: '',
			        text: msg,
			        icon: 'success',
			        button: {
			        	text: 'OK',
			        	closeModal: true
			        },
				    timer: 3000
				});
				successFunction(data.result);
			} else if ('fail' == data.resultFlag) {
				var errors = data.errors;
				if(errors!=null && errors.length>0){
					var a = document.createElement("div");
					a.classList.add('error');
					for(var i=0;i<errors.length;i++){
						var b = document.createElement("span");
						b.innerHTML = "提示: "+errors[i].errorMsg;
						a.appendChild(b);
						var br =document.createElement("br");
						a.appendChild(br);
					}
					contentAlert(a,'error')
				}else{
					commonAlert('出错了','error');
				}
				//alert(JSON.stringify(data.errors));
			}
		},
		error: function(data) {
			hideMask("mask");
			ajaxErrorAlert(data);
		}
	});
}

function paramsAjax(url, data, successFunction, msg) {
	showMask("mask");
	$.ajax({
		url: $.contextPath() + url,
		type: 'post',
		async: true,
		contentType: 'application/json',
		data: data,
		traditional:true,
		dataType: 'json',
		success: function(data) {
			hideMask("mask");
			if ('success' == data.resultFlag) {
				if (null == msg) {
					msg = '保存成功！';
				}
				swal({
					title: '',
					text: msg,
					icon: 'success',
					button: {
						text: 'OK',
						closeModal: true
					},
					timer: 3000
				});
				successFunction(data.result);
			} else if ('fail' == data.resultFlag) {
				var errors = data.errors;
				if(errors!=null && errors.length>0){
					var a = document.createElement("div");
					a.classList.add('error');
					for(var i=0;i<errors.length;i++){
						var b = document.createElement("span");
						b.innerHTML = "错误信息: "+errors[i].errorMsg;
						a.appendChild(b);
						var br =document.createElement("br");
						a.appendChild(br);
					}
					contentAlert(a,'error')
				}else{
					commonAlert('出错了','error');
				}
				//alert(JSON.stringify(data.errors));
			}
		},
		error: function(data) {
			hideMask("mask");
			ajaxErrorAlert(data);
		}
	});
}
function warnAlert(Msg,url,data,todoFunction,msg){
	swal({
		title: '',
        text: Msg,
        icon: 'warning',
        dangerMode:true,
        buttons:{
        	cancel:{
        		text:"取消",
        		visible:true
        	},
	 		confim:{
        		text:"确定"
        	}
        }
	}).then(willdelete =>{
		if(willdelete){
			alertAjax(url, data, todoFunction,msg);
		}
		});
}
function selectAlert(title,button1,button2,Function1,Function2){
	swal({
		title: "",
		text: title,
        icon: 'warning',
        dangerMode:true,
        buttons:{
        	cancel:{
        		text:button1,
        		visible:true
        	},
	 		confim:{
        		text:button2
        	}
        }
	}).then(willdelete =>{
			if(!willdelete) {
				Function1();
			}else if (willdelete) {
				Function2();
			} 
		});
}
//content针对样式需要    对selectAlert进行的微微改动
function selectContentAlert(title,button1,button2,Function1,Function2){
	swal({
		title: "",
		content: title,
        icon: 'warning',
        dangerMode:true,
        buttons:{
        	cancel:{
        		text:button1,
        		visible:true
        	},
	 		confim:{
        		text:button2
        	}
        }
	}).then(willdelete =>{
			if(!willdelete) {
				Function1();
			}else if (willdelete) {
				Function2();
			} 
		});
}
function commonAlert(msg,icon){
	swal({
		title: '',
        text: msg,
        icon: icon,
        button: {
        	text: '确定',
        	closeModal: true
        },
	    //timer: 3000
	})
}
//content针对样式需要    对commonAlert进行的微微改动
function contentAlert(msg,icon){
	swal({
		title: '',
		content: msg,
        icon: icon,
        button: {
        	text: '确定',
        	closeModal: true
        },
	    //timer: 3000
	})
}
//判空
function isNotNull(str){
	if( str=='undefined' || str==undefined || str=="" || str==null){
		return false;
	}else{
		return true;
	}
}
//去除前后空格
function trimEmpty(str){  
	return str.replace(/(^\s*)|(\s*$)/g, "");  
}
function showMask(id)
{
	 $("#"+id).css('width',$(window.parent.window).width());
	 $("#"+id).css('height',$(window.parent.window).height());
	 $("#"+id).css('display',"block");
}
function hideMask(id)
{
	 $("#"+id).css('display',"none");
}

//还原方法  str.split(",").join(""); str必须为字符串
function formatMoney(value, type) {
    if (value == null || value == '') {
        return '0.00';
    }
    if (type == null || type == '') {
        type  = 2;
    }
    return Number(value).toFixed(type).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
};
function getNowMonth(){
    var date=new Date;
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    month =(month<10 ? "0"+month:month);
    var mydate = (year.toString()+"-"+month.toString());
    return mydate;
}

function getNowDay(){
    var date=new Date;
    var year=date.getFullYear();
    var month=date.getMonth()+1;//getMonth方法获取到的月份会比真实月份少一个月
    var day=date.getDate();
    month =(month<10 ? "0"+month:month);
    day =(day<10 ? "0"+day:day);
    var mydate = (year.toString()+"-"+month.toString()+"-"+day.toString());
    return mydate;
}