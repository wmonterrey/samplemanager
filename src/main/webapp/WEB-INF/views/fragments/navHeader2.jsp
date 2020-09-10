<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!-- Divider -->
<div class="nav-item d-none d-lg-block text-big font-weight-light line-height-1 opacity-25 mr-3 ml-1">|</div>
      
<div class="demo-navbar-messages nav-item mr-lg-3">
	<a class="nav-link" href="javascript:location.reload()">
		<i class="ion ion-md-refresh navbar-icon align-middle"></i>
		<span class="d-lg-none align-middle">&nbsp; <spring:message code="refresh" /></span>
	</a>
</div>
<!-- Divider -->
<div class="nav-item d-none d-lg-block text-big font-weight-light line-height-1 opacity-25 mr-3 ml-1">|</div>
      
<div class="demo-navbar-messages nav-item mr-lg-3">
	<a class="nav-link dropdown-toggle hide-arrow" href="javascript:cargarAyuda()">
		<i class="ion ion-md-help navbar-icon align-middle"></i>
		<span class="d-lg-none align-middle">&nbsp; <spring:message code="help" /></span>
	</a>
</div>

<!-- Divider -->
<div class="nav-item d-none d-lg-block text-big font-weight-light line-height-1 opacity-25 mr-3 ml-1">|</div>
      
<div class="demo-navbar-messages nav-item dropdown mr-lg-3">
	<a class="nav-link dropdown-toggle hide-arrow" href="#" data-toggle="dropdown">
		<i class="ion ion-ios-globe navbar-icon align-middle"></i>
		<span class="d-lg-none align-middle">&nbsp; <spring:message code="language" /></span>
	</a>
	<div class="dropdown-menu dropdown-menu-right">
		<div class="bg-primary text-center text-white font-weight-bold p-3">
			<spring:message code="language" />
		</div>
		<div class="list-group list-group-flush">
			<a href="?lang=es" class="list-group-item list-group-item-action media d-flex align-items-center">
				Español
			</a>
			<a href="?lang=en" class="list-group-item list-group-item-action media d-flex align-items-center">
        		English
			</a>
		</div>
	</div>
</div>

<!-- Divider -->
<div class="nav-item d-none d-lg-block text-big font-weight-light line-height-1 opacity-25 mr-3 ml-1">|</div>
     
<div class="demo-navbar-user nav-item dropdown">
	<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
		<span class="d-inline-flex flex-lg-row-reverse align-items-center align-middle">
			<span class="px-1 mr-lg-2 ml-2 ml-lg-0"> <sec:authentication property="principal.username" /> </span>
		</span>
	</a>
	<div class="dropdown-menu dropdown-menu-right">
		<a href="<spring:url value="/users/profile" htmlEscape="true" />" class="dropdown-item"><i class="ion ion-ios-person text-lightest"></i> &nbsp; <spring:message code="profile" /></a>
		<a href="<spring:url value="/users/chgpass" htmlEscape="true" />" class="dropdown-item"><i class="ion ion-ios-key text-lightest"></i> &nbsp; <spring:message code="changepass" /></a>
		<!-- a href="javascript:void(0)" class="dropdown-item"><i class="ion ion-ios-mail text-lightest"></i> &nbsp; <spring:message code="messages" /></a> -->
		<div class="dropdown-divider"></div>
		<a href="<spring:url value="/logout" htmlEscape="true" />" class="dropdown-item"><i class="ion ion-ios-log-out text-danger"></i> &nbsp; <spring:message code="logout" /></a>
	</div>
</div>
</div>
</div>