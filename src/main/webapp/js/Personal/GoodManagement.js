$(function() {
	itemManagement.pageType("preAllPage"); //获取物品列表信息
	cr_item.getUnit($("#editUnit")); //给编辑物品模态框的单位下拉菜单赋值
	cr_item.getUnit($("#addUnit")); //给添加物品模态框的单位下拉菜单赋值

})

//提交新增数据
function submitAddItem() {
	var url = HEADER + "goods/addGoods.do";
	var addType = $("#addType").val(); //物品类型
	var addId = $("#addId").html();
	var addName = $("#addName").val();
	var addNumber = $("#addNumber").val();
	var addProvider = $("#addProvider").val();
	var addUnit = $("#addUnit").val();
	//	alert(addType+"="+addId+"="+addName+"="+addNumber+"="+addProvider+"="+addUnit);
	$.ajax({
		type: "post",
		url: url,
		data: {
			"gid": addId,
			"gname": addName, //名字
			"gtid": addType, //类型
			"gspec": addNumber, //规格
			"providerid": addProvider, //供应商
			"unit": addUnit //单位
		},
		success: function(data) {
			if(data.msg == 1 || data.msg == "1") {
				cr_dialogBox.alert(true, "增加数据成功!");
				itemManagement.pageType(itemManagement.page);
				$("#addMsg").modal("hide");
			} else if(data.msg == 0 || data.msg == "0") {
				cr_dialogBox.alert(true, "增加数据失败!");
			}
		}
	});
}

//提交编辑物品信息
function submitEditItem() {
	var url = HEADER + "goods/updateGoods.do";
	var editType = $("#editType").val();
	var editId = $("#editId").html();
	var editName = $("#editName").val();
	var editNumber = $("#editNumber").val();
	var editProvider = $("#editProvider").val();
	var editUnit = $("#editUnit").val();
	var id = $("#editId").attr("data-id");
	//	alert(editType+"="+editId+"="+editName+"="+editNumber+"="+editUnit+"="+id);
	$.ajax({
		type: "post",
		url: url,
		data: {
			"id": id,
			"gid": editId,
			"gname": editName,
			"gtid": editType,
			"gspec": editNumber,
			"providerid": editProvider,
			"unit": editUnit
		},
		success: function(data) {
			if(data.msg == 1 || data.msg == "1") {
				cr_dialogBox.alert(true, "修改成功!");
				itemManagement.pageType(itemManagement.page);
				$("#editMsg").modal("hide");
			} else if(data.msg == 0 || data.msg == "0") {
				cr_dialogBox.alert(true, "修改失败!");
			}
		}
	});
}

//打开新增物品类别
function addItem() {
	var url = HEADER + "goods/getNextGoodsId.do"; //获取下一个物品ID
	var addId = "";
	$.ajax({
		type: "get",
		url: url,
		async: false,
		success: function(data) {
			addId = data.nextGoodsId;
		}
	});
	$("#addId").html(addId);
	$("#addType").val("01");
	$("#addName").val("");
	$("#addNumber").val("");
	cr_item.getProviderId($("#addProvider")); //给供应商下拉框赋值
	$("#addUnit").val("件");
	$("#addMsg").modal("show"); //显示新增模态框
}

//打开编辑物品窗口
function showEdit(dom) {
	$("#editMsg").modal("show");
	var itemId = $(dom).parents("tr").children().eq(0).html(); //物品编号
	var itemName = $(dom).parents("tr").children().eq(1).html();
	var itemType = $(dom).parents("tr").children().eq(2).html();
	var itemNumber = $(dom).parents("tr").children().eq(3).html(); //规格型号
	var editProvider = $(dom).parents("tr").children().eq(4).html();
	var itemUnit = $(dom).parents("tr").children().eq(5).html();
	var id = $(dom).parents("tr").attr("data-id");
	// alert(editProvider);
	//				alert(itemId+"="+itemName+"="+itemType+"="+itemNumber+"="+itemUnit);
	cr_item.getProviderId($("#editProvider"),true); //给供应商下拉框赋值
    // alert(itemType);
    $("#editType option").each(function(){
        if($(this).text() == itemType){
            $(this).prop("selected","selected");
        }
    });
	$("#editId").html(itemId).attr("data-id", id);
	$("#editName").val(itemName);
	$("#editNumber").val(itemNumber);
	$("#editUnit").val(itemUnit);

    $("#editProvider option").each(function(){
        if($(this).text() == editProvider){
            $(this).attr("selected",true);
        }
    });
}

//根据类型搜索物品
function search() {
	var searchbox = $("#seachbox").val();
	itemManagement.searchMsg(searchbox);
}

//删除列表列
function deleteMsg(dom) {
	var url = HEADER + "goods/deleteGoods.do";
	var id = $(dom).parents("tr").attr("data-id");
    var gsnum = $(dom).parents("tr").attr("data-gsnum");//物品出入库记录数量
	if(gsnum*1<=0){
	cr_dialogBox.confirm(true, "确认要删除吗?", function(flag) {
		if(flag) {
			$.ajax({
				type: "post",
				url: url,
				data: {
					"id": id
				},
				success: function(data) {
					if(data.msg == 1 || data.msg == "1") {
						cr_dialogBox.alert(true, "删除信息成功!");
						itemManagement.pageType(itemManagement.page);
					} else if(data.msg == 0 || data.msg == "0") {
						cr_dialogBox.alert(true, "删除信息失败!");
					}
				}
			});
		}
	});
    }else {
		cr_dialogBox.alert(true,"有库存记录，不能删除！")
	}
}

//获取物品管理列表
var itemManagement = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param: "",
	pageType: function(type) {
		loadingModdle.showModdle();
		var pages = 0;
		var $this = this;
        var gtid = $("#itemType").val();
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
			url: HEADER + "goods/queryGoodsList.do?page=" + pages + "&pageSize=10&gtid="+gtid+"&findkey=" + $this.param,
			success: function(data) {
				if(!!data) {
					loadingModdle.closeModdle();
					var jsonArr = data.result;
					var len = jsonArr.length;
					var itemType = "";
					var html = "";
					for(var i = 0; i < len; i++) {
						switch(jsonArr[i].gtid) {
							case "01":
								itemType = "主料";
								break;
							case "02":
								itemType = "辅料";
								break;
							case "03":
								itemType = "成品";
						}
						html += "<tr data-gsnum='" + jsonArr[i].gsnum + "' data-id='" + jsonArr[i].id + "'>" +
							"	<td>" + (jsonArr[i].gid == null ? "" : jsonArr[i].gid) + "</td>" +
							"	<td>" + (jsonArr[i].gname == null ? "" : jsonArr[i].gname) + "</td>" +
							"	<td>" + itemType + "</td>" +
							"	<td>" + ((jsonArr[i].gspec == ""||null) ? "——" : jsonArr[i].gspec) + "</td>" +
							"	<td>" + (jsonArr[i].providername == null ? "" : jsonArr[i].providername) + "</td>" +
							"	<td>" + (jsonArr[i].unit == null ? "" : jsonArr[i].unit) + "</td>" +
							"	<td ><span onclick='showEdit(this);'>编辑</span>/<span onclick='deleteMsg(this);'>删除</span></td>" +
							"</tr>";
					}
					$("#itemManagementMsg").html(html); //将获取到的数据显示在表格的tbody中
					$this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
					checkAllBtns.check("itemManagementBtns", itemManagement, "itemManagement"); //检查分页按钮是否可用，不可用就显示为禁用标志
					$("#itemManagementNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
					$("#itemManagementTotal").html(data.totalCount);
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
        var searchbox = $("#seachbox").val();
        param=searchbox;
		$this.param = param;
		$this.pageType($this.page);
	}
};