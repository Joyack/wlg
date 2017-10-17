$(function() {
	provManagement.pageType("preAllPage"); //获取物品列表信息
})

//提交新增数据
function submitAddProv() {
	var url = HEADER + "supplier/addSupplier.do";
	var addCompany = $("#addCompany").val(); //供应商名称
	var addIndustry = $("#addIndustry").val(); //所属行业
	var addContact = $("#addContact").val(); //联系人
	var addPhone = $("#addPhone").val(); //联系电话
	var addProduct = $("#addProduct").val(); //经营产品
	var addScore = $("#addScore").val(); //评分
	//	alert(addCompany+"+"+addIndustry+"+"+addContact+"+"+addPhone+"+"+addProduct+"+"+addScore);
	$.ajax({
		type: "post",
		url: url,
		data: {
			"proname": addCompany, //供应商名称
			"industry": addIndustry, //所属行业
			"contractsperosn": addContact, //联系人
			"tele": addPhone, //联系电话
			"prodname": addProduct, //经营产品
			"grade": addScore //评分
		},
		success: function(data) {
			if(data.msg == 1 || data.msg == "1") {
				cr_dialogBox.alert(true, "增加数据成功!");
				provManagement.pageType(provManagement.page);
				$("#addMsg").modal("hide");
			} else if(data.msg == 0 || data.msg == "0") {
				cr_dialogBox.alert(true, "增加数据失败!");
			}
		}
	});
}

//提交编辑供应商信息
function submitEditProv() {
	var url = HEADER + "supplier/updateSupplier.do";
	var editCompany = $("#editCompany").val(); //获取公司名称
	var editIndustry = $("#editIndustry").val(); //获取所属行业
	var editContact = $("#editContact").val(); //获取联系人
	var editPhone = $("#editPhone").val(); //获取联系电话
	var editProduct = $("#editProduct").val(); //获取经营产品
	var editScore = $("#editScore").val(); //获取评分
	var id = $("#editId").val(); //获取列id
    var editCompany1 =$("#editCompany").attr("data-oldproname"); //获取到修改前公司名称

    //	alert(editType+"="+editId+"="+editName+"="+editNumber+"="+editUnit+"="+id);
	$.ajax({
		type: "post",
		url: url,
		data: {
			"id": id, //列id
			"proname": editCompany, //公司名称
			"industry": editIndustry, //所属行业
			"contractsperosn": editContact, //联系人
			"tele": editPhone, //联系电话
			"prodname": editProduct, //经营产品
			"grade": editScore, //评分
			"oldproname":editCompany1
		},
		success: function(data) {
			if(data.msg == 1 || data.msg == "1") {
				cr_dialogBox.alert(true, "修改成功!");
				provManagement.pageType(provManagement.page);
				$("#editMsg").modal("hide");
			} else if(data.msg == 0 || data.msg == "0") {
				cr_dialogBox.alert(true, "修改失败!");
			}
		}
	});
}

//打开新增供应商类别(橙色加号)
function addProv() {
	$("#addId").html();
	$("#addCompany").val("");
	$("#addIndustry").val("");
	$("#addContact").val("");
	$("#addPhone").val("");
	$("#addProduct").val("");
	$("#addScore").val("4分");
	$("#addMsg").modal("show"); //显示模态框
}
//打开编辑供应商窗口
function showEdit(dom) {
	$("#editMsg").modal("show"); //打开编辑模态框(要将之前列表里面的信息显示在模态框中)
	var editCompany = $(dom).parents("tr").children().eq(0).html(); //获取到公司名称的代码
	var editIndustry = $(dom).parents("tr").children().eq(1).html(); //获取到所属行业的代码
	var editContact = $(dom).parents("tr").children().eq(2).html(); //判断并获取联系人的代码
	var editPhone = $(dom).parents("tr").children().eq(3).html(); //获取联系电话的代码
	var editProduct = $(dom).parents("tr").children().eq(4).html(); //获取经营产品的代码
	var editScore = $(dom).parents("tr").children().eq(5).html(); //获取评分的代码

//	var editId =$(dom).parents("tr").children().eq(6).html();//获取id
	var id = $(dom).parents("tr").attr("data-id"); //获取到当前所在的父元素的data-id
	$("#editId").val(id);
	$("#editCompany").val(editCompany);
	$("#editIndustry").val(editIndustry);
	$("#editContact").val(editContact);
	$("#editPhone").val(editPhone);
	$("#editProduct").val(editProduct);
	$("#editScore").val(editScore);
    $("#editCompany").attr("data-oldproname",editCompany);
}


//搜索
function search(dom){
	var searchValue = $("#ProvSearch").val();
//	alert(searchValue);
	provManagement.searchMsg(searchValue);
}

//删除列表列
function deletMsg(dom) {
	var url = HEADER + "supplier/delSupplier.do";
	var id = $(dom).parents("tr").attr("data-id"); //获取到当前所在的父元素的data-id
	cr_dialogBox.confirm(true, "确认要删除吗?", function(flag) {
		if(flag) {
			$.ajax({
				type: "post",
				url: url,
				data: {
					"id": id //将获取到的data-id传过去
				},
				success: function(data) {
					if(data.msg == 1 || data.msg == "1") {
						cr_dialogBox.alert(true, "删除信息成功!");
						provManagement.pageType(provManagement.page); //删除之后刷新物品管理列表
					} else if(data.msg == 0 || data.msg == "0") {
						cr_dialogBox.alert(true, "删除信息失败!");
					}
				}
			});
		}
	});
}

//获取供应商管理列表
var provManagement = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param: "",
	pageType: function(type) {
		loadingModdle.showModdle();
		var pages = 0;
		var $this = this;
        if (type == "preAllPage") {
            pages = 1;
        } else if (type == "prePage") {
            pages = this.prePage;
        } else if (type == "nextPage") {
            pages = this.nextPage;
        } else if (type == "nextAllPage") {
            pages = this.lastPage;
        } else if (typeof(type) == "number") {
            pages = type;
        };
		$.ajax({
			type: "get",
			url: HEADER + "supplier/querySupplierList.do?page=" +pages + "&pageSize=10"+"&findkey="+$this.param,
			success: function(data) {
				if(!!data) {
					loadingModdle.closeModdle();
					var jsonArr = data.result;
					var len = jsonArr.length;
					var html = "";
					for(var i = 0; i < len; i++) {
						html += "<tr data-id='" + jsonArr[i].id + "'>" +
							"	<td>" + (jsonArr[i].proname == null ? "" : jsonArr[i].proname) + "</td>" +
							"	<td>" + (jsonArr[i].industry == null ? "" : jsonArr[i].industry) + "</td>" +
							"	<td>" + (jsonArr[i].contractsperosn == null ? "" : jsonArr[i].contractsperosn) + "</td>" +
							"	<td>" + (jsonArr[i].tele == null ? "" : jsonArr[i].tele) + "</td>" +
							"	<td>" + (jsonArr[i].prodname == null ? "" : jsonArr[i].prodname) + "</td>" +
							"	<td>" + (jsonArr[i].grade == null ? "" : jsonArr[i].grade) + "</td>" +
							"	<td><span onclick='showEdit(this);'>编辑</span>/<span onclick='deletMsg(this);'>删除</span></td>" +
							"</tr>";
					}
					$("#provManagementMsg").html(html);
					$this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
					checkAllBtns.check("provManagementBtns", provManagement, "provManagement");
					$("#provManagementNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
					$("#provManagementTotal").html(data.totalCount);
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
    changePage: function (page, prePage, nextPage, lastPage) {
        this.page = page;
        this.nextPage = nextPage;
        this.prePage = prePage;
        this.lastPage = lastPage;
    },
    //搜索功能
    searchMsg: function (param) {
        var $this = this;
        $this.param = param;
        $this.pageType($this.page);
    }
};