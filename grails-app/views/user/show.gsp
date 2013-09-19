<%@ page import="com.erumppe.portfolio.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-user" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list user">

        <g:if test="${userInstance?.username}">
            <li class="fieldcontain">
                <span id="username-label" class="property-label"><g:message code="user.username.label"
                                                                            default="Username"/></span>

                <span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${userInstance}"
                                                                                            field="username"/></span>

            </li>
        </g:if>

        <g:if test="${userInstance?.password}">
            <li class="fieldcontain">
                <span id="password-label" class="property-label"><g:message code="user.password.label"
                                                                            default="Password"/></span>

                <span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${userInstance}"
                                                                                            field="password"/></span>

            </li>
        </g:if>

        <g:if test="${userInstance?.firstname}">
            <li class="fieldcontain">
                <span id="firstname-label" class="property-label"><g:message code="user.firstname.label"
                                                                             default="Firstname"/></span>

                <span class="property-value" aria-labelledby="firstname-label"><g:fieldValue bean="${userInstance}"
                                                                                             field="firstname"/></span>

            </li>
        </g:if>

        <g:if test="${userInstance?.lastname}">
            <li class="fieldcontain">
                <span id="lastname-label" class="property-label"><g:message code="user.lastname.label"
                                                                            default="Lastname"/></span>

                <span class="property-value" aria-labelledby="lastname-label"><g:fieldValue bean="${userInstance}"
                                                                                            field="lastname"/></span>

            </li>
        </g:if>

        <g:if test="${userInstance?.dateCreated}">
            <li class="fieldcontain">
                <span id="dateCreated-label" class="property-label"><g:message code="user.dateCreated.label"
                                                                               default="Date Created"/></span>

                <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate
                        date="${userInstance?.dateCreated}"/></span>

            </li>
        </g:if>

        <g:if test="${userInstance?.lastUpdated}">
            <li class="fieldcontain">
                <span id="lastUpdated-label" class="property-label"><g:message code="user.lastUpdated.label"
                                                                               default="Last Updated"/></span>

                <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate
                        date="${userInstance?.lastUpdated}"/></span>

            </li>
        </g:if>

        <g:if test="${userInstance?.purchasedSongs}">
            <li class="fieldcontain">
                <span id="purchasedSongs-label" class="property-label"><g:message code="user.purchasedSongs.label"
                                                                                  default="Purchased Songs"/></span>

                <g:each in="${userInstance.purchasedSongs}" var="p">
                    <span class="property-value" aria-labelledby="purchasedSongs-label"><g:link controller="song"
                                                                                                action="show"
                                                                                                id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${userInstance?.id}"/>
            <g:link class="edit" action="edit" id="${userInstance?.id}"><g:message code="default.button.edit.label"
                                                                                   default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
