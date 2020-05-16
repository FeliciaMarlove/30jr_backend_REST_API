package be.iramps.florencemary._30jd_back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.concurrent.TimeUnit;

import static be.iramps.florencemary._30jd_back.security.UserRoles.*;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Définit les accès et les autorisations
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                //rend le cookie X-XSRF inaccessible depuis le côté client (scripts)
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and() //TODO : décommenter à la fin des tests
                /*
                /note/ si l'API est consommée par un service sans interaction humaine . csrf().disable()
                 */
                    .disable() //TODO : supprimer à la fin des tests
                .authorizeRequests()
                    //.antMatchers("/dev_test.html") //TODO : user/connect & user/signup no AuthN
                    //.permitAll()
                    .antMatchers("/api/user/**", "/api/userpath/**")
                    .hasRole(USER.name())
                    .antMatchers("/admin/**")
                    .hasRole(ADMIN.name())
                    /*
                    /note/ gérer les autorisations en fonction du verbe http :
                    antMatchers(HttpMethod.VERB, "/pattern/**").hasAuthority(UserPermission)
                     */
                    .anyRequest()
                    .authenticated()
                .and()
                //form based authN (forms -> full control), username/password, can logout, https recommended, most apps standard, cookie SESSIONID, default expires after 30 minutes of inactivity (change: .and().rememberMe())
                /*
                default for SESSIONID : "in memory database": cookie is lost every time the server is restarted
                possibility to store in a real DB (like Postgres)
                 */
                .formLogin()
                //.loginPage("url").permitAll().defaultSucessUrl("url to page after succesful login") //internal only? view! (not endpoint)
                //works for separate F-E?
                    //.passwordParameter("fieldname") //ref nom "name" du champ html default: "password"
                    //.usernameParameter("fieldname") //idem default: "username"
                .and()
                //good practice: tickbox to ask if user wants to be remembered (desktop apps) -> "remember-me" cookie in memory
                //defaults to two weeks; contains username, expiration time and md5 hash of these two values
                //to use own DB : tokenRepository
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(93))
                    //works for separate F-E?
                   // .rememberMeParameter("field name") //ref nom "name" du champ html default : "remember-me"
                .and()
                .logout()
                    //logout must be a POST to prevent CSRF attacks (GET if CSRF in disabled)
                    // url/logout = triggers logout
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                    //.logoutSuccessUrl("/dev_test.html")
                ;
    }

    /**
     * Retrouve les utilisateurs depuis la base de données
     * @return
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails annaSmithUser =
        User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
                .authorities(USER.getGrantedAuthorities()) //ROLE_USER
                .build()
                ;

        UserDetails lindaUser =
        User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.getGrantedAuthorities()) //ROLE_ADMIN
                .build()
        ;

        return new InMemoryUserDetailsManager(
          annaSmithUser,
            lindaUser
        );
    }
}
