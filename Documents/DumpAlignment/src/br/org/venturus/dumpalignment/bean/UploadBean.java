package br.org.venturus.dumpalignment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import br.org.venturus.dumpalignment.model.Correction;
import br.org.venturus.dumpalignment.model.Block;
import br.org.venturus.dumpalignment.model.Product;
import br.org.venturus.dumpalignment.utils.parser.ParserPCORP;
import br.org.venturus.dumpalignment.utils.parser.ParserSWR;
import br.org.venturus.dumpalignment.utils.parser.Parser;
import br.org.venturus.dumpalignment.utils.parser.ParserCorrectionPage;
import br.org.venturus.dumpalignment.utils.session.SessionUtils;
import br.org.venturus.dumpalignment.utils.connect.SSLConnect;

/**ManagedBean da página index.xhtml, onde recebe do usuário software record/pcorp file. 
 * Executa consulta no MHWeb e gera uma tabela.
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
@ManagedBean(name="uploadBean")
@RequestScoped
public class UploadBean implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String password;	
	private static Integer progress = 0;	
	
	private UploadedFile uploadFileBase;
	private UploadedFile uploadFileDump;
	
	private Product base = new Product(); 
	private Product dump = new Product(); 
	private Product result = new Product();
	
	private boolean isLoadBase = false;
	private boolean isLoadDump = false;
	private static boolean isPageLoad = true;
	private static boolean isProcessingCorrections = false;
	
	private static final String corrView = "https://mhweb.ericsson.se/mhweb/servlet/servletCorrView?corrid=";
	private static final int miliseconds = 1000;
	private static final int timeout = 15*miliseconds;
	
	/** Método que recebe do ManagedBean LoginBean o login e a senha que o usuário tem do MHWeb.
	 * 
	 */
	@PostConstruct
	public void init() {		
		login = (String) SessionUtils.getAttribute("login");
		password = (String) SessionUtils.getAttribute("password");			
	}//fim do método
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}	

	/** Método que através do Ajax preenche um progressbar à medida que as consultas no MHWeb são realizadas.
	 * 
	 * @return Integer - progresso de 0 à 100.
	 */
	public Integer getProgress() {		
		if (progress > 100) progress = 100;
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;		
	}  
	
	public UploadedFile getUploadFileBase() {
		return uploadFileBase;
	}
	
	public void setUploadFileBase(UploadedFile uploadFileBase) {
		this.uploadFileBase = uploadFileBase;
	}
	
	public UploadedFile getUploadFileDump() {
		return uploadFileDump;
	}
	
	public void setUploadFileDump(UploadedFile uploadFileDump) {
		this.uploadFileDump = uploadFileDump;
	}
	
	public Product getResult() {
		return result;
	}

	public void setResult(Product result) {
		this.result = result;
	}

	/** Método que carrega uma estrutura de dados de um arquivo que contém as informações do sistema AXE do cliente
	 *  - Software Record ou Pcorp
	 * 
	 * @param file
	 */
	public void uploadBase(UploadedFile file) {
		
		String baseFile;	       
        FacesContext context = FacesContext.getCurrentInstance(); 
               
        try {	
       
    		baseFile = new Scanner(file.getInputstream()).useDelimiter("\\A").next();                     
        
            if (!baseFile.matches("(?is).*SOFTWARE RECORD.*") && !baseFile.matches("(?is).*PCORP:BLOCK.*")) {
            	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", file.getFileName() + " is not a Software Record or PCORP file."));
            	isLoadBase = false;
            } else {
            	isLoadBase = true;
        		base.removeAllBlocks();
        		
            	if (baseFile.matches("(?is).*SOFTWARE RECORD.*")) {            
            		context.addMessage(null, new FacesMessage("Successful", file.getFileName() + " has been uploaded Software Record file."));
            		base = swrToProduct(baseFile);
            	}//fim do if
            	else {
            		context.addMessage(null, new FacesMessage("Successful", file.getFileName() + " has been uploaded PCORP file."));
            		base = pcorpToProduct(baseFile);
            	}//fim do else-if            	
            }//fim do if-else            
        	    
        } catch (IOException ex) {  
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "An error ocurred while loaded the file " + file.getFileName()));
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);           
        }//fim try-catch 		
	}//fim do método
	
	/** Método que carrega uma estrutura de dados de um arquivo que contém as informações de um dump AXE do SEA
	 *  - Software Record ou Pcorp
	 * 
	 * @param file
	 */
	public void uploadDump(UploadedFile file) {
		
		String dumpFile;	       
		FacesContext context = FacesContext.getCurrentInstance(); 
              
		try {
		
			dumpFile = new Scanner(file.getInputstream()).useDelimiter("\\A").next();     
				
			if (dumpFile.matches("(?is).*PCORP:BLOCK.*")) {
				context.addMessage(null, new FacesMessage("Successful", file.getFileName() + " has been uploaded PCORP file."));
				isLoadDump = true;
				dump.removeAllBlocks();
				dump = pcorpToProduct(dumpFile);
			}//fim do if
			else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", file.getFileName() + " is not a Software Record or PCORP file."));
				isLoadDump = false;
			}//fim do else
          
		} catch (IOException ex) {  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "An error has ocurred while uploaded the file " + file.getFileName()));
			Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);           
		}//fim try-catch 		
	}//fim do método
	
	/**
	 * Método que converte um software record em um objeto do tipo {@link Product}
	 * @param file
	 * @return objeto do tipo Product
	 */
	private Product swrToProduct(String file) {		
		ParserSWR parserSwr = new ParserSWR();		
		return parserSwr.parseContent(file);
	}//fim do método
	
	/**
	 * Método que converte um arquivo pcorp em um objeto do tipo {@link Product}
	 * @param file
	 * @return objeto do tipo Product
	 */
	private Product pcorpToProduct(String file) {		
		ParserPCORP parserPcorp = new ParserPCORP();		
		return parserPcorp.parseContent(file);
	}//fim do método
	
	/**Método que retorna uma correção do MhWeb
	 * 
	 * @param url - https://mhweb.ericsson.se/mhweb/servlet/servletCorrView?corrid=
	 * @param query - Id da correção
	 * @return String - página html
	 */
	private String getPage(String url, String query) {
		
		String aux;
		SSLConnect connect = new SSLConnect();
		boolean isRepeat = false;
		
		connect.setLogin(login);
		connect.setPassword(password);
		connect.setUrl(url);
		
		do {
			aux = connect.receiveHtmlPage(query);
			if (connect.getResponseCode() == SSLConnect.SUCCESS) {				
				if(aux.matches("(?is).*Too many logins.*")) {
					try {
						Thread.sleep(timeout);																							
						isRepeat = true;
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}//fim do try-catch
				} else if(isPageLoad) isRepeat = false;
			} else {
				System.err.print("\t\n Error: " + connect.getResponseCode());
				return "Connection Error" + connect.getResponseCode();
			}//fim do if-else
		} while (isRepeat);
		
		return aux;
	}//fim do método
	
	
	/**Método que preenche uma lista de correções com as respectivas informações:
	 * - Código ASA
	 * - Heading
	 * - Subsystem
	 * - Implementation Instruction
	 * 
	 * @param p - Product
	 * @return - Product 
	 */
	private Product chargeCorrections(Product p) {
		
		String content;
		int index = 0;
		int size = p.numberOfCorrection();
		
		progress = 0;
		isProcessingCorrections= true;		
		
		for (Block block: p.getBlocks()) {
			for (Correction c : block.getCorrections()) {
				
				/* Caso o usuário saia da página antes de concluir o processo*/
				if (!isPageLoad) return null;
				
				content = getPage(corrView, Parser.formatCorrId(c.getCorrectionID())).replaceAll(">\\s+<", "><");
				c.setAsaCode(ParserCorrectionPage.getAsacode(content));
				c.setSlogan(ParserCorrectionPage.getHeading(content));
				c.setSubSystem(ParserCorrectionPage.getSubsystem(content));
				c.setImplementationInstruction(ParserCorrectionPage.getImplementationInstruction(content));					                                     
                progress = ((++index) * 100)/size;                
			}//fim do for
		}//fim do for
		
		/*for (Block block: p.getBlocks()) {
			for (Correction c : block.getCorrections()) {			
				
				System.out.print("\n " + c.getCorrectionID() + " " + c.getSlogan() + " " + c.getSubSystem());
				
			}//fim do for
		}//fim do for*/
		return p;
	}//fim do método 
	
	/**Método que analisa correções e redireciona para enerate_dump.xhtml
	 * 
	 * @return String - redireciona para generate_dump.xhtml
	 */
	public String fetch() {
		
		if (uploadFileBase.getSize() != 0) uploadBase(uploadFileBase);			
		if (uploadFileDump.getSize() != 0) uploadDump(uploadFileDump);	
		
		if (isLoadBase && isLoadDump) {			
			
			result = chargeCorrections(Parser.subtracts(base, dump));
			if (result == null && !isPageLoad) {
				restore();
				SessionUtils.doLogout();
			}//fim do if
			
		} else if (!isLoadBase && !isLoadDump) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "You need upload the files first."));
	    	return null;
	    } else if (!isLoadBase) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Load up SOFTWARE RECORD/PCORP file."));
	    	return null;
	    } else if (!isLoadDump) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Load up PCORP file."));
	    	return null;
	    }//fim dos else if's		
		
		SessionUtils.setAttribute("result", result);
		restore();
		
		return "/core/generate_dump.xhtml?faces-redirect=true";
		
	}//fim do método
	
	/**Método que restaura as variáveis estáticas. Se o bean não for mais 
	 * ResquestScoped as variáveis devem deixar de ser do tipo static.
	 * 
	 */
	private void restore() {
		/* Restore */
		isProcessingCorrections = false;
		isPageLoad = true;
		progress = 0;
	}//fim do método
	
	/**
	 * Evento que é ativado quando o progressbar enche
	 * - zera o progressbar
	 */
	public void onComplete() {
        progress = 0;
    }
	
	public void rollback() {
		if (isProcessingCorrections) isPageLoad = false;		
	}//fim do método
	
	@PreDestroy	
    public void destroy() {
        // This method is called whenever the view scope has been destroyed.
        // That can happen when the user navigates away by a POST which is
        // invoked on this bean, or when the associated session has expired.
		
		
    }//fim do Destroy
	

}//fim da classe
