package br.org.venturus.dumpalignment.bean;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedProperty;




import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import br.org.venturus.dumpalignment.model.Correction;
import br.org.venturus.dumpalignment.model.Product;
import br.org.venturus.dumpalignment.model.Block;
import br.org.venturus.dumpalignment.model.Result;
import br.org.venturus.dumpalignment.utils.parser.Parser;
import br.org.venturus.dumpalignment.utils.session.SessionUtils;
import br.org.venturus.dumpalignment.utils.filehandler.*;

@ManagedBean(name = "generateDumpBean")
@SessionScoped
public class GenerateDumpBean implements Serializable {
	
	private static final long serialVersionUID = 43615580558711213L;
	
	private Result selectedRow = new Result();
	private List<Result> resultTable = new ArrayList<Result>();
	private StreamedContent packageDump;
	private boolean isPCORI = false;
	private boolean isDump = true;
	private boolean isPCORE = false;
	private boolean isPCORS = false;
	private boolean isPCORR = false;
	private String command = "";
	private static int index;	
	private boolean isTop = false;
	
	private String PATH;

	@PostConstruct
	public void init() {			
		
		resultTable = loadCorrections((Product) SessionUtils.getAttribute("result"));
		
		PATH = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");	
	}//fim do método	
	
	private List<Result> loadCorrections (Product p) {
		
		List<Result> r = new ArrayList<>();
		
		for (Block block: p.getBlocks()) {
			for (Correction c : block.getCorrections()) {	
				
				Result row = new Result();
				
				row.setCorrectionID(Parser.formatCorrId(c.getCorrectionID()));
				row.setBlock(block.getName());
				row.setProductNumber(block.getVersion());
				row.setRevision(block.getRevision());
				row.setSlogan(c.getSlogan());
				row.setSubSystem(c.getSubSystem());
				row.setAsaCode(c.getAsaCode());
				row.setImplementationInstruction(c.getImplementationInstruction());
				//row.setLoad(true);
				
				r.add(row);
				
			}//fim do for
		}//fim do for
		
		return r;
	}
	

	public Result getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Result selectedRow) {
		this.selectedRow = selectedRow;
	}

	/* Checkboxes */
	public boolean isPCORI() {
		return isPCORI;
	}

	public void setPCORI(boolean isPCORI) {
		this.isPCORI = isPCORI;
	}

	public boolean isDump() {
		return isDump;
	}

	public void setDump(boolean isDump) {
		this.isDump = isDump;
	}

	public boolean isPCORE() {
		return isPCORE;
	}

	public void setPCORE(boolean isPCORE) {
		this.isPCORE = isPCORE;
	}

	public boolean isPCORS() {
		return isPCORS;
	}

	public void setPCORS(boolean isPCORS) {
		this.isPCORS = isPCORS;
	}

	public boolean isPCORR() {
		return isPCORR;
	}

	public void setPCORR(boolean isPCORR) {
		this.isPCORR = isPCORR;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	/**Método que passa os dados para a tabela
	 * 
	 * @return result - List<Result>
	 */
	public List<Result> getResultTable() {
		return resultTable;
	}//fim do método
	
	public void getResultTable(List<Result> resultTable) {
		this.resultTable = resultTable;
	}//fim do método
	
	/**
	 * Método que seta todos as variavéis load do objet {@link Result} para true
	 */
	public void checkAll() {		
		for (Result result : resultTable) {
			result.setLoad(true);
		}//fim do for		
	}//fim do método
	
	/**
	 * Método que seta todos as variavéis load do objet {@link Result} para false
	 */
	public void unCheckAll() {		
		for (Result result : resultTable) {
			result.setLoad(false);
		}//fim do for		
	}//fim do método
	
	/**Método que chama a página downloadOption.xhtml para o 
	 * usuário escolher quais arquivos serão baixados
	 * 
	 */
	public void donwloadOptionOpen() {
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("contentHeight", 400);
        options.put("modal", true);
        
        RequestContext.getCurrentInstance().openDialog("/core/downloadOption.xhtml", options, null);
	}//fim do método
	
	/**Método que chama a página addCommand.xhtml para o 
	 * usuário adicionar commandos à tabela
	 * 
	 */
	public void addCommandOpen() {
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("width", 400);
        options.put("contentHeight", 400);
        options.put("modal", true);
        
        RequestContext.getCurrentInstance().openDialog("/core/addCommand.xhtml", options, null);
	}//fim do método
	
	/**
	 * Método que fecha a caixa de dialogo
	 */
	public void dialogClose() {
		 RequestContext.getCurrentInstance().closeDialog(null);
	}//fim do método
	
	/**Método que cria os arquivos de dump e retorna-os compactados em um arquivo .zip
	 * 
	 * @return DefaultStreamedContent - arquivo zip
	 */
	public StreamedContent getPackageDump() {
		
		FileHandler.removeFiles(PATH + "/package_files");
		
		if (!(isDump || isPCORI || isPCORS || isPCORE || isPCORR)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn!", "Choose at less a option..."));
			return null;
		}//fim do if
		else {			
			if (isDump)  FileHandler.createDumpFile(PATH + "/package_files/dump.cmd", resultTable);
			if (isPCORI) FileHandler.createPCORXFile(PATH + "/package_files/", "PCORI", resultTable);
			if (isPCORS) FileHandler.createPCORXFile(PATH + "/package_files/", "PCORS", resultTable);		
			if (isPCORE) FileHandler.createPCORXFile(PATH + "/package_files/", "PCORE", resultTable);
			if (isPCORR) FileHandler.createPCORXFile(PATH + "/package_files/", "PCORR", resultTable);
		}//fim do else
		
		FileHandler.compactFiles(PATH + "/package_files", PATH + "/zip_file/package.zip");
		
		try {
			
			InputStream stream = new FileInputStream(PATH + "/zip_file/package.zip");
			packageDump = new DefaultStreamedContent(stream, "application/zip", "package.zip");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}//fim do try-catch 
			
		return packageDump;
	}//fim do método

	public void setPackageDump(StreamedContent packageDump) {
		this.packageDump = packageDump;
	}
	
	/**Método que add um objeto Result a tabela
	 * 
	 */
	public void addCommand() {
		Result r = new Result();
		r.setCorrectionID("CMD-" + index);
		r.setSlogan("COMMAND " + index);
		r.setAsaCode(command);
		r.setSubSystem("N/A");
		r.setProductNumber("COMMAND");		
		r.setBlock("N/A");
		r.setImplementationInstruction("COMMAND " + index++);
		command = "";
		RequestContext.getCurrentInstance().closeDialog(r);	
	}//fim do método
	
	/**Evento que adiciona uma entidade a tabela
	 * 
	 * @param event
	 */
	public void onAddRow(SelectEvent event) {
		Result r = (Result) event.getObject();
		if (this.isTop) this.resultTable.add(0, r);
		else this.resultTable.add(r);	
		this.isTop = false;
	}//fim do método
	
	/**Evento que é chamado toda vez que é movido uma linha da tabela
	 * 
	 * @param event
	 */
	public void onRowReoder(ReorderEvent event) {
		
		
		
	}//fim do método
	

}//fim da classe
