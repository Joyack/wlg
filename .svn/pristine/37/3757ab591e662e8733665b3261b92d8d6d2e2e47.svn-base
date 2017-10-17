$(function() {
	departmentInfo.pageType("preAllPage");

})

//搜索方法
function search() {
	var departname = $("#seachbox").val(); //获取部门名称
	departmentInfo.searchMsg(departname); //按部门名称搜素
}

//打开新增
function openAddDepart() {
    $("#addDepartModal").modal("show");
    var url = HEADER + "dept/getNextDeptid.do"; //获取下一个物品ID
    var html = "";
    $.ajax({
        type: "post",
        url: url,
        async: false,
        success: function(data) {
            html = data.msg;
            // alert(html);
        }
    });
    $("#addDepartNumber").val(html);
}

//打开编辑
function openEditDepart(dom) {
	$("#editDepartment").modal("show");
	var id = $(dom).parents("tr").attr("data-id");
	var editdepartNumber = $(dom).parents("tr").children().eq(0).html();
	var editdepartName = $(dom).parents("tr").children().eq(1).html();
	var editdepartStatus = $(dom).parents("tr").children().eq(2).html();
	//	console.log("部门编号：" + editdepartNumber); //获取没问题
	$("#editdepartNumber").val(editdepartNumber);
	$("#editDepartName").val(editdepartName).attr("data-id", id);
}

//删除
function deleteDepartMsg(dom) {
	var id = $(dom).parents("tr").attr("data-id"); //获取到当前id
	var url = HEADER + "dept/delDept.do";
	cr_dialogBox.confirm(true, "确定删除该部门吗？", function(flag) {
		if(flag) {
			$.ajax({
				type: "post",
				url: url,
				async: true,
				data: {
					"id": id,
				},
				success: function(data) {
					if(data.msg == 1 || data.msg == "1") {
						cr_dialogBox.alert(true, "删除成功!");
						departmentInfo.pageType(departmentInfo.page);
					} else if(data.msg == 0 || data.msg == "0") {
						cr_dialogBox.alert(true, "删除失败!");
					}
				}
			});
		}
	});
}

//提交新增
function submitAddDepart() {
	var departNum = $("#addDepartNumber").val();
	var departName = $("#addDepartName").val();
	var status = $("#addStatus").val();
	cr_dialogBox.confirm(true, "确定新增该数据？", function(flag) {
		if(flag) {
			$.ajax({
				type: "post",
				url: HEADER + "dept/addDept.do",
				async: true,
				data: {
					"deptid": departNum,
					"deptname": departName,
					"status": status
				},
				success: function(data) {
					cr_dialogBox.alert(true, "新增成功！");
					departmentInfo.pageType(departmentInfo.page);
					$("#addDepartModal").modal("hide");
				},
				error: function() {
					cr_dialogBox.alert(true, "新增失败！");
				}
			});
		}
	});
}

//提交编辑
function submitEditDepart() {
	var id = $("#editDepartName").attr("data-id");
	var departNum = $("#editdepartNumber").val();
	var departName = $("#editDepartName").val();
	var status = $("#editStatus").val();
	$.ajax({
		type: "post",
		url: HEADER + "dept/updateDept.do",
		async: true,
		data: {
			"id": id,
			"deptid": departNum,
			"deptname": departName,
			"status": status
		},
		success: function(data) {
			if(data.msg == 1 || data.msg == "1") {
				cr_dialogBox.alert(true, "修改成功!");
				departmentInfo.pageType(departmentInfo.page);
				$("#editDepartment").modal("hide");
			} else if(data.msg == 0 || data.msg == "0") {
				cr_dialogBox.alert(true, "修改失败!");
			}
		}
	});
}

//获取部门信息列表
var departmentInfo = {
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
			url: HEADER + "dept/check_DeptList.do?page=" + pages + "&pageSize=10&deptname=" + $this.param,
			success: function(data) {
				if(!!data) {
					loadingModdle.closeModdle();
					var html = "";
					var jsonArr = data.result;
					var len = jsonArr.length;
					var staName = "";
					for(var i = 0; i < len; i++) {
						switch(jsonArr[i].status) {
							case "1":
								staName = "使用";
								break;
							case "2":
								staName = "禁用";
								break;
							default:
								staName = "";
								break;
						}
						html += "<tr data-id='" + (jsonArr[i].id == null ? "" : jsonArr[i].id) + "'>" +
							"	<td>" + (jsonArr[i].deptid == null ? "" : jsonArr[i].deptid) + "</td>" +
							"	<td>" + (jsonArr[i].deptname == null ? "" : jsonArr[i].deptname) + "</td>" +
							"	<td>" + staName + "</td>" +
							"	<td><span class='operate' onclick='openEditDepart(this);'>编辑</span>/<span class='operate'  onclick='deleteDepartMsg(this);'>删除</span></td>" +
							"</tr>"
					}
					$("#departmentMsg").html(html);
                    $this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
					checkAllBtns.check("departmentBtns", departmentInfo, "departmentInfo"); //检查分页按钮是否可用，不可用就显示为禁用标志
					$("#departmentNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
					$("#departmentTotal").html(data.totalCount);
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
}