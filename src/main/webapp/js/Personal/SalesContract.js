$(function(){
	cr_item.getItemType($("#addItemType"));
	saleManagement.pageType("preAllPage");//显示物品管理列表第一页
	cr_normal.showTime1();
})


//删除销货管理列表信息
function deletesaleManageMsg(dom){
	var stackedNum = $(dom).parents("tr").children().eq(8).children().eq(0).html();//查找当前元素的父元素为tr标签的所有子标签的第九个元素的html内容
	if(stackedNum == "0"){
		cr_dialogBox.confirm(true,"确认要删除吗?",function(flag){
			if(flag){
				var url = HEADER + "deletesale.do";
				var id = $(dom).parents("tr").attr("data-cno");
				$.ajax({
					type:"post",
					url:url,
					data:{
						"id":id
					},
					success:function(data){
						cr_item.showTip(data.msg,function(){
							saleManagement.pageType(saleManagement.page);//调用saleManagement方法刷新显示列表
						});
					}
				});
			}
		});
	}else{
		cr_dialogBox.alert(true,"物品已有入库数，不能删除!");
	}
}

//搜索
function search(){
	var searchValue = $("#search").val();
	saleManagement.searchMsg(searchValue);
}

/*----------------------------录入销货单方法------------------------*/
//根据选择不同物品给录入销货单框赋值
function getOtherMsg(dom){
	var optionDom = $(dom).find("option:selected");
	var gspec = !!optionDom.attr("gspec")?optionDom.attr("gspec"):"";
	var unit = !!optionDom.attr("unit")?optionDom.attr("unit"):"";
	var provider = !!optionDom.attr("provider")?optionDom.attr("provider"):"";
	$("#shoppingUnit").html(unit);
	$("#addProvider").html(provider);
	$("#addGspec").html(gspec);
}

//提交新增录入销货单
function addsalechaseList(){
	var url = HEADER + "addsaleInfo.do";
	var cid = $("#enterShoppingOrderNumber").html();//销货单号
	var gid = $("#addItemType").val();//物品名称
	var cdate = $("#addCdate").val();//销货日期
	var cnum = $("#addNumber").val();//销货数量
	if(gid!=""){//如果物品编号（物品类型）不为空
		if(cdate!=""){//如果销货日期不为空
			if(cnum!=""){//如果销货数量不为空
				$.ajax({
					type:"post",
					url:url,
					data:{
						"cid":cid,//销货单号
						"gid":gid,//物品编号（物品类型）
						"cdate":cdate,//销货日期
						"cnum":cnum,//销货数量
						"cinstate":"01",//入库状态（00全部；01 未入库；02未完全入库；03 已完全入库）
						"instoragednum":0,//已入库数量（默认0）
						"instoragenum":cnum//未入库数量
					},
					success:function(data){
						if(data.msg == 1 || data.msg == "1"){//返回参数msg=1表示成功
							cr_dialogBox.alert(true,"提交成功!");
							$("#enterShoppingList").modal("hide");
							saleManagement.pageType(saleManagement.page);//获取（刷新）物品管理列表
						}else if(data.msg == 0 || data.msg == "0"){//返回参数msg=0表示失败
							cr_dialogBox.alert(true,"提交失败!");
						}
					}
				});
			}else{
				cr_dialogBox.alert(true,"请输入销货数量!");
			}
		}else{
			cr_dialogBox.alert(true,"请输入时间!");
		}
	}else{
		cr_dialogBox.alert(true,"请选择物品类型!");
	}
}
/*----------------------------录入销货单方法结束------------------------*/

/*-----------------------入库方法---------------------------*/
//提交入库信息
function submitIncomingMsg(){
	var url = HEADER + "addStockInfo.do";
	var snum = $("#inStackedNum").val();//入库数量
	var sopertype = "01";//
	var stype = $("#inStackType").val();//入库类型
	var cno = $("#inGoodsName").attr("data-cno");
	var gno = $("#inGoodsName").attr("data-gno");
	var cnum = $("#inGoodsName").attr("data-cnum");
	if(snum!=""){//如果入库数量不为空
		if(snum*1 <= cnum*1){//如果入库数量小于等于销货数量
			$.ajax({
				type:"post",
				url:url,
				data:{
					"cid":cno,//销货单号
					"gid":gno,//物品编号
					"snum":snum,//入库数量
					"sopertype":sopertype,//操作类型（01 入库；02 出库）
					"stype":stype//入库类型（01销货入库；02换新入库）   
				},
				success:function(data){
					cr_item.showTip(data.msg,function(){
						$("#goodsInventory").modal("hide");//隐藏货物入库的模态框
						saleManagement.pageType(saleManagement.page);//获取（刷新）物品管理列表
					});
				}
			});
		}else{
			cr_dialogBox.alert(true,"入库数量不能大于销货数量!");
		}
	}else{
		cr_dialogBox.alert(true,"请输入入库数量!");
	}
}

//提交退货信息
function submitReturnMsg(){
	var url = HEADER + "addStockInfo.do";
	var snum = $("#outNumber").val();//出库数量
	var sopertype = "02";//操作类型：出库
	var stype1 = $("#inStackType").val();//入库类型
	var sthstate = $("#outReturnType").val();//退货物品状态
	var cno = $("#outGoodsName").attr("data-cno");
	var gno = $("#outGoodsName").attr("data-gno");
	var stackedNum = $("#outGoodsName").attr("data-stackedNum");
	if(snum!=""){
		if(snum*1<=stackedNum*1){
			$.ajax({
				type:"post",
				url:url,
				data:{
					"cid":cno,//销货编号
					"gid":gno,//物品编号
					"snum":snum,//数量
					"sopertype":sopertype,//操作类型（01入库；02 出库） 
					"stype1":stype1,//出库类型（01退货；02发货出库） 
					"sthstate":sthstate//退货物品状态（01故障 ；02 正常）
				},
				success:function(data){
					cr_item.showTip(data.msg,function(){//showTip（）？
						$("#returnGoods").modal("hide");//隐藏退货模态框
						saleManagement.pageType(saleManagement.page);//获取（刷新）物品管理列表
					});
				}
			});
		}else{
			cr_dialogBox.alert(true,"退货数量不能大于已入库数量!");
		}
	}else{
		cr_dialogBox.alert(true,"请输入退货数量!");
	}
}
/*-----------------------入库方法结束---------------------------*/

/*------------------------------------窗口打开方法开始-----------------------------------------*/
//打开入库记录窗口
function openInRecordsModdle(dom){
	var id = $(dom).parents("tr").attr("data-cno");
	$("#inventoryRecords").attr("data-cno",id);
	$("#inventoryRecords").modal("show");
	itemDetails.pageType("preAllPage");
}

//打开物品入库窗口
//入库数量不能大于销货数量
function openGoodsInModdle(){
	//当前选中的订单列
	var trDom = $("#saleManagementMsg").find("input[name=order]:checked").parents("tr");
	if(!!trDom.children().eq(1).children().eq(0).html()){
		var name = trDom.children().eq(1).children().eq(0).html();
		var cnum = trDom.children().eq(6).children().eq(0).html();//销货数量
		var stockNum = trDom.children().eq(9).children().eq(0).html();//待入库数
		var order = $.trim(trDom.children().eq(0).children().eq(0).text());
		var gid = trDom.children().eq(1).attr("data-gid");
		var cno = trDom.attr("data-cno");
		var gno = trDom.attr("data-gno");
		$("#goodsInventory").modal("show");
		$("#inGoodsName").html(name).attr({"data-gid":gid,"data-cno":cno,"data-gno":gno,"data-cnum":cnum});
		$("#inShouldNum").html(cnum);
		$("#inStackNum").html(stockNum);
		$("#inStackedNum").val("");
		$("#inStackType").val("01");
		$("#inOrder").val(order);
	}else{
		cr_dialogBox.alert(true,"请选择销货列表列!");
	}
}

//打开退货窗口
//已入库数位0不能打开退货窗口 退货数量不能大于已入库数量
function openGoodsRetrun(){
	//当前选中的订单列
	var trDom = $("#saleManagementMsg").find("input[name=order]:checked").parents("tr");
	if(!!trDom.children().eq(1).children().eq(0).html()){
		var name = trDom.children().eq(1).children().eq(0).html();
		var gspec = trDom.children().eq(2).children().eq(0).html();
		var provider = trDom.children().eq(3).children().eq(0).html();
		var unit = trDom.children().eq(4).children().eq(0).html();
		var gid = trDom.children().eq(1).attr("data-gid");
		var order = $.trim(trDom.children().eq(0).children().eq(0).text());
		var cno = trDom.attr("data-cno");
		var gno = trDom.attr("data-gno");
		var stackedNum = trDom.children().eq(8).children().eq(0).html()*1;//已入库数量
		if((stackedNum+"")!="NaN" && stackedNum > 0){
			$("#returnGoods").modal("show");
			$("#outGoodsName").html(name).attr({"data-gid":gid,"data-cno":cno,"data-gno":gno,"data-stackedNum":stackedNum});
			$("#outGspec").html(gspec);
			$("#outUnit").html(unit);
			$("#outProvider").html(provider);
			$("#outOrder").val(order);
			$("#outReturnType").val("00");
			$("#outNumber").val("");
		}else{
			cr_dialogBox.alert(true,"暂没有库存，不能退货!");
		}
	}else{
		cr_dialogBox.alert(true,"请选择销货列表列!");
	}
}

//打开录入销货清单窗口
function openEnterShoppingList(){
	var url = HEADER + "getNextsaleId.do";
	$.ajax({
		type:"get",
		url:url,
		async:false,
		success:function(data){
			if(!!data){
				$("#enterShoppingOrderNumber").html(data.nextsaleId);
			}
		}
	});
	$("#addItemType").val("");
	$("#shoppingUnit").html("");
	$("#addProvider").html("");
	$("#addGspec").html("");
	$("#enterShoppingList").modal("show");
}
/*------------------------------------窗口打开方法结束-----------------------------------------*/

//获取物品管理列表
var saleManagement = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param:"",
	pageType: function(type) {
		loadingModdle.showModdle();
		var pages = 0;
		var $this = this;
		var cinstate = $("#salechType").val();
		if(type == "preAllPage"){
			pages = 1;
		}else if(type == "prePage"){
			pages = this.prePage;
		}else if(type == "nextPage"){
			pages = this.nextPage;
		}else if(type == "nextAllPage"){
			pages = this.lastPage;
		}else if(typeof(type) == "number"){
			pages = type;
		};
		$.ajax({
			type: "get",
			url: HEADER + "getsaleList.do?page="+$this.page+"&pageSize=10&cinstate="+cinstate+"&findkey="+$this.param,
			//page：页数  ；   pageSize：每页显示记录数； cinstate：入库状态（默认 01 未完全入库）； findkey：销货单号或物品名称；  sinstate：入库状态（01,02,03） 
			success: function(data){
				if(!!data){
					loadingModdle.closeModdle();
					var jsonArr = data.list;
					var len = jsonArr.length;
					var itemType = "";
					var html = "";
					for(var i=0;i<jsonArr.length;i++){
						switch(jsonArr[i].cinstate){
							case "01":itemType="未完全入库";break;
							case "02":itemType="已完全入库";break;
							default:itemType="";break;
						}
						html += "<tr data-cno='"+jsonArr[i].cno+"' data-gno='"+jsonArr[i].gno+"' data-cid='"+jsonArr[i].cid+"' data-id='"+jsonArr[i].id+"'>"+
								"	<td style='width: 160px;'><label><input type='radio' name='order'/>"+(jsonArr[i].cid==null?"":jsonArr[i].cid)+"</label></td>"+
								"	<td data-gid='"+jsonArr[i].gid+"'><label>"+(jsonArr[i].gname==null?"":jsonArr[i].gname)+"</label></td>"+
								"	<td><label>"+(jsonArr[i].gspec==null?"":jsonArr[i].gspec)+"</label></td>"+
								"	<td><label>"+(jsonArr[i].provider==null?"":jsonArr[i].provider)+"</label></td>"+
								"	<td><label>"+(jsonArr[i].unit==null?"":jsonArr[i].unit)+"</label></td>"+
								"	<td><label>"+(jsonArr[i].cdate==null?"":new Date(jsonArr[i].cdate.time).format("yyyy-MM-dd"))+"</label></td>"+
								"	<td><label>"+(jsonArr[i].cnum==null?"":jsonArr[i].cnum)+"</label></td>"+
								"	<td><label>"+itemType+"</label></td>"+
								"	<td><label>"+(jsonArr[i].instoragednum==null?"":jsonArr[i].instoragednum)+"</label></td>"+
								"	<td><label>"+(jsonArr[i].instoragenum==null?"":jsonArr[i].instoragenum)+"</label></td>"+
								"	<td><label><span onclick='openInRecordsModdle(this);'>详情</span>/<span onclick='deletesaleManageMsg(this);'>删除</span></label></td>"+
								"</tr>";
					}
					$("#saleManagementMsg").html(html);
					$this.changePage(data.pageNum, data.prepage, data.nextPage, data.pageTotal);
					checkAllBtns.check("saleManagementBtns",saleManagement,"saleManagement");
					$("#saleManagementNumber").html(($this.page==0?1:$this.page)+"/"+($this.lastPage==0?1:$this.lastPage));
					$("#saleManagementTotal").html(data.totalRecords);
				}else{
					loadingModdle.closeModdle();
				}
			},
			error:function(){
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
	searchMsg:function(param){
		var $this = this;
		$this.param = param;
		$this.pageType($this.page);
	}
};

//获取详情页面列表信息
var itemDetails = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param:"",
	pageType: function(type) {
		loadingModdle.showModdle();//打开默认隐藏的模块（id=loadingModdle）
		var pages = 0;
		var $this = this;
		var id = $("#inventoryRecords").attr("data-cno");
		if(type == "preAllPage"){
			pages = 1;
		}else if(type == "prePage"){
			pages = this.prePage;
		}else if(type == "nextPage"){
			pages = this.nextPage;
		}else if(type == "nextAllPage"){
			pages = this.lastPage;
		}else if(typeof(type) == "number"){
			pages = type;
		};
		$.ajax({
			type: "get",
			url: HEADER + "getsaleDetailById.do?page="+$this.page+"&pageSize=10&id="+id,
			success: function(data){
				if(!!data){
					loadingModdle.closeModdle();//关闭
					var jsonArr = data.list;
					var len = jsonArr.length;
					var stype = "";
					var html = "";
					for(var i=0;i<jsonArr.length;i++){
						switch(jsonArr[i].sopertype){
							case "01":stype="入库";break;
							case "02":stype="出库";break;
							default:stype="";break;
						}
						html += "<tr>"+
								"	<td>"+(jsonArr[i].sid==null?"":jsonArr[i].sid)+"</td>"+
								"	<td>"+(jsonArr[i].sdate==null?"":new Date(jsonArr[i].sdate.time).format("yyyy-MM-dd"))+"</td>"+
								"	<td>"+stype+"</td>"+
								"	<td>"+(jsonArr[i].sperson==null?"":jsonArr[i].sperson)+"</td>"+
								"	<td>"+(jsonArr[i].snum==null?"":jsonArr[i].snum)+"</td>"+
								"</tr>";
					}
					$("#itemDetailsMsg").html(html);//写到表格里面
					$this.changePage(data.pageNum, data.prepage, data.nextPage, data.pageTotal);
					checkAllBtns.check("itemDetailsBtns",itemDetails,"itemDetails");
					$("#itemDetailsNumber").html(($this.page==0?1:$this.page)+"/"+($this.lastPage==0?1:$this.lastPage));
					$("#itemDetailsTotal").html(data.totalRecords);
				}else{
					loadingModdle.closeModdle();//关闭
				}
			},
			error:function(){
				loadingModdle.closeModdle();//关闭
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
	searchMsg:function(param){
		var $this = this;
		$this.param = param;
		$this.pageType($this.page);
	}
};