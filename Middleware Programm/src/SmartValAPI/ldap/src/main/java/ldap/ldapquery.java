package ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import at.ac.fh.salzburg.smartmeter.ldap.ILdapPermissionManager;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

class ldapsearch{

     public static void main(String[] args) {

          ldapquery ldap = new ldapquery();

         try {
             ldap.doFilterSearch("a","People");
         } catch (Exception e) {
             e.printStackTrace();
         }


     }
}



public class ldapquery implements ILdapPermissionManager{

    @Override
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext) {
        return false;
    }

    @Override
    public void doFilterSearch(String name, String group) {

        try {
            DirContext ctx=  getDirContext();
            BasicAttributes searchAttrs = new BasicAttributes();
            searchAttrs.put("sn", name);
            NamingEnumeration answer = ctx.search("ldap://193.170.119.66:389/ou="+group+", dc=maxcrc, dc=com",searchAttrs);
            formatResults(answer);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DirContext getDirContext() throws Exception{
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://193.170.119.66:389");
        env.put(Context.SECURITY_AUTHENTICATION,"simple");
        env.put(Context.SECURITY_PRINCIPAL,"cn=Manager,dc=maxcrc,dc=com"); // specify the username
        env.put(Context.SECURITY_CREDENTIALS,"secret");
        DirContext ctx = new InitialDirContext(env);
        return ctx;
    }

    public  void formatResults(NamingEnumeration answer) throws Exception{
        int count=0;
        try {
            while (answer.hasMore()) {
                SearchResult sr = (SearchResult)answer.next();
                System.out.println("SEARCH RESULT:" + sr.getName());
                formatAttributes(sr.getAttributes());
                System.out.println("====================================================");
                count++;
            }

            System.out.println("Search returned "+ count+ " results");

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public  void formatAttributes(Attributes attrs) throws Exception{
        if (attrs == null) {
            System.out.println("This result has no attributes");
        } else {
            try {
                for (NamingEnumeration answer = attrs.getAll(); answer.hasMore();) {
                    Attribute attrib = (Attribute)answer.next();
                    System.out.println("ATTRIBUTE :" + attrib.getID());
                    for (NamingEnumeration e = attrib.getAll();e.hasMore();)
                        System.out.println("\t\t        = " + e.next());
                }

            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }
}