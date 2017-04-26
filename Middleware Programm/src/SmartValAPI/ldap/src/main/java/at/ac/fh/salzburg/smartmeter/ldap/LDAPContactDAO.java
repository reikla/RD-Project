package at.ac.fh.salzburg.smartmeter.ldap;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.directory.Attributes;
import java.util.List;

/**
 * Created by wiela on 25.04.2017.
 */
public class LDAPContactDAO implements ContactDAO{
    private LdapTemplate ldapTemplate;
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public List getAllContactNames() {
        return ldapTemplate.search("", "(objectclass=person)",
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        try {
                            return attrs.get("cn").get();
                        } catch (javax.naming.NamingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    @Override
    public List getContactDetails(String commonName, String lastName) {
        return null;
    }

    @Override
    public void insertContact(ContactDTO contactDTO) {

    }

    @Override
    public void updateContact(ContactDTO contactDTO) {

    }

    @Override
    public void deleteContact(ContactDTO contactDTO) {

    }

}
