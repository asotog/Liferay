=========================
SPEECH APP SERVER CONFIG
=========================
1. Generate Keystore
First, uses “keytool” command to create a self-signed certificate. 
During the keystore creation process, you need to assign a password and fill in the certificate’s detail.

  $Tomcat\bin>keytool -genkey -alias keyspeech -keyalg RSA -keystore keyspeech.ks

and fill the certificate’s detail.

2. Connector in server.xml
Next, locate your Tomcat’s server configuration file at $Tomcat\conf\server.xml, 
modify it by adding a connector element to support for SSL or https connection.

File : $Tomcat\conf\server.xml

  //...
  <!-- Define a SSL HTTP/1.1 Connector on port 8443
         This connector uses the JSSE configuration, when using APR, the 
         connector should be using the OpenSSL style configuration
         described in the APR documentation -->
 
  <Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
             maxThreads="150" scheme="https" secure="true"
             clientAuth="false" sslProtocol="TLS" 
	           keystoreFile="keyspeech.ks"
	           keystorePass="password" />
  //...

3. Change your "transpor-guarantee" at ../ROOT/WEB-INF/web.xml
Original:
    <user-data-constraint>
			<transport-guarantee>NONE</transport-guarantee>
		</user-data-constChangeraint>
		
Change it:
    <user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constChangeraint>
		
4. Add in your $LIFERAY_HOME/portal-setup-wizard.properties these 3 new properties:
  company.security.auth.requires.https=true
  web.server.http.port=8080
  web.server.https.port=8443

5. Done
  Go to https://localhost:8443/
