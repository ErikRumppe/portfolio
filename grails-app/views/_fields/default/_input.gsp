<%
    def attrs = [ type: 'text', name: property, value: value, class: 'form-control' ]
    if (required)
        attrs.required = ''
    out << g.field(attrs)
%>
