package br.org.venturus.dumpalignment.utils.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.venturus.dumpalignment.model.Block;
import br.org.venturus.dumpalignment.model.Correction;
import br.org.venturus.dumpalignment.model.Product;

/**Classe para ojetos do tipo ParserPCORP, essa classe contém um método que realiza um parsing de um arquivo pcorp
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
public class ParserPCORP extends Parser implements Serializable {        

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Método que faz parsing de um arquivo PCORP
	 * 
	 *  BLOCK    SUID                               CA    CAF
	 *	SIGDA1   7AE0/CAAZA 107 3940/MQOV R1A01     264   4
	 *	 
	 *	CI               S  TYPE  POSITION         SIZE
	 *	RNAFZ0268        C  CODE  H'8178             22
	 *	RNAFZ0268        C  CODE  H'8185             20
	 *	RNAFZ0268        C  CODE  H'8195             20
	 *	RNAFZ0268        C  CODE  H'81A2             20
	 *	RNAFZ0268        C  CODE  H'81B2             20
	 *	RNAFZ0268        C  CODE  H'81BF             20
	 *	RNAFZ0268        C  CODE  H'81CF             20
	 *	RNAYJ1465        C  CODE  H'41B0             22
	 *	RNAYJ1470        C  CODE  H'1C70             26
	 *	RNAYJ1470        C  CODE  H'1C75             24
	 *	RNAYJ1470        C  CODE  H'1C7D             19
	 *	RNAYJ1533        C  CODE  H'7733             27
	 *	                 F                            4
	 *	                 
	 *@param String - arquivo pcorp
	 *
	 *@return Product - blocos, correções e endereços
	 */
	public Product parseContent(String content) {            
	           
	        String blockName = "";
	        String version = "";
	        String revision = "";
	
	        Map<String, List<String>> corrMap = new HashMap<String, List<String>>();	   
	        Product product = new Product();	   
	        boolean newBlock = false;
	
	        String lines[] = content.split(BREAKLINE);
	
	        for (String line : lines) {
	             line = line.replaceFirst("(^\\s+)", "");
	             if (line.isEmpty()) continue;
	             Pattern pattern = Pattern.compile("(^<PCORP)|(^PROGRAM)|(^BLOCK)|(^CI\\s+S\\s+)");
	             Matcher matcher = pattern.matcher(line);
	             if (!matcher.find()) 
	             {
	                 pattern = Pattern.compile("(^\\w+)(\\s+\\w+/)(\\w+\\s+\\d+\\s+\\d+)(/\\w+\\s+)(\\w+)");
	                 matcher = pattern.matcher(line); 			   
	                 //se encontra linha 
	                 if (matcher.find()) {//pega nome do bloco e versão                              
	                     blockName = matcher.group(1);//bloco
	                     version = matcher.group(3);//versão
	                     revision = matcher.group(5);//revisão
	                 }//fim do if 
	                 else {//(^\\w+\\-\\d+)(\\s*)(REL|DLV)                	 
	                     pattern = Pattern.compile("(^\\w+)(\\s+C\\s+CODE\\s+)(H'\\w+)(\\s+\\d+)");
	                     matcher = pattern.matcher(line); 			   
	                     //se encontra linha do CI
	                     if (matcher.find()) {                            
	                         newBlock = true;
	                         if(!corrMap.containsKey(matcher.group(1))) {//verifica se não existe CI
	                             List<String> insAddList = new ArrayList<String>();                        
	                             insAddList.add(matcher.group(3));//adiciona IA 
	                             corrMap.put(matcher.group(1), insAddList);               
	                         }//fim do if
	                         else {//se existe CI adiciona apenas IA   
	                             corrMap.get(matcher.group(1)).add(matcher.group(3)); //adiciona IA                               
	                         }//fim do else */                           
	                     }//fim do if
	                     else warning.add("WARNING: The line '" + line + "' does not match");
	                 }//fim do else
	             }//fim do if     
	             else if(newBlock) {//add bloco ao produto
	                 Block block = new Block();                    
	                 block.setName(blockName);
	                 block.setVersion(version);
	                 block.setRevision(revision);
	                 
	                for(String k : corrMap.keySet()) {
	                     Correction correction = new Correction();
	                     correction.setCorrectionID(k);
	                     List<String> v = corrMap.get(k);
	                     for(String instAdd : v) {
	                         correction.addIntructionAddress(instAdd);
	                     }//fim do foreach
	                     block.addCorrection(correction);
	                 }//fim do foreach                   
	                 if (!block.getCorrections().isEmpty()) product.addBlock(block);
	                 corrMap.clear();
	                 newBlock = false;
	             }//fim do else if
	        }//fim do foreach      
	        return product;
	    }//fim do método parseContent
    
}//fim da classe