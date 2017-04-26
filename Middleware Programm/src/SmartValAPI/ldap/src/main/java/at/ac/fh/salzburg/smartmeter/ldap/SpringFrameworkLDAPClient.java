package at.ac.fh.salzburg.smartmeter.ldap;

import java.util.List;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import org.springframework.dao.DataAccessException;

public class SpringFrameworkLDAPClient {

    public static void main(String[] args) {
        try {
            
            XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("springldap.xml"));
            ContactDAO ldapContact = (LDAPContactDAO)beanFactory.getBean("ldapTemplate");

            List contactList = ldapContact.getContactDetails("Max","Mustermann");
            for( int i = 0 ; i < contactList.size(); i++){
                System.out.println("Contact Name " + contactList.get(i));
            }

        } catch (DataAccessException e) {
            System.out.println("Error occured " + e.getCause());
        }
    }
}
