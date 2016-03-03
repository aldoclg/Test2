package br.org.venturus.dumpalignment.utils.filehandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import br.org.venturus.dumpalignment.model.Result;
import br.org.venturus.dumpalignment.utils.parser.Parser;

public class FileHandler implements Serializable {

	private static final long serialVersionUID = -132408431437185535L;
	private static final int EOF = -1;
	
	/**Método que cria o arquivo de dump
	 * 
	 * @param absolutePath - o caminho que será criado o arquivo
	 * @param resultTable 
	 */
	public static final void createDumpFile(String absolutePath, List<Result> resultTable) {
		
		try {
			
			FileWriter arq = new FileWriter(absolutePath);
			PrintWriter recordDump = new PrintWriter(arq);
			
			recordDump.println("\nIOTXP:Dump Alignment;");
			
			for (Result r : resultTable) {
				if (r.isLoad()) recordDump.println("\n" + r.getAsaCode());
			}//fim do for
			
			recordDump.println("\n\nIOTXP:: " + printTimeStamp() + ";");
			
			if (recordDump != null)	recordDump.close();
			if (arq != null) arq.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//fim do try-catch		
		
	}//fim do método
	
	/**Método que cria arquivo com comandos AXE
	 * 
	 * @param pcorx - comando AXE
	 */
	public static final void createPCORXFile(String path, String pcorx, List<Result> resultTable) {
		
		try {
			FileWriter arq = new FileWriter(path + pcorx + ".cmd");
			PrintWriter pcorxFile = new PrintWriter(arq);
			
			pcorxFile.println("\nIOTXP:Dump Alignment;");
			
			for (Result r : resultTable) {				
				if (r.isLoad())
					pcorxFile.println("\n" + Parser.getPCORI(r.getAsaCode()).replaceAll("PCORI", pcorx) + "\n");
			}//fim do for
			
			pcorxFile.println("\nIOTXP: " + printTimeStamp() + ";");	
			
			if (pcorxFile != null) pcorxFile.close();
			if (arq != null) arq.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//fim do try-catch		
		
	}//fim do método
	
	public static final void compactFiles(String pathFiles, String absoluteZipFile) {
		try {
			FileOutputStream pack = new FileOutputStream(absoluteZipFile);
			ZipOutputStream zipOut = new ZipOutputStream(pack);
			File dir = new File(pathFiles);
			
			for (File file : dir.listFiles()) {
				zipOut.putNextEntry(new ZipEntry(file.getName().toString()));
				
				@SuppressWarnings("resource")
				FileInputStream inputStream = new FileInputStream(file);
				
				int content;
				while ((content = inputStream.read()) != EOF) {
					zipOut.write(content);
				}//fim do while
				
				zipOut.closeEntry();
				
			}//fim do foreach			
			
			if (zipOut != null) zipOut.close();
			if (pack != null) pack.close();
			
		} catch (IOException e) {
		    e.printStackTrace();
		}//fim do try-catch
	}//fim do método
	
	/**Método que remove todos aquivos da pasta package_files
	 * 
	 */
	public static final void removeFiles(String uri) {		
		File dir = new File(uri);			
		try {
			for (File file : dir.listFiles()) {		
				Path path = Paths.get(file.getPath());				
				Files.deleteIfExists(path);							
			}//fim do for 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//fim do try-catch
	}//fim do método
	
	
	/**Método que retorna data e hora
	 * 
	 * @return
	 */
	private static final String printTimeStamp() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();	  

        return dateFormat.format(date);
	}//fim do método

}
