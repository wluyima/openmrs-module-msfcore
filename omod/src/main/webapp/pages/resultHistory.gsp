<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message(pageLabel) ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.encodeHtmlContent(ui.format(patient.familyName)) } ${ ui.encodeHtmlContent(ui.format(patient.givenName)) }",
            link: "${ ui.pageLink("coreapps", "clinicianfacing/patient", [patientId: patient.uuid]) }" },
        { label: "${ ui.message(pageLabel) }" }
    ];
</script>


<style>
	#results-print-close-wrapper {
	    text-align: center;
	}
	#results-print-close {
		display: inline-block;
	}
</style>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<br />

${ ui.includeFragment("msfcore", "results", [ fromResultNumber : 0, toResultNumber : 25 ]) }

