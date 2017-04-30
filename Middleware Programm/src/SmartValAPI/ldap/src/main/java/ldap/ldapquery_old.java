package ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import at.ac.fh.salzburg.smartmeter.ldap.ILdapPermissionManager;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

public class ldapquery_old implements ILdapPermissionManager, IUserContext, IDataSourceContext {

    public static void main(String[] args) {
        ldapquery_old neu = new ldapquery_old();

        IUserContext kundea = new IUserContext() {
            @Override
            public String userid() {
                return "kunde.a";
            }

            @Override
            public String useridnumber() {
                return null;
            }

            @Override
            public String password() {
                return "kunde.a";
            }
        };
        IUserContext kundeb = new IUserContext() {
            @Override
            public String userid() {
                return "kunde.b";
            }

            @Override
            public String password() {
                return "kunde.b";
            }
            @Override
            public String useridnumber() {
                return null;
            }
        };
        IUserContext maxmustermann = new IUserContext() {
            @Override
            public String userid() {
                return "max.mustermann";
            }

            @Override
            public String password() {
                return "max.mustermann";
            }
            @Override
            public String useridnumber() {
                return null;
            }
        };

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

            @Override
            public String useridnumber() {
                return "54321";
            }
        };

        IDataSourceContext ID1234567890 = () -> "1234567890";
        IDataSourceContext ID9876543210 = () -> "9876543210";
        IDataSourceContext ID12345 = () -> "12345";
        IDataSourceContext ID123456 = () -> "123456";

        ///neu.CreateUser(JNDI, ID12345);
        //neu.DeleteUser(JNDI);

        //System.out.print(neu.IsAllowedToAccess(maxmustermann, ID1234567890));
        //System.out.print(neu.GiveGroupName(maxmustermann));
    }

    //mitgegebener User darf auf mitgegebenen Smartmeter zugreifen
    @Override
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext) {

        try {
            DirContext ctx;
            ctx = getDirContext();

            BasicAttributes searchAttrs = new BasicAttributes();
            searchAttrs.put("uid",userContext.userid());
            searchAttrs.put("userPassword", userContext.password());
            searchAttrs.put("description", dataSourceContext.MeterID());
            NamingEnumeration<SearchResult> answer = ctx.search("ldap://193.170.119.66:389/ou=People, dc=maxcrc, dc=com", searchAttrs);

            while (answer.hasMoreElements()) {

                SearchResult searchResult = answer.next();
                Attributes attributes = searchResult.getAttributes();
                Attribute MeterID = attributes.get("description");

                for(int i = 0; i < MeterID.size(); i++) {
                    String ID = MeterID.get(i).toString();
                    if (MeterID != null && (ID.equals(dataSourceContext.MeterID()))) {
                        ctx.close();
                        return true;
                    }
                }
            }
            ctx.close();
            return false;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //gibt die Gruppenzugehörigkeit des mitgegebenen users mit in String
    public String GiveGroupName(IUserContext userContext) {

        try {
            DirContext ctx;
            ctx = getDirContext();

            //Hier noch Suche nach allen gruppen des LDAP einfügen
            String[] Groups = new String[5];
            Groups[0] = "Administrator";
            Groups[1] = "Energieberater";
            Groups[2] = "Forschungszentrum";
            Groups[3] = "Kunden";
            Groups[4] = "Netzbetreiber";

            String searchBase = "OU=Groups,DC=maxcrc,DC=com";

            for(int i = 0; i< 5; i++){

                String searchFilter = "(&(cn="+Groups[i]+")(memberUid="+userContext.userid()+"))";
                SearchControls sCtrl = new SearchControls();
                sCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
                NamingEnumeration answer = ctx.search(searchBase, searchFilter, sCtrl);

                if(answer.hasMoreElements()){
                    return "\n"+Groups[i];
                }
            }

        } catch (NamingException ex) {
            //Do something with the exception...
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    //Erstellt einen User und pusht Ihn ins LDAP
    @Override
    public boolean CreateUser(IUserContext userContext, IDataSourceContext dataSourceContext) {

        try {
            DirContext ctx;
            ctx = getDirContext();

            //User Attributes
            Attribute userCn = new BasicAttribute("cn", userContext.userid());
            Attribute userUid = new BasicAttribute("uidNumber", userContext.useridnumber());
            Attribute userGid = new BasicAttribute("gidNumber", "0");
            Attribute homeDirectory = new BasicAttribute("homeDirectory", "/home/user/");
            Attribute userSn = new BasicAttribute("sn", userContext.userid());
            Attribute userPassword = new BasicAttribute("userPassword",userContext.password());
            Attribute MeterID = new BasicAttribute("description",dataSourceContext.MeterID());

            //ObjectClass attributes
            Attribute oc = new BasicAttribute("objectClass");
            oc.add("top");
            oc.add("PosixAccount");
            oc.add("inetOrgPerson");

            Attributes entry = new BasicAttributes();
            entry.put(userCn);
            entry.put(MeterID);
            entry.put(userUid);
            entry.put(userGid);
            entry.put(homeDirectory);
            entry.put(userSn);
            entry.put(userPassword);
            entry.put(oc);

            ctx.createSubcontext("uid="+userContext.userid()+",ou=People,dc=maxcrc,dc=com", entry);
            return true;

        } catch (NamingException e) {
            System.err.println("AddUser: error adding entry." + e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    //Löscht mitgegebenen Benutzer aus dem LDAP
    @Override
    public boolean DeleteUser(IUserContext userContext) {

        try {
            DirContext ctx;
            ctx = getDirContext();
            ctx.destroySubcontext("uid=" + userContext.userid() + ",ou=People,dc=maxcrc,dc=com");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public DirContext getDirContext() throws Exception{
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://193.170.119.66:389");
        env.put(Context.SECURITY_AUTHENTICATION,"simple");
        //env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_PRINCIPAL,"cn=Manager,dc=maxcrc,dc=com"); // specify the username
        env.put(Context.SECURITY_CREDENTIALS,"secret");
        DirContext ctx = new InitialDirContext(env);
        return ctx;
    }

    @Override
    public String userid() {
        return null;
    }
    @Override
    public String useridnumber() {
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