package ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

class ldapsearch{

     public static void main(String[] args) {

          ldapquery ldap = new ldapquery();

         try {
             ldap.doFilterSearch();
         } catch (Exception e) {
             e.printStackTrace();
         }


     }
}

public class ldapquery {

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

    public void doFilterSearch() throws Exception{
        DirContext ctx=  getDirContext();

        SearchControls ctls = new SearchControls();
        ctls. setReturningObjFlag (true);
        ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
        String filter = "(&(objectclass=organizationalunit))";
        NamingEnumeration answer = ctx.search("ou=People", filter, ctls);
        formatResults(answer);
        ctx.close();


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


    /*
     public void memberof (String DN)     {

          try {

              DirContext ctx=  getDirContext();

               //Create the search controls           
               SearchControls searchCtls = new SearchControls();
          
               //Specify the search scope
               searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

               //specify the LDAP search filter
               String searchFilter = DN;
          
               //Specify the Base for the search
               String searchBase = "ou=People,dc=maxcrc,dc=com";

               //initialize counter to total the group members
               int totalResults = 0;

               //Specify the attributes to return
               String returnedAtts[]={"memberOf"};
               searchCtls.setReturningAttributes(returnedAtts);
          
               //Search for objects using the filter
               NamingEnumeration<?> answer = ctx.search(searchBase, searchFilter, searchCtls);

               //Loop through the search results
               while (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult)answer.next();

                    System.out.println(">>>" + sr.getName());

                    //Print out the groups
 
                    Attributes attrs = sr.getAttributes();
                    if (attrs != null) {

                         try {
                              for (NamingEnumeration<?> ae = attrs.getAll();ae.hasMore();) {
                                   Attribute attr = (Attribute)ae.next();
                                   System.out.println("Attribute: " + attr.getID());
                                   for (NamingEnumeration<?> e = attr.getAll();e.hasMore();totalResults++) {

                                        System.out.println(" " +  totalResults + ". " +  e.next());
                                   }

                              }

                         }      
                         catch (NamingException e)     {
                              System.err.println("Problem listing membership: " + e);
                         }
                    
                    }
               }

               System.out.println("Total groups: " + totalResults);
               ctx.close();






          } 
          
          catch (NamingException e) {
               System.err.println("Problem searching directory: " + e);
                }

          catch (Exception e) {
              e.printStackTrace();
          }

     }
     */
}