package edu.berkeley.harrislab.samplemanager.web.controller;

import edu.berkeley.harrislab.samplemanager.domain.Equip;
import edu.berkeley.harrislab.samplemanager.domain.Subject;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.EquipService;
import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
import edu.berkeley.harrislab.samplemanager.service.SubjectService;
import edu.berkeley.harrislab.samplemanager.service.UsuarioService;
import edu.berkeley.harrislab.samplemanager.service.VisitsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/capture/storespecimens/*")
public class CaptureSpecimenStoragesController {
	private static final Logger logger = LoggerFactory.getLogger(CaptureSpecimenStoragesController.class);
	
	


	@Resource(name="usuarioService")
	private UsuarioService usuarioService;

	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="equipService")
	private EquipService equipService;

	@Resource(name="subjectService")
	private SubjectService subjectService;
	
	@Resource(name="visitsService")
	private VisitsService visitsService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException { 	
    	logger.debug("Mostrando registros en JSP");
    	List<MessageResource> types = this.messageResourceService.getCatalogo("CAT_SP_TYPE");
	    model.addAttribute("types",types);
	    List<MessageResource> volUnits = this.messageResourceService.getCatalogo("CAT_VOL_UNITS");
	    model.addAttribute("volUnits",volUnits);
	    List<MessageResource> conditions = this.messageResourceService.getCatalogo("CAT_SP_COND");
	    model.addAttribute("conditions",conditions);
	    List<Subject> subjects = this.subjectService.getActiveSubjects();
	    model.addAttribute("subjects",subjects);
	    List<Equip> equips = this.equipService.getActiveEquips();
	    model.addAttribute("equips",equips);
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturenewspecimenpage");
    	return "capture/storage/enterForm";
	}
	
	

	
}
