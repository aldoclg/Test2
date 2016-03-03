package br.org.venturus.dumpalignment.model;

import java.util.List;
import java.util.ArrayList;




/**Classe para o objeto do tipo Correction, no qual ser�o contidos id, slogan, sub-system, endere�os de PCORL e vari�veis da corre��o
 * 
 * @author vntalgr
 *
 * @version 1.0
 */


public class Correction implements Comparable<Correction>, java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	

    protected String correctionID;	
    private String slogan;	
    private String subSystem;
    private String asaCode;
    private int asaSize;
    private String implementationInstruction;
    private List<String> variables;
    private List<String> instructionAddress;    
    
    public Correction() {    	
    	this.variables = new ArrayList<String>();
        this.instructionAddress = new ArrayList<String>();
    }

    public String getCorrectionID() {
        return correctionID;
    }

    /** M�todo que que retorna lista de endere�os
     * 
     * @return List<String> - Lista de endere�os
     */
    public List<String> getInstructionAddresses() {
        return instructionAddress;
    }

    public void setInstructionAddresses(List<String> instructionAddress) {
        this.instructionAddress = instructionAddress;
    }
    
    /** M�todo que adiciona um endere�o a lista de endere�os de PCORL's
     * 
     * @param instructionAddress
     */
    public void addIntructionAddress(String instructionAddress) {
        this.instructionAddress.add(instructionAddress);        
    }
    
    public boolean removeIntructionAddress(String instructionAddress) {
        return this.instructionAddress.remove(instructionAddress);
    }    
    
    public String getInstructionAddress(int index) {
    	return this.instructionAddress.get(index);
    }
    
    public void setCorrectionID(String corrrectionID) {
        this.correctionID = corrrectionID;
    }
    
    public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public List<String> getVariables() {
        return variables;
    }
    
    public void addVariable(String variable) {
        this.variables.add(variable);
    }
	
    public boolean removeVariable(String variable) {
        return this.variables.remove(variable);
    }
    
    public void removeVariable(int index) {
        this.variables.remove(index);
    }
	
    public String getVariable(int index){
        return this.variables.get(index);
    }   
    
    public String getAsaCode() {
		return asaCode;
	}

	public void setAsaCode(String asaCode) {
		this.asaCode = asaCode;
	}

	public String getImplementationInstruction() {
		return implementationInstruction;
	}

	public void setImplementationInstruction(String implementationInstruction) {
		this.implementationInstruction = implementationInstruction;
	}

	public int getAsaSize() {
		return asaSize;
	}

	public void setAsaSize(int asaSize) {
		this.asaSize = asaSize;
	}

	/** M�todo que verifica se existe uma vari�vel
     * 
     * @param var
     * @return String - N�mero da vari�vel 
     */
    public boolean hasVariable(String var) {
    	return this.variables.contains(var);
    }
    
    /** M�todo que verifica se existe um endere�o
     * 
     * @param var
     * @return String - Endere�o em hexadecimal
     */
    public boolean hasInstructionAddress(String corrid) {
    	return this.instructionAddress.contains(corrid);
    }
    
    @Override
    public int compareTo(Correction another) {
        if (this.correctionID.compareTo(another.correctionID) < 0)
            return -1;
        else if (this.correctionID.compareTo(another.correctionID) > 0)
            return 1;
        else return 0;                
    } 
    
    @Override
    public String toString() {
        return this.correctionID;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.correctionID != null ? this.correctionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Correction other = (Correction) obj;
        if ((this.correctionID == null) ? (other.correctionID != null) : !this.correctionID.equals(other.correctionID)) {
            return false;
        }
        return true;
    }
    
    
    
}//fim da classe
