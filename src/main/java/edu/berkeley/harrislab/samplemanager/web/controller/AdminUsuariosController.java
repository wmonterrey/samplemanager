package edu.berkeley.harrislab.samplemanager.web.controller;

import com.google.gson.Gson;

import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.AuditTrailService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.VisitsService;
import edu.berkeley.harrislab.samplemanager.users.model.Authority;
import edu.berkeley.harrislab.samplemanager.users.model.AuthorityId;
import edu.berkeley.harrislab.samplemanager.users.model.Rol;
import edu.berkeley.harrislab.samplemanager.users.model.UserAccess;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Controlador web de peticiones relacionadas a usuarios
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/users/*")
public class AdminUsuariosController {
	private static final Logger logger = LoggerFactory.getLogger(AdminUsuariosController.class);
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;

	
	@Resource(name="visitsService")
	private VisitsService visitsService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerUsuarios(Model model) throws ParseException { 	
    	logger.debug("Mostrando Usuarios en JSP");
    	List<UserSistema> usuarios = usuarioService.getUsers();
    	model.addAttribute("usuarios", usuarios);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminuserspage");
    	return "admin/users/list";
	}
	
	
	/**
     * Custom handler for adding an user.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newUser/", method = RequestMethod.GET)
	public String initAddUserForm(Model model) {
    	List<Rol> roles = usuarioService.getRoles();
	    model.addAttribute("roles", roles);
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminnewuserpage");
		return "admin/users/newForm";
	}
    
    
    
    /**
     * Custom handler for displaying an user.
     *
     * @param username the ID of the user to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{username}/")
    public ModelAndView showUser(@PathVariable("username") String username) {
    	ModelAndView mav;
    	UserSistema user = this.usuarioService.getUser(username);
        if(user==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/users/viewForm");
            List<UserAccess> accesosUsuario = usuarioService.getUserAccess(username);
            List<AuditTrail> bitacoraUsuario = auditTrailService.getBitacora(username);
            mav.addObject("user",user);
            mav.addObject("accesses",accesosUsuario);
            mav.addObject("bitacora",bitacoraUsuario);
            List<Authority> rolesusuario = this.usuarioService.getRolesUsuarioTodos(username);
            mav.addObject("rolesusuario", rolesusuario);
            List<Rol> roles = usuarioService.getRolesNoTieneUsuario(username);
            mav.addObject("roles", roles);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminviewuserpage");
        }
        return mav;
    }
	
	/**
     * Custom handler for editing an user.
     * @param model Modelo enlazado a la vista
     * @param username the ID of the user to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editUser/{username}/", method = RequestMethod.GET)
	public String initUpdateUserForm(@PathVariable("username") String username, Model model) {
		UserSistema usuarioEditar = this.usuarioService.getUser(username);
		if(usuarioEditar!=null){
			model.addAttribute("user",usuarioEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminedituserpage");
			return "admin/users/editForm";
		}
		else{
			return "403";
		}
	}
    
    /**
     * Custom handler for saving an user.
     * 
     * @param userName nombre de usuario
     * @param completeName nombre completo de usuario
     * @param confirmPassword Contrasena confirmar
     * @param password Contrasena
     * @param email Correo
     * @param authorities Roles
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveUser/", method=RequestMethod.POST)
	public ResponseEntity<String> processUpdateUserForm( @RequestParam(value="username", required=true ) String userName
	        , @RequestParam( value="completeName", required=true ) String completeName
	        , @RequestParam( value="confirm_password", required=false ) String confirmPassword
	        , @RequestParam( value="password", required=false, defaultValue="" ) String password
	        , @RequestParam( value="email", required=true, defaultValue="" ) String email
	        , @RequestParam( value="roles", required=false, defaultValue="") List<String> authorities
	        , @RequestParam( value="checkChgPass", required=false, defaultValue="") String checkChgPass
	        )
	{
    	Gson gson = new Gson();
    	try{
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
	    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			UserSistema user = this.usuarioService.getUser(userName);
	    	if (user==null){
	    		user = new UserSistema();
	    		user.setUsername(userName);
	    		user.setCompleteName(completeName);
	    		user.setEmail(email);
	    		user.setCreated(new Date());
	    		user.setCreatedBy(usuarioActual.getUsername());
	    		user.setModified(new Date());
	    		user.setModifiedBy(usuarioActual.getUsername());
	    		user.setLastCredentialChange(new Date());
	    		if(checkChgPass.equals("checkChgPass")) {
					user.setChangePasswordNextLogin(true);
				}
				else {
					user.setChangePasswordNextLogin(false);
				}
	    		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
	    		String encodedPass = encoder.encode(password);
	    		user.setPassword(encodedPass);
	    		this.usuarioService.saveUser(user);
	    		for(String a:authorities){
	    			Authority auth = new Authority();
	    			auth.setAuthId(new AuthorityId(userName,a));
					auth.setRecordUser(usuarioActual.getUsername());
					auth.setRecordDate(new Date());
					auth.setRecordIp(wad.getRemoteAddress());
					this.usuarioService.saveRoleUser(auth);
				}
	    	}
	    	else{
	    		return new ResponseEntity<String>( gson.toJson("Duplicado"), HttpStatus.CREATED);
	    	}
			
			return createJsonResponse(user);
    	}
    	catch(Exception e){
    	    String json = gson.toJson(e.toString());
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
    	}
	}
    
    
    /**
     * Custom handler for saving an user.
     * 
     * @param userName nombre de usuario
     * @param completeName nombre completo de usuario
     * @param email Correo
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEditedUser/", method=RequestMethod.POST)
	public ResponseEntity<String> processEditUserForm( @RequestParam(value="username", required=true ) String userName
	        , @RequestParam( value="completeName", required=true ) String completeName
	        , @RequestParam( value="email", required=true, defaultValue="" ) String email
	        , @RequestParam( value="region", required=true, defaultValue="" ) String region
	        )
	{
    	Gson gson = new Gson();
    	try{
	    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			UserSistema user = this.usuarioService.getUser(userName);
	    	if (user!=null){
	    		user.setCompleteName(completeName);
	    		user.setEmail(email);
	    		user.setModified(new Date());
	    		user.setModifiedBy(usuarioActual.getUsername());
	    		this.usuarioService.saveUser(user);
	    	}
	    	else{
	    		return new ResponseEntity<String>( gson.toJson("No existe"), HttpStatus.CREATED);
	    	}
			return createJsonResponse(user);
    	}
    	catch(Exception e){
    	    String json = gson.toJson(e.toString());
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
    	}
	}
    
    /**
     * Custom handler for enabling/disabling an user.
     *
     * @param username the ID of the user to enable
     * @param accion Habilitar o deshabilitar
     * @param redirectAttributes Regresa nombre de usuario
     * @return a String
     */
    @RequestMapping("/habdes/{accion}/{username}/")
    public String enableUser(@PathVariable("username") String username, 
    		@PathVariable("accion") String accion, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	boolean hab;
    	if (accion.matches("enable1")){
    		redirecTo = "redirect:/admin/users/";
    		hab = true;
    		redirectAttributes.addFlashAttribute("usuarioHabilitado", true);
        }
        else if (accion.matches("enable2")){
        	redirecTo = "redirect:/admin/users/{username}/";
    		hab = true;
    		redirectAttributes.addFlashAttribute("usuarioHabilitado", true);
        }
        else if(accion.matches("disable1")){
        	redirecTo = "redirect:/admin/users/";
    		hab = false;
    		redirectAttributes.addFlashAttribute("usuarioDeshabilitado", true);
        }
        else if(accion.matches("disable2")){
        	redirecTo = "redirect:/admin/users/{username}/";
    		hab = false;
    		redirectAttributes.addFlashAttribute("usuarioDeshabilitado", true);
        }
        else{
        	return redirecTo;
        }
    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserSistema user = this.usuarioService.getUser(username);
    	if(user!=null){
    		user.setModified(new Date());
    		user.setModifiedBy(usuarioActual.getUsername());
    		user.setEnabled(hab);
    		this.usuarioService.saveUser(user);
    		redirectAttributes.addFlashAttribute("nombreUsuario", username);
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for disabling a rol.
     *
     * @param username the ID of the user
     * @param rol Rol a deshabilitar
     * @param redirectAttributes Regresa nombre de rol
     * @return a String
     */
    @RequestMapping("/disableRol/{username}/{rol}/")
    public String disableRol(@PathVariable("username") String username, 
    		@PathVariable("rol") String rol, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Authority rolUser = this.usuarioService.getRolUsuario(username, rol);
		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    	if(rolUser!=null){
    		rolUser.setPasive('1');
    		rolUser.setRecordIp(wad.getRemoteAddress());
    		MessageResource mr = this.messageResourceService.getMensaje(rolUser.getAuthId().getAuthority());
    		String nombreRol = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    		this.usuarioService.saveRoleUser(rolUser);
    		redirecTo = "redirect:/admin/users/{username}/";
    		redirectAttributes.addFlashAttribute("rolDeshabilitado", true);
    		redirectAttributes.addFlashAttribute("nombreRol", nombreRol);
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    


    /**
     * Custom handler for enabling a rol.
     *
     * @param username the ID of the user
     * @param rol Rol a deshabilitar
     * @param redirectAttributes Regresa nombre de rol
     * @return a String
     */
    @RequestMapping("/enableRol/{username}/{rol}/")
    public String enableRol(@PathVariable("username") String username, 
    		@PathVariable("rol") String rol, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Authority rolUser = this.usuarioService.getRolUsuario(username, rol);
		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    	if(rolUser!=null){
    		rolUser.setPasive('0');
    		rolUser.setRecordIp(wad.getRemoteAddress());
    		MessageResource mr = this.messageResourceService.getMensaje(rolUser.getAuthId().getAuthority());
    		String nombreRol = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    		this.usuarioService.saveRoleUser(rolUser);
    		redirecTo = "redirect:/admin/users/{username}/";
    		redirectAttributes.addFlashAttribute("rolHabilitado", true);
    		redirectAttributes.addFlashAttribute("nombreRol", nombreRol);
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    
    /**
     * Custom handler for adding a rol.
     *
     * @param username the ID of the user
     * @param rol Rol a agregar
     * @param redirectAttributes Regresa nombre de rol
     * @return a String
     */
    @RequestMapping("/addRol/{username}/{rol}/")
    public String addRol(@PathVariable("username") String username, 
    		@PathVariable("rol") String rol, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		Authority rolUser = this.usuarioService.getRolUsuario(username, rol);
		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    	if(rolUser==null){
    		rolUser = new Authority();
    		rolUser.setAuthId(new AuthorityId(username,rol));
    		rolUser.setRecordUser(usuarioActual.getUsername());
    		rolUser.setRecordDate(new Date());
    		rolUser.setRecordIp(wad.getRemoteAddress());
    		MessageResource mr = this.messageResourceService.getMensaje(rolUser.getAuthId().getAuthority());
    		String nombreRol = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    		this.usuarioService.saveRoleUser(rolUser);
    		redirecTo = "redirect:/admin/users/{username}/";
    		redirectAttributes.addFlashAttribute("rolAgregado", true);
    		redirectAttributes.addFlashAttribute("nombreRol", nombreRol);
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for locking/unlocking an user.
     *
     * @param username the ID of the user to lock/unlock
     * @param accion lock/unlock
     * @param redirectAttributes Regresa nombre de usuario
     * @return a String
     */
    @RequestMapping("/lockunl/{accion}/{username}/")
    public String lockUnlockUser(@PathVariable("username") String username, 
    		@PathVariable("accion") String accion, RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	boolean lock;
    	if (accion.matches("lock1")){
    		redirecTo = "redirect:/admin/users/";
    		lock = true;
    		redirectAttributes.addFlashAttribute("usuarioBloqueado", true);
        }
        else if (accion.matches("lock2")){
        	redirecTo = "redirect:/admin/users/{username}/";
    		lock = true;
    		redirectAttributes.addFlashAttribute("usuarioBloqueado", true);
        }
        else if(accion.matches("unlock1")){
        	redirecTo = "redirect:/admin/users/";
    		lock = false;
    		redirectAttributes.addFlashAttribute("usuarioNoBloqueado", true);
        }
        else if(accion.matches("unlock2")){
        	redirecTo = "redirect:/admin/users/{username}/";
    		lock = false;
    		redirectAttributes.addFlashAttribute("usuarioNoBloqueado", true);
        }
        else{
        	return redirecTo;
        }
    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserSistema user = this.usuarioService.getUser(username);
    	if(user!=null){
    		user.setModified(new Date());
    		user.setModifiedBy(usuarioActual.getUsername());
    		user.setAccountNonLocked(!lock);
    		this.usuarioService.saveUser(user);
    		redirectAttributes.addFlashAttribute("nombreUsuario", username);
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for changing an user password.
     *
     * @param username the ID of the user to display
     * @param model Modelo
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/chgpass/{username}/", method = RequestMethod.GET)
	public String initChangePassForm(@PathVariable("username") String username, Model model) {
    	UserSistema usertoChange = this.usuarioService.getUser(username);
		if(usertoChange!=null){
			model.addAttribute("user",usertoChange);
			this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"adminuserpasspage");
			return "admin/users/passForm";
		}
		else{
			return "403";
		}
	}
    
    @RequestMapping( value="/chgPass/", method=RequestMethod.POST)
	public ResponseEntity<String> processChangePassForm( @RequestParam(value="username", required=true ) String userName
			, @RequestParam( value="password", required=true ) String password
			, @RequestParam( value="checkChgPass", required=false, defaultValue="") String checkChgPass
	        )
	{
    	UserSistema usuario = usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    	UserSistema user = usuarioService.getUser(userName);
    	try{
			user.setModifiedBy(usuario.getUsername());
			user.setModified(new Date());
			StandardPasswordEncoder encoder = new StandardPasswordEncoder();
			String encodedPass = encoder.encode(password);
			user.setPassword(encodedPass);
			user.setLastCredentialChange(new Date());
			user.setCredentialsNonExpired(true);
			if(checkChgPass.equals("checkChgPass")) {
				user.setChangePasswordNextLogin(true);
			}
			else {
				user.setChangePasswordNextLogin(false);
			}
			this.usuarioService.saveUser(user);
			return createJsonResponse(user);
    	}
    	catch(Exception e){
    		Gson gson = new Gson();
    	    String json = gson.toJson(e.toString());
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
    	}
	}
    
    
    /**
     * Custom handler for enabling an outdated password.
     *
     * @param username the ID of the user to unlock password
     * @param redirectAttributes Regresa nombre de usuario
     * @return a String
     */
    @RequestMapping("/enablepass/{username}/")
    public String unlockPass(@PathVariable("username") String username, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserSistema user = this.usuarioService.getUser(username);
    	if(user!=null){
    		user.setModified(new Date());
    		user.setModifiedBy(usuarioActual.getUsername());
    		user.setCredentialsNonExpired(true);
    		user.setLastCredentialChange(new Date());
    		this.usuarioService.saveUser(user);
    		redirecTo = "redirect:/admin/users/{username}/";
    		redirectAttributes.addFlashAttribute("passNoVencido", true);
    		redirectAttributes.addFlashAttribute("nombreUsuario", username);
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
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
