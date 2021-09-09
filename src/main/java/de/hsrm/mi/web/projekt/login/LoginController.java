package de.hsrm.mi.web.projekt.login;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.benutzer.BenutzerCheck;

@Controller
@SessionAttributes("loggedinusername")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/login")
	public String login(Model model) {
		LoginValues loginValues = new LoginValues();
		model.addAttribute("loginValues", loginValues);
		return "login";
	}

	@PostMapping("/login")
	public String loginPost(
		@ModelAttribute LoginValues loginValues, 
		Model model,
		HttpSession session
	){
		BenutzerCheck bc = new BenutzerCheck();
		if(bc.loginPrufen(loginValues.getUsername(), loginValues.getPassword())){
			model.addAttribute("loggedinusername", loginValues.getUsername());
			loginValues.setPassword("");

			return "redirect:/sichtung/meine";
		}else{
			model.addAttribute("passwordFail", "Da ich hier auf sicherheit scheiße sag ich es ihnen einfach das passwort zu "+ loginValues.getUsername()+ " lautet "+ bc.getPasswort(loginValues.getUsername()));
			model.addAttribute("loggedinusername", "");
			logger.error("Achtung: Das Passwort zum Username:{} ist {}", loginValues.getUsername(), loginValues.getPassword());
			return "login";
		}
		
	}

}