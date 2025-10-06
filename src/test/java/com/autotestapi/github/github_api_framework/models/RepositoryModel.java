package com.autotestapi.github.github_api_framework.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import lombok.Data;
//import lombok.experimental.Accessors;

//@Data // Gera Getters, Setters, toString, hashCode, equals
//@Accessors(chain =true) // Permite chamadas encadeadas como .setName().setPrivate()
@JsonIgnoreProperties(ignoreUnknown = true) // <-- ADICIONAR ESTA LINHA
public class RepositoryModel {
	
	//Campos essenciaais para a API do Github
	private String name;
	private String description;
	private boolean isPrivate; // ackson mapeia "private" automaticamente para isPrivate
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	private String homepage;
	
	//Campos que aparecem na resposta (Response)
	private int id;
	private String full_name;
	private String html_url;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getHtml_url() {
		return html_url;
	}
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	

}
