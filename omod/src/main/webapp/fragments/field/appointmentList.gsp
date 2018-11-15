<%
    def options = appointments
    options = options.collect {
        [ label: it.label, value: it.value ]
    }
    options = options.sort { a, b -> a.label <=> b.label }
%>

<select name="appointment-type" id="appointment-type-list">
	<option value=""></option>
    <% for (item in options) { %>
        <option value="${item.label}">${item.label}</option>
    <% } %>
</select>