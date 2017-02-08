<#import "macro.ftl" as m>
<@m.page_header title='配置任务查询' />
<div id="page-content-wrapper">
    <div id="page-content">
        <h3><span>任务配置</span></h3>

        <div class="row pad5A">
            <div class="col-md-6">
            </div>
            <div class="col-md-6">
                <a id="addWorkOrder" href="#" class="btn medium primary-bg float-right" onclick="initAdd();">
                    <span class="button-content">添加配置</span>
                </a>
            </div>
        </div>
        <table id="jobsConfigs" class="table">
            <thead>
            <tr>
                <th>任务名称</th>
                <th>任务 Class</th>
                <th>是否允许多任务</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list list as entity>
            <tr>
                <td>
                ${entity.name}
                </td>
                <td>${entity.clazz}</td>
                <td> <#if entity.multiply >允许<#else>不允许</#if>
                <td>
                    <a class="btn small bg-blue-alt tooltip-button" title="" data-placement="top"
                       href="javascript:initUpdate(${entity.id});" data-original-title="修改">
                        <i class="glyph-icon icon-edit"></i>
                    </a>
                <#--<a class="btn small bg-red tooltip-button" title="" data-placement="top"-->
                <#--href="javascript:deleteWot(${entity.id});" data-original-title="删除">-->
                <#--<i class="glyph-icon icon-remove"></i>-->
                <#--</a>-->
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

// 显示配置任务添加页面
function initAdd(){
	$.get( "/jobs/config/add", function( data ){

		var html = '<div id="dialog" class="hide" title="添加配置"><div class="mrg10A">' + data + '</div></div>';
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
	$.get( "/jobs/config/update/" + id, function( data ){
	
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