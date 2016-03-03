package br.org.venturus.dumpalignment.utils.parser;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserCorrectionPage implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private static final String BREAKLINE = "\r\n|\r|\n";

	/** M�todo que retorna o Heading da corre��o de uma p�gina do mhweb
	 * 
	 * @param content - p�gina html
	 * @return String - Heading
	 */
	public final static String getHeading(String content) {	
		
		Pattern pattern = Pattern.compile("(?is)(Heading:�</TD><TD CLASS=\"text\" COLSPAN=3>)(.+?)(</TD>)"); //<TD CLASS=\"text\" COLSPAN=3>(.+?)(</TD>)
        Matcher matcher = pattern.matcher(content);	
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Heading not found";
	}//fim do m�todo
	
	/** M�todo que retorna o Subsystem da corre��o de uma p�gina do mhweb
	 * 
	 * @param content - p�gina html
	 * @return String - Subsystem
	 */
	public final static String getSubsystem(String content) {
		
		Pattern pattern = Pattern.compile("(?is)(Subsystem:�</TD><TD CLASS=\"text\">)(.+?)(</TD>)");
        Matcher matcher = pattern.matcher(content);	
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Subsystem not found";
	}//fim do m�todo
	
	/** M�todo faz parsing de uma p�gina do mhweb e retorna o c�digo asa. 
	 * 
	 * Exemplo:
	 * 
	 * <PRE>IOTXP: MDE 11111: TITLE AMC;
	 *	
	 *	
	 *	PCSSL:BLOCK=ABCDE,SSP=100,SIGNAL=RALLACTGRP,CI=R01MCXJO5555;
	 *
	 *	
	 *	PCORL:BLOCK=ABCDE,IA=H'5555,CI=R01MCXJO5555;         ! PLEX 11111            !
	 *	LCC  WR01-0;                    ! ZZERO                                      !
	 *	WSS  555/C0-WR01;
	 *  ...
	 * </PRE>
	 * 
	 * @param content - p�gina html
	 * @return String - c�digo asa
	 */
	public final static String getAsacode(String content) {
		
		Pattern pattern = Pattern.compile("(?is)(ASA Solution</A></TD>.+?<PRE>)(.+?)(</PRE>)");
        Matcher matcher = pattern.matcher(content);	
        
        while (matcher.find()) {        	
        	if (matcher.group(2).matches("(?is).*(PCORL:BLOCK|PCSSL:BLOCK).*")) return matcher.group(2);     	        	
        }//fim do if
		return "IOTXP: Error: Asa not found;";
	}//fim do m�todo
	
	/** M�todo faz parsing de uma p�gina do mhweb e retorna o implementation Instruction 
	 * 
	 * @param content - p�gina html
	 * @return String - Implementation instruction
	 */
	public final static String getImplementationInstruction(String content) {	
		
		
		Pattern pattern = Pattern.compile("(?is)(Implementation�Instruction:</P><TABLE.+?><TR><TD class.+?>)(.*?)(</TD></TR></TABLE><BR>)");
        Matcher matcher = pattern.matcher(content);
        
        if (matcher.find()) {
        	String aux = matcher.group(2).replaceAll("<br>|<BR>", "\n");        	
        	aux = aux.replaceAll("<.+?>", "");
        	if (aux.isEmpty()) aux = "\nEMPTY";
        	return aux.replaceAll("\n", "<BR>");
        }//fim do if    	
        
		return "Error: Implementation Instruction not found";
	}//fim do m�todo
	
	

}//fim da classe
