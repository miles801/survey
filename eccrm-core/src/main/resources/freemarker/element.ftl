<#assign defaultLength="col-2-half">
<#assign type="${(element.type)!'text'}">
<#if type == "text" >
<input class="${element.length!defaultLength}" type="text" ng-model="beans.${element.name}" />
    <#if element.addOnIcon??>
    <span class="add-on" >
    <i class="glyphicons ${element.addOnIcon} icon" ></i >
</span >
    </#if>
<#elseif type == 'hidden'>
<input type="hidden" ng-model="${element.name}" />
<#elseif type == 'textarea'>
<textarea class="${element.length!'col-10'}" rows="3" ng-model="beans.${element.name}" ></textarea >
<#elseif type =='select'>
<select ng-model="beans.${element.name}" class="${element.length!defaultLength}" ng-options="foo.value as foo.name for foo in x">
</select >
<#elseif type == 'checkbox'>
<div class="${element.length!'col-2-half'}" >
    <#if element.items??>
        <#list element.items as item>
            <label style="cursor: pointer;margin-right: 10px;" >
                <input name="${element.name}" type="checkbox" ng-model="beans.${element.name}" /> ${item}
            </label >
        </#list>
    <#else>
        <input name="${element.name}" type="checkbox" ng-model="beans.${element.name}" />
    </#if>
</div >
<#elseif type == 'radio'>
<div class="${element.length!'col-2-half'}" >
    <#if element.items??>
        <#list element.items as item>
            <label style="cursor: pointer;margin-right: 10px;" >
                <input name="${element.name}" type="radio" ng-model="beans.${element.name}" /> ${item}
            </label >
        </#list>
    <#else>
        <input name="${element.name}" type="radio" ng-model="beans.${element.name}" />
    </#if>
</div >
<#elseif type == 'label'>
<div class="form-label ${element.length!'col-1-half'}" >
    <label >${element.name}:</label >
</div >
<#elseif type == 'text-only'>
<span ng-bind-template="{{ ${element.name } }}" class="${element.length!'col-2-half'}" ></span >
<#elseif type == 'text-only-center'>
<span ng-bind-template="{{ ${element.name } }}" style="text-align: center;" class="${element.length!'col-2-half'}" ></span >
<#elseif type == "add-on">
<div class="add-on col" >
    <i class="glyphicons ${element.addOnIcon} icon" ></i >
</div >
<#elseif type == "date">
<input type="text" class="${element.length!defaultLength}" date-type="string" bs-datepicker ng-model="beans.${element.name}" data-date-format="yyyy-mm-dd" readonly placeholder="点击选择开始日期" />
<div class="add-on col" >
    <i class="glyphicons calendar icon" title="清除时间" ng-click="beans.${element.name}=null;" ></i >
</div >
<#elseif type == "date-range">
<div class="${element.length!defaultLength}">
    <input type="text" class="col-5-half" date-type="string" bs-datepicker ng-model="beans.startDate" data-date-format="yyyy-mm-dd" readonly placeholder="选择日期" />
    <div class="add-on col" >
        <i class="glyphicons calendar icon" title="清除时间" ng-click="beans.startDate=null;" ></i >
    </div >
    <span class="col-1" style="text-align: center" >-</span >
    <input type="text" class="col-5-half" date-type="string" bs-datepicker ng-model="beans.endDate" data-date-format="yyyy-mm-dd" readonly placeholder="选择日期" />
    <div class="add-on col" >
        <i class="glyphicons calendar icon" title="清除时间" ng-click="beans.endDate=null;" ></i >
    </div >
</div>
<#elseif type == "time">
<input type="text" class="${element.length!defaultLength}" date-type="string" bs-datepicker ng-model="beans.${element.name}" data-date-format="yyyy-mm-dd" readonly placeholder="点击选择开始日期" />
<span class="add-on col" >
    <i class="glyphicons calendar icon" title="清除时间" ng-click="beans.${element.name}=null;" ></i >
</span >
<#elseif type == "datetime">
<input type="text" class="${element.length!defaultLength}" date-type="string" bs-datepicker ng-model="beans.${element.name}" data-date-format="HH:mm:ss" readonly placeholder="点击选择开始时间" />
<span class="add-on col" >
    <i class="glyphicons calendar icon" title="清除时间" ng-click="beans.${element.name}=null;" ></i >
</span >
</#if>
