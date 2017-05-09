package at.ac.fh.salzburg.smartmeter.ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IPermissionManager;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringFrameworkLDAPClient implements IPermissionManager, IUserContext, IDataSourceContext{

    public static void main(String[] args) {

        SpringFrameworkLDAPClient neu = new SpringFrameworkLDAPClient();

        //Neu erstellen
         IUserContext JNDI = new IUserContext() {
            @Override
            public String userid() {
                return "jn.di";
            }

            @Override
            public String password() {
                return "JNDI";
            }
        };
         IUserContext JNDI2 = new IUserContext() {
            @Override
            public String userid() {
                return "jn.di2";
            }

            @Override
            public String password() {
                return "JNDI2";
            }
        };

         IDataSourceContext ID1234567890 = () -> "1234567890";
         IDataSourceContext ID9876543210 = () -> "9876543210";
         IDataSourceContext ID12345 = () -> "12345";
         IDataSourceContext ID123456 = () -> "123456";

        //neu.AddUserToGroup(JNDI2,"Administrator");
        //neu.AddMeterToUser(JNDI2,ID12345);
        //neu.CreateUser(JNDI, ID12345);
        //neu.DeleteUser(JNDI2);
        //neu.CreateSmartmeter(ID12345);
        //neu.DeleteSmartmeter(ID12345);
    }

    //mitgegebener User darf auf mitgegebenen Smartmeter zugreifen
    @Override
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext) {

        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.IsAllowedToAccess(userContext,dataSourceContext)) {
            return true;
        }
        else{
            return false;
        }
        }
    //Erstellt einen User und pusht Ihn ins LDAP
    @Override
    public boolean CreateUser(IUserContext userContext, IDataSourceContext dataSourceContext) {

        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.CreateUser(userContext,dataSourceContext)) {
            return true;
        }
        else{
            return false;
        }
    }
    //Löscht mitgegebenen Benutzer aus dem LDAP
    @Override
    public boolean DeleteUser(IUserContext userContext) {

        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(DeleteUserFromAll(userContext)) {
            if (ldapContact.DeleteUser(userContext)) {

                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    //Erstellt neuen Smartmeter
    @Override
    public boolean CreateSmartmeter(IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.CreateSmartMeter(dataSourceContext)) {
            return true;
        }
        else{
            return false;
        }
    }
    //Löscht bekannten Smartmeter
    @Override
    public boolean DeleteSmartmeter(IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(DeleteMeterfromAll(dataSourceContext)) {
            if (ldapContact.DeleteSmartMeter(dataSourceContext)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    //Fügt Smartmeter zu User hinzu
    @Override
    public boolean AddMeterToUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.AddMeterToUser(userContext, dataSourceContext)) {
            return true;
        }
        else{
            return false;
        }
    }
    //fügt user einer Gruppe hinzu
    @Override
    public boolean AddUserToGroup(IUserContext userContext, String Group) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.AddUserToGroup(userContext, Group)) {
            return true;
        }
        else{
            return false;
        }
    }
    //Löscht Smartmeter von spezifischen user
    @Override
    public boolean DeleteMeterFromUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.DeleteMeterFromUser(userContext, dataSourceContext)) {
            return true;
        }
        else{
            return false;
        }
    }
    //Löscht meter aus allen usern
    @Override
    public boolean DeleteMeterfromAll(IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.DeleteMeterfromAll(dataSourceContext)) {
            return true;
        }
        else{
            return false;
        }
    }
    //Löscht User aus spezifischer Gruppe
    @Override
    public boolean DeleteUserFromGroup(IUserContext userContext, String Group) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.DeleteUserFromGroup(userContext, Group)) {
            return true;
        }
        else{
            return false;
        }
    }
    //Löscht User aus allen gruppen
    @Override
    public boolean DeleteUserFromAll(IUserContext userContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ContactDAO ldapContact = (LDAPContactDAO) factory.getBean("ldapContact");
        if(ldapContact.DeleteUserFromAll(userContext)) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String userid() {
        return null;
    }
    @Override
    public String password() {
        return null;
    }
    @Override
    public String MeterID() {
        return null;
    }
}