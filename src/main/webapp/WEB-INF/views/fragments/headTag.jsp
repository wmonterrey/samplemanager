  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <title><spring:message code="title" /> | <spring:message code="heading" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="x-ua-compatible" content="IE=edge,chrome=1">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
  <spring:url value="/resources/img/favicon.png" var="favicon" />
  <link rel="shortcut icon" href="${favicon}"/>

  <!-- Icon fonts -->
  <spring:url value="/resources/vendor/fonts/fontawesome.css" var="fontawesome" />
  <link rel="stylesheet" href="${fontawesome}">
  
  <spring:url value="/resources/vendor/fonts/ionicons.css" var="ionic" />
  <link rel="stylesheet" href="${ionic}">
  
  <spring:url value="/resources/vendor/fonts/linearicons.css" var="linearicons" />
  <link rel="stylesheet" href="${linearicons}">
  
  <spring:url value="/resources/vendor/fonts/open-iconic.css" var="openiconic" />
  <link rel="stylesheet" href="${openiconic}">
  
  <spring:url value="/resources/vendor/fonts/pe-icon-7-stroke.css" var="peicon7" />
  <link rel="stylesheet" href="${peicon7}">


  <!-- Core stylesheets -->
  <spring:url value="/resources/vendor/css/rtl/bootstrap.css" var="themebootstrap" />
  <link rel="stylesheet" href="${themebootstrap}" class="theme-settings-bootstrap-css">
  <spring:url value="/resources/vendor/css/rtl/appwork.css" var="themeappwork" />
  <link rel="stylesheet" href="${themeappwork}" class="theme-settings-appwork-css">
    <spring:url value="/resources/vendor/css/rtl/theme-shadow.css" var="themeshadow" />
  <link rel="stylesheet" href="${themeshadow}" class="theme-settings-theme-css">
  <spring:url value="/resources/vendor/css/rtl/colors.css" var="colors" />
  <link rel="stylesheet" href="${colors}" class="theme-settings-colors-css">
  <spring:url value="/resources/vendor/css/rtl/uikit.css" var="uikit" />
  <link rel="stylesheet" href="${uikit}">
  <spring:url value="/resources/vendor/libs/toastr/toastr.css" var="toastcss" />
	<link href="${toastcss}" rel="stylesheet" type="text/css"/>
  <spring:url value="/resources/vendor/css/demo.css" var="demo" />
  <link rel="stylesheet" href="${demo}">
  
  <!-- Load polyfills -->
  <spring:url value="/resources/vendor/js/polyfills.js" var="Polyfills" />
  <script src="${Polyfills}" type="text/javascript"></script>
  <script>document['documentMode']===10&&document.write('<script src="https://polyfill.io/v3/polyfill.min.js?features=Intl.~locale.en"><\/script>')</script>

  <spring:url value="/resources/vendor/js/material-ripple.js" var="MaterialRipple" />
  <script src="${MaterialRipple}" type="text/javascript"></script>
  <spring:url value="/resources/vendor/js/layout-helpers.js" var="LayoutHelpers" />
  <script src="${LayoutHelpers}" type="text/javascript"></script>
  
  <!-- Lenguaje -->
<c:choose>
<c:when test="${cookie.eSampleManagerLang.value == null}">
	<c:set var="lenguaje" value="en"/>
</c:when>
<c:otherwise>
	<c:set var="lenguaje" value="${cookie.eSampleManagerLang.value}"/>
</c:otherwise>
</c:choose>
	  

  <!-- Theme settings -->
  <!-- This file MUST be included after core stylesheets and layout-helpers.js in the <head> section -->
  <spring:url value="/resources/vendor/js/theme-settings-{language}.js" var="ThemeSettings">
  	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${ThemeSettings}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendor/css/rtl/" var="pathCss" />
  <spring:url value="/resources/vendor/css/rtl/" var="pathThemes" />
  
  <script>
    window.themeSettings = new ThemeSettings({
      cssPath: '${pathCss}',
      themesPath: '${pathThemes}'
    });
  </script>

  <!-- Core scripts -->
  <spring:url value="/resources/vendor/js/pace.js" var="Pace" />
  <script src="${Pace}" type="text/javascript"></script>
  <spring:url value="/resources/vendor/js/jquery.min.js" var="jQuery" />
  <script src="${jQuery}" type="text/javascript"></script>

  <!-- Libs -->
  <spring:url value="/resources/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" var="ScrollBarCss" />
  <link rel="stylesheet" href="${ScrollBarCss}">
  <spring:url value="/resources/vendor/libs/spinkit/spinkit.css" var="SpinKitCss" />
  <link rel="stylesheet" href="${SpinKitCss}">
  
  <spring:url value="/resources/vendor/libs/datatables/datatables.css" var="datatablesCss" />
  <link rel="stylesheet" href="${datatablesCss}">
  
  <spring:url value="/resources/vendor/libs/datatables/responsive.dataTables.min.css" var="datatablesRespCss" />
  <link rel="stylesheet" href="${datatablesRespCss}">
  
  <spring:url value="/resources/vendor/libs/select2/select2.css" var="select2css" />
  <link rel="stylesheet" href="${select2css}">
  <spring:url value="/resources/vendor/libs/swiper/swiper.css" var="swipercss" />
  <link rel="stylesheet" href="${swipercss}">
    <style>
    .swiper-container .swiper-slide {
      padding: 0rem 0;
      text-align: center;
      font-size: 1 rem;
      background: #00000;
    }
    .datatablediv{
    	box-sizing: border-box;
    	padding: 30px;
    }
  </style>
  
  <spring:url value="/resources/vendor/libs/nouislider/nouislider.css" var="nouislidercss" />
  <link rel="stylesheet" href="${nouislidercss}">
  
  <spring:url value="/resources/vendor/libs/sweetalert2/sweetalert2.css" var="sweetalert2" />
  <link rel="stylesheet" href="${sweetalert2}">
  
  <spring:url value="/resources/vendor/libs/bootstrap-datepicker/bootstrap-datepicker.css" var="datepickerCSS" />
  <link rel="stylesheet" href="${datepickerCSS}">
  
  
  <!-- Page -->
  <spring:url value="/resources/vendor/css/pages/authentication.css" var="Authentication" />
  <link rel="stylesheet" href="${Authentication}">
  
