var rangeLocale = {
     'Hoy': [moment(), moment()],
     'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
     'Ultimos 7 Dias': [moment().subtract(6, 'days'), moment()],
     'Ultimos 30 Dias': [moment().subtract(29, 'days'), moment()],
     'Este Mes': [moment().startOf('month'), moment().endOf('month')],
     'Mes Pasado': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
   }