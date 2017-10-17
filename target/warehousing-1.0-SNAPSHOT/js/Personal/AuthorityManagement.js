$(function() {
	getAllRole();//获取所有角色
});

//获取所有角色
function getAllRole() {
	var url = HEADER+"role/check_RoleList.do";
	$.get(url, function(data) {
		var html = "";
		for(var i = 0; i < data.length; i++) {
			if(data[i].id != 1){
				html += "<li><label><input name='role' type='radio' data-id=" + data[i].id + " onclick='getPermission(this);'> " + data[i].rname + "</label></li>"
			};
		};
		$("#role").html(html);
		$("#role").children().eq(0).find("input[data-id='2']").prop("checked",true)
		getPermission();
	});
};

//获取权限列表
function getPermission(dom) {
	var roleId = $(dom).attr("data-id");
	if(!!roleId == false){
		roleId = 2;
	};
	var url = HEADER+"resources/queryAllResources.do?ruflag=role&ruid=" + roleId;
	$.get(url, function(d) {
		var len = d.result.length;
		var html = "";
		var jsonarr=d.result;
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
	var inputDom = $("input[data-flag]", "#permission");
	for(var i = 0; i < inputDom.length; i++) {
		var flag = $(inputDom[i]).attr("data-flag");
		if(flag == "true") {
			$(inputDom[i]).prop("checked", true);
		};
	};
};

//提交信息
function submitMsg() {
	var roleId = $("#role").find("input:checked").data("id");
	var inputDoms = $("#permission").find("input[data-flag]");
	var resourceIds = [];
	for(var i = 0; i < inputDoms.length; i++) {
		var flag = $(inputDoms[i]).is(":checked");
		if(flag == true) {
			resourceIds.push($(inputDoms[i]).data("id"));
		};
	};
	if(roleId) {
		$.ajax({

			type: "post",
			url: HEADER+"resources/updateRoleorUserRes.do",
			data: {
				"roleId": roleId,
				"resourceIds": resourceIds.toString(),
                "ruflag":"role"
			},
			success: function(data) {
				var msg = data.msg;
				if(msg == "success"){
					cr_dialogBox.alert(true,"修改成功");
				}else if(msg == "error"){
					cr_dialogBox.alert(true,"请选择权限！");
				}
			}
		});
	} else {
		cr_dialogBox.alert(true,"请选择角色！");
	}
};