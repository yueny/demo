<form id="form" action="/jobs/config/update" method="post">
    <input type="hidden" name="id" value="${entity.id}">


    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">任务名称:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <input placeholder="" type="text" data-required="true" name="name" value="${entity.name}"
                           data-rangelength="[1,50]">
                </div>
            </div>
        </div>
    </div>

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">任务 Class:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <input placeholder="" type="text" data-required="true" name="clazz" disabled value="${entity.clazz}"
                           data-rangelength="[1,100]">
                </div>
            </div>
        </div>
    </div>

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">是否允许多任务:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <select name="multiply">
                        <option value="0" <#if entity.multiply  >selected</#if>>不允许</option>
                        <option value="1" <#if entity.multiply  >selected</#if>>允许</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

</form>