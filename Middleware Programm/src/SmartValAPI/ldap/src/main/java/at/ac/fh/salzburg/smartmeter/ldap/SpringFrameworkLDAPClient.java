package at.ac.fh.salzburg.smartmeter.ldap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class SpringFrameworkLDAPClient {

    public static void main(String[] args) {

        try {
            BeanFactory factory = new XmlBeanFactory(new ClassPathResource("springldap.xml"));
            System.out.println(factory.toString() + "\n");

            ContactDAO ldapContact = (LDAPContactDAO)factory.getBean("ldapContact");

            //List contactList = ldapContact.getContactDetails("30662");
            List contactList =ldapContact.getAllContactNames();
            System.out.println(contactList.size());

            int count = 0;
            for( int i = 0 ; i < contactList.size(); i++){
                System.out.print("CN: " + contactList.get(i) + "\n");
                //System.out.println("SAP: " + ((ContactDTO) contactList.get(i)).getSap());
                count++;
            }
            System.out.println("\n" + count);

        } catch (DataAccessException e) {
            System.out.println("Error occured " + e.getCause());
        }
    }
}