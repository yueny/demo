<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
		<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
		<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-small btn-success">
								<i class="icon-signal"></i>
							</button>

							<button class="btn btn-small btn-info">
								<i class="icon-pencil"></i>
							</button>

							<button class="btn btn-small btn-warning">
								<i class="icon-group"></i>
							</button>

							<button class="btn btn-small btn-danger">
								<i class="icon-cogs"></i>
							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>
						</div>
					</div><!-- #sidebar-shortcuts -->

					<ul class="nav nav-list">
						<li class="active">
							<a href="index.html">
								<i class="icon-dashboard"></i>
								<span class="menu-text">控制台</span>
							</a>
						</li>

						<li>
							<a href="typography.html">
								<i class="icon-text-width"></i>
								<span class="menu-text">项目浏览</span>
							</a>
						</li>

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-desktop"></i>
								<span class="menu-text">项目管理</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a href="elements.html">
										<i class="icon-double-angle-right"></i>
										文档管理
									</a>
								</li>

								<li>
									<a href="buttons.html">
										<i class="icon-double-angle-right"></i>
										类图管理
									</a>
								</li>
							</ul>
						</li>

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-list"></i>
								<span class="menu-text">人员管理</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a target="mainFrame" onclick="siMenu('z06','lm06','人员简介','./pages/workers/workers-profile.jsp')">
										<i class="icon-double-angle-right"></i>
										人员简介
									</a>
								</li>

							</ul>
						</li>

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-edit"></i>
								<span class="menu-text">知识管理 </span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a href="form-elements.html">
										<i class="icon-double-angle-right"></i>
										问题管理
									</a>
								</li>

								<li>
									<a href="form-wizard.html">
										<i class="icon-double-angle-right"></i>
										算法管理
									</a>
								</li>

							</ul>
						</li>

						<li>
							<a href="widgets.html">
								<i class="icon-list-alt"></i>
								<span class="menu-text">图表统计 </span>
							</a>
						</li>

						<li>
							<a  href="#" class="dropdown-toggle">
								<i class="icon-calendar"></i>

								<span class="menu-text">
									作业管理
								</span>
								<b class="arrow icon-angle-down"></b>
							</a>
				<ul class="submenu">
								<li>
									<a target="mainFrame" onclick="siMenu('z07','lm07','作业监控','./pages/quartz/pms-job.jsp')">
										<i class="icon-double-angle-right"></i>
										作业监控
									</a>
								</li>

							</ul>
						</li>


						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-tag"></i>
								<span class="menu-text"> 系统管理</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a href="profile.html">
										<i class="icon-double-angle-right"></i>
										用户信息
									</a>
								</li>

								<li>
									<a href="inbox.html">
										<i class="icon-double-angle-right"></i>
										角色信息
									</a>
								</li>

								<li>
									<a target="mainFrame" onclick="siMenu('z10','lm10','项目信息','./pages/project/projectMange.jsp')">
										<i class="icon-double-angle-right"></i>
										资源信息
									</a>
								</li>

							</ul>
						</li>

					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>
