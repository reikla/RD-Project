package at.ac.fh.salzburg.smartmeter.ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LDAPManager implements ILDAPManager {

    //mitgegebener User darf auf mitgegebenen Smartmeter zugreifen
    @Override
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext) {

        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.IsAllowedToAccess(userContext, dataSourceContext);
    }

    //Erstellt einen User und pusht Ihn ins LDAP
    @Override
    public boolean CreateUser(IUserContext userContext, IDataSourceContext dataSourceContext) {

        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.CreateUser(userContext, dataSourceContext);
    }

    //Löscht mitgegebenen Benutzer aus dem LDAP
    @Override
    public boolean DeleteUser(IUserContext userContext) {

        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return DeleteUserFromAll(userContext) && ldapContact.DeleteUser(userContext);
    }

    //Erstellt neuen Smartmeter
    @Override
    public boolean CreateSmartMeter(IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.CreateSmartMeter(dataSourceContext);
    }

    //Löscht bekannten Smartmeter
    @Override
    public boolean DeleteSmartMeter(IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return DeleteMeterFromAll(dataSourceContext) && ldapContact.DeleteSmartMeter(dataSourceContext);
    }

    //Fügt Smartmeter zu User hinzu
    @Override
    public boolean AddMeterToUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.AddMeterToUser(userContext, dataSourceContext);
    }

    //fügt user einer Gruppe hinzu
    @Override
    public boolean AddUserToGroup(IUserContext userContext, String Group) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.AddUserToGroup(userContext, Group);
    }

    //Löscht Smartmeter von spezifischen user
    @Override
    public boolean DeleteMeterFromUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.DeleteMeterFromUser(userContext, dataSourceContext);
    }

    //Löscht meter aus allen usern
    @Override
    public boolean DeleteMeterFromAll(IDataSourceContext dataSourceContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.DeleteMeterFromAll(dataSourceContext);
    }

    //Löscht User aus spezifischer Gruppe
    @Override
    public boolean DeleteUserFromGroup(IUserContext userContext, String Group) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.DeleteUserFromGroup(userContext, Group);
    }

    //Löscht User aus allen Gruppen
    @Override
    public boolean DeleteUserFromAll(IUserContext userContext) {
        @SuppressWarnings("resource")
        ApplicationContext factory = new ClassPathXmlApplicationContext("springldap.xml");
        ILDAPManager ldapContact = (ILDAPManager) factory.getBean("ldapContact");
        return ldapContact.DeleteUserFromAll(userContext);
    }
}