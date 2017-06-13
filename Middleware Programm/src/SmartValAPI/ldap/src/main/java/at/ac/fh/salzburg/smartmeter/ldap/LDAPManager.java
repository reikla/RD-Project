package at.ac.fh.salzburg.smartmeter.ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

public class LDAPManager implements ILDAPManager,IUserContext, IDataSourceContext {

    public static LdapTemplate setResource() {

        LdapTemplate ldapTemplate = null;
        try

        {
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("springldap.xml");
            ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ldapTemplate;
    }
    private static LdapTemplate ldapTemplate = setResource();

    public static void main(String[] args){

        IUserContext user = new IUserContext() {
            @Override
            public String userid() {
                return "567890";
            }

            @Override
            public String password() {
                return "test";
            }
        };
        IDataSourceContext data = () -> "567890";

        LDAPManager manager = new LDAPManager();
        manager.CreateUser(user,data);
    }

    @Override
    public boolean CreateUser(IUserContext userContext, IDataSourceContext dataSourceContext){
        try{

        DistinguishedName test = new DistinguishedName("ou=People");
        test.add("uid", userContext.userid());

        //LdapName test = new LdapName("ou=People");
        //test.add(userContext.userid());
        //User Attributes

        //Attribute useruid = new BasicAttribute("uid", userContext.userid());
        Attribute userCn = new BasicAttribute("cn", userContext.userid());
        Attribute userPassword = new BasicAttribute("userPassword",userContext.password());
        Attribute MeterID = new BasicAttribute("description",dataSourceContext.MeterID());
        Attribute UserID = new BasicAttribute("uidNumber","0");
        Attribute Usergid = new BasicAttribute("gidNumber","0");
        Attribute Userhome = new BasicAttribute("homeDirectory","/home/user/");

        //ObjectClass attributes
        Attribute oc = new BasicAttribute("objectClass");
        oc.add("top");
        oc.add("PosixAccount");
        oc.add("PosixGroup");

        Attributes entry = new BasicAttributes();
        //entry.put(useruid);
        entry.put(userCn);
        entry.put(Userhome);
        entry.put(Usergid);
        entry.put(UserID);
        entry.put(MeterID);
        entry.put(userPassword);
        entry.put(oc);

        ldapTemplate.bind(test, null, entry);
        return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean CreateSmartMeter(IDataSourceContext dataSourceContext) {
        try{
            DistinguishedName distinguisedName = new DistinguishedName("ou=Smartmeter");
            distinguisedName.add("uid", dataSourceContext.MeterID());

            //User Attributes
            Attribute userCn = new BasicAttribute("cn", dataSourceContext.MeterID());
            Attribute UserID = new BasicAttribute("uidNumber",dataSourceContext.MeterID());
            Attribute Usergid = new BasicAttribute("gidNumber","0");
            Attribute Userhome = new BasicAttribute("homeDirectory","/home/smartmeter/");

            //ObjectClass attributes
            Attribute oc = new BasicAttribute("objectClass");
            oc.add("top");
            oc.add("PosixAccount");
            oc.add("PosixGroup");

            Attributes entry = new BasicAttributes();
            entry.put(userCn);
            entry.put(Userhome);
            entry.put(Usergid);
            entry.put(UserID);
            entry.put(oc);

            ldapTemplate.bind(distinguisedName, null, entry);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean DeleteSmartMeter(IDataSourceContext dataSourceContext) {
        try{
            DistinguishedName distinguisedName = new DistinguishedName("ou=Smartmeter");
            distinguisedName.add("uid", dataSourceContext.MeterID());
            ldapTemplate.unbind(distinguisedName);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean DeleteUser(IUserContext userContext) {
        try{
            DistinguishedName distinguisedName = new DistinguishedName("ou=People");
            distinguisedName.add("uid", userContext.userid());
            ldapTemplate.unbind(distinguisedName);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext) {
        try {
            @SuppressWarnings("resource")
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("springldap.xml");
            LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");
            final String[] cntemp = new String[1];

            ldapTemplate.search(
                    LdapQueryBuilder.query().where("description").is(dataSourceContext.MeterID()),

                    new AttributesMapper<Void>() {

                        public Void mapFromAttributes(Attributes attrs) throws NamingException {

                            Attribute MeterID = attrs.get("description");
                            Attribute nameAttr = attrs.get("uid");
                            System.out.printf("%s - %s%n",
                                    MeterID == null ? "" : MeterID.get(),
                                    nameAttr == null ? "" : nameAttr.get()

                            );
                            cntemp[0] = nameAttr.toString();
                            return null;
                        }
                    });
            try {
                if (cntemp[0].equals("uid: " + userContext.userid())) {
                    return true;
                } else {
                    return false;
                }
            }
            catch(NullPointerException ne){
                //
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean AddMeterToUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
      try {
          DistinguishedName distinguisedName = new DistinguishedName("ou=People");
          distinguisedName.add("uid", userContext.userid());
          Attribute MeterID = new BasicAttribute("memberUid", dataSourceContext.MeterID());
          ModificationItem ID = new ModificationItem(

                  DirContext.ADD_ATTRIBUTE, MeterID);

          ldapTemplate.modifyAttributes(distinguisedName, new ModificationItem[]{ID});

          return true;
      }
      catch(Exception e){
          e.printStackTrace();
      }
      return false;
    }
    @Override
    public boolean AddUserToGroup(IUserContext userContext, String Group) {
        try {
            DistinguishedName distinguisedName = new DistinguishedName("ou=Groups");
            distinguisedName.add("cn", Group);
            Attribute UserID = new BasicAttribute("memberUid", userContext.userid());
            ModificationItem ID = new ModificationItem(

                    DirContext.ADD_ATTRIBUTE, UserID);

            ldapTemplate.modifyAttributes(distinguisedName, new ModificationItem[]{ID});
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean DeleteMeterFromUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
        try {
            DistinguishedName distinguisedName = new DistinguishedName("ou=People");
            distinguisedName.add("uid", userContext.userid());
            Attribute MeterID = new BasicAttribute("description", dataSourceContext.MeterID());
            ModificationItem ID = new ModificationItem(

                    DirContext.REMOVE_ATTRIBUTE, MeterID);

            ldapTemplate.modifyAttributes(distinguisedName, new ModificationItem[]{ID});

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean DeleteMeterfromAll(IDataSourceContext dataSourceContext) {

        try {
            @SuppressWarnings("resource")
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("springldap.xml");
            LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");

            final String[] test = new String[1];

            ldapTemplate.search(
            LdapQueryBuilder.query().where("memberUid").is(dataSourceContext.MeterID()),

                    new AttributesMapper<Void>() {

                        public Void mapFromAttributes(Attributes attrs) throws NamingException {

                            Attribute nameAttr = attrs.get("cn");
                            System.out.printf("%s%n",
                                    nameAttr == null ? "" : nameAttr.get()
                            );
                            test[0] = nameAttr.toString();
                            final String[] parts = test[0].split(":");
                            String cntemp = parts[1];
                            try {

                                DistinguishedName distinguisedName = new DistinguishedName("ou=People");
                                distinguisedName.add("uid", cntemp.substring(1));
                                Attribute MeterID = new BasicAttribute("memberUid", dataSourceContext.MeterID());
                                ModificationItem ID = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, MeterID);

                                ldapTemplate.modifyAttributes(distinguisedName, new ModificationItem[]{ID});
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                            return null;
                        }
                    });

        return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean DeleteUserFromGroup(IUserContext userContext, String Group) {
        try {
            DistinguishedName distinguisedName = new DistinguishedName("ou=Groups");
            distinguisedName.add("cn", Group);
            Attribute UserID = new BasicAttribute("memberUid", userContext.userid());
            ModificationItem ID = new ModificationItem(

                    DirContext.REMOVE_ATTRIBUTE, UserID);

            ldapTemplate.modifyAttributes(distinguisedName, new ModificationItem[]{ID});

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean DeleteUserFromAll(IUserContext userContext) {
        try {
            @SuppressWarnings("resource")
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("springldap.xml");
            LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");

            final String[] test = new String[1];

            ldapTemplate.search(
                    LdapQueryBuilder.query().where("memberUid").is(userContext.userid()),

                    new AttributesMapper<Void>() {

                        public Void mapFromAttributes(Attributes attrs) throws NamingException {

                            Attribute nameAttr = attrs.get("cn");
                            System.out.printf("%s%n",
                                    nameAttr == null ? "" : nameAttr.get()
                            );
                            test[0] = nameAttr.toString();
                            final String[] parts = test[0].split(":");
                            String cntemp = parts[1];
                            try {

                                DistinguishedName distinguisedName = new DistinguishedName("ou=Groups");
                                distinguisedName.add("cn", cntemp.substring(1));
                                Attribute UserID = new BasicAttribute("memberUid", userContext.userid());
                                ModificationItem ID = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, UserID);

                                ldapTemplate.modifyAttributes(distinguisedName, new ModificationItem[]{ID});
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                            return null;
                        }
                    });

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
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

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }
}