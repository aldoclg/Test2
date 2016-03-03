package br.org.venturus.dumpalignment.model;

import java.util.List;
import java.util.ArrayList;

/** Classe do objeto tipo Product, no qual ser�o contidos vers�o e lista de blocos
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
public class Product implements Comparable<Product>, java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
    private String version;
    private List<Block> blocks;    
    
    public Product() {
    	this.version = "";
    	this.blocks = new ArrayList<Block>();
    }
    
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	/** M�todo que retorna lista de blocos
	 * 
	 * @return List<Block> - Lista de blocos
	 */
	public List<Block> getBlocks() {
		return blocks;
	}
	
	/** M�todo que recebe uma lista de blocos
	 * 
	 * @param blocks
	 */
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}  
	
	/** M�todo que adiciona um bloca a lista de blocos
	 * 
	 * @param block
	 */
	public void addBlock(Block block) {
		this.blocks.add(block);
	}
	
	/** M�todo que remove um bloc
	 * 
	 * @param block
	 */
	public void removeBlock(Block block) {
		this.blocks.remove(block);
	}
	
	/** Metodo que remove todos os blocos
	 * 
	 */
	public void removeAllBlocks() {
		blocks.removeAll(getBlocks());
	}
	
	public Block getBlock(int index){
		return this.blocks.get(index);		
	}
	
	/** M�todo que verifica se existe um bloco
	 * 
	 * @param block
	 * @return boolean - true ou false
	 */
	public boolean hasBlock(Block block) {
        return this.blocks.contains(block);
    }
	
	/** M�todo que retorna a posi��o(index) de um bloco na lista de blocos
	 * 
	 * @param block
	 * @return int - Index
	 */
    public int indexOf(Block block) {
        return this.blocks.indexOf(block);
    }
    
    /** M�todo que retorna o n�mero de blocos na lista
     * 
     * @return int - tamanho da lista de blocos
     */
    public int getSize() {
		return blocks.size();
	}

    /** M�todo que retorna o n�mero total de corre��es contido na lista de blocos
     * 
     * @return int - total de corre��es
     */
	public int numberOfCorrection() {
    	int size = 0;
    	for (Block block : blocks) {
			size += block.getCorrections().size();
		}
    	return size;
    }
	
	@Override
    public int compareTo(Product another) {
       if (this.version.compareTo(another.version) < 0)
            return -1;
        else if (this.version.compareTo(another.version) > 0)
            return 1;
        else return 0;      
    }

    @Override
    public String toString() {
        return this.version;
    }   
    
	
}