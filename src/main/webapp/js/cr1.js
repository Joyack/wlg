/*
	*作者：郭灿荣
	*一些常用的js方法
*/
var cr = {
	//打印显示的内容
	alert:function(msg){
		if(typeof(msg) == "object"){
			if(msg == null){
				cr_dialogBox.alert(true,"null");
			}else if(Object.prototype.toString.call(msg).toLowerCase() == "[object object]" && !msg.length){
				cr_dialogBox.alert(true,JSON.stringify(msg));
			}else if(Object.prototype.toString.call(msg) === '[object Array]'){
				var flag = true;
				for(var i=0;i<msg.length;i++){
					if((Object.prototype.toString.call(msg[i]).toLowerCase() == "[object object]")){
						cr_dialogBox.alert(true,JSON.stringify(msg[i]));
						flag = false;
					}
				};
				if(flag){
					cr_dialogBox.alert(true,msg.toLocaleString());
				}
			}else if(Object.prototype.toString.call(msg) === '[object Storage]'){
				cr_dialogBox.alert(true,JSON.stringify(msg));
			}else{
				var len = msg.length;
				if(len){
					for(var i=0;i<len;i++){
						cr_dialogBox.alert(true,msg[i].innerHTML);
					}
				}else{
					cr_dialogBox.alert(true,msg.innerHTML);
				}
			}
		}else if(((typeof msg) == "string")||((typeof msg) == "number")||((typeof msg) == "boolean")||((typeof msg) == "undefined")){
			cr_dialogBox.alert(true,msg);
		};
	},
	//在控制台上打印数值
	console:function(msg){
		if(typeof(msg) == "object"){
			if(msg == null){
				console.log("null");
			}else if(Object.prototype.toString.call(msg).toLowerCase() == "[object object]" && !msg.length){
				console.log(JSON.stringify(msg));
			}else if(Object.prototype.toString.call(msg) === '[object Array]'){
				var flag = true;
				for(var i=0;i<msg.length;i++){
					if((Object.prototype.toString.call(msg[i]).toLowerCase() == "[object object]")){
						console.log(JSON.stringify(msg[i]));
						flag = false;
					}
				};
				if(flag){
					console.log(msg.toLocaleString());
				}
			}else if(Object.prototype.toString.call(msg) === '[object Storage]'){
				console.log(JSON.stringify(msg));
			}else{
				var len = msg.length;
				if(len){
					for(var i=0;i<len;i++){
						console.log(msg[i].innerHTML);
					}
				}else{
					console.log(msg.innerHTML);
				}
			}
		}else if(((typeof msg) == "string")||((typeof msg) == "number")||((typeof msg) == "boolean")||((typeof msg) == "undefined")){
			console.log(msg);
		};
	},
	//ajax请求 -----必须要引入jquery
	ajax:function(type,url,data,callback){
		$.ajax({
			type:type,
			url:url,
			data:data,
			success:function(data1){
				if(callback)callback(data1);
			}
		});
	}
}

/*
	*作者：郭灿荣
	*dom对象的相关操作
	*ps:需要注意的是，目前只支持dom对象要有id值，传值的时候传id值
*/
var cr_dom={
	/*通过id获取dom元素*/
	dom:function(id){
		return document.getElementById(id);
	},
	//得到class
	getClass:function(className){
		return document.getElementsByClassName(className);
	},
	//找到dom元素下面的孩子
	getChildren:function(dom){
		return cr_dom.dom(dom).children;
	},
	//找到dom元素下面需要的tag标签
	getTagName:function(dom,tagName){
		return cr_dom.dom(dom).getElementsByTagName(tagName);
	},
	//简单混入--可以将不同的对象合在一起；
	mixin:function(obj,obj2){
		for(var k in obj2){
			if(obj2.hasOwnProperty(k)){
				obj[k] = obj2[k];
			}
		}
		return obj;
	},
	//多对象混入
	mix:function(target,source){
		var arr = [];
		var args = arr.slice.call(arguments);
		var i = 1;
		if(args.length==1){
			return target;
		};
		while((source = args[i++])){
			for(var key in source){
				if(source.hasOwnProperty(key)){
					target[key] = source[key];
				}
			}
		}
		return target;
	}
};

/*
	*作者：郭灿荣
	*css操作-style、css
*/
var cr_style={
	//获取css中的样式的值，跟浏览器兼容无关
	getStyle:function(dom,attr){
		var style = [];
		if(dom.length){
			for(var i=0;i<dom.length;i++){
				style.push(window.getComputedStyle ? window.getComputedStyle(dom[i],false)[attr]:dom[i].currentStyle[attr]);
			}
			return style;
		}else{
			return window.getComputedStyle ? window.getComputedStyle(dom,false)[attr]:dom.currentStyle[attr];
		}
	},
	//元素居中
	centerElement:function(id){
		var $this = this;
		var boxDom = cr_dom.dom(id);
		var sw = boxDom.offsetWidth;
		var sh = boxDom.offsetHeight;
		var dw = window.innerWidth;
		var dh = window.innerHeight;
		var cleft = (dw - sw)/2;
		var ctop = (dh - sh)/2;
		boxDom.style.position = "absolute";
		boxDom.style.left = cleft+"px";
		boxDom.style.top = ctop+"px";
		window.onresize = function(){
			$this.centerElement("box");
		};
	}
};

/*
	*作者：郭灿荣
	*事件-event
*/
var cr_event={
	/*页面加载完毕后添加事件(可添加多个事件都不会对浏览器造成影响)*/
	addloadEvent:function(func){
		var oldonload = window.onload;
		if(typeof window.onload != "function"){
			window.onload = func;
		}else{
			window.onload = function(){
				oldonload();
				func();
			};
		}
	},
	//获取鼠标的位置。兼容ie678
	getXY:function(e){
		var ev = e || window.event;
		var x=0,y=0;
		if(ev.pageX){
			x = ev.pageX;
			y = ev.pageY;
		}else{
			//拿到scrollTop 和scrollLeft
			var sleft = 0,stop = 0;
			//ie678---
			if(document.documentElement){
				stop =document.documentElement.scrollTop;
				sleft = document.documentElement.scrollLeft;
			}else{
			//ie9+ 谷歌 
				stop = document.body.scrollTop;
				sleft = document.body.scrollLeft;
			}	
			x = ev.clientX + sleft;
			y = ev.clientY + stop;
		}
		return {x:x,y:y};
	},
	//传入{id:'id'，eventType:'eventType'，type(要委托给谁):'type'，callback:'function(){}'}
	crOn:function(opts){
		var obj = cr_dom.dom(opts.id);
		obj.addEventListener(opts.eventType,function(e){
			//事件类型兼容写法
			var ev = e || window.event;
			//当前目标元素
			var currentTarget = ev.currentTarget;
			//鼠标停留的元素
			var target = ev.target || ev.srcElement;
			//类型栏判断是不是委托
			var TypeName = "";
			if(opts.type){
				if(target.id){
					TypeName = target.id.toLowerCase();
				}else if(target.className){
					TypeName = target.className.toLowerCase();
				}else if(target.tagName){
					TypeName = target.tagName.toLowerCase();
				}
				if(TypeName === opts.type.toLowerCase()){
					if(opts.callback)opts.callback.call(target);
				}
			}else{
				if(opts.callback)opts.callback.call(currentTarget);
			}
			
		},false);
	}
};

/*
	*作者：郭灿荣
	*数值的处理-nunber
*/
var cr_number={
	//保留2位小数
	getFloatStr:function(num){
    	num += '';  
        num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符  
          
        if(/^0+/) //清除字符串开头的0  
            num = num.replace(/^0+/, '');  
        if(!/\./.test(num)) //为整数字符串在末尾添加.00  
            num += '.00';  
        if(/^\./.test(num)) //字符以.开头时,在开头添加0  
            num = '0' + num;  
        num += '00';        //在字符串末尾补零  
        num = num.match(/\d+\.\d{2}/)[0];  
        return num;
 	},
	//经一个10进制的数变成各种进制数
	baseConverter:function(num,scale){
		var arr = [];
		var aa = "";
		decNumber = num;
		while (decNumber > 0){
			rem = Math.floor(decNumber % scale);
			arr.push(rem);
			decNumber = Math.floor(decNumber / scale);
		}
		for(var i=0;i<arr.length;i++){
			if(arr[i]==10){arr[i] = 'A'};
			if(arr[i]==11){arr[i] = 'B'};
			if(arr[i]==12){arr[i] = 'C'};
			if(arr[i]==13){arr[i] = 'D'};
			if(arr[i]==14){arr[i] = 'E'};
			if(arr[i]==15){arr[i] = 'F'};
		};
		arr.reverse();
		for(var i=0;i<arr.length;i++){
			aa+=arr[i];
		}
		return aa;
	},
    //检测整数
    checkInt:function(num){
        var reg = /(^[1-9]\d*$)/;//整数
        if(reg.test(num)){
            return num;
        }else{
            var msg = num.toString().split(".");
            if(msg.length > 1){
                return num.toString().split(".")[0];
            }else{
                return parseInt(num).toString()=="NaN"?"0":parseInt(num);
            };
        };
    },
	//范围随机数
	random:function(start,end){
		return Math.floor(Math.random()*(end-start+1))+start;
	}
};

/*
	*作者：郭灿荣
	*缓存
	*整合了localStorage、sessionStorage、cookie
*/
var cr_cache={
	//如果true进入的是localStorage否则不写或者false都是进入sessionStorage
	setCache:function(key,value){
		var args = arguments;
		if(window.localStorage){
			var mark = args[2]?true:args[2];
			var storage = mark?localStorage:sessionStorage;
			storage.setItem("CR_"+key,value);
		}else{
			var period = args[3]?true:args[3];
			cr_cache.setCookie("CR_"+key,value,period);
		}
	},
	//获得缓存值
	getCache:function(key){
		if(window.localStorage){
			var args = arguments;
			var mark = args[1]?true:args[1];
			var storage = mark?localStorage:sessionStorage;
			return storage.getItem("CR_"+key);
		}else{
			return cr_cache.getCookie("CR_"+key);
		}
	},
	//删除缓存值
	removeCache:function(key){
		if(window.localStorage){
			var args = arguments;
			var mark = args[1]?true:args[1];
			var storage = mark?localStorage:sessionStorage;
			storage.removeItem("CR_"+key);
		}else{
			cr_cache.delCookie("CR_"+key);
		}
	},
	//设置cookie添加，修改，删除
	setCookie:function(name, value,time,option){
		var str=name+'='+escape(value); 
		var date = new Date();
		date.setTime(date.getTime()+this.getCookieTime(time)); 
		str += "; expires=" + date.toGMTString();
		if(option){ 
			if(option.path) str+='; path='+option.path; 
			if(option.domain) str+='; domain='+option.domain; 
			if(option.secure) str+='; true'; 
		} 
		document.cookie=str; 
	},
	getCookie:function(name){
		var arr = document.cookie.split('; '); 
		if(arr.length==0) return ''; 
		for(var i=0; i <arr.length; i++){ 
			tmp = arr[i].split('='); 
			if(tmp[0]==name) return unescape(tmp[1]); 
		} 
		return ''; 
	},
	delCookie:function(name){
		this.setCookie(name,'',-1); 
		var date=new Date();
		date.setTime(date.getTime()-10000);
		document.cookie=name+"=; expires="+date.toGMTString()+"; path=/";
	},
	//这个方法不用再外面调用
	getCookieTime:function(time){
	   if(time<=0)return time;
	   var str1=time.substring(1,time.length)*1;
	   var str2=time.substring(0,1);
	   if (str2=="s"){
			return str1*1000;
	   }
	   else if (str2=="m"){
		   return str1*60*1000;
	   }
	   else if (str2=="h"){
		   return str1*60*60*1000;
	   }
	   else if (str2=="d"){
		   return str1*24*60*60*1000;
	   }
	}
};


/*
	*作者：郭灿荣
	*其他js方法
*/
var cr_other={
	/*验证身份证方法
		直接返回true或者false
	*/
	VerifyidCard:function IdentityCodeValid(code) {
		if(typeof(code) == "number"){
			return "请把数字放在\" \"里面!";
		};
		var city = {
			11: "北京",12: "天津",13: "河北",14: "山西",15: "内蒙古",21: "辽宁",22: "吉林",
			23: "黑龙江 ",31: "上海",32: "江苏",33: "浙江",34: "安徽",35: "福建",36: "江西",
			37: "山东",41: "河南",42: "湖北 ",43: "湖南",44: "广东",45: "广西",46: "海南",
			50: "重庆",51: "四川",52: "贵州",53: "云南",54: "西藏 ",61: "陕西",62: "甘肃",
			63: "青海",64: "宁夏",65: "新疆",71: "台湾",81: "香港",82: "澳门",91: "国外 "
		};
		var tip = "";
		var pass = true;
		if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
			tip = "身份证号格式错误";
			pass = false;
		} else if (!city[code.substr(0, 2)]) {
			tip = "地址编码错误";
			pass = false;
		} else {
			//18位身份证需要验证最后一位校验位
			if (code.length == 18) {
				code = code.split('');
				//∑(ai×Wi)(mod 11)
				//加权因子
				var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
				//校验位
				var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
				var sum = 0;
				var ai = 0;
				var wi = 0;
				for (var i = 0; i < 17; i++) {
					ai = code[i];
					wi = factor[i];
					sum += ai * wi;
				}
				var last = parity[sum % 11];
				if (parity[sum % 11] != code[17]) {
					tip = "校验位错误";
					pass = false;
				}
			}
		}
		return pass;
	}
};


/*
	*作者：郭灿荣
	*加载当前音乐的音轨数组
	*音轨对象只能加载一次audio标签的音轨，若需要多个则要重新创建audio标签
	* 用法 cr_music.init(audioDom,mark,callback);
	* audioDom--音乐dom对象    mark--标记，控制是否调用音轨加载，true为调用。     callback--回调函数，会传一个因为的数组总共有1024项
	* 
	* cr_music.init(audioDom,true,function(array){//array 1024长度与
		console.log(array);
	});
*/
var cr_music = {
	mark:false,
	init:function(audioDom,mark,callback){
		var $this = this;
		try{
			var audioContext = $this.audioContext();
			//拿到播放器去解析你音乐文件
			var audioBufferSouceNode = audioContext.createMediaElementSource(audioDom);
			//创建解析对象
			var analyser = audioContext.createAnalyser();
			//将source与分析器连接
			audioBufferSouceNode.connect(analyser); 
			//将分析器与destination连接，这样才能形成到达扬声器的通路
			analyser.connect(audioContext.destination);
			$this.mark = mark;
			$this.data(analyser,callback);
		}catch(e){
			console.log(e);
		}
	},
	audioContext:function(){
		//1:音频上下文===html5+ajax+audioContext   html5+audio+audioContext  
		window.AudioContext = window.AudioContext || window.webkitAudioContext || window.mozAudioContext || window.msAudioContext;
		/*动画执行的兼容写法*/
		window.requestAnimationFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.msRequestAnimationFrame;
		//2:初始化音轨对象
		var audioContext = new window.AudioContext();
		return audioContext;
	},
	data:function(analyser,callback){
		if(cr_music.mark){
			var array = new Uint8Array(analyser.frequencyBinCount);
			analyser.getByteFrequencyData(array);
			if(callback)callback(array);
			//无限循环调用自己
			requestAnimationFrame(function(){
			 	cr_music.data(analyser,callback);
			});
		}
	}
};

/*
 * 页面一定要有<div id="dddd"></div>
 初始化消息框
 flag: true--显示alert框  false--关闭alert框
 msg:输出的内容体
 callback:回调函数 返回flag true-用户点击了确认  false-用户点击了取消;
 * */
var cr_dialogBox = {
//弹出框
alert:function(flag,msg){
	if(msg == undefined){
		msg = "";
	}
	var html = "";
	html = "<div class='allBox' id='alertModdle'>"+
				"	<div class='cr_moddle'></div>"+
				"<div class='cr_box'>"+
				"	<div style='position: relative;z-index: 9999;border-bottom: 1px solid #7dc4dc;'>"+
				"		<div class='cr_title'>"+
				"			<span class='cr_title_msg'>消息</span>"+
				"		</div>"+
				"		<div class='cr_content'>"+
				"			<div class='cr_content_msg'>"+
				"				<span id='cr_content_msg'>"+msg+"</span>"+
				"			</div>"+
				"			<div class='cr_bottom'>"+
				"				<div class='col-xs-4 col-md-4 col-md-push-4 col-xs-push-4 btn btn-success' onclick='cr_dialogBox.alert(false);'>确认</div>"+
				"			</div>"+
				"		</div>"+
				"	</div>"+
				"</div>"+
			"</div>"
	$("#dddd").html(html);
	if(flag){
		$("#alertModdle").fadeIn("fast");
	}else{
		$("#alertModdle").fadeOut("fast");
	};
},
//确认框
confirm:function(flag,msg,callback){
	if(msg == undefined){
		msg = "";
	}
	var html = "";
	html = "<div class='allBox' id='alertModdle'>"+
				"	<div class='cr_moddle'></div>"+
				"<div class='cr_box'>"+
				"	<div style='position: relative;z-index: 9999;border-bottom: 1px solid #7dc4dc;'>"+
				"		<div class='cr_title'>"+
				"			<span class='cr_title_msg'>消息</span>"+
				"		</div>"+
				"		<div class='cr_content'>"+
				"			<div class='cr_content_msg'>"+
				"				<span id='cr_content_msg'>"+msg+"</span>"+
				"			</div>"+
				"			<div class='cr_bottom' id='btn22222222222'>"+
				"				<div class='col-xs-3 col-md-3 col-md-offset-2 col-xs-offset-2 btn btn-success' data-flag=true>确认</div>"+
				"				<div class='col-xs-3 col-md-3 col-md-offset-2 col-xs-offset-2 btn btn-success' data-flag=false>取消</div>"+
				"			</div>"+
				"		</div>"+
				"	</div>"+
				"</div>"+
			"</div>"
	$("#dddd").html(html);
	if(flag){
		$("#alertModdle").fadeIn("fast");
	}else{
		$("#alertModdle").fadeOut("fast");
	};
	$.each($("#btn22222222222").children(),function(i,dom){
		$("#btn22222222222").children().eq(i).on("click",function(){
			$("#alertModdle").fadeOut("fast");
			var flag = true;
			if(callback){
				if($(this).attr("data-flag") == "true"){
					flag = true;
				}else if($(this).attr("data-flag") == "false"){
						flag = false;
					}
					callback(flag);
				}
			});
		});
	}
};

//日期格式化
Date.prototype.format = function(fmt)
{ //author: meizz
   var o = {
      "M+" : this.getMonth()+1,                 //月份
      "d+" : this.getDate(),                    //日
      "h+" : this.getHours(),                   //小时
      "m+" : this.getMinutes(),                 //分
      "s+" : this.getSeconds(),                 //秒
      "q+" : Math.floor((this.getMonth()+3)/3), //季度
      "S"  : this.getMilliseconds()             //毫秒
   };
   if(/(y+)/.test(fmt))
      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   for(var k in o)
      if(new RegExp("("+ k +")").test(fmt))
         fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
   return fmt;
}
