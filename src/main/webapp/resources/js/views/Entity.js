var ProcessEntity = function () {
	
return {
  //main function to initiate the module
  init: function (parametros) {	
  
  var isRtl = $('html').attr('dir') === 'rtl';
  
  $('#enrollmentDate,#labReceiptDate,#storageDate').datepicker({
	 orientation: isRtl ? 'auto right' : 'auto left',
	 format:'yyyy-mm-dd',
	 autoclose: true,
	 language:parametros.lenguaje
  });

  // Select2
  $(function() {
	    $('.select2-control').each(function() {
	      $(this)
	        .wrap('<div class="position-relative"></div>')
	        .select2({
	          placeholder: parametros.seleccionar,
	          dropdownParent: $(this).parent(),
	          language:parametros.lenguaje
	        });
	    })
	  });
	  
  $.validator.setDefaults( {
    submitHandler: function () {
      processEntity();
    }
  } );
  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
	}, "Invalid");
  $( '#edit-form' ).validate( {
    rules: {
      'labId': {
          required: true
      },
      'labEmail': {
          email: true
      },
      'id': {
          required: true
      },
      'lab': {
          required: true
      },
      'equip': {
          required: true
      },
      'equipType': {
          required: true
      },
      'rack': {
          required: true
      },
      'rows': {
          required: true
      },
      'columns': {
          required: true
      },
      'messageKey': {
          required: true
      },
      'catKey': {
          required: true
      },
      'spanish': {
          required: true
      },
      'english': {
          required: true
      },
      'order': {
          required: true,
          min:1,
          max:99
      },
      'subjectId': {
          required: true
      },
      'studyId': {
          required: true
      },
      'specimenId': {
          required: true
      },
      'specimenType': {
          required: true
      },
      'labReceiptDate': {
          required: true
      },
      'volume': {
          required: false,
          min:0,
          max:9999
      },
      'inStorage': {
          required: true
      },
      'storageDate': {
          required: true
      }
      
    },
    // Errors
    //

    errorPlacement: function errorPlacement(error, element) {
      var $parent = $(element).parents('.form-group');

      // Do not duplicate errors
      if ($parent.find('.jquery-validation-error').length) { return; }

      $parent.append(
        error.addClass('jquery-validation-error small form-text invalid-feedback')
      );
    },
    highlight: function(element) {
      var $el = $(element);
      var $parent = $el.parents('.form-group');

      $el.addClass('is-invalid');

      // Select2 and Tagsinput
      if ($el.hasClass('select2-hidden-accessible') || $el.attr('data-role') === 'tagsinput') {
        $el.parent().addClass('is-invalid');
      }
    },
    unhighlight: function(element) {
      $(element).parents('.form-group').find('.is-invalid').removeClass('is-invalid');
    }
  });
  
  function processEntity(){
	  $.blockUI({ message: parametros.waitmessage });
	    $.post( parametros.saveUrl
	            , $( '#edit-form' ).serialize()
	            , function( data )
	            {
	    			entidad = JSON.parse(data);
	    			if (entidad.recordUser === undefined) {
	    				data = data.replace(/u0027/g,"");
	    				toastr.error(data, parametros.errormessage, {
	    					    closeButton: true,
	    					    progressBar: true,
	    					  });
	    				$.unblockUI();
					}
					else{
						$.blockUI({ message: parametros.successmessage });
						setTimeout(function() { 
				            $.unblockUI({ 
				                onUnblock: function(){ window.location.href = parametros.listUrl; } 
				            }); 
				        }, 1000); 
					}
	            }
	            , 'text' )
		  		.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		    		alert( "error:" + errorThrown);
		    		$.unblockUI();
		  		});
	}
  
  
  $(document).on('keypress','form input',function(event)
  		{                
  		    event.stopImmediatePropagation();
  		    if( event.which == 13 )
  		    {
  		        event.preventDefault();
  		        var $input = $('form input');
  		        if( $(this).is( $input.last() ) )
  		        {
  		            //Time to submit the form!!!!
  		            //alert( 'Hooray .....' );
  		        }
  		        else
  		        {
  		            $input.eq( $input.index( this ) + 1 ).focus();
  		        }
  		    }
  		});
  }
 };
}();
