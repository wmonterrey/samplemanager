<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<spring:url value="/resources/img/logo2.png" var="logo" />
<!-- Brand demo (see assets/css/demo/demo.css) -->
<a href="<spring:url value="/" htmlEscape="true "/>" class="navbar-brand app-brand demo d-lg-none py-0 mr-4">

	<span class="app-brand-text demo font-weight-normal ml-2"><img src="${logo}" alt="<spring:message code="logo" />" /></span>
</a>

<!-- Sidenav toggle (see assets/css/demo/demo.css) -->
<div class="layout-sidenav-toggle navbar-nav d-lg-none align-items-lg-center mr-auto">
	<a class="nav-item nav-link px-0 mr-lg-4" href="javascript:void(0)">
    	<i class="ion ion-md-menu text-large align-middle"></i>
    </a>
</div>
      
<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#layout-navbar-collapse">
	<span class="navbar-toggler-icon"></span>
</button>
      
<div class="navbar-collapse collapse" id="layout-navbar-collapse">
<!-- Divider -->
<hr class="d-lg-none w-100 my-2">

<div class="navbar-nav align-items-lg-center">
<!-- Search -->
<label class="nav-item navbar-text navbar-search-box p-0 active">
</label>
</div>
   
<div class="navbar-nav align-items-lg-center ml-auto">

