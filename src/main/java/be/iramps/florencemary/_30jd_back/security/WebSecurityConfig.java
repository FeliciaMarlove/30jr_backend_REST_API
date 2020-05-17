package be.iramps.florencemary._30jd_back.security;

import be.iramps.florencemary._30jd_back.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static be.iramps.florencemary._30jd_back.security.UserRoles.*;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    /**
     * Définit les accès et les autorisations
     * @param http
     * @throws Exception
     */
    /*
    Notes personnelles
    .csrf : rend le cookie X-XSRF inacessible depuis le côté client (par des scripts côté client)
    /note/ si l'API est consommée par un service sans interaction humaine : .csrf().disable()
    /note/ pour gérer les autorisations en fonction du verbe http : antMatchers(HttpMethod.VERB, "/pattern/**").hasAuthority(UserPermission)
    form based authN (forms -> full control), username/password, can logout, https recommended, most apps standard, cookie SESSIONID, default expires after 30 minutes of inactivity (change: .and().rememberMe())
      default for SESSIONID : "in memory database": cookie is lost every time the server is restarted possibility to store in a real DB (like Postgres)
      FORM LOGIN
      .loginPage("url").permitAll().defaultSucessUrl("url to page after succesful login") //internal only? view! (not endpoint)
            .passwordParameter("fieldname") //ref nom "name" du champ html default: "password"
            .usernameParameter("fieldname") //idem default: "username"
      REMEMBER ME
      good practice: tickbox to ask if user wants to be remembered (desktop apps) -> "remember-me" cookie in memory
        defaults to two weeks; contains username, expiration time and md5 hash of these two values
        to use own DB : tokenRepository
         .rememberMeParameter("field name") //ref nom "name" du champ html default : "remember-me"
      LOGOUT
      logout must be a POST to prevent CSRF attacks (GET if CSRF in disabled)
        url/logout = triggers logout
        .logoutSuccessUrl("/dev_test.html")
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and() //TODO : décommenter à la fin des tests
                    .disable() //TODO : supprimer à la fin des tests
                .authorizeRequests()
                    .antMatchers("/connect*", "/signup*").permitAll()
                    .antMatchers("/api/user/**", "/api/userpath/**")
                    .hasRole(USER.name())
                    .antMatchers("/admin/**")
                    .hasRole(ADMIN.name())
                    .anyRequest()
                    .authenticated()
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(93))
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                ;
    }

    /**
     * wire
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * Retrouve les utilisateurs depuis la base de données
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
