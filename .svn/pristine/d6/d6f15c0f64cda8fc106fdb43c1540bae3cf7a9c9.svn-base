var userId = "";
var username = "";
var rolelist=null;
var deptlist=null
var datainfo=null;
var getRoleListUrl=HEADER + "role/check_RoleList.do";
var getDeptListUrl=HEADER + "dept/check_DeptList.do?page=1&pageSize=1000";

$(function() {
	allUserMessageList.pageType('preAllPage');
	//得到所有权限
	userRole.getRoleSelect("roles");

    $.ajax({
        type: "GET",
        async:false,
        url: getRoleListUrl,
        success: function(data) {
           rolelist=data;
        }
    });
    $.ajax({
        type: "GET",
        async:false,
        url: getDeptListUrl,
        success: function(data) {
            deptlist=data.result;
        }
    });
});

//删除新增行
function deleteThisList(dom) {
    $(dom).parents("tr").remove();
    $("#addMeter").attr("data-times", "0");
    $("#addMeter1").attr("data-times", "0");
    $("#addMeter2").attr("data-times", "0");
    $("#addUser").attr("data-times","0");
}

//添加用户（橙色+号）
function addUser(dom) {
	var dom = dom;
	var times = parseInt($(dom).attr("data-times"));
	if(times == 0) {
		var html = "";
		var html1 = "<option value=''>--请选择--</option>";
        var html2 = "<option value=''>--请选择--</option>";

		html += "<tr>" +
			"	<td><input style='height:35px;line-height:35px;font-size:16px;width:100%;' type='text' id='username' name='username' onchange='checkChinese(this);' placeholder='添加'/></td>" +
			"	<td><input style='height:35px;line-height:35px;font-size:16px;width:100%;' type='text' id='name' name='name' placeholder='添加'/></td>" +
			"	<td><input style='height:35px;line-height:35px;font-size:16px;width:100%;' type='text' id='phonenumber' name='phonenumber' placeholder='添加'/></td>" +
            "	<td><select name='dept' class='form-control' id='deptid'>" +
            "	</select></td>" +
            "	<td><input style='height:35px;line-height:35px;font-size:16px;width:100%;' type='text' id='email' name='email' placeholder='添加'/></td>" +
			"	<input class='form-control' type='hidden' id='password' name='password' value='12345678'/>" +
			"	<td><select name='roles' class='form-control' id='roles1'>" +
			"	</select></td>" +
			"	<td style='position:relative;'><input class='btn btn-primary form-control' type='button' value='确定' onclick='saveUser();'/><span style='position:absolute;top:0;right:-20%;height:50px;line-height:50px;font-size:25px;font-weight:700;cursor:pointer;color:#FF9205' onclick='deleteThisList(this);'>X</span></td>" +
			"</tr>";
		$(dom).parents("thead").next().prepend(html);

			for(var i = 0; i < rolelist.length; i++) {
				html1 += "<option value='" + rolelist[i].id + "'>" + rolelist[i].rname + "</option>";
			};
			$("#roles1").html(html1);


            for(var i = 0; i < deptlist.length; i++) {
                html2 += "<option value='" + deptlist[i].id + "'>" + deptlist[i].deptname + "</option>"
            };
            $("#deptid").html(html2);
	} else {
		cr_dialogBox.alert(true, "抱歉！一次只能添加一个用户！");
	}
	$(dom).attr("data-times",1);
};

//搜索内容（搜索框）
function searchMsg(dom) {
	var param = $(dom).prev().val();
	var type = $("#myTab").children("li[class=active]").children().html();
	allUserMessageList.searchMsg(param);
}

function searchMsg1(dom) {
	var param = $(dom).val();
	var type = $("#myTab").children("li[class=active]").children().html();
	allUserMessageList.searchMsg(param);
}

//添加用户检测输入的数据（点击橙色加号后的“确认”按钮）
function saveUser() {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var formDom = $("form");
	//获取用户名
	var name = $.trim($("#name").val());
	var email = $.trim($("#email").val());
	var phonenumber = $.trim($("#phonenumber").val());
	var username = $.trim($("#username").val());
	var password = 12345678;
	var roles = $("#roles1 option:selected").val();
	var deptid=$("#deptid  option:selected").val();
	var jsonUser = {};
	//					cr_dialogBox.alert(true,name+"=="+ email+"=="+ phonenumber+"=="+ username+"=="+ password+"=="+ roles);
	if(username == "") {
		cr_dialogBox.alert(true, "用户名不能为空...");
		$("#username").focus();
		return false;
	};
	if(email == "") {
		cr_dialogBox.alert(true, "邮箱不能为空...");
		$("#email").focus();
		return false;
	} else if(!filter.test(email)) {
		cr_dialogBox.alert(true, "邮箱格式不正确...");
		$("#email").focus();
		return false;
	};
	if(phonenumber == "") {
		cr_dialogBox.alert(true, "手机号码不能为空...");
		$("#phonenumber").focus();
		return false;
	} else if(phonenumber.length != 11) {
		cr_dialogBox.alert(true, "手机号码格式不正确...");
		$("#phonenumber").focus();
		return false;
	};
	if(name == "") {
		cr_dialogBox.alert(true, "姓名不能为空...");
		$("#name").focus();
		return false;
	};
	if(roles == "") {
		cr_dialogBox.alert(true, "用户角色不能为空...");
		$("#roles1").focus();
		return false;
	};
	jsonUser.uname = name;
	jsonUser.username = username;
	jsonUser.email = email;
	jsonUser.phonenumber = phonenumber;
	jsonUser.roleid = roles;
	jsonUser.password = password;
	jsonUser.deptid=deptid;
	$.ajax({
		type: "post",
		url: HEADER + "user/add_User.do",
		data: jsonUser,
		success: function(data) {
			if(data.status == "1") {
				cr_dialogBox.alert(true, "添加成功！");
				allUserMessageList.pageType(allUserMessageList.page);
			} else if(data.status == "2"||data.status =="0") {
				cr_dialogBox.alert(true, "提交失败！");
				return false;
			} else if(data.status == "3") {
				cr_dialogBox.alert(true, "用户名重复！");
				return false;
			}else {

			}
		}
	});
};

//隐藏修改信息
function fadeIn() {
	$("#alterPasswsord").fadeOut(); //隐藏修改信息
};
//滑动显示error的内容然后又隐藏
function error(msg) {
	$("#error p").html(msg).slideDown().delay(1000).slideUp();
}

//打开编辑
function alterMsg(dom) {

	//判断是否在添加用户
	var times = $("#addUser").attr("data-times");
	if(times == "0") {
		//拿到当前被编辑的用户的所有信息
		var tdDom = $(dom).parent().parent().parent().children();
		userId = tdDom[0].children[0].innerHTML; //获取未显示的用户id
		username = tdDom[3].children[0].innerHTML; //获取第一列的用户名
         var name = tdDom[4].children[0].innerHTML; //获取第二列的名字
         var phoneNumber = tdDom[5].children[0].innerHTML; //获取第三列的额电话号码
        var dept=tdDom[6].children[0].innerHTML;

         var role = tdDom[8].children[0].innerHTML.split("(")[0]; //获取第五列的角色的小括号里面的内容，如（自）
         var email = tdDom[7].children[0].innerHTML; //获取第四列的邮箱

        var html="";
        var html1="";
        for(var i = 0; i < rolelist.length; i++) {
            html += "<option value='" + rolelist[i].id + "'>" + rolelist[i].rname + "</option>"
			if(role==rolelist[i].rname){
                $("#rolesName").html(rolelist[i].rname);
			}

        };
        for(var i = 0; i < deptlist.length; i++) {
            html1 += "<option value='" + deptlist[i].id + "'>" + deptlist[i].deptname + "</option>"
            if(dept==deptlist[i].deptname){
                $("#deptsName").html(deptlist[i].deptname);
            }

        };
        $("#roles").html(html);
        $("#depts").html(html1);
        var rolesName=$("#rolesName").text();
        var deptsName=$("#deptsName").text();
        $("#roles option").each(function(){
            if($(this).text() == rolesName){
                $(this).attr("selected",true);
            }
        });
        $("#depts option").each(function(){
            if($(this).text() == deptsName){
                $(this).attr("selected",true);
            }
        });


		$("#email").val(email); //将邮箱显示到点开的编辑框里的邮箱栏
		$("#phoneNumber").val(phoneNumber); //将电话号码显示到编辑框里的电话栏
		$("#name").val(name); //将名字显示到编辑框里的姓名栏
		$("#userId").val(userId); //将用户Id传给userId
		//$("#rolesName").html(role); //将角色
		$("#alterPasswsord").fadeIn(); //显示修改信息
		checkType($("#roles"), role);
	} else if(times == "1") {
		$("#rolesName").html("");
		cr_dialogBox.alert(true, "你正在添加用户，不能编辑！");
	}
};

//打开权限分配
function permissionsAssignment(dom) {
    var tdDom = $(dom).parent().parent().parent().children();
	var type = $(dom).parent().parent().prev().text().split("(");
	var userid = $(dom).parents("tr").children().eq(0).text();
	var username = $(dom).parents("tr").children().eq(1).text();
    //获取未显示的用户id
	var roleid = tdDom[1].children[0].innerHTML;

	//获取所有权限
	getPermission(userid);
	$("#permissionsAssignment").attr({
		"roleid": roleid,
		"userid": userid,
		"username": username
	}).removeClass("hidden");
};

//关闭权限分配页面
function closeModdle11() {
	$("#permissionsAssignment").addClass("hidden");
	$("#permissionsAssignment").removeAttr("roleid");
	$("#permissionsAssignment").removeAttr("userid");
};

//删除用户
function deleteUser(dom) {
	cr_dialogBox.confirm(true, "确认要删除么？", function(flag) {
		if(flag) {
			var userId1 = $(dom).parents("tr").children().eq(0).text();
			$.ajax({
				type: "POST",
				url: HEADER + "user/delete_User.do?id=" + userId1,
				success: function(data) {
					if(data.status == "1") {
						//删除成功之后返回到首页；
						allUserMessageList.pageType(allUserMessageList.page);
					} else if(data.status == "error") {
						cr_dialogBox.alert(true, "删除用户失败！");
					} else if(data.status == "5") {
						cr_dialogBox.alert(true, "无法删除当前用户！");
					};
				}
			});
		}
	});
};

//编辑用户提交数据
function submitMsg() {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var email = $.trim($("#email").val()); //邮箱（去除空格）
	var phoneNumber = $.trim($("#phoneNumber").val()); //电话（去除空格）
	var name = $.trim($("#name").val()); //名字（去除空格）
	var roles = $("#roles").val(); //角色（去除空格）
	var depts=$("#depts").val();
	var locationids = $("#locationids").val(); //区域（去除空格）
	var communityId = $("#community").val(); //小区（去除空格）
	var mainMenu = $("#mainMenu").val(); //主菜单（去除空格）
	var subMenu = $("#subMenu").val(); //子菜单（去除空格）
	var resourceIds = [];
	var locationids1 = [];
	if(roles == 0) {
		locationids1 = "";
		resourceIds.push(mainMenu);
		resourceIds.push(subMenu);
		resourceIds = resourceIds.toString();
	} else if(roles == 4) {
		resourceIds = "";
		if(locationids) {
			for(var i = 0; i < locationids.length; i++) {
				locationids1.push(locationids[i]);
			};
			locationids1 = locationids1.toString();
		}
	} else if(roles == 5) {
		resourceIds = "";
		if(communityId) {
			for(var i = 0; i < communityId.length; i++) {
				locationids1.push(communityId[i]);
			};
			locationids1 = locationids1.toString();
		}
	};
	if(name == null || name == "") {
		error("姓名不能为空！");
		$("#name").focus();
		return false;
	};
    if(phoneNumber == "") {
        cr_dialogBox.alert(true, "手机号码不能为空...");
        $("#phoneNumber").focus();
        return false;
    } else if(phoneNumber.length != 11) {
        cr_dialogBox.alert(true, "手机号码格式不正确...");
        $("#phoneNumber").focus();
        return false;
    };
	if(email == null || email == "") {
		error("邮箱不能为空！");
		$("#email").focus();
		return false;
	} else if(!filter.test(email)) {
		error("邮箱格式不正确...");
		$("#email").focus();
		return false;
	};
	if(roles == null || roles == "") {
		error("角色不能为空！");
		$("#roles").focus();
		return false;
	};
    if(depts == null || depts == "") {
        error("部门不能为空！");
        $("#depts").focus();
        return false;
    };
	//提交数据
	$.ajax({
		type: "POST",
		url: HEADER + "user/update_User.do",
		data: {
			"id": userId,
			"username": username,
			"phonenumber": phoneNumber,
			"email": email,
			"uname": name,
			"deptid":depts,
			"roleid":roles,
			"userrole": roles,
			"locationids": locationids1,
			"resourceIds": resourceIds
		},
		success: function(data) {
			if(data.status == '1') {
				cr_dialogBox.alert(true, "提交成功");
				allUserMessageList.pageType(allUserMessageList.page);
				if(roles == 4) {
					$.ajax({
						type: "get",
						url: HEADER + "updateUserLocationToJs.do?username=" + username,
						success: function(data) {

						}
					});
				};
			} else {
				cr_dialogBox.alert(true, "提交失败");
			}
			fadeIn();
		}
	});
};

//提交个人权限分配信息
function submitRoleMsg(dom) {
	var userid = $("#permissionsAssignment").attr("userid");
	var roleid = $("#permissionsAssignment").attr("roleid");
	var username = $("#permissionsAssignment").attr("username");
	var inputDoms = $("#permission").find("input[data-flag]");
	var resourceIds = [];
	for(var i = 0; i < inputDoms.length; i++) {
		var flag = $(inputDoms[i]).is(":checked");
		if(flag == true) {
			resourceIds.push($(inputDoms[i]).data("id"));
		};
	};
	if(userid) {
		var url = HEADER + "resources/updateRoleorUserRes.do?ruflag=user&userId=" + userid + "&resourceIds=" + resourceIds.toString();
		$.ajax({
			type: "post",
			url: url,
			async: true,
			success: function(data) {
				if(data.msg == "success") {
					cr_dialogBox.alert(true, "提交成功");
					allUserMessageList.pageType(allUserMessageList.page);
				};
				$("#permissionsAssignment").addClass("hidden");
			}
		});
	}else{
		alert("用户不能为空");
	}
};

//取消个人自定义权限
function canclePersonRole(dom) {
	cr_dialogBox.confirm(true, "是否取消自定义权限？", function(flag) {
		if(flag) {
			//			cr_dialogBox.alert(true,"取消了！");
			var id = $("#permissionsAssignment").attr("userid");
			var userrole = $("#permissionsAssignment").attr("roleid");
			var username = $("#permissionsAssignment").attr("username");
			var url = HEADER + "cancelUserRes.do?id=" + id + "&userrole=" + userrole + "&username=" + username;
			$.post(url, function(data) {
				var status = data.msg;
				if(status == "1") {
					var inputDom = $("input[data-flag]", "#permission");
					for(var i = 0; i < inputDom.length; i++) {
						var flag = $(inputDom[i]).attr("data-flag");
						if(flag == "true") {
							$(inputDom[i]).prop("checked", true);
						};
					};
					$("#permissionsAssignment").addClass("hidden");
					allUserMessageList.pageType(allUserMessageList.page);
				} else if(status == "0") {
					cr_dialogBox.alert(true, "操作失败！");
				};
			});
		};
	});
};

//检测中文
function checkChinese(dom) {
	var value = dom.value;
	dom.value = value.replace(/[\W]/g, '');
};

//根据用户的id查找所管理的事业部(事业部管理员方法)
function findBusiness() {
	var url = HEADER + "getUserLocations.do?userid=" + userId;
	$.ajax({
		type: "get",
		url: url,
		async: false,
		success: function(data) {
			if(data.length > 0) {
				var areaId = [];
				for(var i = 0; i < data.length; i++) {
					areaId.push(data[i].locationid);
				};
				$('#locationids').val(areaId);
				$('#locationids').selectpicker('render');
				$('#community').val(areaId);
				$('#community').selectpicker('render');
			} else {
				$('#locationids').val("");
				$('#locationids').selectpicker('render');
			}
		}
	});
};


//显示用户所有权限
function showAllRoles() {
	var url = HEADER + "getAllFirstLevelResource.do";
	$.get(url, function(data) {
		var html = "";
		for(var i = 0; i < data.length; i++) {
			html += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
		};
		$("#mainMenu").html(html);
		$('#mainMenu').selectpicker('refresh');
		$('#mainMenu').selectpicker('show');
	});
};

//根据主菜单获取子菜单
function getSubMenu() {
	var mainMenu = $("#mainMenu").val();
	if($("#mainMenu").val()) {
		var url = HEADER + "getAllResourceByParentIds.do?parentids=" + mainMenu.toString();
		$.get(url, function(data) {
			var html = "";
			for(var i = 0; i < data.length; i++) {
				html += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
			};
			$("#subMenu").html(html);
			$('#subMenu').selectpicker('refresh');
			$('#subMenu').selectpicker('show');
		});
		getOldSubMenu();
	} else {
		$("#subMenu").html("");
		$('#subMenu').selectpicker('refresh');
		$('#subMenu').selectpicker('show');
	}
};

//获取当前用户之前的主菜单记录
function getOldMainMenu() {
	var userid = $("#permissionsAssignment").attr("userid");
	var username = $("#permissionsAssignment").attr("username");
	var url = HEADER + "getAllResourceParentByUserid.do?userid=" + userId + "&username=" + username;
	var value = [];
	$.get(url, function(data) {
		for(var i = 0; i < data.length; i++) {
			value.push(data[i].id);
		};
		$('#mainMenu').val(value);
		$('#mainMenu').selectpicker('render');
		getSubMenu();
	});
};

//获取当前用户子菜单记录
function getOldSubMenu() {
    var userid = $("#permissionsAssignment").attr("userid");
    var username = $("#permissionsAssignment").attr("username");
    var url = HEADER + "getAllResourceChildByUserid.do?userid=" + userId + "&username=" + username;
    var value = [];
    $.get(url, function (data) {
        for (var i = 0; i < data.length; i++) {
            value.push(data[i].id);
        }
        ;
        //		$('#subMenu').val(value);
        $('#subMenu').selectpicker('val', value);
        $('#subMenu').selectpicker('refresh');
        $('#subMenu').selectpicker('render');
    });
}
//列表翻页
var allUserMessageList = {
	//回到第一页
	page: 1,
	nextPage: 1,
	prePage: 1,
	lastPage: 1,
	param: "",
	pageType: function(type) {
		loadingModdle.showModdle(); //显示权限分配
		var $this = this;
		var pages = 0;
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
			url: HEADER + "user/check_UserList.do?page=" + pages + "&param=" + this.param,
			success: function(data) {

				if(!!data) {
					loadingModdle.closeModdle(); //关闭权限分配
					var jsonArr = data.result;
					var len = jsonArr.length;
					var username = "";
					var userrole = "";
					var html = "";
					$("#addUser").attr("data-times", 0);
					$.ajax({
						type: "get",
						url: HEADER + "getDetailsUserName.do",
						async: false,
						success: function(data) {
							username = data.username;
						}
					});
					for(var i = 0; i < len; i++) {
						userrole = "";
						if(jsonArr[i].username && username) {
							jsonArr[i].username = jsonArr[i].username.toLowerCase();
							username = username.toLowerCase();
						};

						if(jsonArr[i].username == username) {
							html += "<tr>" +
								"		<td style='border-color:#000;display:none;'><label style='font-weight:500;'>" + jsonArr[i].id + "</label></td>" +
                                "		<td style='border-color:#000;display:none;'><label style='font-weight:500;'>" + jsonArr[i].roleid + "</label></td>" +
                                "		<td style='border-color:#000;display:none;'><label style='font-weight:500;'>" + jsonArr[i].deptid + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].username + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].uname + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].phonenumber + "</label></td>" +
                                "		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].deptname+ "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].email + "</label></td>" +
								" 		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].rname + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'><span style='color:#a29696;'>编辑</span>/<span style='color:#a29696;'>权限分配</span>/<span style='color:#a29696;'>删除</span></label></td>" +
								"</tr>,";
						} else {
							html += "<tr>" +
                                "		<td style='border-color:#000;display:none;'><label style='font-weight:500;'>" + jsonArr[i].id + "</label></td>" +
                                "		<td style='border-color:#000;display:none;'><label style='font-weight:500;'>" + jsonArr[i].roleid + "</label></td>" +
                                "		<td style='border-color:#000;display:none;'><label style='font-weight:500;'>" + jsonArr[i].deptid + "</label></td>" +
                                "		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].username + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].uname + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].phonenumber + "</label></td>" +
                                "		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].deptname+ "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].email + "</label></td>" +
								" 		<td style='border-color:#000;'><label style='font-weight:500;'>" + jsonArr[i].rname + "</label></td>" +
								"		<td style='border-color:#000;'><label style='font-weight:500;'><a href='javascript:void(0);' onclick='alterMsg(this);'>编辑</a>/<a href='javascript:void(0);' onclick='permissionsAssignment(this)';>权限分配</a>/<a href='javascript:void(0);' onclick='deleteUser(this)';>删除</a></label></td>" +
								"</tr>,";
						};
					};
					$("#userMsg").html(html); //将上面的html写到表格里面去
					$this.changePage(data.pageNo, data.prePage, data.nextPage, data.totalPageBeans); //改变变量的值
					checkAllBtns.check("allBtns", allUserMessageList, "allUserMessageList");
					$("#pageNumber").html(($this.page == 0 ? 1 : $this.page) + "/" + ($this.lastPage == 0 ? 1 : $this.lastPage));
					$("#allUserMsgTotal").html(data.totalCount);
				};
			},
			error: function() {
				loadingModdle.closeModdle();
			}
		});
	},
	//改变变量的值
	changePage: function(page, prePage, nextPage, lastPage) {
		if(prePage) { //如果有第一个数
			this.prePage = page - 1; //上一页等于当前页减1
		} else { //否则
			this.prePage = page; //当上一页等于当前页
		};
		if(nextPage) { //如果有下一页
			this.nextPage = page + 1; //下一页等于当前页加1
		} else {
			this.nextPage = page; //否则下一页等于当前页
		};
		this.page = page; //当前页等于当前页
		this.lastPage = lastPage; //最后一页等于最后一页
	},
	//搜索功能
	searchMsg: function(param) {
		var $this = this;
		$this.param = param;
		$this.pageType('preAllPage');
	}
};

//获取权限列表
function getPermission(userid) {
	var url = HEADER + "resources/queryAllResources.do?ruflag=user&ruid=" + userid;
	loadingModdle.showModdle();
	$.get(url, function(data) {
		var len = data.result.length;
		var jsonarr=data.result;
		var html = "";
		var list = [];
		for(var i = 0; i < len; i++) {
			if(jsonarr[i].level=='2'){
                if(jsonarr[i].hasAuth=='1') {
                    html += "<li class='list'>" +
                        "	<p class='fileset'><label><input style='padding-right: 10px;' type='checkbox' checked='checked' data-id='" + jsonarr[i].id + "' data-flag='" + jsonarr[i].hasAuth + "' data-parent=1 onclick='getAllLowerList(this);'/>" + jsonarr[i].resourcename + "</label></p>" +
                        "	<ul id='" + jsonarr[i].id + "'></ul>" +
                        "</li>";
                }else{
                    html += "<li class='list'>" +
                        "	<p class='fileset'><label><input style='padding-right: 10px;' type='checkbox'  data-id='" + jsonarr[i].id + "' data-flag='" + jsonarr[i].hasAuth + "' data-parent=0 onclick='getAllLowerList(this);'/>" + jsonarr[i].resourcename + "</label></p>" +
                        "	<ul id='" + jsonarr[i].id + "'></ul>" +
                        "</li>";
				}
            }
            if(jsonarr[i].level=='3'){
			  list.push(jsonarr[i]);
            }
		};
		$("#permission").html(html);
		html = "";
		for(var i = 0; i < list.length; i++) {
			var allChildren =list[i];
			if(allChildren.hasAuth=='1'){
                html += "<li class='lowerList'>" +
                    "	<label><input style='padding-right: 10px;' name='permission' type='checkbox' checked='checked' data-id='" + allChildren.id + "' data-flag='" + allChildren.hasAuth + "' data-children=1 onclick='conHeightList(this);'/>" + allChildren.resourcename + "</label>" +
                    "</li>";
			}else{
                html += "<li class='lowerList'>" +
                    "	<label><input style='padding-right: 10px;' name='permission' type='checkbox'  data-id='" + allChildren.id + "' data-flag='" + allChildren.hasAuth + "' data-children=0 onclick='conHeightList(this);'/>" + allChildren.resourcename + "</label>" +
                    "</li>";
			}

			//};
            $("#" + list[i].parentid).append(html);
            html = "";
		};

		checkboxInit();
		loadingModdle.closeModdle();
	});
};

//点击主菜单全选子级菜单
function getAllLowerList(dom) {
	var flag = $(dom).is(":checked");
	var liDom = $(dom).parents("li").find("ul").children();
	if(flag) {
		for(var i = 0; i < liDom.length; i++) {
			$(liDom[i]).find("input[name='permission']").prop("checked", true);
		}
	} else {
		for(var i = 0; i < liDom.length; i++) {
			$(liDom[i]).find("input[name='permission']").prop("checked", false);
		}
	}
};

//子级菜单选中，主菜单也要选中或不选中
function conHeightList(dom) {
	var liDoms = $(dom).parents("ul").children("li[class='lowerList']");
	var parentFlag = $(dom).parents("ul").prev().find("input[data-flag]");
	var flag = $(dom).is(":checked");
	var flag1 = false;
	for(var i = 0; i < liDoms.length; i++) {
		flag1 = flag1 || $(liDoms[i]).find("input[data-flag]").prop("checked");
	};
	if(flag) {
		if(parentFlag.prop("checked") == false) {
			parentFlag.prop("checked", true);
		}
	} else if(flag1 == false) {
		parentFlag.prop("checked", false);
	};
}

//初始化checkbox是否选中
function checkboxInit() {
	var userid = $("#permissionsAssignment").attr("userid");
	var username = $("#permissionsAssignment").attr("username");
	var inputDom = $("input[data-flag]", "#permission");
	var parentUrl = HEADER + "getAllResourceParentByUserid.do?userid=" + userid + "&username=" + username;
	var childUrl = HEADER + "getAllResourceChildByUserid.do?userid=" + userid + "&username=" + username;
	var statusUrl = HEADER + "checkRoleWhetherCus.do?username=" + username;
	$.get(statusUrl, function(data) {
		console.log(JSON.stringify(data));
		if(data.status == "0" || data.status == 0) {
			for(var i = 0; i < inputDom.length; i++) {
				var flag = $(inputDom[i]).attr("data-flag");
				if(flag == "true") {
					$(inputDom[i]).prop("checked", true);
				};
			};
			//若权限不是自定义则禁止“取消自定义权限”按钮
			//			alert("不是自定义");
			$("#cancleUserRole", $("#box")).addClass("disabled");
			$("#cancleUserRole", $("#box")).removeAttr("onclick");
		} else if(data.status == "1" || data.status == 1) {
			$.get(parentUrl, function(data) {
				//判断该用户是否有自定义权限设置 有则显示自定义权限，没有则显示统一的权限
				if(data.length > 0) {
					var parentDom = $("input[data-parent=1]", $("#permission"));
					for(var i = 0; i < data.length; i++) {
						for(var j = 0; j < parentDom.length; j++) {
							if(data[i].id == $(parentDom[j]).attr("data-id")) {
								$(parentDom[j]).prop("checked", true);
							};
						};
					};
					//获取子菜单选中值
					$.get(childUrl, function(data1) {
						var childrenDom = $("input[data-children=1]", $("#permission"));
						for(var i = 0; i < data1.length; i++) {
							for(var j = 0; j < childrenDom.length; j++) {
								if(data1[i].id == $(childrenDom[j]).attr("data-id")) {
									$(childrenDom[j]).prop("checked", true);
								};
							};
						};
					});
					//			alert("自定义");
					//若权限是自定义则开启“取消自定义权限”按钮
					$("#cancleUserRole").removeClass("disabled");
					$("#cancleUserRole").attr("onclick", "canclePersonRole(this);");
				};
			});
		};
	});
	/*//获取主菜单选中值
	$.get(parentUrl,function(data){
		//判断该用户是否有自定义权限设置 有则显示自定义权限，没有则显示统一的权限
		if(data.length <= 0){
			for(var i = 0; i < inputDom.length; i++) {
				var flag = $(inputDom[i]).attr("data-flag");
				if(flag == "true") {
//					$(inputDom[i]).prop("checked", true);
				};
			};
			//若权限不是自定义则禁止“取消自定义权限”按钮
//			alert("不是自定义");
			$("#cancleUserRole",$("#box")).addClass("disabled");
			$("#cancleUserRole",$("#box")).removeAttr("onclick");
		}else if(data.length > 0){
			var parentDom = $("input[data-parent=1]",$("#permission"));
			for(var i=0;i<data.length;i++){
				for(var j=0;j<parentDom.length;j++){
					if(data[i].id == $(parentDom[j]).attr("data-id")){
						$(parentDom[j]).prop("checked", true);
					};
				};
			};
			//获取子菜单选中值
			$.get(childUrl,function(data1){
				var childrenDom = $("input[data-children=1]",$("#permission"));
				for(var i=0;i<data1.length;i++){
					for(var j=0;j<childrenDom.length;j++){
						if(data1[i].id == $(childrenDom[j]).attr("data-id")){
							$(childrenDom[j]).prop("checked", true);
						};
					};
				};
			});
//			alert("自定义");
			//若权限是自定义则开启“取消自定义权限”按钮
			$("#cancleUserRole").removeClass("disabled");
			$("#cancleUserRole").attr("onclick","canclePersonRole(this);");
		};
	});*/
};