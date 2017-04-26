package at.ac.fh.salzburg.smartmeter.ldap;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * Created by wiela on 25.04.2017.
 */
public class ContactAttributeMapper implements AttributesMapper{

    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        ContactDTO contactDTO = new ContactDTO();
        String commonName = null;
        try {
            commonName = (String)attributes.get("cn").get();
        } catch (javax.naming.NamingException e) {
            e.printStackTrace();
        }
        if(commonName != null)
            contactDTO.setCommonName(commonName);
        String lastName = null;
        try {
            lastName = (String)attributes.get("sn").get();
        } catch (javax.naming.NamingException e) {
            e.printStackTrace();
        }
        if(lastName != null)
            contactDTO.setLastName(lastName);
        Attribute description = attributes.get("description");
        if(description != null)
            try {
                contactDTO.setDescription((String)description.get());
            } catch (javax.naming.NamingException e) {
                e.printStackTrace();
            }
        return contactDTO;
    }

}


