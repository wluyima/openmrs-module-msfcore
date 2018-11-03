<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message('msfcore.resultList') ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.encodeHtmlContent(ui.format(patient.person.personName.familyName)) } ${ ui.encodeHtmlContent(ui.format(patient.person.personName.givenName)) }",
            link: "${ ui.pageLink("coreapps", "clinicianfacing/patient", [patientId: patient.uuid]) }" },
        { label: "${ ui.message('msfcore.resultList') }" }
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<br />

${ ui.includeFragment("msfcore", "results") }

