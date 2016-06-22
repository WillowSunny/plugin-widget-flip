;
(function($) {
	FR.Flip = FR.extend(FR.Widget, {
		_defaultConfig : function() {
			return $.extend(FR.Flip.superclass._defaultConfig.apply(), {
				baseName : 'fr-clock',
				baseClass : 'fr-clock'
			});
		},

		_init : function() {
			FR.Flip.superclass._init.apply(this, arguments);
			FR.$defaultImport('/com/fr/plugin/widget/flip/web/flip.min.js', 'js');
			FR.$defaultImport('/com/fr/plugin/widget/flip/web/flip.css', 'css');
			this._buildAndRun();
			
			var o = this.options;
			var clock;
			var w ;
			var th = this;
			$(document).ready(function() {
				var ov = o.value.toString();
				var i;
				if(ov == ""){
					o.value = 0;
				}
				if(o.align = null || o.align.length == 0){
					o.align = '居中对齐';
				}
				var i = o.value.toString().length;
				var w = o.value<10?i*80*2:i*80;
				$( '.c'+o.widgetName).restWidth(w);
				th._setAlign(w);
				clock = new FlipClock($('.c'+o.widgetName), o.value, {
					clockFace : 'Counter'
				});
				var int = self.setInterval(
						function() {
					        $.ajax({
					        	type:"POST",
					            url: o.widgetUrl,
					            success: function (res) {
					                var data = FR.jsonDecode(res);
					                o.data = data[0];
					                v = data[0].value;
					                
					                if(i != v.toString().length || v ==0){
					                	i = v.toString().length;
					                	if(v<10){
					                		w = i*80*2;
					                	}else{
					                		w = i*80;
					                	}
					                	$( '.c'+o.widgetName).restWidth(w);
					                	if(o.align=='右对齐'){
											$('.c'+o.widgetName).rAlign(w,o);
										}
					                	clock = null;
					                	clock = new FlipClock($('.c'+o.widgetName), v, {
					    					clockFace : 'Counter'
					    				});
					                }else{
					                	clock.setValue(v);
					                }
					                
					                
					            }
					        });
					    },o.refresh*1000);
				var rf = self.setInterval(function(){
					if(o.align=='右对齐'){
						$('.c'+o.widgetName).rAlign(w,o);
					}
				},50);
			});
		},
		
		_buildAndRun : function(){
			var o = this.options;
            var w = Math.min(o.width, o.height);
            this.element[0].innerHTML = '';
            this.element[0].innerHTML = this._createClockHtml({
                id : o.widgetName,
                w: w - w * 0.04 * 2,
                top : w == o.width ? (o.height - w) / 2 : 0,
                left : w == o.height ? (o.width - w) / 2 : 0
            });
		},
		
		_setAlign :function(ww){
			var o = this.options;
			if(o.align=='右对齐'){
				$('.c'+o.widgetName).rAlign(ww,o);
			}else if(o.align =='左对齐'){
				$('.c'+o.widgetName).lAlign();
			}else if(o.align =='居中对齐'){
				$( '.c'+o.widgetName).vAlign();
				$('.c'+o.widgetName).hAlign();
			}
		},
		
		
		_createClockHtml:function(o){
			 var pc = '<div class="' +'c'+ o.id + '" style="width:' + o.w + 'px;height:100px;position:absolute;' + 'left:' + o.left + 'px;'+'top:'+ o.top + 'px;">';
			 pc += '</div>';
	         return pc;
		}

	});
	
	$.fn.restWidth = function (w) {
        return this.each(function (i) {
            $(this).css("width", w + "px");
        });
    };
	
	
	$.fn.vAlign = function () {
        return this.each(function (i) {
            var h = $(this).height();
            var oh = $(this).outerHeight();
            var mt = (h + (oh - h)) / 2;
            $(this).css("margin-top", "-" + mt + "px");
            $(this).css("top", "50%");
            $(this).css("position", "absolute");
        });
    };
    
    $.fn.hAlign = function() {
    	return this.each(function(i){
    	var w = $(this).width();
    	var ow = $(this).outerWidth();	
    	var ml = (w + (ow - w)) / 2;	
    	$(this).css("margin-left", "-" + ml + "px");
    	$(this).css("left", "50%");
    	$(this).css("position", "absolute");
    	});
    };
    
    $.fn.lAlign = function() {
    	return this.each(function(i){
    		$(this).css("left", "0px");
    		$(this).css("margin-left", "auto");
    		$(this).css("position", "absolute");
    	});
    };
    
    $.fn.rAlign = function(fw,o) {
    	return this.each(function(i){
    		
    		var ow = o.width
    		var w = ow-fw;
    		$(this).css("left", w+"px");
    		$(this).css("margin-right", "auto");
    		$(this).css("position", "absolute");
    	});
    };
	$.shortcut("flip", FR.Flip);
})(jQuery);