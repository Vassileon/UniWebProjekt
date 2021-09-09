package de.hsrm.mi.web.projekt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Bean
    PasswordEncoder passwordEncoder() { // @Bean -> Encoder woanders per @Autowired abrufbar
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authmanagerbuilder) throws Exception {
        PasswordEncoder pwenc = passwordEncoder(); // Injection "in sich selbst" geht leider nicht
        authmanagerbuilder.inMemoryAuthentication() // "in memory"-Benutzerdatenbank anlegen
                .withUser("friedfert").password(pwenc.encode("dingdong")) // Passwörter nicht im Klartext speichern
                .roles("GUCKER").and() // nächster Eintrag
                .withUser("joghurta").password(pwenc.encode("geheim123")).roles("PHOTOGRAPH");
    }

}
