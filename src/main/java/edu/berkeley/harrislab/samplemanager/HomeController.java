package edu.berkeley.harrislab.samplemanager;

import java.util.Date;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.VisitsService;



/**
 * Controlador que provee los mapeos en la pagina Web para:
 * 
 * <ul>
 * <li>Pagina Principal
 * <li>Pagina de Ingreso
 * <li>Ingreso Fallido
 * <li>Pagina de Salida
 * <li>No autorizado
 * <li>No encontrado
 * <li>Reset contrasena
 * </ul>
 * 
 * @author William Aviles
 **/
@Controller
@RequestMapping("/*")
public class HomeController {

	@Resource(name="visitsService")
	private VisitsService visitsService;
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
    	try {
	    	logger.info("Sample management home page started..."); 
	    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"homepage");
    	}
    	catch(Exception e) {
    		logger.error(e.getLocalizedMessage());
    	}
    	return "home";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}
    
    @RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
    	model.addAttribute("error", "true");
		return "login";
	}
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value="/403", method = RequestMethod.GET)
	public String noAcceso() {
		return "403"; 
	}
	
	@RequestMapping(value="/404", method = RequestMethod.GET)
	public String noEncontrado() { 
		return "404";
	}	
    
	@RequestMapping( value="keepsession")
	public @ResponseBody String keepSession()
	{	
		return "OK";
	}

}
