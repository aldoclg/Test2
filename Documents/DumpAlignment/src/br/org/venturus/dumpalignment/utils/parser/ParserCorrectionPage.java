package br.org.venturus.dumpalignment.utils.parser;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserCorrectionPage implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private static final String BREAKLINE = "\r\n|\r|\n";

	/** Método que retorna o Heading da correção de uma página do mhweb
	 * 
	 * @param content - página html
	 * @return String - Heading
	 */
	public final static String getHeading(String content) {	
		
		Pattern pattern = Pattern.compile("(?is)(Heading: </TD><TD CLASS=\"text\" COLSPAN=3>)(.+?)(</TD>)"); //<TD CLASS=\"text\" COLSPAN=3>(.+?)(</TD>)
        Matcher matcher = pattern.matcher(content);	
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Heading not found";
	}//fim do método
	
	/** Método que retorna o Subsystem da correção de uma página do mhweb
	 * 
	 * @param content - página html
	 * @return String - Subsystem
	 */
	public final static String getSubsystem(String content) {
		
		Pattern pattern = Pattern.compile("(?is)(Subsystem: </TD><TD CLASS=\"text\">)(.+?)(</TD>)");
        Matcher matcher = pattern.matcher(content);	
        
        if (matcher.find()) 
        	return matcher.group(2).trim();
        		
		return "Error: Subsystem not found";
	}//fim do método
	
	/** Método faz parsing de uma página do mhweb e retorna o código asa. 
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
	 * @param content - página html
	 * @return String - código asa
	 */
	public final static String getAsacode(String content) {
		
		Pattern pattern = Pattern.compile("(?is)(ASA Solution</A></TD>.+?<PRE>)(.+?)(</PRE>)");
        Matcher matcher = pattern.matcher(content);	
        
        while (matcher.find()) {        	
        	if (matcher.group(2).matches("(?is).*(PCORL:BLOCK|PCSSL:BLOCK).*")) return matcher.group(2);     	        	
        }//fim do if
		return "IOTXP: Error: Asa not found;";
	}//fim do método
	
	/** Método faz parsing de uma página do mhweb e retorna o implementation Instruction 
	 * 
	 * @param content - página html
	 * @return String - Implementation instruction
	 */
	public final static String getImplementationInstruction(String content) {	
		
		
		Pattern pattern = Pattern.compile("(?is)(Implementation Instruction:</P><TABLE.+?><TR><TD class.+?>)(.*?)(</TD></TR></TABLE><BR>)");
        Matcher matcher = pattern.matcher(content);
        
        if (matcher.find()) {
        	String aux = matcher.group(2).replaceAll("<br>|<BR>", "\n");        	
        	aux = aux.replaceAll("<.+?>", "");
        	if (aux.isEmpty()) aux = "\nEMPTY";
        	return aux.replaceAll("\n", "<BR>");
        }//fim do if    	
        
		return "Error: Implementation Instruction not found";
	}//fim do método
	
	

}//fim da classe
