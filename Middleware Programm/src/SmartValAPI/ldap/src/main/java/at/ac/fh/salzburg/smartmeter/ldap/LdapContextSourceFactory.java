package at.ac.fh.salzburg.smartmeter.ldap;

import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Created by reimarklammer on 13.06.17.
 */
public class LdapContextSourceFactory {

    public static LdapContextSource getContextSource()
    {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUserDn("cn=Manager,dc=maxcrc,dc=com");
        contextSource.setPassword("secret");
        contextSource.setUrl("ldap://193.170.119.66:389/");
        contextSource.setBase("dc=maxcrc,dc=com");
        contextSource.setPooled(false);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

}
