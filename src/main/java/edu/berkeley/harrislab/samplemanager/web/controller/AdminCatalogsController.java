package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.language.DatabaseDrivenMessageSource;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.VisitsService;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/catalogs/*")
public class AdminCatalogsController {
	private static final Logger logger = LoggerFactory.getLogger(AdminCatalogsController.class);
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	
	@Resource(name="messageSource")
	private DatabaseDrivenMessageSource messageSource;

	
	@Resource(name="visitsService")
	private VisitsService visitsService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException { 	
    	logger.debug("Mostrando registros en JSP");
    	List<MessageResource> entidades = messageResourceService.getCatalogos();
    	model.addAttribute("entidades", entidades);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"admincatalogspage");
    	return "admin/catalogs/list";
	}
	
    
    /**
     * Custom handler for displaying a entidad.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{messageKey}/")
    public ModelAndView showEntity(@PathVariable("messageKey") String messageKey) {
    	ModelAndView mav;
    	MessageResource entidad = messageResourceService.getMensaje(messageKey);
        if(entidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/catalogs/viewForm");
            List<AuditTrail> bitacoraEntidad = auditTrailService.getBitacora(messageKey);
            List<MessageResource> opciones = messageResourceService.getCatalogoTodos(messageKey);
            mav.addObject("entidad",entidad);
            mav.addObject("opciones",opciones);
            mav.addObject("bitacora",bitacoraEntidad);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminviewcatalogpage");
        }
        return mav;
    }

    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/disableEntity/{messageKey}/")
    public String disableEntity(@PathVariable("messageKey") String messageKey, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	MessageResource entidad = this.messageResourceService.getMensaje(messageKey);
    	if(entidad!=null){
    		entidad.setPasive('1');
    		this.messageResourceService.saveMensaje(entidad);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getMessageKey());
    		redirecTo = "redirect:/admin/catalogs/"+entidad.getCatRoot()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for enabling.
     *
     * @param ident the ID to enable
     * @param redirectAttributes
     * @return a String
     */
    @RequestMapping("/enableEntity/{messageKey}/")
    public String enableEntity(@PathVariable("messageKey") String messageKey, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	MessageResource entidad = this.messageResourceService.getMensaje(messageKey);
    	if(entidad!=null){
    		entidad.setPasive('0');
    		this.messageResourceService.saveMensaje(entidad);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getMessageKey());
    		redirecTo = "redirect:/admin/catalogs/"+entidad.getCatRoot()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
	/**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{messageKey}/", method = RequestMethod.GET)
	public String initEnterForm(@PathVariable("messageKey") String messageKey, Model model) {
    	MessageResource entidadEditar = this.messageResourceService.getMensaje(messageKey);
		if(entidadEditar!=null){
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"admineditcatalogpage");
			return "admin/catalogs/enterForm";
		}
		else{
			return "403";
		}
	}
    
    
    /**
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processEntity( @RequestParam(value="messageKey", required=true) String messageKey
	        , @RequestParam( value="catRoot", required=true ) String catRoot
	        , @RequestParam( value="catKey", required=true) String catKey
	        , @RequestParam( value="spanish", required=true) String spanish
	        , @RequestParam( value="english", required=true) String english
	        , @RequestParam( value="order", required=true) int order
	        )
	{
    	try{
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        	MessageResource entidad = new MessageResource();
        	entidad.setCatKey(catKey);
        	entidad.setCatRoot(catRoot);
        	entidad.setEnglish(english);
        	entidad.setSpanish(spanish);
        	entidad.setOrder(order);
        	entidad.setMessageKey(messageKey);
        	entidad.setRecordUser(usuarioActual.getUsername());
        	entidad.setRecordDate(new Date());
        	entidad.setRecordIp(wad.getRemoteAddress());
			this.messageResourceService.saveMensaje(entidad);
			messageSource.reload();
			return createJsonResponse(entidad);
    	}
		catch (DataIntegrityViolationException e){
			String message = e.getMostSpecificCause().getMessage();
			Gson gson = new Gson();
		    String json = gson.toJson(message);
		    return createJsonResponse(json);
		}
		catch(Exception e){
			Gson gson = new Gson();
		    String json = gson.toJson(e.toString());
		    return createJsonResponse(json);
		}
    	
	}
    
    
    private ResponseEntity<String> createJsonResponse( Object o )
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    Gson gson = new Gson();
	    String json = gson.toJson(o);
	    return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
	}
	
	
}
