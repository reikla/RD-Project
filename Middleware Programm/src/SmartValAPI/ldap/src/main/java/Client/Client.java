package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Client {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, KeyManagementException {
		//import jks truststore from filesystem	
		KeyStore trustStore = KeyStore.getInstance("jks");
		InputStream truststore= new FileInputStream("client.store");
		trustStore.load(truststore, "123456".toCharArray());
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
		trustManagerFactory.init(trustStore);
		
		//import jks keystore from filesystem (includes private part for client auth)
		KeyStore keyStore = KeyStore.getInstance("jks");
		InputStream keystore=new FileInputStream("client.store");
		keyStore.load(keystore, "123456".toCharArray());
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		keyManagerFactory.init(keyStore, "1234".toCharArray());

		
		//create SSLContext - https://docs.oracle.com/javase/7/docs/api/javax/net/ssl/SSLContext.html
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
	
		 try {
			 	//create clientsocket
			 	SSLSocketFactory sslsocketfactory = context.getSocketFactory();
			 	SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);
	            sslsocket.setEnabledCipherSuites(sslsocketfactory.getSupportedCipherSuites());
	           
	            //client<->server Console Text Transfer
	            InputStream inputstream = System.in;
	            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

	            OutputStream outputstream = sslsocket.getOutputStream();
	            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
	            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
	            	           
	            SSLSession session = sslsocket.getSession();	// initiate ssl handshake and client auth if server.needclientauth=true
	            
	            //display some connection parameters
			    System.out.println("The Certificates used by peer");
			 	System.out.println("Peer host is " + session.getPeerHost());
			    System.out.println("Cipher: " + session.getCipherSuite());
			    System.out.println("Protocol: " + session.getProtocol());
			    System.out.println("ID: " + new BigInteger(session.getId()));
			    System.out.println("Session created: " + session.getCreationTime());
			    System.out.println("Session accessed: " + session.getLastAccessedTime());
			    System.out.println("Peer certificates: " +session.getPeerCertificates());
			    System.out.println("local certificates: " +session.getLocalCertificates().toString());
			    System.out.println("peer cert-chain: " +session.getPeerCertificateChain());
	            
	            String string = null;
	            while ((string = bufferedreader.readLine()) != null) {
	                bufferedwriter.write(string + '\n');
	                bufferedwriter.flush();
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	       }
	 }
}
