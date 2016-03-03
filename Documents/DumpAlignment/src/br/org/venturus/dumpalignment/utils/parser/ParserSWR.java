package br.org.venturus.dumpalignment.utils.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.venturus.dumpalignment.model.Block;
import br.org.venturus.dumpalignment.model.Correction;
import br.org.venturus.dumpalignment.model.Product;

/**Classe que gera um objeto ParserSWR, que faz parsing de um software record(swr)
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
public class ParserSWR extends Parser implements Serializable {
	
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Método que faz parsing de um software record(swr) 
	 * 
	 * EMGRER   199 A   5600/CAAZ  107 6992/M07G  R1A03    25   6   C X12PA4632
     *                                                              C X12PA4697
     *                                                              C X12PA4976
     *                                                              C X12PA4983
     *                                                              C X12PA4987
     *                                                              C X12PA5158
     * EMGRES   198 A   5600/CAAZ  107 8382/M07S  R1A02     9   4   C X12PA5061
     *    	                                                        C X12PA5227
     *                                                              C Z02OG0081
     *                                                              C Z02PA5433
	 * 
	 * @param content - arquivo swr
	 * @return Product - blocos e correções
	 */
	public Product parseContent(String content) {        
        
        Product product = new Product();
        List<String> auxCorrID = new ArrayList<String>();
        String blockName = "";
        String version = "";
        String revision = "";
        String corrid = "";
        boolean newBlock = false;           
        String lines[] = content.split(BREAKLINE);
	   
        for (String line : lines) {//analisa todas as linhas             
             line = line.replaceFirst("(^\\s+)(C\\s)|(^\\s+)", "");//tira espaços em branco da lateral esqueda
             if (line.isEmpty()) continue;
             if (line.length() > 14) {                 
                 if(newBlock) { 
                     Block block = new Block();
                     if (!corrid.isEmpty()) {                          
                         block.setName(blockName);
                         block.setVersion(version); 
                         block.setRevision(revision);
                         
                         auxCorrID.add(corrid);                         
                         for(String c: auxCorrID) {
                             //System.out.println("ID " + c);
                             Correction correction = new Correction();
                             correction.setCorrectionID(c);
                             block.addCorrection(correction);
                         }//fim do for
                         /* Verifica se já existe bloco */
                         if (!product.hasBlock(block)) product.addBlock(block);                            
                         Collection<String> collection = auxCorrID;
                         auxCorrID.removeAll(collection);
                         corrid = "";       
                         //System.out.println("Add SWR: " + block.getName());
                     }//fim do if
                     newBlock = false;				   
                 }//fim do if
                
                 
                 //procura nome do bloco e procura versão do bloco e revisão
                 Pattern pattern = Pattern.compile("(^\\w+)(\\s+\\w+\\s+\\w+\\s+)(\\w+/)(\\w+\\s+\\d+\\s+\\d+)(/\\w+\\s+)(\\w+)");
                 Matcher matcher = pattern.matcher(line);		    	   
                 //se encontra versão
                 if (matcher.find()) {
                	 blockName = matcher.group(1);
                	 //pega versão e revisão do bloco		    		   
                      version = matcher.group(4);	
                      revision = matcher.group(6);
                      //procura Id da correção
                      pattern = Pattern.compile("(\\s+C\\s+)(\\w+)");
                      matcher = pattern.matcher(line);		    		   
                       //se encontra 
                      if (matcher.find()) { //pega o Id das correções                                                  
                         corrid = matcher.group(2);                         
                         newBlock = true;
                         //System.out.println(block.getName() + " " + block.getVersion() + " " + block.getCorrection(matcher.group(2)));
                      }//fim do if
                   }//fim do if
                 else warning.add("WARNING: The line " + line + " does not match.");             
              
         }//fim do if 		   
         else if(line.length() >= 9) 
         {//pega o Id das correções           
             auxCorrID.add(line);             			   
         }//fim do else if
         else warning.add("WARNING: The expression '" + line + "' is not a Correction ID");		   
      }//fim do foreach	             
      return product;
   }//fim do método parseContent()
     
}//fim da classe

