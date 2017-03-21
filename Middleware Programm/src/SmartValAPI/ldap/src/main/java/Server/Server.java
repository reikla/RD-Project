package Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import ldap.ldapquery;

public class Server {

	public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
		//Enable CRL check
		System.setProperty("com.sun.security.enableCRLDP", "true");
		System.setProperty("com.sun.net.ssl.checkRevocation","true");
				
		//import jks truststore from filesystem
		KeyStore trustStore = KeyStore.getInstance("jks");
		InputStream truststore=new FileInputStream("server.store");
		trustStore.load(truststore, "123456".toCharArray());
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
		trustManagerFactory.init(trustStore);
		
		//import jks keystore from filesystem
		KeyStore keyStore = KeyStore.getInstance("jks");
		InputStream keystore=new FileInputStream("server.store");
		keyStore.load(keystore, "123456".toCharArray());
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		keyManagerFactory.init(keyStore, "1234".toCharArray());
		
		try {
			 	//create SSLContext - https://docs.oracle.com/javase/7/docs/api/javax/net/ssl/SSLContext.html
			 	SSLContext context = SSLContext.getInstance("TLS");
	    		context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
	    		
	    		//create serversocket with configured context
	    		SSLServerSocketFactory sslserversocketfactory = context.getServerSocketFactory();
	    		SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
	    		
	    		//display SSL Parameter
	            System.out.println("SSL Parameters: " + sslserversocket.getSSLParameters());
	            
	            //display initial client auth settings
	            System.out.println("initial Clientauth: " + sslserversocket.getNeedClientAuth());  	
	            
	            //enable, to request client authentication
	            sslserversocket.setNeedClientAuth( true );											
	                       	            
	            sslserversocket.setEnabledCipherSuites(sslserversocketfactory.getSupportedCipherSuites());
	            
	            //display final client auth settings
	            System.out.println("final Clientauth: " + sslserversocket.getNeedClientAuth());		
	            
	            //create sslsocket
	            SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();							
	            
	            //get remote certifiacte details
	            SSLSession session = sslsocket.getSession();										
	            
	            //query remote certificates information
	            X509Certificate[] x509 = (X509Certificate[]) session.getPeerCertificates();
	            X509Certificate cert = x509[0];													//select first certificate in Array, which is the client certificate
	            String dn = cert.getSubjectDN().getName();										//save remote Subject DN for further Use (eg. Ldap Queries)
	            System.out.println("Remote Cert SubjectDN: " + cert.getSubjectDN().getName());	//print out remote SubjectDN
	            System.out.println("Remote Cert Serial Number: " + cert.getSerialNumber());		//print out Client Cert Serial Number
	            
	            //perform LDAP search for memberof Attribute

	                        
	            
	            //client<->server Console Text Transfer
	            InputStream inputstream = sslsocket.getInputStream();
	            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
	            

	            String string = null;
	            while ((string = bufferedreader.readLine()) != null) {
	                System.out.println(string);
	                System.out.flush();
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        
	}

}
