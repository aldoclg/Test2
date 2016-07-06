package br.unesp.rc.scrumboard.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que representa o modelo de dados para um usuário.
 * @author Vinicius
 */
@Entity
@Table(name = "User")
public class User implements Serializable{
	
    private static final long serialVersionUID = -1789125023002149384L;

	@Id
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    
    @Column
    private String name;
    
    @Column
    private String password;

    public User() {
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString(){
        return "User{email = "+this.email+", name = "+this.name+"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return this.email.equals(user.getEmail()) 
               && this.password.equals(user.getPassword());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return email.hashCode() ^ password.hashCode();
    }
}
