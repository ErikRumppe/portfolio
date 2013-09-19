<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta author="Erik Rumppe">

    <title>Store | <g:layoutTitle/></title>

    <r:require modules="bootstrap-css, bootstrap-theme-css, store"/>
    <r:layoutResources/>
</head>

<body>
<div class="container">
    <div class="row">
        <!-- Begin page-header -->
        <h1 class="page-header page-header-custom">
            <g:link controller="store" action="index" class="store-header">
                <span class="text-logo">me</span>Tunes
            </g:link>
            <small><em>Your online music store</em></small>
            <g:if test="${session?.user}">
                <span style="font-size: 14px">
                    <nav:menu scope="store" class="nav nav-pills pull-right"/>
                </span>
            </g:if>
        </h1>

        <g:layoutBody/>
    </div>

</div>
<r:layoutResources/>
</body>
</html>