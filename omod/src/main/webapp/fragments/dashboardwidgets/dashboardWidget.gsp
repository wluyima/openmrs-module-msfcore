<%
    ui.includeJavascript("msfcore", "web/msfcore.vendor.js")
    ui.includeJavascript("msfcore", "web/msfcore.dashboardwidgets.js")
    ui.includeJavascript("uicommons", "handlebars/handlebars.js")
%>
<div id="msfcore-${config.id}" class="info-section msfcore-openmrs-contrib-dashboardwidgets">
    <div class="info-header">
        <i class="${config.icon}"></i>
        <h3>${ ui.message(config.label) }</h3>
    </div>
    <div class="info-body">
        <${config.widget} config="${config.json}"></${config.widget}>
    </div>
</div>