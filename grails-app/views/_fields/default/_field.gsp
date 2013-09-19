<%@ page defaultCodec="html" %>
<div class="form-group ${invalid ?  'has-error' : ''}">
    <label for="${property}">${label}</label>
    <%=  widget %>
    <g:if test="${invalid}">
        <p class="help-block">${errors.join('<br>')}</p>
    </g:if>
</div> 