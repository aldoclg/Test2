package br.org.venturus.tambor.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="STATUS")
public class Status implements Serializable, Comparable<Status> {
	
	private static final long serialVersionUID = -1691336917080533059L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false, length=20)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="status_parent_id", nullable=false)	
	private Status parent;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval=true, mappedBy="parent")		
	private Set<Status> children = new TreeSet<Status>();	

	public Status() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Status getParent() {
		return parent;
	}

	public void setParent(Status parent) {
		this.parent = parent;
	}

	public Set<Status> getChildren() {
		return children;
	}

	public void setChildren(Set<Status> children) {
		this.children = children;
	}
	
	public void addChild(Status child) {
		this.children.add(child);
	}//fim do método

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (id != other.id)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public int compareTo(Status o) {
		if (this.id < o.id) return -1;
		if (this.id > o.id) return 1;
		return 0;
	}
	

}
