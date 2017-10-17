$(function(){
	itemManagement.pageType("preAllPage");
	cr_item.getUnit($("#editUnit"));
	cr_item.getUnit($("#addUnit"));
	$("#downloadFile").prop("href",HEADER + "goods/downGoodsTemp.do");


})

/*function  downGoodsTemp() {
    $.ajax({
        url:HEADER + "goods/downGoodsTemp.do",
        data:{"aa":""},
        success:function (data){
            var d=$.parse(data);
            alert(d);
        }
    });
}
*/




//提交新增数据
function submitAddItem(){
	var url = HEADER + "goods/addGoods.do";
	var addType = $("#addType").val();
	var addId = $("#addId").html();
	var addName = $("#addName").val();
	var addNumber = $("#addNumber").val();
	var addProvider = $("#addProvider").val();
	var addUnit = $("#addUnit").val();
//	alert(addType+"="+addId+"="+addName+"="+addNumber+"="+addProvider+"="+addUnit);
	$.ajax({
		type:"post",
		url:url,
		data:{
			"gid":addId,
			"gname":addName,
			"gtid":addType,
			"gspec":addNumber,
			"provider":addProvider,
			"unit":addUnit
		},
		success:function(data){
			if(data.msg == 1 || data.msg == "1"){
				cr_dialogBox.alert(true,"增加数据成功!");
				itemManagement.pageType(itemManagement.page);
				$("#addMsg").modal("hide");
			}else if(data.msg == 0 || data.msg == "0"){
				cr_dialogBox.alert(true,"增加数据失败!");
			}
		}
	});
}

//提交编辑物品信息
function submitEditItem(){
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
		type:"post",
		url:url,
		data:{
			"id":id,
			"gid":editId,
			"gname":editName,
			"gtid":editType,
			"gspec":editNumber,
			"provider":editProvider,
			"unit":editUnit
		},
		success:function(data){
			if(data.msg == 1 || data.msg == "1"){
				cr_dialogBox.alert(true,"修改成功!");
				itemManagement.pageType(itemManagement.page);
				$("#editMsg").modal("hide");
			}else if(data.msg == 0 || data.msg == "0"){
				cr_dialogBox.alert(true,"修改失败!");
			}
		}
	});
}

//打开新增物品类别
function addItem(){
	var url = HEADER + "goods/getNextGoodsId.do";//获取下一个物品ID
	var addId = "";
	$.ajax({
		type:"get",
		url:url,
		async:false,
		success:function(data){
			addId = data.nextGoodsId;
		}
	});
	$("#addId").html(addId);
	$("#addType").val("01");
	$("#addName").val("");
	$("#addNumber").val("");
	$("#addProvider").val("");
	$("#addUnit").val("件");	
	$("#addMsg").modal("show");
}
//打开编辑物品窗口
function showEdit(dom){
	$("#editMsg").modal("show");
	var itemId = $(dom).parents("tr").children().eq(0).html();//物品编号
	var itemName = $(dom).parents("tr").children().eq(1).html();
	var itemType = $(dom).parents("tr").children().eq(2).html()==""?"":($(dom).parents("tr").children().eq(2).html()=="主料"?"01":"02");
	var itemNumber = $(dom).parents("tr").children().eq(3).html();//规格型号
	var editProvider = $(dom).parents("tr").children().eq(4).html();
	var itemUnit = $(dom).parents("tr").children().eq(5).html();
	var id = $(dom).parents("tr").attr("data-id");
//				alert(itemId+"="+itemName+"="+itemType+"="+itemNumber+"="+itemUnit);
	$("#editProvider").val(editProvider);
	$("#editType").val(itemType);
	$("#editId").html(itemId).attr("data-id",id);
	$("#editName").val(itemName);
	$("#editNumber").val(itemNumber);
	$("#editUnit").val(itemUnit);
}

//根据类型搜索物品
function searceMsg(){
	var itemType = $("#itemType").val();
	itemManagement.searchMsg(itemType);
}

//删除列表列
function deletMsg(dom){
	var url = HEADER + "goods/deleteGoods.do";
	var id = $(dom).parents("tr").attr("data-id");
	cr_dialogBox.confirm(true,"确认要删除吗?",function(flag){
		if(flag){
			$.ajax({
				type:"post",
				url:url,
				data:{
					"id":id
				},
				success:function(data){
					if(data.msg == 1 || data.msg == "1"){
						cr_dialogBox.alert(true,"删除信息成功!");
						itemManagement.pageType(itemManagement.page);
					}else if(data.msg == 0 || data.msg == "0"){
						cr_dialogBox.alert(true,"删除信息失败!");
					}
				}
			});
		}
	});
}

//提交上传数据
function doUpload(dom) { 
	var url = HEADER + "goods/importGoods.do";
    var formData = new FormData($("#exportFile")[0]);
    var fileName = $(dom).val().split("\\")[2];
    var fileType = fileName.split(".")[1];
    var reg = new RegExp("xl");
    if(reg.test(fileType)){
    	$.ajax({  
			url: url,  
	        type: 'POST',  
	        data:formData,
			async: false,  
			cache: false,
			contentType: false,
			processData: false,
			success: function (data) {
				if(data.msg == 0){
					cr_dialogBox.alert(true,"部分或全部导入失败");
				}else{
					cr_item.showTip(data.msg,function(){
						itemManagement.pageType(itemManagement.page);
						cr_dialogBox.alert(true,"上传文件成功!");
					});
				}
			},	
			error: function (data) {
				alert(JSON.stringify(data));
			}  
	    });
    }else{
    	cr_dialogBox.alert(true,"文件格式不正确!");
    }
}

//获取物品管理列表
var itemManagement = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param:"",
	pageType: function(type) {
		loadingModdle.showModdle();
		var pages = 0;
		var $this = this;
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
			url: HEADER + "goods/getGoodsList.do?page="+pages+"&pageSize=10&gtid="+$this.param,
			success: function(data){
				if(!!data){
					loadingModdle.closeModdle();
					var jsonArr = data.list;
					var len = jsonArr.length;
					var itemType = "";
					var html = "";
					for(var i=0;i<jsonArr.length;i++){
						switch(jsonArr[i].gtid){
							case "01":itemType="主料";break;
							case "02":itemType="辅料";break;
						}
						html += "<tr data-id='"+jsonArr[i].id+"'>"+
								"	<td>"+(jsonArr[i].gid==null?"":jsonArr[i].gid)+"</td>"+
								"	<td>"+(jsonArr[i].gname==null?"":jsonArr[i].gname)+"</td>"+
								"	<td>"+itemType+"</td>"+
								"	<td>"+(jsonArr[i].gspec==null?"":jsonArr[i].gspec)+"</td>"+
								"	<td>"+(jsonArr[i].provider==null?"":jsonArr[i].provider)+"</td>"+
								"	<td>"+(jsonArr[i].unit==null?"":jsonArr[i].unit)+"</td>"+
								"	<td><span onclick='showEdit(this);'>编辑</span>/<span onclick='deletMsg(this);'>删除</span></td>"+
								"</tr>";
					}
					$("#itemManagementMsg").html(html);
					$this.changePage(data.pageNum, data.prepage, data.nextPage, data.pageTotal);
					checkAllBtns.check("itemManagementBtns",itemManagement,"itemManagement");
					$("#itemManagementNumber").html(($this.page==0?1:$this.page)+"/"+($this.lastPage==0?1:$this.lastPage));
					$("#itemManagementTotal").html(data.totalRecords);
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