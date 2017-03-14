package ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * memberof.java
 * December 2004
 * Sample JNDI application to determine what groups a user belongs to
 * https://community.oracle.com/thread/1157430
 * 
 */


public class ldapquery {
     public void memberof (String DN)     {
          //setting up connection parameters
          Hashtable<String, String> env = new Hashtable<String, String>();
          String ldapURL = "ldap://192.168.199.1:389";
          env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
          //set security credentials, note using simple cleartext authentication
          env.put(Context.SECURITY_AUTHENTICATION,"simple");
          env.put(Context.SECURITY_PRINCIPAL,"cn=admin,dc=opennes,dc=local");
          env.put(Context.SECURITY_CREDENTIALS,"ldapadmin");
                    
          //connect to my domain controller
          env.put(Context.PROVIDER_URL,ldapURL);
          
          try {

               //Create the initial directory context
               LdapContext ctx = new InitialLdapContext(env,null);
          
               //Create the search controls           
               SearchControls searchCtls = new SearchControls();
          
               //Specify the search scope
               searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

               //specify the LDAP search filter
               String searchFilter = DN;
          
               //Specify the Base for the search
               String searchBase = "ou=Austria,ou=Europe,ou=World,dc=opennes,dc=local";

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
     }
}