package br.org.venturus.dumpalignment.model;

import java.io.Serializable;

public class Result extends Correction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7200552996750374288L;
	
	private String block;
	private String productNumber;
	private String revision;
	private boolean isLoad = true;
	
	
	public String getBlock() {
		return block;
	}
	
	public void setBlock(String block) {
		this.block = block;
	}
	
	public String getProductNumber() {
		return productNumber;
	}
	
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public boolean isLoad() {
		return isLoad;
	}

	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}			
	
}//fim da classe
