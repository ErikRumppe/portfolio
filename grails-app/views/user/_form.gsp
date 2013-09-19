<%@ page import="com.erumppe.portfolio.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="user.username.label" default="Username"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" maxlength="15" pattern="${userInstance.constraints.username.matches}" required=""
                 value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
    <label for="password">
        <g:message code="user.password.label" default="Password"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="password" maxlength="15" pattern="${userInstance.constraints.password.matches}" required=""
                 value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstname', 'error')} required">
    <label for="firstname">
        <g:message code="user.firstname.label" default="Firstname"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="firstname" required="" value="${userInstance?.firstname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastname', 'error')} required">
    <label for="lastname">
        <g:message code="user.lastname.label" default="Lastname"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lastname" required="" value="${userInstance?.lastname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'purchasedSongs', 'error')} ">
    <label for="purchasedSongs">
        <g:message code="user.purchasedSongs.label" default="Purchased Songs"/>

    </label>
    <g:select name="purchasedSongs" from="${com.erumppe.portfolio.Song.list()}" multiple="multiple" optionKey="id"
              size="5" value="${userInstance?.purchasedSongs*.id}" class="many-to-many"/>
</div>

