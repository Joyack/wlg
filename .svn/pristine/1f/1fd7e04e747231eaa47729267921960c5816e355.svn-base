$(function() {
	cr_item.initTime($("#outBoundBeginTime"), "start");
	cr_item.initTime($("#outBoundEndTime"));
	outinboundDetails.pageType("preAllPage");
	showTime();

})


// function Exportofdocuments() {
//     var arrs=new Array();
//     var trDom = $("#outinboundDetailsMsg").find("input[name=order]:checked").parents("tr");
//     if(trDom.length>0){
//     	alert(trDom.length);
//     	arrs.push(trDom.attr("data-id"));
//
//     	alert(arrs);
//
//         window.location.href= HEADER + "stock/exportOutinStock.do?ids=" + arrs.toString();
// 	}else{
//     	cr_dialogBox.alert(true,"请先选择列表再进行导出！");
// 	}
// }

function Exportofdocuments(){ //jquery获取复选框值
    var chk_value =[];
    $('input[name="order"]:checked').each(function(){
        chk_value.push($(this).attr("data-id"));
    });
    if(chk_value.length>0){
			var i = 0;
			var timer = setInterval(function(){
				if(i > chk_value.length){
					clearInterval(timer);
				}
                //window.location.href= HEADER + "stock/exportOutinStock.do?ids=" + chk_value[i];
                 //$("#Exportofdocuments").prop("href",HEADER+"stock/exportOutinStock.do?ids=" + chk_value[i]);
                window.open(HEADER + "stock/exportOutinStock.do?ids=" + chk_value[i],"_blank");
                window.opener=null;
                window.open('','_self');
                window.close();
                i++;
			},1000);
    }else{
        cr_dialogBox.alert(true,"请先选择列表再进行导出！");
	}
    //alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value);
}
//日期选择
function showTime() {
	$(".timeSelect").datetimepicker({
		language: 'en',
		format: "yyyy-mm-dd",
		minView: "month",
		todayBtn: 1, //点击显示今天时间
		autoclose: 1, //选择日期后关闭
		todayHighlight: 1, //当前天数高亮
		startView: 3, //开始的显示年、还是月、或日、时、分、秒
		forceParse: 0, //默认是那一天
		minuteStep: 1
	});
}

//根据类型搜索物品
function search() {
	var outBoundSearch = $("#outBoundSearch").val();
	outinboundDetails.searchMsg(outBoundSearch);
}

//获取出入库明细列表
var outinboundDetails = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param: "",
	pageType: function(type) {
		loadingModdle.showModdle();
		var pages = 0;
		var pagesize=10;
		var $this = this;
		var begintime = $("#outBoundBeginTime").val() + " 00:00:00"; //开始时间
		var endtime = $("#outBoundEndTime").val() + " 23:59:59"; //结束时间
		var sopertype = $("#outInBoundType").val(); //出入库类型
		var auditstatus="";
		//		alert(sopertype);
		if(type == "preAllPage") {
			pages = 1;
		} else if(type == "prePage") {
			pages = this.prePage;
		} else if(type == "nextPage") {
			pages = this.nextPage;
		} else if(type == "nextAllPage") {
			pages = this.lastPage;
		} else if(typeof(type) == "number") {
			pages = type;
		};
		$.ajax({
			type: "get",
			url: HEADER + "stock/queryStockOutinList.do?page=" + pages + "&pageSize="+pagesize+"&findkey=" + $this.param + "&begintime=" + begintime + "&endtime=" + endtime + "&sopertype=" + sopertype,
			success: function(data) {
				if(!!data) {
					loadingModdle.closeModdle();
					var jsonArr = data.result;
					var len = jsonArr.length;
					var html = "";
					var gType = "";
					var cType = "";
					for(var i = 0; i < jsonArr.length; i++) {
						switch(jsonArr[i].gtid) {
							case "01":
								gType = "主料";
								break;
							case "02":
								gType = "辅料";
								break;
                            case "03":
                                gType = "成品";
                                break;
							default:
								gType = "";
								break;
						}
						switch(jsonArr[i].sopertype) {
							case "01":
								switch (jsonArr[i].stype){
									case "01":
                                        cType="采购入库";
										break;
                                    case "02":
                                        cType="销货退货入库";
                                        break;
                                    case "03":
                                        cType="成品入库";
                                        break;
								}
								break;
							case "02":
                                switch (jsonArr[i].stype1) {
                                    case "01":
                                        cType = "发货出库";
                                        break;
                                    case "02":
                                        cType = "采购退货出库";
                                        break;
                                    case "03":
                                        cType = "领料出库";
                                        break;
                                    case "04":
                                        cType = "领用出库";
                                        break;
                                }
                                        break;
							default:
								cType = "";
								break;
						}
                        switch (jsonArr[i].auditstatus) {
                            case "00":
                                auditstatus = "出库完成";
                                break;
                            case "01":
                                auditstatus = "审核中";
                                break;
                            case "02":
                                auditstatus = "审核通过（仓库）";
                                break;
                            case "03":
                                auditstatus = "审核未过（仓库）";
                                break;
                            case "04":
                                auditstatus = "撤销";
                                break;
                            case "05":
                                auditstatus = "审核通过（领导）";
                                break;
                            case "06":
                                auditstatus = "审核未过（领导）";
                                break;
                        }
						var xuhao = cr_item.getXuhao(i, pages,pagesize);
						//						alert("序号:"+xuhao);
						html += "<tr data-id='" + jsonArr[i].id + "'>" +
							 "	<td style='width: 100px;'><label><input type='checkbox' data-id='"+jsonArr[i].id +"' name='order'/>" + cr_item.getXuhao(i, pages,pagesize) + "</label></td>" +
                            //  "	<td style='width: 100px;'><label>" + cr_item.getXuhao(i, pages) + "</label></td>" +
                            //							"	<td>" + cr_item.getXuhao(i, pages) + "</td>" +
							"	<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
							"	<td>" + gType + "</td>" +
							"	<td>" + (jsonArr[i].gspec == null ? "" : jsonArr[i].gspec) + "</td>" +
							"	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
							"	<td>" + (jsonArr[i].proname == null ? "" : jsonArr[i].proname) + "</td>" +
							"	<td>" + cType + "</td>" +
                            "	<td>" + (jsonArr[i].yt == null ? "" : jsonArr[i].yt) + "</td>" +
							"	<td>" + (jsonArr[i].storagenum == null ? "" : jsonArr[i].storagenum) + "</td>" +
							"	<td>" + (jsonArr[i].sdate == null ? "" : new Date(jsonArr[i].sdate.time).format("yyyy-MM-dd hh:mm:ss")) + "</td>" +
							"	<td>" + (jsonArr[i].sperson == null ? "" : jsonArr[i].sperson) + "</td>" +
                            "	<td>" + auditstatus + "</td>" +
							// "	<td></td>" +
							"</tr>";
					}
					$("#outinboundDetailsMsg").html(html);
					$this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans);
					checkAllBtns.check("outinboundDetailsBtns", outinboundDetails, "outinboundDetails");
					$("#outinboundDetailsNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
					$("#outinboundDetailsTotal").html(data.totalCount);
				} else {
					loadingModdle.closeModdle();
				}
			},
			error: function() {
				loadingModdle.closeModdle();
			}
		});
	},
	//改变变量的值
	changePage: function(page, prePage, nextPage, lastPage) {
		this.page = page;
		this.nextPage = nextPage;
		this.prePage = prePage;
		this.lastPage = lastPage;
	},
	//搜索功能
	searchMsg: function(param) {
		var $this = this;
		$this.param = param;
		$this.pageType($this.page);
	}
};