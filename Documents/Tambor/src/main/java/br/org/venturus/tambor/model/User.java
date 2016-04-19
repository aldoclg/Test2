package br.org.venturus.tambor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
	
	private static final long serialVersionUID = 2657943384625514600L;

	@Id	
	@Column(nullable = false, unique = true, name="ericsson_id", length=10)
	private String idEricsson;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(nullable=false, length=45)
	private String email;
	
	public User() {}

	public String getIdEricsson() {
		return idEricsson;
	}

	public void setIdEricsson(String idEricsson) {
		this.idEricsson = idEricsson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idEricsson == null) ? 0 : idEricsson.hashCode());
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
		User other = (User) obj;
		if (idEricsson == null) {
			if (other.idEricsson != null)
				return false;
		} else if (!idEricsson.equals(other.idEricsson))
			return false;
		return true;
	}	

}
