package br.com.joaoparana.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.annotation.Nullable;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 7596840801911905586L;
	@Id
	@GeneratedValue
	long cpf;
	String name;
	Phone phone;

	public User() {
		super();
		this.name = "*NONE*";
	}

	public User(String name, @Nullable Phone phone) {
		super();
		this.name = checkNotNull(name, "name");
		this.phone = phone;
	}
}
