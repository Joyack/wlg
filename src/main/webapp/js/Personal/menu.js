function gohome() {
	window.location.href = "home.jsp";
}

window.onresize = function() {
	getWH();
};
//自动调整屏幕宽高
$(function() {
	getWH();
	$.ajax({
		type: "get",
		url: HEADER + "getUserRole.do",
		success: function(data) {
			getList(data[0].roleId);//得到所有的导航栏列表
		}
	});
	//	checkWorkHasMsg();//检查工作台是否有信息
});

//计算出屏幕的宽度和高度
function getWH() {
	var wHeight = $(window).height() - 105;
	var wWidth = $(window).width();
	if(wWidth >= 1000) {
		$("#c_iframe").width(wWidth);
	} else {
		$("#c_iframe").width(1000);
	}
	$("#c_iframe").height(wHeight);
	$("header").width(wWidth);
};

//点击给iframe页面打开
function openWindowUrl(dom) {
	var url = $(dom).attr("data-url");
	$("#c_iframe").attr("src", url);
}

////检查工作台是否有信息(有信息显示红点)
function checkWorkHasMsg() {
	var url = HEADER + "workPlanMsg/checkWorkPalnMsg.do";
	$.ajax({
		type: "get",
		url: url,
		success: function(data) {
			if(!!data.sums && data.sums > 0) {
				$("#showMsgRed").attr("class", "showMsgRed");
			} else {
				$("#showMsgRed").removeAttr("class");
			}
		},
		error: function() {
			$("#showMsgRed").removeAttr("class");
		}
	});
}

//根据不同的url显示不同内容到iframe里面
function showIframe() {
	var url = window.location.href;
	var name = url.split("#")[1];
	var href = "";
	if(name) {
		//若name为审核则直接执行
		if(name == "审核" || name == encodeURIComponent("审核")) { //encodeURIComponent（）可把字符串作为URI组件进行编码
			href = "Auditing.html";
		} else {
			var allDrow = document.getElementById("allDrowmenu1").children; //获取到导航栏中间的下拉菜单列表的每个选项
			for(var i = 0; i < allDrow.length; i++) {
				if(allDrow[i].children[2].children.length >= 1) {
					var liDoms = allDrow[i].children[2].children;
					for(var j = 0; j < liDoms.length; j++) {
						//火狐用法
						if(encodeURIComponent(liDoms[j].children[0].getAttribute("data-name")) == name) {
							href = liDoms[j].children[0].getAttribute("data-url");
							//google等跳转用法
						} else if(liDoms[j].children[0].getAttribute("data-name") == name) {
							href = liDoms[j].children[0].getAttribute("data-url");
						};
					};
				} else {
					var liDom = allDrow[i].children[0];
					if(encodeURIComponent(liDom.getAttribute("data-name")) == name) {
						href = liDom.getAttribute("data-url");
						//google等跳转用法
					} else if(liDom.getAttribute("data-name") == name) {
						href = liDom.getAttribute("data-url");
					};
				};
			}
		}
		$("#c_iframe").attr("src", href);
	}
};

//得到所有导航列表
function getList(roleId) {
	$.ajax({
		type: "get",
		url: HEADER + "resource/queryAllResourceByRole.do?roleId=" + roleId,
		success: function(data) {
			var childrenList = [];
			var html = "";
			//添加大列表
			for(var i = 0; i < data.length; i++) {
				if(data[i].hasAuth && data[i].children.length > 1) { //如果有hasAuth并且能获取到数据
					var len = data[i].children.length;
					var aa = 0;
					var list11 = "";
					for(var j = 0; j < len; j++) { //遍历子菜单
						//						console.log(i+"=="+data[i].children[j].hasAuth);
						if(data[i].children[j].hasAuth) { //如果子菜单存在
							aa++;
							list11 = data[i].children[j];
						};
					};
					if(aa == 1) {
						//						visible-lg-inline-block 
						html += "<li class='menuList' data-name='" + data[i].name +
							"' onmouseenter='changeImg(this);' style='position:relative;'>" +
							"	<a class='speceil' onclick='openWindowUrl(this);' data-url='" + list11.url + "'  data-name='" + list11.name +
							"' style='display:block;position:absolute;top:0;left:0;width:100%;height:100%;background:none;border:none;'></a>" +
							"	<span><img width='20' src='images/" + data[i].name + ".png' width='20'/></span><span class='giveBlue' data-name='" + data[i].name + "'>" +
							list11.name + "</span>" + "</li>";
						childrenList.push([data[i].sn, data[i].children]);
					} else { //aa>1即len>1，则代表有子菜单，所以显示下拉菜单
						html += "<li class='menuList' data-name='" + data[i].name + "' onmouseenter='changeImg(this);'>" +
							"	<span><img width='20' src='images/" + data[i].name + ".png' width='20'/></span><span class=''>" + data[i].name + "</span>" +
							"	<ul id='" + data[i].sn + "'>" +
							"	</ul>" +
							"</li>";
						childrenList.push([data[i].sn, data[i].children]);
					};
				} else if(data[i].hasAuth && data[i].children.length == 1) { //没有子菜单，所以不显示下拉菜单，只是改变了图片，点击直接进入页面
					//data[i].children[0].name
					html += "<li class='menuList' data-name='" + data[i].name + "' onmouseenter='changeImg(this);' style='position:relative;'>" +
						"	<a class='speceil' onclick='openWindowUrl(this);' data-url='" + data[i].children[0].url + "' data-name='" + data[i].children[0].name +
						"' style='display:block;position:absolute;top:0;left:0;width:100%;height:100%;background:none;border:none;'></a>" +
						"	<span><img width='20' src='images/" + data[i].children[0].name + ".png' width='20'/></span><span class='giveBlue' data-name='" +
						data[i].children[0].name + "'>" + data[i].children[0].name + "</span>" +
						"</li>";
					childrenList.push([data[i].sn, data[i].children]);
				};
			}
			$("#allDrowmenu1").html(html);
			html = "";
			for(var i = 0; i < childrenList.length; i++) {
				var list1 = childrenList[i][1];
				for(var j = 0; j < list1.length; j++) {
					if(list1[j].hasAuth) {
						html += "<li style='line-height:30px;'><a onclick='openWindowUrl(this);' data-url='" + list1[j].url +
							"' data-name='" + list1[j].name + "'>" + list1[j].name + "</a></li>";
					};
				}
				$("#" + childrenList[i][0]).html(html);
				html = "";
			};
			//打开相应页面
			gaintUrl();
		}
	});
};

//导航栏右边的个人（修改密码）
function alterMsg(dom) {
	$.ajax({
		type: "get",
		url: HEADER + "updateUserbefore.do",
		success: function(data) {
			data.user.username != null ? $("#username").text(data.user.username) : ""; //将个人信息显示在相应位置
			data.user.name != null ? $("#name").val(data.user.name) : "";
			data.user.phonenumber != null ? $("#phoneNumber").val(data.user.phonenumber) : "";
			data.user.email != null ? $("#email").val(data.user.email) : "";
			var jsonArr = data.role;
			if(jsonArr.roleid == "1") {
				userrole = "系统管理员";
			} else if(jsonArr.roleid == "2") {
				userrole = "仓储管理员";
			} else if(jsonArr.roleid == "3") {
				userrole = "总监";
			} else if(jsonArr.roleid == "4") {
				userrole = "财务人员";
			} else if(jsonArr.roleid == "5") {
				userrole = "职员";
			} else {
				if(jsonArr.status == "1") {
					userrole = "系统管理员(自)";
				} else if(jsonArr.status == "2") {
					userrole = "仓储管理员(自)";
				} else if(jsonArr.status == "3") {
					userrole = "总监(自)";
				} else if(jsonArr.status == "4") {
					userrole = "财务人员(自)";
				} else if(jsonArr.status == "5") {
					userrole = "职员(自)";
				} else {
					userrole = "";
				};
			};
			$("#role").text(userrole); //设置角色显示的内容为userrole的值
		},
		error: function() {
			cr_dialogBox.alert(true, "获取个人信息出错！");
		}
	});
	//拿到当前被编辑的用户的所有信息
	$("#alterPasswsord").fadeIn();
};

function fadeIn() {
	$("#alterPasswsord").fadeOut(); //淡出效果来隐藏一个 修改密码弹出框
};

function error(msg) {
	$("#error p").html(msg).slideDown().delay(1000).slideUp(); //报错提示
}

//用户提交修改密码的数据
function submitMsg() {
	var oldPassword = $("#oldPassword").val();
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	if(oldPassword == null || oldPassword == "") {
		error("旧密码不能为空！");
		$("#oldPassword").focus();
		return false;
	};
	if(password == null || password == "") {
		error("新密码不能为空！");
		$("#password").focus();
		return false;
	};
	if(password == oldPassword) {
		error("旧密码不能与新密码相同");
		$("#newPassword").focus();
		return false;
	};
	if(confirmPassword == null || confirmPassword == "") {
		error("确认密码不能为空！");
		$("#confirmPassword").focus();
		return false;
	};
	if(password != confirmPassword) {
		error("确认密码与新密码不相同！");
		$("#confirmPassword").focus();
		return false;
	};
	$.ajax({
		type: "post",
		url: HEADER + "updatePassword.do",
		data: {
			"oldPassword": oldPassword,
			"password": password
		},
		success: function(data) {
			if(data.status == 2) {
				cr_dialogBox.alert(true, "用户不存在！");
				return false;
			}

			if(data.status == 0) {
				cr_dialogBox.alert(true, "更新异常！");
				return false;
			}

			if(data.status == 3) {
				cr_dialogBox.alert(true, "旧密码不正确！");
				return false;
			}
			cr_dialogBox.alert(true, "提交成功！"); //提交成功将所有框框里面的内容清空
			$("#oldPassword").val("");
			$("#password").val("");
			$("#confirmPassword").val("");
			$("#error p").html("");
			$("#alterPasswsord").fadeOut(); //隐藏修改密码的框
		}
	});
};
//用户提交编辑信息
function submitMsg1() { //为什么没有选择角色
	var name = $("#name").val(); //姓名
	var phoneNumber = $("#phoneNumber").val(); //电话
	var email = $("#email").val(); //邮箱
	$.ajax({
		type: "post",
		url: HEADER + "updateByUser.do",
		data: {
			"name": name,
			"phonenumber": phoneNumber,
			"email": email
		},
		success: function(data) {
			if(data.status == "1") {
				cr_dialogBox.alert(true, "提交成功！");
			}
			if(data.status == "2") {
				cr_dialogBox.alert(true, "用户不存在！");
			}
			if(data.status == "0") {
				cr_dialogBox.alert(true, "提交异常！");
			}
			$("#alterPasswsord").fadeOut(); //隐藏修改信息框
		}
	});
}

//点击导航给url赋值，这样可以刷新跳回自己页面
function gaintUrl() {
	showIframe(); //根据不同的url显示不同内容到iframe里面
	var aDoms = $("#allDrowmenu1").find("li").find("ul").find("li").find("a"); //找到导航栏里的每个超链接元素
	var aaa = $("#allDrowmenu1").find("li").find("a[class='speceil']"); //主菜单
	for(var i = 0; i < aDoms.length; i++) {
		$(aDoms[i]).on("click", function() { //点击时
			var name = $(this).text(); //获取当前元素的文本内容
			var url = window.location.href.split("#")[0];
			window.location.href = url + "#" + name;
			showIframe(); //根据不同的url显示不同内容到iframe里面
		});
	};
	for(var i = 0; i < aaa.length; i++) {
		$(aaa[i]).on("click", function() {
			var name = $(this).attr("data-name");
			var url = window.location.href.split("#")[0];
			window.location.href = url + "#" + name;
			showIframe(); //根据不同的url显示不同内容到iframe里面
		});
	};
};

function gaintUrl1(dom) {
	var name = $(dom).attr("data-name");
	var url = window.location.href.split("#")[0];
	window.location.href = url + "#" + name;
	showIframe(); //根据不同的url显示不同内容到iframe里面
}

//修改鼠标进出时的图片
function changeImg(dom) {
	var src = "";
	var name = "";
	if(!!dom.children[0].children[0]) {
		src = dom.children[0].children[0].src;
		name = dom.children[1].innerHTML;
	} else if(!!dom.children[1].children[0]) {
		src = dom.children[1].children[0].src;
		name = dom.children[2].getAttribute("data-name");
	};
	var url = src.split("images/")[0] + "images/";
	var type = "." + src.split("images/")[1].split(".")[1];
	if(!!dom.children[0].children[0]) {
		dom.children[0].children[0].src = url + name + "1" + type;
		dom.children[1].style.color = "#1da5ff";
		dom.onmouseleave = function() {
			dom.children[0].children[0].src = url + name + type;
			dom.children[1].style.color = "#333";
		}
	} else if(!!dom.children[1].children[0]) {
		dom.children[1].children[0].src = url + name + "1" + type;
		dom.children[1].style.color = "#1da5ff";
		dom.onmouseleave = function() {
			dom.children[1].children[0].src = url + name + type;
			dom.children[1].style.color = "#333";
		}
	};
}

//给父窗口iframe设值
function setMsgForIframe(info) {
	$("#c_iframe").attr("info", info);
}

//获取窗口iframe设置的值
function getMsgForIframe() {
	return $("#c_iframe").attr("info");
}