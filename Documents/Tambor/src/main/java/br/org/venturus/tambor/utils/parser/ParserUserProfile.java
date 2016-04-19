package br.org.venturus.tambor.utils.parser;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUserProfile implements Serializable{
	
	
	private static final long serialVersionUID = 9024595618900384189L;


	/** M�todo que retorna o nome do usu�rio de uma p�gina do mhweb
	 * 
	 * @param content - p�gina html
	 * @return String - Name
	 */
	public final static String getName(String content) {	
		
		Pattern pattern = Pattern.compile("(?is)(Name.*<td class=\"text\">)(.+)(</td></tr><tr><td width=\"180\" class=\"label\">Email)"); //<TD CLASS=\"text\" COLSPAN=3>(.+?)(</TD>)
        Matcher matcher = pattern.matcher(content);        
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Name not found";
	}//fim do m�todo
	
	/** M�todo que retorna o email do usu�rio de uma p�gina do mhweb
	 * 
	 * @param content - p�gina html
	 * @return String - email
	 */
	public final static String getEmail(String content) {	
		
		Pattern pattern = Pattern.compile("(?is)(</td><td class=\"text\"><a href=\"mailto:)(.+?)(\">)"); //<TD CLASS=\"text\" COLSPAN=3>(.+?)(</TD>)
        Matcher matcher = pattern.matcher(content);	
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Email not found";
	}//fim do m�todo

}
