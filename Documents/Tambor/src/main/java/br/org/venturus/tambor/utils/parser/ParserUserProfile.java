package br.org.venturus.tambor.utils.parser;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUserProfile implements Serializable{
	
	
	private static final long serialVersionUID = 9024595618900384189L;


	/** Método que retorna o nome do usuário de uma página do mhweb
	 * 
	 * @param content - página html
	 * @return String - Name
	 */
	public final static String getName(String content) {	
		
		Pattern pattern = Pattern.compile("(?is)(Name.*<td class=\"text\">)(.+)(</td></tr><tr><td width=\"180\" class=\"label\">Email)"); //<TD CLASS=\"text\" COLSPAN=3>(.+?)(</TD>)
        Matcher matcher = pattern.matcher(content);        
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Name not found";
	}//fim do método
	
	/** Método que retorna o email do usuário de uma página do mhweb
	 * 
	 * @param content - página html
	 * @return String - email
	 */
	public final static String getEmail(String content) {	
		
		Pattern pattern = Pattern.compile("(?is)(</td><td class=\"text\"><a href=\"mailto:)(.+?)(\">)"); //<TD CLASS=\"text\" COLSPAN=3>(.+?)(</TD>)
        Matcher matcher = pattern.matcher(content);	
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Email not found";
	}//fim do método

}
