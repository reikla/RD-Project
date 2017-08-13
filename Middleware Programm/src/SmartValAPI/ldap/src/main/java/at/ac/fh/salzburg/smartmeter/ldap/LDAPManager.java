package at.ac.fh.salzburg.smartmeter.ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;

import javax.naming.InvalidNameException;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;
import java.util.*;

/*import static com.sun.deploy.config.JREInfo.getAll;*/
/*import static sun.net.InetAddressCachePolicy.get;*/

@Component
public class LDAPManager implements ILDAPManager {

    private static LdapTemplate ldapTemplate = new LdapTemplate(LdapContextSourceFactory.getContextSource());
/*
    public static void main(String[] args) {

        UserContext cons1 = new UserContext("consultant2","consultant2");
        UserContext prov1 = new UserContext("provider1","provider1");
        //UserContext cust1 = new UserContext("customer5","customer5");
        IDataSourceContext sm1 = () -> "5";

        LDAPManager manager = new LDAPManager();

        if(manager.IsAllowedToAccess(prov1, sm1)){
            System.out.println("\nhat Zugriff\n");
        }
        else{
            System.out.println("\nhat keinen Zugriff\n");
        }

        //manager.CreateConsultant(cons1,prov1);
        //manager.AddUserToGroup(prov1,"Netzbetreiber");

        //manager.CreateConsultant(cust1,cons1);
        //manager.AddUserToUser(cust1,cons1);
        //manager.AddUserToGroup(cons1,"Energieberater");
    }
*/
    @Override
    public boolean CreateCustomer(IUserContext userContext, IDataSourceContext dataSourceContext){
        try{
            LdapName dn = new LdapName("uid="+userContext.userId()+",ou=People");

            //User Attributes
            Attribute userCn = new BasicAttribute("cn", userContext.userId());
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
        LdapName dn = new LdapName("uid="+Consultant.userId()+",ou=People");

        //User Attributes
        Attribute userCn = new BasicAttribute("cn", Consultant.userId());
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
            LdapName dn = new LdapName("uid="+userContext.userId()+",ou=People");
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
            final String[] gruppe = new String[1];

            //Hol die größte Gruppenzugehörigkeit
            ldapTemplate.search(
                    LdapQueryBuilder.query().where("memberUid").is(userContext.userId()),

                    (AttributesMapper<Void>) attrs -> {
                        Attribute nameAttr = attrs.get("cn");
                            if(nameAttr.contains("Kunden")){
                                gruppe[0] = "cn: Kunden";
                            }
                            if(nameAttr.contains("Energieberater")){
                                gruppe[0] = "cn: Energieberater";
                            }
                            if(nameAttr.contains("Netzbetreiber")){
                               gruppe[0] = "cn: Netzbetreiber";
                            }

                        return null;
                    });

            if(gruppe[0].contains("cn: Netzbetreiber")){

                LdapName dn = new LdapName("uid="+userContext.userId()+",ou=People");
                List consultants = new ArrayList();
                List customers = new ArrayList();

                //Holt alle Energieberater des Netzbetreibers
                ldapTemplate.lookup(
                        dn,
                        (AttributesMapper<Void>) attrs -> {
                            Attribute nameAttr = attrs.get("memberUid");
                            cntemp[0] = nameAttr.toString();
                            String[] parts = cntemp[0].split("[:,]");
                            for(int i = 1; i< parts.length; i++){
                                consultants.add(parts[i].toString());
                            }
                            return null;
                        });

                for(int i = 0; i < consultants.size(); i++) {

                    LdapName dn2 = new LdapName("uid="+consultants.get(i).toString()+",ou=People");
                    //Holt alle Kunden aller Energieberater
                    ldapTemplate.lookup(
                            dn2,
                            (AttributesMapper<Void>) attrs -> {
                                Attribute nameAttr = attrs.get("memberUid");
                                cntemp[0] = nameAttr.toString();
                                String[] parts = cntemp[0].split("[:,]");
                                for(int n = 1; n< parts.length; n++){
                                    customers.add(parts[n].toString());
                                }
                                return null;
                            });

                    //Abfrage, ob deren Mitglieder den angegebenen Smartmeter besitzen
                    int check = 0;
                    for(int n = 0; n < customers.size(); n++) {
                        ldapTemplate.search(
                                LdapQueryBuilder.query().where("memberUid").is(dataSourceContext.MeterID()),
                                (AttributesMapper<Void>) attrs -> {
                                    Attribute nameAttr = attrs.get("uid");
                                    cntemp[0] = nameAttr.toString();
                                    return null;
                                });
                        try {
                            if (cntemp[0].equals("uid:" + customers.get(n))) {

                                check = 1;
                                break;
                            } else {
                                check = 0;
                            }
                        } catch (NullPointerException ne) {
                            //
                        }
                    }
                    if(check == 1){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
            else if(gruppe[0].contains("cn: Energieberater")){

                LdapName dn = new LdapName("uid="+userContext.userId()+",ou=People");
                List mitglieder = new ArrayList();

                //Holt alle Mitglieder des Energieberaters
                ldapTemplate.lookup(
                        dn,
                        (AttributesMapper<Void>) attrs -> {
                            Attribute nameAttr = attrs.get("memberUid");
                            cntemp[0] = nameAttr.toString();
                            String[] parts = cntemp[0].split("[:,]");
                            for(int i = 1; i< parts.length; i++){
                                mitglieder.add(parts[i].toString());
                            }
                            return null;
                        });

                //Abfrage, ob deren Mitglieder den angegebenen Smartmeter besitzen
                int check = 0;
                for(int i = 0; i < mitglieder.size(); i++) {

                    ldapTemplate.search(
                            LdapQueryBuilder.query().where("memberUid").is(dataSourceContext.MeterID()),

                            (AttributesMapper<Void>) attrs -> {
                                Attribute nameAttr = attrs.get("uid");
                                cntemp[0] = nameAttr.toString();
                                return null;
                            });
                    try {
                        if (cntemp[0].equals("uid:" + mitglieder.get(i))) {
                            check = 1;
                            break;
                        } else {
                           check = 0;
                        }
                    } catch (NullPointerException ne) {
                        //
                    }
                }
                if(check == 1){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if(gruppe[0].contains("cn: Kunden")){

                ldapTemplate.search(
                        LdapQueryBuilder.query().where("memberUid").is(dataSourceContext.MeterID()),

                        (AttributesMapper<Void>) attrs -> {
                            Attribute nameAttr = attrs.get("uid");
                            cntemp[0] = nameAttr.toString();
                            return null;
                        });
                try {
                    if (cntemp[0].equals("uid: " + userContext.userId())) {
                        return true;
                    } else {
                        return false;
                    }
                }
                catch(NullPointerException ne){
                    //
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean AddMeterToUser(IUserContext userContext, IDataSourceContext dataSourceContext) {
      try {
          LdapName dn = new LdapName("uid="+userContext.userId()+",ou=People");
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
            LdapName dn = new LdapName("uid=" + Consultant.userId() + ",ou=People");
            Attribute UserID = new BasicAttribute("memberUid", Customer.userId());
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
            Attribute UserID = new BasicAttribute("memberUid", userContext.userId());
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
            LdapName dn = new LdapName("uid="+userContext.userId()+",ou=People");
            Attribute MeterID = new BasicAttribute("memberUid", dataSourceContext.MeterID());
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
            Attribute UserID = new BasicAttribute("memberUid", userContext.userId());
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
                    LdapQueryBuilder.query().where("memberUid").is(userContext.userId()),

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
                            Attribute UserID = new BasicAttribute("memberUid", userContext.userId());
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