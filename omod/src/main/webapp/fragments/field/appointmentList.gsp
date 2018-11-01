<%
    def options = appointments
    options = options.collect {
        [ label: it.label, value: it.value ]
    }
%>

<select name="appointment-type" id="appointment-type-list">
	<option value=""></option>
    <% for (item in options) { %>
        <option value="${item.value}">${item.label}</option>
    <% } %>
</select>