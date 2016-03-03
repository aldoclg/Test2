package br.org.venturus.dumpalignment.utils.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

import br.org.venturus.dumpalignment.model.Block;
import br.org.venturus.dumpalignment.model.Correction;
import br.org.venturus.dumpalignment.model.Product;

public class Parser implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;    
    protected final List<String> error;
    protected final List<String> warning;
    public static final String BREAKLINE = "\r\n|\r|\n";

    public Parser() {        
        error = new ArrayList<String>();
        warning = new ArrayList<String>();
    }//fim do construtor   

    public void setContent(String content) {
        this.content = content;
    }//fim do método

    public final String separeContent(String beginRegEx, String endRegEx, boolean takeOffRegEx) {
    	if ((content.indexOf(beginRegEx) > 0) && (content.indexOf(endRegEx) > 0)) {   
    		if (takeOffRegEx){    			
    			return content.substring(content.indexOf(beginRegEx) + beginRegEx.length(), content.indexOf(endRegEx));    	
    		} else return content.substring(content.indexOf(beginRegEx), content.indexOf(endRegEx) + endRegEx.length());    				
    	}
        else {
        	//System.out.println("Parse result false\n" + content);
        	//System.out.println("WARNING: The " + beginRegEx + " or " + endRegEx + " do not found.");
        	warning.add("WARNING: The " + beginRegEx + " or " + endRegEx + " do not found.");
        }
    	return "";       
    }//fim do método separeContent   
   
   
   /** 
    * Retorna correções que nao estao na base,
    * mas estão no target gerando um product object 
    * do target 
    * 
    * @param base
    * @param target
    * @param filteredBase
    * @return - Result
    */
   public static final Product filterCorrectionByCompare(Product base, Product target, Product filteredBase) {//filtra AC's	   
        Product filteredProduct = new Product();
        boolean hasCorrection = false;
        for(Block filtered : filteredBase.getBlocks()) {
            if(target.hasBlock(filtered)) {
                
                Block targetBlock = target.getBlock(target.indexOf(filtered));
                Block baseBlock = base.getBlock(base.indexOf(filtered));
                Block auxBlock = new Block();
                auxBlock.setName(targetBlock.getName());
                auxBlock.setVersion(targetBlock.getVersion());                
                for(Correction c : targetBlock.getCorrections()) {
                	//System.out.println("CT " + c.getCorrrectionID());
                    if(!baseBlock.hasCorrection(c.getCorrectionID())) { 
                    	//System.out.println("HAVE " + c.getCorrrectionID());
                        auxBlock.addCorrection(c);   
                        hasCorrection = true;
                    }//fim do if
                }//fim do for
                if(hasCorrection) filteredProduct.addBlock(auxBlock);
                hasCorrection = false;
            }//fim do if
        }//fim do for
        return filteredProduct;
   }//fim do método
   
   /**A função retorna um objeto do tipo Product com todas as correções do tipo AMC e EC  
    * que estão na base mas não estão no target
    *  
    * @param base
    * @param target
    * @return - objeto {@link Product} com as correções
    */
   public static final Product filterBaseByTarget(Product base, Product target) {
	   
	   Product filteredProduct = new Product();
	   boolean hasCorrection = false;
	   for (Block block : base.getBlocks()) {
		   if(target.hasBlock(block)) {
			   
			   Block targetBlock = target.getBlock(target.indexOf(block));
			   Block auxBlock = new Block();
			   
			   auxBlock.setName(block.getName());
			   auxBlock.setVersion(block.getVersion());
			   auxBlock.setRevision(block.getRevision());
			   
			   if (!filteredProduct.hasBlock(auxBlock)) {
				   
				   for (Correction correction : block.getCorrections()) {
					   if (!targetBlock.hasCorrection(correction.getCorrectionID())) {		  
						  
						   if(!auxBlock.hasCorrection(correction.getCorrectionID())) {
							   
							   Pattern pattern = Pattern.compile("(^MC\\w+)|(^\\w+MCMD\\w+)|(^EC\\w+)"); 
							   Matcher matcher = pattern.matcher(correction.getCorrectionID());
							   
							   if (matcher.find()) {
								   auxBlock.addCorrection(correction);
								   hasCorrection = true;
							   }//fim do if
						   }//fim do if	
						   				   					   
					   }//fim do if						   
				   }//fim do for
				   if(hasCorrection) filteredProduct.addBlock(auxBlock);
				   hasCorrection = false;
			   }//fim do if
		   }//fim do if
	   }//fim do for
	   
	   return filteredProduct;
   }//fim do método
 
   /**A função retorna um objeto do tipo Product com todas as correções 
    * que estão na base mas não estão no target
    *  
    * @param base
    * @param target
    * @return - objeto {@link Product} com as correções
    */
   public static final Product subtracts(Product a, Product b) {
	   
	   Product result = new Product();
	   boolean hasCorrection = false;
	   
	   for (Block block : a.getBlocks()) {
		   
		   if (b.hasBlock(block)) {
			   
			   Block targetBlock = b.getBlock(b.indexOf(block));
			   Block auxBlock = new Block();
			   
			   auxBlock.setName(block.getName());
			   auxBlock.setVersion(block.getVersion());
			   auxBlock.setRevision(block.getRevision());
			   
			   if (!result.hasBlock(auxBlock)) {
				   
				   for (Correction correction : block.getCorrections()) {
					   if (!targetBlock.hasCorrection(correction.getCorrectionID())) {		  
						  
						   if(!auxBlock.hasCorrection(correction.getCorrectionID())) {						  
							   auxBlock.addCorrection(correction);
							   hasCorrection = true;						   
						   }//fim do if							   				   					   
					   }//fim do if						   
				   }//fim do for
				   if (hasCorrection) result.addBlock(auxBlock);
				   hasCorrection = false;
			   }//fim do if
		   }//fim do if
	   }//fim do for
	   return result;
   }//fim do método
   
    public boolean hasContent(String regex) {
       Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
       Matcher matcher = pattern.matcher(this.content);
       return (matcher.find());
   }//fim do método
    
    
   /** Método que filtra todas as AMC's em um objeto {@link Product}
    *  
    * @param product
    * @return - AMC's
    */
   public static final Product filterMarketCorrections(Product product) {
	   
        Product filteredProduct = new Product();
        	 	
            for (Block block : product.getBlocks()) {
                Block filteredBlock = new Block();
                for (Correction correction : block.getCorrections()) {
                    Pattern pattern = Pattern.compile("(^MC[^H]{1}\\w+)|(^\\w+MCMD\\w+)");
                    Matcher matcher = pattern.matcher(correction.getCorrectionID());
                    if(matcher.find()) {         	
                        filteredBlock.addCorrection(correction);
                    }//fim do if                
                }//fim do foreach
                if(!(filteredProduct.getBlocks().contains(block))) {        		 
                    if(!filteredBlock.getCorrections().isEmpty()) {
                        filteredBlock.setName(block.getName());
                        filteredBlock.setVersion(block.getVersion()); 
                        filteredBlock.setRevision(block.getRevision());
                        filteredProduct.addBlock(filteredBlock);
                    }//fim do if
                }//fim do if    		
            }//fim do foreach
        return filteredProduct;        	
   }//fim do método
   
   
   /** Método que filtra EC's e PC's 
    * 
    * @param product
    * @return
    */
   public static final Product filterEmergencyCorrections(Product product) {
	   
       Product filteredProduct = new Product();
       	 	
           for (Block block : product.getBlocks()) {
               Block filteredBlock = new Block();
               for (Correction correction : block.getCorrections()) {
                   Pattern pattern = Pattern.compile("(^EC\\w+)");
                   Matcher matcher = pattern.matcher(correction.getCorrectionID());
                   if(matcher.find()) {         	
                       filteredBlock.addCorrection(correction);
                   }//fim do if                
               }//fim do foreach
               if(!(filteredProduct.getBlocks().contains(block))) {        		 
                   if(!filteredBlock.getCorrections().isEmpty()) {
                       filteredBlock.setName(block.getName());
                       filteredBlock.setVersion(block.getVersion()); 
                       filteredBlock.setRevision(block.getRevision());
                       filteredProduct.addBlock(filteredBlock);
                   }//fim do if
               }//fim do if    		
           }//fim do foreach
       return filteredProduct;        	
  }//fim do método
   
   public static final Product filterErirefCorrections(Product product) {
	   //((MC)?([A-Z]{2}[0-9]{5})(MKT)?)
       Product filteredProduct = new Product();
       	 	
           for (Block block : product.getBlocks()) {
               Block filteredBlock = new Block();
               for (Correction correction : block.getCorrections()) {
                   Pattern pattern = Pattern.compile("(^EC\\w+)|(^PC\\w+)|(^MCH\\w+)");
                   Matcher matcher = pattern.matcher(correction.getCorrectionID());
                   if(matcher.find()) {         	
                       filteredBlock.addCorrection(correction);
                   }//fim do if                
               }//fim do foreach
               if(!(filteredProduct.getBlocks().contains(block))) {        		 
                   if(!filteredBlock.getCorrections().isEmpty()) {
                       filteredBlock.setName(block.getName());
                       filteredBlock.setVersion(block.getVersion());
                       filteredBlock.setRevision(block.getRevision());
                       filteredProduct.addBlock(filteredBlock);
                   }//fim do if
               }//fim do if    		
           }//fim do foreach
       return filteredProduct;        	
  }//fim do método
   
   public static final Product filterNoACCorrections(Product product) {
	   //((MC)?([A-Z]{2}[0-9]{5})(MKT)?)
       Product filteredProduct = new Product();
       	 	
           for (Block block : product.getBlocks()) {
               Block filteredBlock = new Block();
               for (Correction correction : block.getCorrections()) {
                   Pattern pattern = Pattern.compile("(^EC\\w+)|(^PC\\w+)|(^MCH\\w+)|(^\\w+MCMD\\w+)");
                   Matcher matcher = pattern.matcher(correction.getCorrectionID());
                   if(matcher.find()) {         	
                       filteredBlock.addCorrection(correction);
                   }//fim do if                
               }//fim do foreach
               if(!(filteredProduct.getBlocks().contains(block))) {        		 
                   if(!filteredBlock.getCorrections().isEmpty()) {
                       filteredBlock.setName(block.getName());
                       filteredBlock.setVersion(block.getVersion()); 
                       filteredBlock.setRevision(block.getRevision());
                       filteredProduct.addBlock(filteredBlock);
                   }//fim do if
               }//fim do if    		
           }//fim do foreach
       return filteredProduct;        	
  }//fim do método
  
   /**Método que inclui um "-" no id da correção
    * 
    * @param corrid - correção
    * @return String - corrreção formtada
    */
   public static final String formatCorrId(String corrid) {
		
	   String part1;
	   String part2;
	   String part3;
		
	   if (corrid.length() < 10) {
		   if(corrid.matches("(^EC|^PC|^MC)(.+)")) {
			   part1 = corrid.substring(0, 2);
			   part2 = corrid.substring(2);
		   } else {
			   part1 = corrid.substring(0, corrid.length()-4);
			   part2 = corrid.substring(corrid.length()-4);
		   }// fim do if-else	   
	   } else if (corrid.matches("\\w+MC\\w+")) {
		   part1 = corrid.substring(0, 8);
		   part2 = corrid.substring(corrid.length()-4);
	   } else {
		   part1 = corrid.substring(0, 5);
		   part2 = corrid.substring(5, 9);
		   part3 = corrid.substring(corrid.length()-5);
		   return part1 + "-" + part2 + "-" + part3;
	   }//fim do if-else
			
	   return part1 + "-" + part2;
   }//fim do método
   
   /** Método faz parsing de um código asa e retorna o PCORI da mesma
	 * 
	 * @param content - asa code
	 * @return String - PCORI:.*;
	 */
	public final static String getPCORI(String content) {
		
		Pattern pattern = Pattern.compile("(?is)(PCORI:.+?;)");
		Matcher matcher = pattern.matcher(content);
		String aux = "";
		
		while (matcher.find()) {			
			aux += matcher.group(0) + "\n";			
		}//fim do while    	
       
		if (aux.isEmpty()) aux = "IOTXP:PCORI not found;";
		return aux;
	}//fim do método
   

    public List<String> getErrors() {
        return error;
    }//fim do método   
    
    public int getNumberOfErrors(){
        return error.size();
    }//fim do método

    public List<String> getWarning() {
        return warning;
    }//fim do método
    
    public int getNumberOfWarnings() {
        return warning.size();
    }//fim do método
    
}//fim da classe