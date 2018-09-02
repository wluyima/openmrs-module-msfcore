<h3>${ui.message("msfcore.oldPatientInfo.question")}</h3>
${ ui.includeFragment("uicommons", "field/text",
["initialValue" : ui.escapeAttribute(uiUtils.getIdentifier(patient, "Old Patient ID"))  ] << config) }