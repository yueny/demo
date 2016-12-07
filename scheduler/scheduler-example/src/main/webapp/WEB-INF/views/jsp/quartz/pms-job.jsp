<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title></title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- jsp文件头和头部 -->
<%@ include file="../layout/top.jsp"%>
<link rel="stylesheet" href="static/easyui/easyui.css" />
<link rel="stylesheet" href="static/Cron/themes/icon.css" />
<link href="static/Cron/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="static/easyui/jquery.easyui.min1.4.4.js"></script>
<script src="static/Cron/cron.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$('#list').datagrid({
			url : './queryAll.html',
			toolbar : '#toolbar-airTicketUploadFileError',
			singleSelect : true,
			height : 400,
			columns : [ [ {
				title : '调度器名称',
				field : 'sched_name',
				width : 80
			}, {
				title : '触发器名称',
				field : 'trigger_name',
				width : 80
			}, {
				title : '触发器分组',
				field : 'trigger_group',
				width : 80
			}, {
				title : 'Job作业名称',
				field : 'job_name',
				width : 120
			}, {
				title : 'Job作业分组',
				field : 'job_group',
				width : 80
			}, {
				title : '上次执行时间',
				field : 'prev_fire_time',
				width : 150
			}, {
				title : '下次执行时间',
				field : 'next_fire_time',
				width : 150
			}, {
				title : '作业状态',
				field : 'trigger_state',
				width : 150
			}, {
				title : '作业描述',
				field : 'desccription',
				width : 150
			}, {
				title : '时间规则',
				field : 'cron_expression',
				width : 150
			}, {
				title : '作业类',
				field : 'job_class_name',
				width : 150
			},

			] ]
		});
	});

	//暂停作业
	function pause() {
		var row = $('#list').datagrid('getSelected');
		if (row) {
			$.ajax({
				url : './pmsJob_pause.action',
				type : 'post',
				dataType : 'json',
				data : {
					"trigger_name" : row.trigger_name,
					"trigger_group" : row.trigger_group
				},
				success : function(r) {
					$('#list').datagrid('load');
					if (r.flag == false) {
						alert(r.msg);
					}
				}
			});
		} else {

		}
	}

	//重启作业
	function restart() {

		var row = $('#list').datagrid('getSelected');
		if (row) {
			$.ajax({
				url : './pmsJob_restart.action',
				type : 'post',
				dataType : 'json',
				data : {
					"trigger_name" : row.trigger_name,
					"trigger_group" : row.trigger_group
				},
				success : function(r) {
					$('#list').datagrid('load');
					if (r.flag == false) {
						alert(r.msg);
					}
				}
			});
		}
	}

	//添加作业
	function addJob() {
		$('#myModal').modal({
			keyboard : true
		})
	}
	

	//编辑作业
	function editJob() {

	}

	//立即执行
	function executeNow() {

		var row = $('#list').datagrid('getSelected');
		if (row) {
			$.ajax({
				url : './pmsJob_executeNow.action',
				type : 'post',
				dataType : 'json',
				data : {
					"job_name" : row.job_name,
					"job_group" : row.job_group
				},
				success : function(r) {
					$('#list').datagrid('load');
					if (r.flag == false) {
						alert(r.msg);
					}
				}
			});
		}
	}
</script>
</head>
<body>

	<div class="page-content">
		<div class="page-header">
			<div class="btn-group">
				<a class="btn" href="javascript:void(0)" onclick="addJob()"> <i
					class="icon-repeat"></i> 新增作业
				</a> <a class="btn" href="javascript:void(0)" onclick="editJob()"> <i
					class=" icon-edit"></i> 修改作业
				</a> <a class="btn" href="javascript:void(0)" onclick="pause()"> <i
					class="icon-pause"></i> 暂停作业
				</a> <a class="btn" href="javascript:void(0)" onclick="restart()"> <i
					class="icon-repeat"></i> 重启作业
				</a> <a class="btn" href="javascript:void(0)" onclick="executeNow()">
					<i class="icon-play"></i> 立即执行
				</a>
			</div>
		</div>

		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" style="height: 300px;width: 500px">
			
		</div>
		<!-- /.modal -->
		
		<div id="list"></div>
	</div>
</body>
</html>