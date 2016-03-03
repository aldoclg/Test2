package br.org.venturus.dumpalignment.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**Classe para objetos do tipo Block, onde estar�o contidos nome, vers�o, revis�o e lista de corre��es
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
public class Block implements Comparable<Block>, java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
    private String name;
    private String revision;
    private String version;
    private Map<String, Correction> corrections;
   
    
    public Block() {	
    	this.name = "";
    	this.version = "";
    	this.revision = "";
        this.corrections = new HashMap<String, Correction>();
    }//fim do construtor

    public String getName() {
        return name;
    }//fim do m�todo

    public void setName(String name) {
        this.name = name;
    }//fim do m�todo

    public String getVersion() {
        return version;
    }//fim do m�todo

    public void setVersion(String version) {
        this.version = version;
    }//fim do m�todo    

    /** M�todo que verifica se o objeto bloco possui uma determinada corre��o
     * 
     * @param correctionID
     * @return Boolean - true ou false
     */
    public boolean hasCorrection(String correctionID) {        
        return this.corrections.containsKey(correctionID);
    }//fim do m�todo
    
    /** M�todo que retorna um objeto corre��o
     * 
     * @param correctionID
     * @return Correction - corre��o
     */
    public Correction getCorrection(String correctionID) {
    	return this.corrections.get(correctionID);
    }//fim do m�todo   
    
       
    public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}
	
	/** M�todo que adiciona um objeto corre��o
	 * 
	 * @param correction
	 */
	public void addCorrection(Correction correction) {
    	this.corrections.put(correction.getCorrectionID(), correction);
    }//fim do m�todo
	
    /** M�todo que remove um objeto corre��o
     * 
     * @param correctionID
     */
    public void removeCorrection(String correctionID) {
    	this.corrections.remove(correctionID);
    }//fim do m�todo 
    
    /** M�todo que retorna lista de corre��oe
     * 
     * @return Collection<Correction> - Lista de corre��es
     */
    public Collection<Correction> getCorrections() {
    	return corrections.values();
    }//fim do m�todo        

    public int getSize() {
		return corrections.size();
	}//fim do m�todo	   
    

	@Override
    public int compareTo(Block another) {
       if (this.name.compareTo(another.name) < 0)
            return -1;
        else if (this.name.compareTo(another.name) > 0)
            return 1;
        else return 0;      
    }//fim do m�todo

    @Override
    public String toString() {
        return this.name;        
    }//fim do m�todo   
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }//fim do m�todo

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }//fim do m�todo
    
}