package edu.berkeley.harrislab.samplemanager.language;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.berkeley.harrislab.samplemanager.service.MessageResourceService;
/**
 * Servicio que provee mensajes a Spring desde la base de datos
 * 
 * @author William Aviles
 **/
@Service("messageSource")
public class DatabaseDrivenMessageSource extends DataBaseDrivenMessageSourceBase {
	
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;

	@Override
    protected Map<String, Map<String, String>> loadTexts() {
        Map<String, Map<String, String>> m = new HashMap<String, Map<String, String>>();
        List<MessageResource> texts = messageResourceService.loadAllMessages();
        for (MessageResource text : texts) {
        	Map<String, String> v = new HashMap<String, String>();
            v.put("en", text.getEnglish());
            v.put("es", text.getSpanish());
            m.put(text.getMessageKey(), v);
        }
        return m;
    }
}
