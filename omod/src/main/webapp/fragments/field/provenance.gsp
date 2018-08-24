<h3>${ui.message("msfcore.provenance.question")}</h3>
<%
    def options = provenances
    options = options.collect {
        def selected = (it == config.initialValue);
        [ label: it.label, value: it.value, selected: selected ]
    }
    options = options.sort { a, b -> a.label <=> b.label }
%>
${ ui.includeFragment("uicommons", "field/dropDown", [ options: options ] << config) }