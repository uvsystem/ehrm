package com.unitedvision.sangihe.ehrm.manajemen;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.EntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;

@Controller
@RequestMapping("token")
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}")
	@ResponseBody
	public RestMessage create(@PathVariable String nip) throws ApplicationException, PersistenceException {
		tokenService.create(nip);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{kode}")
	@ResponseBody
	public RestMessage lock(@PathVariable String kode) throws ApplicationException, PersistenceException {
		tokenService.lock(kode);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{kode}")
	@ResponseBody
	public EntityRestMessage<Token> get(@PathVariable String kode) throws ApplicationException, PersistenceException {
		Token token = tokenService.get(kode);
		
		return EntityRestMessage.create(token);
	}
}
