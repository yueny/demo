<form id="form" action="/jobs/config/add" method="post">

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">任务名称:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <input placeholder="" type="text" data-required="true" name="name" data-rangelength="[1,50]">
                </div>
            </div>
        </div>
    </div>

    <div class="form-row">
        <div class="form-label col-md-2">
            <label for="">任务Class:</label>
        </div>
        <div class="form-input col-md-10">
            <div class="row">
                <div class="col-md-10">
                    <input placeholder="" type="text" data-required="true" name="clazz"   data-rangelength="[1,100]">
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
                        <option value="0" selected> 不允许</option>
                        <option value="1" >允许</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

</form>