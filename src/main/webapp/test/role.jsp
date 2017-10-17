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
    <title>role</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            resList();
        });

        function sub(){
            var name = $("#name").val();
            var rname = $("#rname").val();
            if(name==''|rname=='')return;
            $.ajax({
                type: "post",
                url: "role/add_Role.do",
                data: {name:name,rname:rname},
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

        function delRes(i){
            var id = $("#list input").eq(i-3).val();
            $.ajax({
                type: "post",
                url: "role/delete_Role.do",
                data: {id:id},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("删除成功");
                        resList();
                    }else if(data.status==0){
                        alert("删除失败");
                    }else if(data.status==2){
                        alert("id not in");
                    }
                }
            });
        }

        function updateRes(i){
            var rname = $("#list input").eq(i-1).val();
            var name = $("#list input").eq(i-2).val();
            var id = $("#list input").eq(i-3).val();
            $.ajax({
                type: "post",
                url: "role/update_Role.do",
                data: {name:name,rname:rname,id:id},
                dataType: 'json',
                success: function (data) {
                    if(data.status==1){
                        alert("更新成功");
                        getparent();
                        resList();
                    }else if(data.status==0){
                        alert("更新失败");
                    }else if(data.status==2){
                        alert("id not in");
                    }
                }
            });

        }

        function resList(){
            $.ajax({
                type: "get",
                url: "role/check_RoleList.do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    $("#list").empty();
                    var n = 0;
                    for(var i=0;i<data.length;i++){
                        $("#list").append("<input type='text' value='"+data[i].id+"' id='name"+i+"'>");n++;
                        $("#list").append("<input type='text' value='"+data[i].name+"' id='name"+i+"'>");n++;
                        $("#list").append("　　").append("<input type='text' value='"+data[i].rname+"'>").append("　　");n++;
                        $("#list").append("<input type='button' value='更新' onclick='updateRes("+n+");'/>").append("　　");
                        $("#list").append("<a href='javascript:void(0)' onclick='delRes("+n+");'>删除</a>").append("　　");n++;
                        $("#list").append(data[i].id).append("　　").append(data[i].sn).append("　　").append(data[i].parentid).append("<br/><br/>");;

                    }
                }
            });
        }
    </script>
</head>
<body>
权限英文名称:<input type="text" name="name" id="name"/><br/><br/>
权限中文别名:<input type="text" name="rname" id="rname"/><br/><br/>
好了提交：<input type="button" onclick="sub();" value="提交吧" /><br/><br/>

=====================简单不失华丽的分割线=====================<br/><br/>
<div id="list"></div>
</body>
</html>

