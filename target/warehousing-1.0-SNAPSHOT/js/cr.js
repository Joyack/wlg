var cr = {
	/*验证身份证方法
		直接返回true或者false
	*/
	VerifyidCard:function IdentityCodeValid(code) {
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
	},
	
	/*页面加载完毕后添加事件(可添加多个事件都不会对浏览器造成影响)*///1
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
	//保留2位小数//1
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
	/*通过id获取dom元素*///1
	dom:function(id){
		return document.getElementById(id);
	},
	
	/*通过className获取dom元素进行过滤*///1
	domClass:function(pid,sClass){
		var aEle = cr.dom(pid).getElementsByTagName('*');
		var arrs = [];
		for(var i=0;i<aEle.length;i++){
			if(aEle[i].className.indexOf(sClass)!=-1){
				arrs.push(aEle[i]);
			}
		}
		return arrs;
	},

	//找到dom元素下面的孩子//1
	getChildren:function(dom){
		return cr.dom(dom).children;
	},

	//找到dom元素下面需要的tag标签//1
	getTagName:function(dom,tagName){
		return cr.dom(dom).getElementsByTagName(tagName);
	},
	
	//经一个10进制的数变成各种进制数//1
	baseConverter:function baseConverter(num,scale){
		var arr = [];
		decNumber = num;
		while (decNumber > 0){
			rem = Math.floor(decNumber % scale);
			arr.push(rem);
			decNumber = Math.floor(decNumber / scale);
		}
		return arr.reverse();
	},

	//获取css中的样式的值，跟浏览器兼容无关//1
	getStyle:function(dom,attr){
		return window.getComputedStyle ? window.getComputedStyle(dom,false)[attr]:dom.currentStyle[attr];
	},
	
	//简单混入//1
	mixin:function(obj,obj2){
		for(var k in obj2){
			if(obj2.hasOwnProperty(k)){
				obj[k] = obj2[k];
			}
		}
		return obj;
	},
	
	//多对象混入//1
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
	},
	//获取鼠标的位置。兼容ie678//1
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
	
	//范围随机数//1
	randomRange:function(start,end){
		return Math.floor(Math.random()*(end-start+1))+start;
	},
	
	//元素居中//1
	centerElement:function(id){
		var boxDom = dom(id);
		var sw = boxDom.offsetWidth;
		var sh = boxDom.offsetHeight;
		var dw = window.innerWidth;
		var dh = window.innerHeight;
		var cleft = (dw - sw)/2;
		var ctop = (dh - sh)/2;
		boxDom.style.left = cleft+"px";
		boxDom.style.top = ctop+"px";
		window.onresize = function(){
			centerElement("box");
		};
	},
	//传入{id，eventType，type(要委托给谁)，callback}//1
	crOn:function(opts){
		var obj = cr.dom(opts.id);
		obj.addEventListener(opts.eventType,function(e){
			//事件类型兼容写法
			var ev = e || window.event;
			//当前目标元素
			var currentTarget = ev.currentTarget;
			//鼠标停留的元素
			var target = ev.target || ev.srcElement;
			//类型栏判断是不是委托
			if(opts.type){
				if(target.tagName.toLowerCase() === opts.type){
					if(opts.callback)opts.callback.call(target)
				}
			}else{
				if(opts.callback)opts.callback.call(currentTarget)
			}
			
		},false);
	},

	/*--------------------------------------------缓存的相关方法----------------------------------------------------------*/
	//整合了localStorage、sessionStorage、cookie
	//如果true进入的是localStorage否则不写或者false都是进入sessionStorage
	setCache:function(key,value){
		var args = arguments;
		if(window.localStorage){
			var mark = args[2]?true:args[2];
			var storage = mark?localStorage:sessionStorage;
			storage.setItem("CR_"+key,value);
		}else{
			var period = args[3]?true:args[3];
			crCookie.setCookie("CR_"+key,value,period);
		}
	},

	getCache:function(key){
		if(window.localStorage){
			var args = arguments;
			var mark = args[1]?true:args[1];
			var storage = mark?localStorage:sessionStorage;
			return storage.getItem("CR_"+key);
		}else{
			return crCookie.getCookie("CR_"+key);
		}
	},

	removeCache:function(key){
		if(window.localStorage){
			var args = arguments;
			var mark = args[1]?true:args[1];
			var storage = mark?localStorage:sessionStorage;
			storage.removeItem("CR_"+key);
		}else{
			crCookie.delCookie("CR_"+key);
		}
	}

};

/*--------------------------------------------面向对象的方法-------------------------------------------------------*/
var Json = {
	//继承
	inherits:function(Child, Parent) {
		var F = function () {};
		F.prototype = Parent.prototype;
		Child.prototype = new F();
		Child.prototype.constructor = Child;
	}
};
/*------------------------------------------------Ajax的方法--------------------------------------------------------------*/
var ajax = {
	creatXHR:function(){
		if (typeof XMLHttpRequest != "undefined"){
			return new XMLHttpRequest();
		} else if (typeof ActiveXObject != "undefined"){
			if (typeof arguments.callee.activeXString != "string"){
				var versions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.3.0",
								"MSXML2.XMLHttp"],
					i, len;
		
				for (i=0,len=versions.length; i < len; i++){
					try {
						new ActiveXObject(versions[i]);
						arguments.callee.activeXString = versions[i];
						break;
					} catch (ex){
						//skip
					}
				}
			}
			return new ActiveXObject(arguments.callee.activeXString);
		} else {
			throw new Error("No XHR object available.");
		}
	}
};

/*-----------------------------------------------cookie方法//1----------------------------------------------------------*/
//设置cookie添加，修改，删除
var crCookie = {
	setCookie : function(name, value,time,option){
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
	getCookie : function(name){
		var arr = document.cookie.split('; '); 
		if(arr.length==0) return ''; 
		for(var i=0; i <arr.length; i++){ 
			tmp = arr[i].split('='); 
			if(tmp[0]==name) return unescape(tmp[1]); 
		} 
		return ''; 
	},
	delCookie : function(name){
		//crCookie.setCookie(name,'',-1);
		this.setCookie(name,'',-1); 
		var date=new Date();
		date.setTime(date.getTime()-10000);
		document.cookie=name+"=; expires="+date.toGMTString()+"; path=/";
	},
	
	getCookieTime : function(time){
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
				"	<div style='position: relative;z-index: 999;'>"+
				"		<div class='cr_title'>"+
				"			<span class='cr_title_msg'>消息</span>"+
				"		</div>"+
				"		<div class='cr_content'>"+
				"			<div class='cr_content_msg'>"+
				"				<span id='cr_content_msg'>"+msg+"</span>"+
				"			</div>"+
				"			<div class='cr_bottom'>"+
				"				<div class='col-xs-4 col-md-4 col-md-push-4 col-xs-push-4 btn btn-primary' onclick='cr_dialogBox.alert(false);'>确认</div>"+
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
				"	<div style='position: relative;z-index: 999;'>"+
				"		<div class='cr_title'>"+
				"			<span class='cr_title_msg'>消息</span>"+
				"		</div>"+
				"		<div class='cr_content'>"+
				"			<div class='cr_content_msg'>"+
				"				<span id='cr_content_msg'>"+msg+"</span>"+
				"			</div>"+
				"			<div class='cr_bottom' id='btn22222222222'>"+
				"				<div class='col-xs-3 col-md-3 col-md-offset-2 col-xs-offset-2 btn btn-primary' data-flag=true>确认</div>"+
				"				<div class='col-xs-3 col-md-3 col-md-offset-2 col-xs-offset-2 btn btn-danger' data-flag=false>取消</div>"+
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
