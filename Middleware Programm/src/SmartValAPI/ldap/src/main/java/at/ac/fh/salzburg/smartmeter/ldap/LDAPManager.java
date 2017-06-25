package at.ac.fh.salzburg.smartmeter.ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import at.ac.fh.salzburg.smartmeter.access.UserContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;

import javax.naming.InvalidNameException;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;

@Component
public class LDAPManager implements ILDAPManager {

    private static LdapTemplate ldapTemplate = new LdapTemplate(LdapContextSourceFactory.getContextSource());
/*
    public static void main(String[] args) {

        UserContext cons1 = new UserContext("consultant2","consultant2");
        UserContext cust1 = new UserContext("customer5","customer5");
        //IDataSourceContext sm1 = () -> "5";

        LDAPManager manager = new LDAPManager();
        //manager.CreateConsultant(cust1,cons1);
        manager.AddUserToUser(cust1,cons1);
        //manager.AddUserToGroup(cons1,"Energieberater");
    }
*/
    @Override
    public boolean CreateCustomer(IUserContext userContext, IDataSourceContext dataSourceContext){
        try{
            LdapName dn = new LdapName("uid="+userContext.userid()+",ou=People");

            //User Attributes
            Attribute userCn = new BasicAttribute("cn", userContext.userid());
            Attribute userPassword = new BasicAttribute("userPassword",userContext.password());
            Attribute UserID = new BasicAttribute("uidNumber","0");
            Attribute Usergid = new BasicAttribute("gidNumber","0");
            Attribute Userhome = new BasicAttribute("homeDirectory","/home/user/");

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
            entry.put(userPassword);
            entry.put(oc);

            ldapTemplate.bind(dn, null, entry);

            CreateSmartMeter(dataSourceContext);
            AddMeterToUser(userContext,dataSourceContext);

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean CreateConsultant(IUserContext Customer, IUserContext Consultant){
        try{
        LdapName dn = new LdapName("uid="+Consultant.userid()+",ou=People");

        //User Attributes
        Attribute userCn = new BasicAttribute("cn", Consultant.userid());
        Attribute userPassword = new BasicAttribute("userPassword",Consultant.password());
        Attribute UserID = new BasicAttribute("uidNumber","0");
        Attribute Usergid = new BasicAttribute("gidNumber","0");
        Attribute Userhome = new BasicAttribute("homeDirectory","/home/user/");

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
        entry.put(userPassword);
        entry.put(oc);

        ldapTemplate.bind(dn, null, entry);

        AddUserToUser(Customer, Consultant);

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
            LdapName dn = new LdapName("uid="+dataSourceContext.MeterID()+",ou=Smartmeter");

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

            ldapTemplate.bind(dn, null, entry);
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
            LdapName dn = new LdapName("uid="+dataSourceContext.MeterID()+",ou=Smartmeter");
            ldapTemplate.unbind(dn);
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
            LdapName dn = new LdapName("uid="+userContext.userid()+",ou=People");
            ldapTemplate.unbind(dn);
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
            final String[] cntemp = new String[1];

            ldapTemplate.search(
                    LdapQueryBuilder.query().where("description").is(dataSourceContext.MeterID()),

                    (AttributesMapper<Void>) attrs -> {

                        Attribute MeterID = attrs.get("description");
                        Attribute nameAttr = attrs.get("uid");
                        System.out.printf("%s - %s%n",
                                MeterID == null ? "" : MeterID.get(),
                                nameAttr == null ? "" : nameAttr.get()

                        );
                        cntemp[0] = nameAttr.toString();
                        return null;
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
          LdapName dn = new LdapName("uid="+userContext.userid()+",ou=People");
          Attribute MeterID = new BasicAttribute("memberUid", dataSourceContext.MeterID());
          ModificationItem ID = new ModificationItem(

                  DirContext.ADD_ATTRIBUTE, MeterID);

          ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});

          return true;
      }
      catch(Exception e){
          e.printStackTrace();
      }
      return false;
    }
    @Override
    public boolean AddUserToUser(IUserContext Customer, IUserContext Consultant) {

        try {
            LdapName dn = new LdapName("uid=" + Consultant.userid() + ",ou=People");
            Attribute UserID = new BasicAttribute("memberUid", Customer.userid());
            ModificationItem ID = new ModificationItem(

                    DirContext.ADD_ATTRIBUTE, UserID);

            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});

            return true;
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
      return false;
    }
    @Override
    public boolean AddUserToGroup(IUserContext userContext, String Group) {
        try {
            LdapName dn = new LdapName("cn="+Group+",ou=Groups");
            Attribute UserID = new BasicAttribute("memberUid", userContext.userid());
            ModificationItem ID = new ModificationItem(

                    DirContext.ADD_ATTRIBUTE, UserID);

            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});
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
            LdapName dn = new LdapName("uid="+userContext.userid()+",ou=People");
            Attribute MeterID = new BasicAttribute("description", dataSourceContext.MeterID());
            ModificationItem ID = new ModificationItem(

                    DirContext.REMOVE_ATTRIBUTE, MeterID);

            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});

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
            final String[] test = new String[1];

            ldapTemplate.search(
            LdapQueryBuilder.query().where("memberUid").is(dataSourceContext.MeterID()),

                    (AttributesMapper<Void>) attrs -> {

                        Attribute nameAttr = attrs.get("cn");
                        System.out.printf("%s%n",
                                nameAttr == null ? "" : nameAttr.get()
                        );
                        test[0] = nameAttr.toString();
                        final String[] parts = test[0].split(":");
                        String cntemp = parts[1];
                        try {
                            LdapName dn = new LdapName("uid="+cntemp.substring(1)+",ou=People");
                            Attribute MeterID = new BasicAttribute("memberUid", dataSourceContext.MeterID());
                            ModificationItem ID = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, MeterID);

                            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        return null;
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
            LdapName dn = new LdapName("cn="+Group+",ou=Groups");
            Attribute UserID = new BasicAttribute("memberUid", userContext.userid());
            ModificationItem ID = new ModificationItem(

                    DirContext.REMOVE_ATTRIBUTE, UserID);

            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});

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
            final String[] test = new String[1];

            ldapTemplate.search(
                    LdapQueryBuilder.query().where("memberUid").is(userContext.userid()),

                    (AttributesMapper<Void>) attrs -> {

                        Attribute nameAttr = attrs.get("cn");
                        System.out.printf("%s%n",
                                nameAttr == null ? "" : nameAttr.get()
                        );
                        test[0] = nameAttr.toString();
                        final String[] parts = test[0].split(":");
                        String cntemp = parts[1];
                        try {
                            LdapName dn = new LdapName("cn="+cntemp.substring(1)+",ou=Groups");
                            Attribute UserID = new BasicAttribute("memberUid", userContext.userid());
                            ModificationItem ID = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, UserID);

                            ldapTemplate.modifyAttributes(dn, new ModificationItem[]{ID});
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    });

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}