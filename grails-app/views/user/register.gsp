<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="store"/>
    <title>Register</title>
</head>

<body>
<div class="col-md-8 col-md-offset-2">
<h3>Registration</h3>

<p>Please fill out the form below.  All fields are required.</p>

<g:hasErrors bean="${user}">
    <div class="text-danger">
        <g:renderErrors bean="${user}" class="text-danger"/>
    </div>
</g:hasErrors>

<g:form controller="user" action="save" role="form">
    <f:with bean="user">
        <f:field property="username"/>
        <f:field property="password"/>
        <f:field property="confirm" label="Confirm Password"/>
        <f:field property="firstname"/>
        <f:field property="lastname"/>
    </f:with>

    <g:submitButton name="Register" class="btn btn-primary pull-right"/>
</g:form>
</div>
</body>
</html>