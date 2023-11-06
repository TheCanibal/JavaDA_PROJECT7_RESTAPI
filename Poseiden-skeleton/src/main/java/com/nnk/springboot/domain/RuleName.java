package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rulename")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;

    public RuleName() {
	super();
    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
	this.name = name;
	this.description = description;
	this.json = json;
	this.template = template;
	this.sqlStr = sqlStr;
	this.sqlPart = sqlPart;
    }

    public Integer getId() {
	return Id;
    }

    public void setId(Integer id) {
	Id = id;
    }

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

    public String getJson() {
	return json;
    }

    public void setJson(String json) {
	this.json = json;
    }

    public String getTemplate() {
	return template;
    }

    public void setTemplate(String template) {
	this.template = template;
    }

    public String getSqlStr() {
	return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
	this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
	return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
	this.sqlPart = sqlPart;
    }

}
