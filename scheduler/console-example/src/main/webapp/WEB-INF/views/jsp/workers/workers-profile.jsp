<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
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
</head>
<body>

	<div class="page-content">
		<div class="page-header">
			<h1>
				Tables <small> <i class="icon-double-angle-right"></i>
					Static & Dynamic Tables
				</small>
			</h1>
		</div>

		<div class="row" style="margin-left:0px">
			<div class="col-xs-12">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover"
						id="sample-table-1">
						<thead>
							<tr>
								<th class="center"><label> <input type="checkbox"
										class="ace"> <span class="lbl"></span>
								</label></th>
								<th>姓名</th>
								<th>邮箱</th>
								<th class="hidden-480">职位</th>

								<th><i class="icon-time bigger-110 hidden-480"></i>参与过的项目</th>
								<th class="hidden-480">Status</th>

								<th></th>
							</tr>
						</thead>

						<tbody>
							<tr>
								<td class="center"><label> <input type="checkbox"
										class="ace"> <span class="lbl"></span>
								</label></td>

								<td><a href="#">ace.com</a></td>
								<td>$45</td>
								<td class="hidden-480">3,330</td>
								<td>Feb 12</td>

								<td class="hidden-480"><span
									class="label label-sm label-warning">Expiring</span></td>

								<td>
									<div
										class="visible-md visible-lg hidden-sm hidden-xs btn-group">
										<button class="btn btn-xs btn-success">
											<i class="icon-ok bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-info">
											<i class="icon-edit bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-danger">
											<i class="icon-trash bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-warning">
											<i class="icon-flag bigger-120"></i>
										</button>
									</div>
								</td>
							</tr>

							<tr>
								<td class="center"><label> <input type="checkbox"
										class="ace"> <span class="lbl"></span>
								</label></td>

								<td><a href="#">base.com</a></td>
								<td>$35</td>
								<td class="hidden-480">2,595</td>
								<td>Feb 18</td>

								<td class="hidden-480"><span
									class="label label-sm label-success">Registered</span></td>

								<td>
									<div
										class="visible-md visible-lg hidden-sm hidden-xs btn-group">
										<button class="btn btn-xs btn-success">
											<i class="icon-ok bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-info">
											<i class="icon-edit bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-danger">
											<i class="icon-trash bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-warning">
											<i class="icon-flag bigger-120"></i>
										</button>
									</div>
								</td>
							</tr>

							<tr>
								<td class="center"><label> <input type="checkbox"
										class="ace"> <span class="lbl"></span>
								</label></td>

								<td><a href="#">max.com</a></td>
								<td>$60</td>
								<td class="hidden-480">4,400</td>
								<td>Mar 11</td>

								<td class="hidden-480"><span
									class="label label-sm label-warning">Expiring</span></td>

								<td>
									<div
										class="visible-md visible-lg hidden-sm hidden-xs btn-group">
										<button class="btn btn-xs btn-success">
											<i class="icon-ok bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-info">
											<i class="icon-edit bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-danger">
											<i class="icon-trash bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-warning">
											<i class="icon-flag bigger-120"></i>
										</button>
									</div>

								</td>
							</tr>

							<tr>
								<td class="center"><label> <input type="checkbox"
										class="ace"> <span class="lbl"></span>
								</label></td>

								<td><a href="#">best.com</a></td>
								<td>$75</td>
								<td class="hidden-480">6,500</td>
								<td>Apr 03</td>

								<td class="hidden-480"><span
									class="label label-sm label-inverse arrowed-in">Flagged</span></td>

								<td>
									<div
										class="visible-md visible-lg hidden-sm hidden-xs btn-group">
										<button class="btn btn-xs btn-success">
											<i class="icon-ok bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-info">
											<i class="icon-edit bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-danger">
											<i class="icon-trash bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-warning">
											<i class="icon-flag bigger-120"></i>
										</button>
									</div>
								</td>
							</tr>

							<tr>
								<td class="center"><label> <input type="checkbox"
										class="ace"> <span class="lbl"></span>
								</label></td>

								<td><a href="#">pro.com</a></td>
								<td>$55</td>
								<td class="hidden-480">4,250</td>
								<td>Jan 21</td>

								<td class="hidden-480"><span
									class="label label-sm label-success">Registered</span></td>

								<td>
									<div
										class="visible-md visible-lg hidden-sm hidden-xs btn-group">
										<button class="btn btn-xs btn-success">
											<i class="icon-ok bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-info">
											<i class="icon-edit bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-danger">
											<i class="icon-trash bigger-120"></i>
										</button>

										<button class="btn btn-xs btn-warning">
											<i class="icon-flag bigger-120"></i>
										</button>
									</div>

								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- /.table-responsive -->
			</div>
			<!-- /span -->
		</div>
	</div>
</body>
</html>