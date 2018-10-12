<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.patientSummary") ])
%>
${ ui.includeFragment("msfcore", "patientSummary") }  
