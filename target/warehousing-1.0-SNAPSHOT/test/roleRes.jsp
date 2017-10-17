<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/23
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>roleRes</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            resList();
            roleList();
        });


        function sub(){
            var chk_value =[];
            $('input[name="resid"]:checked').each(function(){
                chk_value.push($(this).val());
            });
            var roleid = $('input:radio[name="roleid"]:checked').val();
            $.ajax({
                type: "post",
                url: "resourcesRole/update_ResourcesRoleByMy.do",
                data: {roleid:roleid,resid:chk_value.join(",")},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("添加成功");
                        resList();
                    }else{
                        alert("添加失败");
                    }
                }
            });
        }


        function resList(){
            $.ajax({
                type: "get",
                url: "resourcesRole/check_ResourcesRoleList.do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    $("#list").empty();
                    for(var i=0;i<data.length;i++){
                        $("#list").append("　　").append(data[i].resname).append("　　");
                    }
                }
            });
        }

        function choose(i){
            $('input:radio[name="roleid"]:checked').attr("checked",false);
            $(':radio[name="roleid"]').eq(i).attr("checked",true);
//            var radio = $('input:radio[name="roleid"]:checked').val();;
        }

        function resListChange(){
            var roleid = $("input[name='roleid']:checked").val();
            $.ajax({
                type: "get",
                url: "resourcesRole/check_ResourcesRoleList.do",
                data: {roleid:roleid},
                dataType: 'json',
                success: function (data) {
                    $("#list").empty();
                    for(var i=0;i<data.length;i++){
                        if(data[i].status==1){
                            $("#list").append("　　").append("<input name='resid' type='checkbox' checked='true' value='"+data[i].id+"' />").append(data[i].name);
                        }else if(data[i].status==0){
                            $("#list").append("　　").append("<input name='resid' type='checkbox' value='"+data[i].id+"' />").append(data[i].name);
                        }

                    }
                }
            });
        }

        function roleList(){
            $.ajax({
                type: "get",
                url: "role/check_RoleList.do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    $("#rolelist").empty();
                    var n = 0;
                    for(var i=0;i<data.length;i++){
                        if(n==0)
                            $("#rolelist").append("<input type='radio' checked='true' value='"+data[i].id+"' name='roleid' onclick='resListChange();'/> ");
                        else
                            $("#rolelist").append("<input type='radio' value='"+data[i].id+"' name='roleid' onclick='resListChange();'/> ");
                        $("#rolelist").append("<span onclick='choose("+i+");'>"+data[i].rname+"</span>").append("　　");
                        n++;
                    }
                }
            });
        }
    </script>
</head>
<body>
好了提交：<input type="button" onclick="sub();" value="提交吧" /><br/><br/>
=====================简单不失华丽的分割线=====================<br/><br/>
<div id="rolelist"></div><br/><br/>
=====================简单不失华丽的分割线=====================<br/><br/>
<div id="list"></div>
</body>
</html>
