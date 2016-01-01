package br.com.joaoparana.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	long cpf;
	String name;

	public User() {
		super();
		this.name = "*NONE*";
	}

	public User(String name) {
		super();
		this.name = name;
	}
}
