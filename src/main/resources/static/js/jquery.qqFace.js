// QQ表情插件
(function($){  
	$.fn.qqFace = function(options){
		var defaults = {
			id : 'facebox',
			path : 'face/',
			assign : 'content',
			tip : 'em_'
		};
		var option = $.extend(defaults, options);
		var assign = $('#'+option.assign);
		var id = option.id;
		var path = option.path;
		var tip = option.tip;
		
		if(assign.length<=0){
			//alert('缺少表情赋值对象。');
			//return false;
		}
		
		//$(this).click(function(e){
		addIcon = function(e){
			var strFace, labFace;
			if($('#'+id).length<=0){
				strFace = '<div id="'+id+'" style="position:absolute;display:none;z-index:1000;background:#fff" class="qqFace">' +
							  '<table border="1" width="350px" height="150px" cellspacing="0" cellpadding="0"><tr>';
				for(var i=1; i<=90; i++){
					labFace = '['+tip+i+']';
					var b = i;
					if(b<10){
						b="0"+b;
					}
					//ww  没有具体的样式     仅仅是一个锚点
					strFace += '<td><div style="font-size: 20px;" class="icon-c'+b+' ww"  ></div></td>';
					if( i % 15 == 0 ) strFace += '</tr><tr>';
				}
				strFace += '</tr></table></div>';
			}
			$(this).parent().append(strFace);
			var offset = $(this).position();
			var top = offset.top + $(this).outerHeight();
			$('#'+id).css('top',top);
			$('#'+id).css('left',offset.left);
			$('#'+id).show();
			e.stopPropagation();
		};
		//jquery1.9以上  live()方法被废弃
		$("body").on("click",".ww",function(){
			$('#saytext').find("span").remove();
			var b =  $(this).attr("class")
			$('#saytext').removeClass();
			$('#saytext').addClass(b);
			$("#resIcon").val(b);
			});

		$(document).click(function(){
			$('#'+id).hide();
			$('#'+id).remove();
		});
	};

})(jQuery);

jQuery.extend({ 
unselectContents: function(){ 
	if(window.getSelection) 
		window.getSelection().removeAllRanges(); 
	else if(document.selection) 
		document.selection.empty(); 
	} 
}); 
