package br.org.venturus.dumpalignment.utils.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringEscapeUtils;


/**Classe para objetos do tipo SSLConnect que cria uma conexão em uma página web usando o protocolo de segurança SSL
 * 
 * @author vntalgr
 * 
 * @version 1.1
 *
 */
public class SSLConnect implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String AUTHENTICATION_ERROR_MESSAGE = "401 Unauthorized";
	public static final String URL_ERROR_MESSAGE = "404 Not Found";
	public static final String TIMEOUT_ERROR_MESSAGE = "408 Request Timeout";
	public static final String SERVER_ERROR_MESSAGE = "500 Internal Server Error";
	public static final String GATEWAY_ERROR_MESSAGE = "502 Bad Gateway Error";
	
	public static int SUCCESS = 200;
	public static int AUTHENTICATION_ERROR = 401;
	public static int URL_ERROR = 404;
	public static int TIMEOUT_ERROR = 408;
	public static int SERVER_ERROR = 500;
	public static int GATEWAY_ERROR = 502;
	
	private String login;
	private String password;
	private String url;		
	private int responseCode;
	HttpsURLConnection connection = null;
	
	public String getLogin() {
		return login;
	}//fim do método

	public void setLogin(String login) {
		this.login = login;
	}//fim do método

	public String getPassword() {
		return password;
	}//fim do método

	public void setPassword(String password) {
		this.password = password;
	}//fim do método

	public String getUrl() {
		return url;
	}//fim do método

	public void setUrl(String url) {
		this.url = url;
	}//fim do método	

	/** Método que retorna a resposta do servidor.
	 * 
	 * @return int - Resposta HTTP
	 */
	public int getResponseCode() {
		return responseCode;
	}//fim do método
	
	/** Método que cria uma conexão SSL com uma página web(https), porém com a verificação de certificado desativada.
	 * 
	 * @param query - paramêtros de consulta
	 * @return String - página web
	 * @throws MalformedURLException, IOException, NoSuchAlgorithmException e KeyManagementException
	 * 
	 */
	public String receiveHtmlPage(String query) {
    	String htmlPage = "";   	
    	 	
    	//forrmat = AXE   	   	
    	
    	try {
    		TrustManager[] trustAllCerts = new TrustManager[] {
  			       new X509TrustManager() {
  			    	   
  			          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
  			            return null;
  			          }
  			    	  
  			          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }
  			    	  
  			          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

  			       }
  			    };    		
     		
    		SSLContext sc = SSLContext.getInstance("SSL");
    		sc.init(null, trustAllCerts, new java.security.SecureRandom());
    		// Install the all-trusting host verifier
    		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());   

    			// Create all-trusting host name verifier
    		HostnameVerifier allHostsValid = new HostnameVerifier() {				
    			@Override
    			public boolean verify(String hostname, SSLSession session) {					
 					return true;
 				}
 			};
 				    
 			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);			
 			URLEncoder.encode(query,"UTF-8");
    		//"https://mhweb.ericsson.se/mhweb/servlet/servletCorrView?"   
 			
    		URL mhweb = new URL(URIUtil.encodeQuery(this.url + query));
    		
    		/*---Authentication---*/
    		String authString = this.login + ":" + this.password;			
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());			
			String authStringEnc = new String(authEncBytes);
				
			/*---Abre conexão, seta charset e autentica ---*/
			connection = (HttpsURLConnection)mhweb.openConnection();			
		    connection.setRequestProperty("Accept-Charset", "UTF-8");
		    connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		    
		    responseCode = connection.getResponseCode();
			
			if (responseCode == SUCCESS)	{				
			//recebe dados				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				while((inputLine = in.readLine()) != null) {				
					htmlPage += StringEscapeUtils.unescapeHtml(inputLine) + "\n";
				}//fim do while
				in.close();			
			}//fim do if 
			else if(responseCode == AUTHENTICATION_ERROR) return AUTHENTICATION_ERROR_MESSAGE;	
			else if(responseCode == URL_ERROR) return URL_ERROR_MESSAGE;	
			else if(responseCode == TIMEOUT_ERROR) return TIMEOUT_ERROR_MESSAGE;
			else if(responseCode == SERVER_ERROR) return SERVER_ERROR_MESSAGE; 
			else if(responseCode == GATEWAY_ERROR) return GATEWAY_ERROR_MESSAGE; 
		    
			
		} catch (MalformedURLException ex) {			
			Logger.getLogger(SSLConnect.class.getName()).log(Level.SEVERE, null, ex);
			return "";
		} catch (IOException ex) { 			
			connection.disconnect();
            Logger.getLogger(SSLConnect.class.getName()).log(Level.SEVERE, null, ex);  
            return "";
        } catch (NoSuchAlgorithmException ex) {			
        	Logger.getLogger(SSLConnect.class.getName()).log(Level.SEVERE, null, ex);
        	return "";
		} catch (KeyManagementException ex) {						
			Logger.getLogger(SSLConnect.class.getName()).log(Level.SEVERE, null, ex);
			return "";
		}//fim do try-catch
    	return htmlPage;//retorna asa code page
    }//fim do método
	
	/** Método que disconecta a conexão
	 * 
	 */
	public void disconect() {
		connection.disconnect();
	}

}//fim da classe