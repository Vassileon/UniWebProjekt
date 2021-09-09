package de.hsrm.mi.web.projekt.benutzer;


public class BenutzerCheck {
    
    public boolean loginPrufen(String loginname, String password){
        if(getPasswort(loginname).equals(password)){
            return true;
        }
        return false;
    }

    public String getPasswort(String loginname){
        String password;
        password = loginname + loginname.length();
        return password;
    }
}
