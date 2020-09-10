<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<spring:url value="/resources/img/logo2.png" var="logo" />
<!-- Layout sidenav -->
<div id="layout-sidenav" class="layout-sidenav sidenav sidenav-vertical bg-dark">
	<!-- Brand demo (see assets/css/demo/demo.css) -->
    <div class="app-brand demo">
    	<a href="<spring:url value="/" htmlEscape="true "/>" class="app-brand-text demo sidenav-text font-weight-normal ml-2"><img src="${logo}" alt="<spring:message code="logo" />" />
    	<a href="javascript:void(0)" class="layout-sidenav-toggle sidenav-link text-large ml-auto">
        	<i class="ion ion-md-menu align-middle"></i>
      	</a>
    </div>
    
    <div class="sidenav-divider mt-0"></div>
    
	<!-- Links -->
	<ul class="sidenav-inner py-1">
		<li class="sidenav-item home">
            <a href="javascript:void(0)" class="sidenav-link sidenav-toggle"><i class="sidenav-icon ion ion-md-home"></i>
              <div><spring:message code="home" /></div>
            </a>
            <ul class="sidenav-menu">
              <li class="sidenav-item home1">
                <a href="<spring:url value="/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="front" /></div>
                </a>
              </li>
            </ul>
        </li>
        <li class="sidenav-item capture">
            <a href="javascript:void(0)" class="sidenav-link sidenav-toggle"><i class="sidenav-icon ion ion-md-add-circle"></i>
              <div><spring:message code="capture" /></div>
            </a>
            <ul class="sidenav-menu">
              <li class="sidenav-item subjects">
                <a href="<spring:url value="/capture/subjects/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="subjects" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item specimens">
                <a href="<spring:url value="/capture/specimens/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="specimens" /></div>
                </a>
              </li>
            </ul>
        </li>
        <li class="sidenav-item storage">
            <a href="javascript:void(0)" class="sidenav-link sidenav-toggle"><i class="sidenav-icon ion ion-md-archive"></i>
              <div><spring:message code="storage" /></div>
            </a>
            <ul class="sidenav-menu">
              <li class="sidenav-item storespecimens">
                <a href="<spring:url value="/capture/storespecimens/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="storespecimens" /></div>
                </a>
              </li>
            </ul>
        </li>
        <sec:authorize url="/admin/">
        <li class="sidenav-item admin">
            <a href="javascript:void(0)" class="sidenav-link sidenav-toggle"><i class="sidenav-icon ion ion-md-cog"></i>
              <div><spring:message code="manage" /></div>
            </a>
            <ul class="sidenav-menu">
              <li class="sidenav-item users">
                <a href="<spring:url value="/admin/users/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="users" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item catalogs">
                <a href="<spring:url value="/admin/catalogs/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="catalogs" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item studies">
                <a href="<spring:url value="/admin/studies/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="studies" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item labs">
                <a href="<spring:url value="/admin/labs/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="labs" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item equips">
                <a href="<spring:url value="/admin/equips/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="equips" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item racks">
                <a href="<spring:url value="/admin/racks/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="racks" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item boxes">
                <a href="<spring:url value="/admin/boxes/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="boxes" /></div>
                </a>
              </li>
            </ul>
            <ul class="sidenav-menu">
              <li class="sidenav-item translations">
                <a href="<spring:url value="/admin/translations/" htmlEscape="true "/>" class="sidenav-link">
                  <div><spring:message code="translations" /></div>
                </a>
              </li>
            </ul>
        </li>
        </sec:authorize>
        <li class="sidenav-item">
          <a href="<spring:url value="/logout" htmlEscape="true" />" class="sidenav-link"><i class="sidenav-icon ion ion-ios-log-out"></i>
            <div><spring:message code="logout" /></div>
          </a>
        </li>
	</ul>
    
</div>
<!-- / Layout sidenav -->