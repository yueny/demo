<#import "macro.ftl" as m>
<@m.page_header title='执行任务查询' />
<div id="page-content-wrapper">
    <div id="page-content">
        <h3><span>执行任务</span></h3>

        <div class="row pad5A">
            <div class="col-md-6">
            </div>
            <div class="col-md-6">
                <a id="addWorkOrder" href="#" class="btn medium primary-bg float-right" onclick="initAdd();">
                    <span class="button-content">添加执行</span>
                </a>
            </div>
        </div>
        <table id="jobsConfigs" class="table">
            <thead>
            <tr>
                <th>任务名称</th>
                <th>任务标识</th>
                <th>执行时间/Cron 表达式</th>
                <th>任务描述</th>
                <th>任务参数</th>
                <th>上次执行时间</th>
                <th>下次执行时间</th>
                <th>任务状态</th>
                <th width="120">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as entity>
            <tr>
                <td> ${entity.name}</td>
                <td>${entity.identifier}</td>
                <td>${entity.expression}</td>
                <td>${entity.description}</td>
                <td>${entity.params}</td>
                <td>${entity.previousFireTime}</td>
                <td>${entity.nextFireTime}</td>
                <td>${entity.state}</td>

                <td>

                    <#if entity.status=='PAUSED' >
                        <a class="btn small bg-green tooltip-button" title="" data-placement="top"
                           href="/jobs/task/resume/${entity.id}?name=${entity.name}" data-original-title="恢复执行">
                            <i class="glyph-icon icon-play"></i>
                        </a>
                    </#if>

                    <#if entity.status=='NORMAL' >
                        <a class="btn small bg-yellow tooltip-button" title="" data-placement="top"
                           href="/jobs/task/paused/${entity.id}?name=${entity.name}" data-original-title="暂停执行">
                            <i class="glyph-icon icon-pause"></i>
                        </a>
                        <a class="btn small bg-blue tooltip-button" title="" data-placement="top"
                           href="javascript:initUpdate(${entity.id});" data-original-title="修改执行表达式">
                            <i class="glyph-icon icon-edit"></i>
                        </a>
                        <a class="btn small bg-red tooltip-button" title="" data-placement="top"
                           href="/jobs/task/remove/${entity.id}?name=${entity.name}" data-original-title="删除执行">
                            <i class="glyph-icon icon-remove"></i>
                        </a>
                    </#if>

                </td>


            </tr>
            </#list>
            </tbody>
        </table>
        <div class="button-group center-div">
        ${pagerHelper.content}
        </div>
    </div>
    <!-- #page-content -->

</div>


<script>

$(document).ready(function() {
	<#if msg!=null>
		<#if status!='success'>
		$.jGrowl("${msg}", {sticky:!1,position:'top-right',theme:'bg-red'});
		</#if>
		<#if status =='success'>
		$.jGrowl("${msg}", {sticky:!1,position:'top-right',theme:'bg-green'});
		</#if>
	</#if>
});

// 显示添加执任务页面
function initAdd(){
	$.get( "/jobs/task/add", function( data ){

		var html = '<div id="dialog" class="hide" title="添加执行"><div class="mrg10A">' + data + '</div></div>';
		$( html ).dialog({
	        resizable:!0,
	        minWidth:600,
	        minHeight:300,
	        modal:!0,
	        dialogClass:"modal-dialog",
	        closeOnEscape:!0,
	        close : function() {
				$( this ).dialog( "destroy" );
			},
	        buttons: {
	            提交: function() {
	                var valid = $('#dialog form').parsley( 'validate' );
	            	if(!valid){
	            		return;
	            	}
	            	$('#dialog form').trigger('submit');
				}
			}
		});
	});
}

// 显示配置任务修改页面
function initUpdate(id){
	$.get( "/jobs/task/update/" + id, function( data ){

		var html = '<div id="dialog" class="hide" title="修改配置"><div class="mrg10A">' + data + '</div></div>';
		$( html ).dialog({
	        resizable:!0,
	        minWidth:600,
	        minHeight:300,
	        modal:!0,
	        dialogClass:"modal-dialog",
	        closeOnEscape:!0,
	        close : function() {
				$( this ).dialog( "destroy" );
			},
	        buttons: {
	            提交: function() {
	                var valid = $('#dialog form').parsley( 'validate' );
	            	if(!valid){
	            		return;
	            	}

	            	$('#dialog form').trigger('submit');

				}
			}
		});
	});
}




































</script>