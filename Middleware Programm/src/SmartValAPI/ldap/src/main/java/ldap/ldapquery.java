package ldap;

import at.ac.fh.salzburg.smartmeter.access.IDataSourceContext;
import at.ac.fh.salzburg.smartmeter.access.IUserContext;
import at.ac.fh.salzburg.smartmeter.ldap.ILdapPermissionManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.net.ssl.*;

import static java.lang.System.*;

public class ldapquery implements ILdapPermissionManager, IUserContext, IDataSourceContext{

    @Override
    public String givenname() {
        return "Max";
    }

    @Override
    public String surname() {
        return "Mustermann";
    }

    @Override
    public String password() {
        return null;
    }

    @Override
    public String MeterID() {
        return "1234567890";
    }

    //mitgegebener User darf auf mitgegebenen Smartmeter zugreifen
    @Override
    public boolean IsAllowedToAccess(IUserContext userContext, IDataSourceContext dataSourceContext) {

       String givenname =  userContext.givenname();
       String surname =  userContext.surname();
       String password = "";
       String ID = dataSourceContext.MeterID();

        try {
            DirContext ctx;
            ctx = getDirContext();

            BasicAttributes searchAttrs = new BasicAttributes();
            searchAttrs.put("givenName", givenname);
            searchAttrs.put("sn", surname);
            searchAttrs.put("gecos", ID);
            NamingEnumeration answer = ctx.search("ldap://193.170.119.66:389/ou=People, dc=maxcrc, dc=com", searchAttrs);

            if (formatResults(answer) != 0) {
                ctx.close();
                return true;
            }
            else{
                ctx.close();
                return false;
            }
        } catch (Exception e){
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
    public  int formatResults(NamingEnumeration answer) throws Exception{
        int count=0;
        try {
            while (answer.hasMore()) {
                SearchResult sr = (SearchResult)answer.next();
                out.println("SEARCH RESULT:" + sr.getName());
                formatAttributes(sr.getAttributes());
                out.println("====================================================");
                count++;
            }

            out.println("Search returned "+ count+ " results");

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return count;
    }
    public  void formatAttributes(Attributes attrs) throws Exception{
        if (attrs == null) {
            out.println("This result has no attributes");
        } else {
            try {
                for (NamingEnumeration answer = attrs.getAll(); answer.hasMore();) {
                    Attribute attrib = (Attribute)answer.next();
                    out.println("ATTRIBUTE :" + attrib.getID());
                    for (NamingEnumeration e = attrib.getAll();e.hasMore();)
                        out.println("\t\t        = " + e.next());
                }

            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }
    public void SSLConnection(){

        try {

        //Enable CRL check
        setProperty("com.sun.security.enableCRLDP", "true");
        setProperty("com.sun.net.ssl.checkRevocation","true");

        //import jks truststore from filesystem
        KeyStore trustStore = KeyStore.getInstance("jks");
        InputStream truststore=new FileInputStream("development.cer");
        trustStore.load(truststore, "123456".toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(trustStore);

        //import jks keystore from filesystem
        KeyStore keyStore = KeyStore.getInstance("jks");
        InputStream keystore=new FileInputStream("development.cer");
        keyStore.load(keystore, "123456".toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "1234".toCharArray());


            //create SSLContext - https://docs.oracle.com/javase/7/docs/api/javax/net/ssl/SSLContext.html
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            //create serversocket with configured context
            SSLServerSocketFactory sslserversocketfactory = context.getServerSocketFactory();
            SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);

            //display SSL Parameter
            out.println("SSL Parameters: " + sslserversocket.getSSLParameters());

            //display initial client auth settings
            out.println("initial Clientauth: " + sslserversocket.getNeedClientAuth());

            //enable, to request client authentication
            sslserversocket.setNeedClientAuth( true );

            sslserversocket.setEnabledCipherSuites(sslserversocketfactory.getSupportedCipherSuites());

            //display final client auth settings
            out.println("final Clientauth: " + sslserversocket.getNeedClientAuth());

            //create sslsocket
            SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

            //get remote certifiacte details
            SSLSession session = sslsocket.getSession();

            //query remote certificates information
            X509Certificate[] x509 = (X509Certificate[]) session.getPeerCertificates();
            X509Certificate cert = x509[0];													//select first certificate in Array, which is the client certificate
            String dn = cert.getSubjectDN().getName();										//save remote Subject DN for further Use (eg. Ldap Queries)
            out.println("Remote Cert SubjectDN: " + cert.getSubjectDN().getName());	//print out remote SubjectDN
            out.println("Remote Cert Serial Number: " + cert.getSerialNumber());		//print out Client Cert Serial Number

            //client<->server Console Text Transfer
            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                out.println(string);
                out.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}