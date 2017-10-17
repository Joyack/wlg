/*$(function(){
	$(document).on("keydown",function(e){
		var ev = event || window.event || arguments.callee.caller.arguments[0]; 
		if(e && e.keyCode==13){
			$("#alertModdle").fadeOut("fast");
		};
	});
});*/
//常用方法
var cr_normal = {
	//计算浏览器高度
	getHW:function(){
		var wHeight = $(window).height()+135;
		$("#box").children().height(wHeight);
		wHeight = $(window).height()+75;
		$("#treeDom").height(wHeight);
	},
	//显示时间选择
	showTime:function(){
		$(".timeSelect").datetimepicker({
	    	language:  'en',
	        weekStart: 0,//一个星期的开始
	        todayBtn:  1,//点击显示今天时间
			autoclose: 1,//选择日期后关闭
			todayHighlight: 1,//当前天数高亮
			startView: 2,//开始的显示年、还是月、或日、时、分、秒
			forceParse: 0,//默认是那一天
	        showMeridian: 0,//十二小时显示还是二十四小时显示
	        minuteStep:1
	   });
	},
	showTime1:function(){
		$(".timeSelect").datetimepicker({
			format:"yyyy-mm-dd",
	    	language:  'en',
	        weekStart: 0,//一个星期的开始
	        todayBtn:  1,//点击显示今天时间
			autoclose: 1,//选择日期后关闭
			todayHighlight: 1,//当前天数高亮
			startView: 2,//开始的显示年、还是月、或日、时、分、秒
			forceParse: 0,//默认是那一天
	        showMeridian: 0,//十二小时显示还是二十四小时显示
	        minuteStep:1,
	        minView:2
	   });
	},
	showTime2:function(){
		$(".timeSelect").datetimepicker({
			format:"yyyy-mm",
	    	language:  'en',
	        weekStart: 0,//一个星期的开始
//	        todayBtn:  1,//点击显示今天时间
			autoclose: 1,//选择日期后关闭
			todayHighlight: 1,//当前天数高亮
			startView: 3,//开始的显示年、还是月、或日、时、分、秒
			forceParse: 0,//默认是那一天
	        showMeridian: 0,//十二小时显示还是二十四小时显示
	        minuteStep:1,
	        minView:3
	   });
	},

	//显示与隐藏回到顶部按钮
	returnTop:function(dom){
		$("#treeDom").scroll(function(){
			var top = $("#treeDom").scrollTop();
			if(top >= 100){
				$(".cr_top").fadeIn(500);
			} else {
				$(".cr_top").fadeOut(500);
			};
		});
		$("#box").scroll(function(){
			var top1 = $("body").scrollTop();
			if(top1 >= 100){
				$(".cr_top").fadeIn(500);
			} else {
				$(".cr_top").fadeOut(500);
			};
		});
	},
	//区域选择回到顶部
	areaTop:function(){
		$("#treeDom").animate({scrollTop:0},500);
		$("body").animate({scrollTop:0},500);
	},
	//检查浮点数
	checkFloat:function(dom){
		var value = $(dom).val();
		if(value.split(".")[1]){
			$(dom).val(cr_number.getFloatStr(value));
		}else{
			if((value*1+"") == "NaN"){
				$(dom).val("0");
			}else{
				$(dom).val(value);
			};
		}
	},
	//判断小数，若只有一位小数则自动补一个0
	checkFloatGetFirst:function(value){
		//判断是否为小数
		var number = value;
		if(typeof(value) == "number" && (""+value*1) != "NaN"){
			if((""+value).split(".")[1]){
				var arr = (""+value).split(".");
				if(arr[1] == "00"){
					number = arr[0];
				}else if(arr[1].length == 1){
					number = arr[0]+"."+arr[1]+"0";
				}else if(arr[1].length > 1){
					var second = arr[1].substring(0,2);
					number = arr[0]+"."+second;
				};
			}else{
				number = value;
			};
		}else{

			number = value;
		};
		return number;
	},
    getFloatStr:function(num){
		var s = parseInt(num)+"";
		if(s=="NaN"){
            cr_dialogBox.alert(true,"请输入正确的数字！");
		}else{
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
		}
    },
	//判断正数
    checkInteger: function (dom) {
        var value = parseInt($(dom).val())+"";
        console.log(value);
        if (value == "NaN") {
            cr_dialogBox.alert(true,"请输入正确的数字!");
            $(dom).val(0);
        } else if(value*1<0){
            cr_dialogBox.alert(true,"请输入正数！")
            $(dom).val(0);
        }else{
            $(dom).val(parseInt(Math.round(value)));
        };
    },
	//判断是不是数字
	checkIsNumber:function(dom){
    	var value = $(dom).val()*1;
    	console.log(value);
        if(isNaN(value)){
            cr_dialogBox.alert(true,"请输入正确的数字!");
            $(dom).val(0);
        }else if(value*1<0){
            cr_dialogBox.alert(true,"请输入正数！")
            $(dom).val(0);
        }else {
            $(dom).val(value);
		}
	},
	//检查邮箱是否正确
	checkEmail:function(dom,value){
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var email = "";
		if(value == null){
			email = $(dom).val();
		}else{
			email = value;
		};
		if(!!filter.exec(email) == false){
			$(dom).val("");
		};
		return !!filter.exec(email);
	},
	//检测中文
	checkChinese:function(dom) {
		var value = dom.value;
		dom.value = value.replace(/[\W]/g, '');
	},
	//检测时间
	checkTime:function(dom){
		var $this = this;
		var dateTime = $(dom).val();
		var nowTime = new Date().getTime();
		var selectTime = new Date(dateTime).getTime();
		if(dateTime == ""){
			cr_dialogBox.alert(true,"请选择时间！");
		}else if((nowTime - selectTime)<0){
//			cr_dialogBox.alert(true,"日期不能大于当前时间!");
			$(dom).val($this.initTime1(new Date()));
		};
	},
	//检查电话格式是否正确
	checkPhoneNumber:function(dom,number){
		var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
		var value = "";
		if(number == null){
			value = $(dom).val();
		}else{
			value = number;
		};
		if(value != ""){
			$(dom).val(reg.exec(value));
		};
		return !!reg.exec(value);
	},
	//初始化时间
	initTime:function(time){
		var year = time.getFullYear()<10?"0"+time.getFullYear():time.getFullYear();
		var month = (time.getMonth()+1)<10?"0"+(time.getMonth()+1):(time.getMonth()+1);
		var day = time.getDate()<10?"0"+time.getDate():time.getDate();
		var hour = time.getHours()<10?"0"+time.getHours():time.getHours();
		var minute = time.getMinutes()<10?"0"+time.getMinutes():time.getMinutes();
		var seconds = time.getSeconds()<10?"0"+time.getSeconds():time.getSeconds();
		return year+"-"+month+"-"+" "+hour+":"+minute;
	},
	initTime1:function(time){
		var year = time.getFullYear()<10?"0"+time.getFullYear():time.getFullYear();
		var month = (time.getMonth()+1)<10?"0"+(time.getMonth()+1):(time.getMonth()+1);
		var day = time.getDate()<10?"0"+time.getDate():time.getDate();
		return year+"-"+month+"-"+day;
	},
	//将富文本格式转换为html格式
	outHTML:function(value) {
		var outHTML=((value.replace(/<(.+?)>/gi,"&lt;$1&gt;")).replace(/ /gi,"&nbsp;")).replace(/\n/gi,"gcr132gcr132");
		return outHTML;
	},
	//将html格式转换为富文本格式
	inHTML:function(value){
		var inHTML = (value.replace(new RegExp("gcr132gcr132","gim"),"\n")).replace(new RegExp("&nbsp;","gmi")," ");
		return inHTML;
	},
	inHTML1:function(value){
		if(!!value){
			var inHTML = value.split("gcr132gcr132");
			var temp = "";
			for(var i=0;i<inHTML.length;i++){
				temp += "<span class='textInit'>"+inHTML[i]+"</span>"
			}
			return temp;
		}else{
			return "";
		}
	}
};

//修改tab框样式
var checkTab={
	//切换tab框内容
	changeTabBg:function(dom){
		var liDoms = $("#myTab").find("li");
		for(var i=0;i<liDoms.length;i++){
			$(liDoms[i]).children("a").css({"background":"none","border":"none"});
		};
		$(dom).children("a").css({
			"background":"url(images/tab_bg.png) no-repeat",
			"background-size":"100%",
			"width":"150px",
			"height":"31px",
			"line-height":"14px",
			"text-align":"center",
			"cursor":"pointer"
		});
	},
	tab_init:function(){
		var liDoms = $("#myTab").children("li");
		$("#myTab").children("li").eq(0).children("a").css({
			"background":"url(images/tab_bg.png) no-repeat",
			"background-size":"100%",
			"width":"150px",
			"height":"31px",
			"line-height":"14px",
			"text-align":"center",
			"border":"none"
		});
		for(var i=1;i<liDoms.length;i++){
			$("#myTab").children("li").eq(i).children("a").css({
				"width":"150px",
				"height":"31px",
				"line-height":"14px",
				"text-align":"center",
				"border":"none"
			});
		};
	}
};

//区域公共方法
var cr_areNormal = {
	//根据选中的小区赋值给下拉框
	gainAreaForCheck:function(callback,dom){
		if(cr_cache.getCache("areaIdName",true) && cr_cache.getCache("areaOperationId",true)){
			var domName = cr_cache.getCache("areaIdName",true).split(",");
			var domId = cr_cache.getCache("areaOperationId",true).split(",");
			var html = "<option value=''>--请选择--</option>";
			for(var i=0;i<domId.length;i++){
				html += "<option value="+domId[i]+">"+domName[i]+"</option>";
			};
			if(!!dom){
				$(dom).html(html);
			}else{
				$("#lid").html(html);
				$("#lid1").html(html);
			}
		}else{
			cr_dialogBox.alert(true,"请选择小区！");
		};
		if(callback){
			callback();
		}
	}
};

/*-----------------------------------检查分页按钮--------------------------------------------------*/
var checkAllBtns = {
	check:function(btnDom,json,name){
		//检测按钮是否可用
		var btns = $("#"+btnDom).children();
		for(var i=0;i<btns.length;i++){
			$(btns[i]).removeAttr("disabled");
		}
		$(btns[0]).attr("onclick",name+".pageType('preAllPage');");
		$(btns[1]).attr("onclick",name+".pageType('prePage');");
		$(btns[3]).attr("onclick",name+".pageType('nextPage');");
		$(btns[4]).attr("onclick",name+".pageType('nextAllPage');");
		if(json.lastPage <= 1){
			for(var i=0;i<btns.length;i++){
				$(btns[i]).attr("disabled","disabled").removeAttr("onclick");
			};
		};
		if(json.page <= 1){
			$(btns[0]).attr("disabled","disabled").removeAttr("onclick");
			$(btns[1]).attr("disabled","disabled").removeAttr("onclick");
		}else if(json.page >= json.lastPage){
			$(btns[3]).attr("disabled","disabled").removeAttr("onclick");
			$(btns[4]).attr("disabled","disabled").removeAttr("onclick");
		};
	}
};

//图表
var cr_charts = {
	drawEcharts2:function(dom,title,xData,legend,series){
		var myChart = echarts.init(document.getElementById(dom));
		option = {
		    title: {
		        text: title,
		        left: 0
		    },
		    legend: {
		    	//数组
		        data: legend,
		        width:"75%",
		        left:"17%",
		        top:"4%",
//		        align:"left",
		        padding:[0,0,"10%",0]
		    },
		    toolbox: {
		        feature: {
		            dataView: {},
		            saveAsImage: {
		                pixelRatio: 2
		            }
		        }
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer: {
		            animation: false
		        }
		    },
		    xAxis: {
		        type: 'category',
		        data: xData,
		        scale: true
		    },
		    yAxis: {
		    },
		    series:series,
		    animationEasing: 'elasticOut',
		    animationDelayUpdate: function (idx) {
		        return idx * 5;
		    }
		};
		myChart.setOption(option);
	},
 	drawEchartsCircle:function(dom,title,dataName,data){
		// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(dom));
        // 指定图表的配置项和数据
        option = {
		    title : {
		        text: title,
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: dataName
		    },
		    series : [
		        {
		            name: '用电总量',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:data,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	},
	//画制垂直树状图
	/*
    legend=["收人","利润"];数据名称
    yAxis=['周一','周二','周三','周四','周五','周六','周日'];轴名称
    series--数据个数
	{
		name:'收入',
        type:'bar',
        stack: '总量',
        label: {
            normal: {
                show: true
            }
        },
        data:[320, 302, 341, 374, 390, 450, 420]
    }
	 * */
	drawVerticalChart:function(dom,legend,yAxis,series){
		// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(dom));
        option = {
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:legend
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            axisTick : {show: false},
		            data : yAxis
		        }
		    ],
		    series : series
		};
       	myChart.setOption(option);
	}
};