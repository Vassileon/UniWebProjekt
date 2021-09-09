package de.hsrm.mi.web.projekt.validierung;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SiebzehnValidator implements ConstraintValidator<Siebzehnhaft, String> {

    @Override
    public void initialize(Siebzehnhaft siebzehn){

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern pattern = Pattern.compile("(17|Siebzehn|siebzehn|seventeen)");
        Matcher match = pattern.matcher(value); 
        return(match.find());
    }
    
}
