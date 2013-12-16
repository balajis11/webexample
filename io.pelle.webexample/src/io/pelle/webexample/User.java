package io.pelle.webexample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name="ID", nullable = false)
	  private long id;
	  
	  @Column(name="NAME", nullable = false)
	  private String name;

	  @Column(name="MAIL")
	  private String mail;
	  
	  public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public long getId() {
	    return id;
	  }
	  
	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }
	  
	  public void setName(String name) {
	    this.name = name;
	  }

}
