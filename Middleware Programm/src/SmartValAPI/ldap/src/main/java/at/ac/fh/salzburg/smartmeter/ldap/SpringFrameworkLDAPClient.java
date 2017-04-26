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

            List contactList =ldapContact.getAllUserID();
            System.out.println(contactList.size());

            int count = 0;
            for( int i = 0 ; i < contactList.size(); i++){
                System.out.print("uid: " + contactList.get(i) + "\n");
                count++;
            }
            System.out.println("\n" + count);

        } catch (DataAccessException e) {
            System.out.println("Error occured " + e.getCause());
        }
    }
}