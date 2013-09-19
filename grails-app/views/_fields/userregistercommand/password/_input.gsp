<%
    def attrs = [ type: 'password', name: property, value: value, class: 'form-control' ]
    if (required)
        attrs.required = ''
    out << g.field(attrs)
%>
