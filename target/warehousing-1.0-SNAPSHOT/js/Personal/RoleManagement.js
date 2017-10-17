$(function() {
	roleManageInfo.pageType("preAllPage");
})

//搜索方法
function search() {
	var value = $("#seachbox").val(); //获取角色名称
	roleManageInfo.searchMsg(value); //按角色名称搜素
}

//打开新增
function openAddRole() {
	$("#addRoleModal").modal("show");
}

//打开编辑
function openEditRole(dom) {
	$("#editRoleModal").modal("show");
	var id = $(dom).parents("tr").attr("data-id");
	var editroleNumber = $(dom).parents("tr").children().eq(0).html();//编号
	var editUserRole = $(dom).parents("tr").children().eq(1).html();//用户角色
	var editroleName = $(dom).parents("tr").children().eq(2).html();//名称
	$("#editRoleNumber").val(editroleNumber);
	$("#editUserRole").val(editUserRole);
	$("#editRoleName").val(editroleName).attr("data-id", id);
}

//提交新增
function submitAddRole() {
	// var times = parseInt($("#submit1").attr("data-times"));
	var roleNum = $("#addRoleNumber").val(); //编号
	var name = $("#addUserRole").val(); //用户角色
	var roleName = $("#addRoleName").val(); //名称
    // if(times == 0){
        cr_dialogBox.confirm(true, "确定新增该数据？", function(flag) {
            if(flag) {
                $.ajax({
                    type: "post",
                    url: HEADER + "role/add_Role.do",
                    async: true,
                    data: {
                        "rid": roleNum,
                        "name": name,
                        "rname": roleName
                    },
                    success: function(data) {
                        if(data.status == 1 || data.status == "1") {
                            cr_dialogBox.alert(true, "新增成功!");
                            roleManageInfo.pageType(roleManageInfo.page);
                            $("#addRoleModal").modal("hide");
                        } else if(data.status == 0 || data.status == "0") {
                            cr_dialogBox.alert(true, "新增失败!");
                        }
                    }
                });
            }
        });
    // }else{
    //     cr_dialogBox.alert(true,"");
    // }
    // $("#submit1").attr("data-times",1);
}

//提交编辑
function submitEditRole(dom) {
    // var times = $("#submit2").attr("data-times");
	var id = $("#editRoleName").attr("data-id");
	var roleNum = $("#editRoleNumber").val(); //编号
	var name = $("#editUserRole").val(); //用户角色
	var roleName = $("#editRoleName").val(); //名称
	// if(times==0){
        cr_dialogBox.confirm(true, "确定修改？", function(flag) {
            if(flag) {
                $.ajax({
                    type: "post",
                    url: HEADER + "role/update_Role.do",
                    async: true,
                    data: {
                        "id": id,
                        "rid": roleNum,
                        "name": name,
                        "rname": roleName
                    },
                    success: function(data) {
                        if(data.status == 1 || data.status == "1") {
                            cr_dialogBox.alert(true, "修改成功!");
                            roleManageInfo.pageType(roleManageInfo.page);
                            $("#editRoleModal").modal("hide");
                        } else if(data.status == 0 || data.status == "0") {
                            cr_dialogBox.alert(true, "修改失败!");
                            $("#editRoleModal").modal("hide");
                        }
                    }
                });
            }
        });
	// }else{
     //    cr_dialogBox.alert(true,"");
	// }
    // $("#submit2").attr("data-times",1);
}

//删除角色信息
function deleteRoleMsg(dom) {
	var id = $(dom).parents("tr").attr("data-id");
	var url = HEADER + "role/delete_Role.do";
	cr_dialogBox.confirm(true,"确定删除？",function(flag) {
		if(flag) {
			$.ajax({
				type: "post",
				url: url,
				async: true,
				data: {
					"id": id
				},
				success: function(data) {
					if(data.status == 1 || data.status == "1") {
						cr_dialogBox.alert(true, "删除成功！");
						roleManageInfo.pageType(roleManageInfo.page);
					} else if(data.status == 0 || data.status == "0") {
						cr_dialogBox.alert(true, "删除失败！");
					}
				}
			});
		}
	});
}

//获取角色信息列表
var roleManageInfo = {
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param: "",
	pageType: function(type) {
		loadingModdle.showModdle();
		var pages = 0;
		var $this = this;
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
			url: HEADER + "role/check_RoleForPage.do?page=" + pages + "&pageSize=10&findkey=" + $this.param,
			success: function(data) {
				if(!!data) {
					loadingModdle.closeModdle();
					var html = "";
					var jsonArr = data.result;
					var len = jsonArr.length;
					for(var i = 0; i < len; i++) {
						html += "<tr data-id='" + (jsonArr[i].id == null ? "" : jsonArr[i].id) + "'>" +
							"	<td>" + (jsonArr[i].rid == null ? "" : jsonArr[i].rid) + "</td>" +
							"	<td>" + (jsonArr[i].name == null ? "" : jsonArr[i].name) + "</td>" +
							"	<td>" + (jsonArr[i].rname == null ? "" : jsonArr[i].rname) + "</td>" +
							"	<td><span class='operate' onclick='openEditRole(this);'>编辑</span>/<span onclick='deleteRoleMsg(this);'>删除</span></td>" +
							"</tr>"
					}
					$("#RoleManageMsg").html(html);
					$this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
					checkAllBtns.check("roleManageBtns", roleManageInfo, "roleManageInfo"); //检查分页按钮是否可用，不可用就显示为禁用标志
					$("#roleManageNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
					$("#roleManageTotal").html(data.totalCount);
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