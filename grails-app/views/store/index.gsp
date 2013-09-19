<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="store"/>
  <title>Home</title>
</head>
<body>
<g:if test="${!session?.user}">
    <g:render template="welcome"/>
</g:if>
<g:else>
    <g:render template="storefront"/>
</g:else>
</body>
</html>