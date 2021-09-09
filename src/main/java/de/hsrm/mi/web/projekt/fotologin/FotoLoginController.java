package de.hsrm.mi.web.projekt.fotologin;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.benutzer.BenutzerCheck;
import de.hsrm.mi.web.projekt.login.LoginValues;
 
@Controller
@SessionAttributes("loggedinusername")
public class FotoLoginController {
	Logger logger = LoggerFactory.getLogger(FotoLoginController.class);

	@GetMapping("/fotologin")
	public String login() {
		return "foto/login";
	}

	@PostMapping("/fotologin")
	public String loginPost(
		@ModelAttribute LoginValues loginValues, 
		Model model,
		HttpSession session,
		@RequestParam String username, 
		@RequestParam String password
	){
		BenutzerCheck bc = new BenutzerCheck();
		if(bc.loginPrufen(username, password)){
			model.addAttribute("loggedinusername", username);
			return "redirect:/foto";
		}else{
			model.addAttribute("passwordFail", "Da ich hier auf sicherheit scheiße sag ich es ihnen einfach das passwort zu "+ username+ " lautet "+ bc.getPasswort(password));
			model.addAttribute("loggedinusername", "");
			logger.error("Achtung: Das Passwort zum Username:{} ist {}", username, password);
			return "foto/login";
		}	
	}
}