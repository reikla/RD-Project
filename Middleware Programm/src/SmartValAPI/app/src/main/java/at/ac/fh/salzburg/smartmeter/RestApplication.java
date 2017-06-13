package at.ac.fh.salzburg.smartmeter;

import at.ac.fh.salzburg.smartmeter.ldap.LdapContextSourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import java.util.Arrays;

/**
 * Created by reimarklammer on 04.04.17.
 */
@SpringBootApplication
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Configuration
    @Order(2)
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable()
                    .antMatcher("**")
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .sessionManagement().disable();
        }
    }

    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/admin/**")
                    .hasRole("ADMINISTRATOR")
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and().sessionManagement().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.ldapAuthentication()
                    .groupRoleAttribute("cn")
                    .groupSearchBase("ou=Groups")
                    .groupSearchFilter("(memberUid={1})")
                    .rolePrefix("ROLE_")
                    .userSearchFilter("(uid={0})")
                    .contextSource(contextSource())
                    .passwordCompare()
                    .passwordEncoder(new PlaintextPasswordEncoder())
                    .passwordAttribute("userPassword");
        }
        @Bean
        public LdapContextSource contextSource() {
            return LdapContextSourceFactory.getContextSource();
        }


    }

}


