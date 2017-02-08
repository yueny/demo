<form id="form" action="/jobs/task/update" method="post">
    <input type="hidden" name="id" value="${entity.id}">

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">任务类型：</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <select name="configId" id="configId" disabled class="custom-select">
                    <#list list as entity>
                        <option value="${entity.id}">
                        ${entity.name}
                        </option>
                    </#list>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">执行时间:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <input placeholder="" type="text" data-required="true" name="expression" value="${entity.expression}" data-rangelength="[1,50]">
                </div>
            </div>
        </div>
    </div>

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">任务描述:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <input placeholder="" type="text" name="description" data-rangelength="[1,50]" value="${entity.description}">
                </div>
            </div>
        </div>
    </div>

<#--<div class="form-row">-->
<#--<div class="form-label col-md-2">-->
<#--<label for="">任务参数:</label>-->
<#--</div>-->
<#--<div class="form-input col-md-10">-->
<#--<div class="row">-->
<#--<div class="col-md-10">-->
<#--<input placeholder="" type="text" name="params" data-rangelength="[1,50]">-->
<#--</div>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->


</form>