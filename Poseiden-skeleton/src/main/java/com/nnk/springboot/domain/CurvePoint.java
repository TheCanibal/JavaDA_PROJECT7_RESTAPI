package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;
    private Integer CurveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;

    public CurvePoint() {
	super();
    }

    public CurvePoint(Integer curveId, double term, double value) {
	this.CurveId = curveId;
	this.term = term;
	this.value = value;
    }

    public Integer getId() {
	return Id;
    }

    public void setId(Integer id) {
	Id = id;
    }

    public Integer getCurveId() {
	return CurveId;
    }

    public void setCurveId(Integer curveId) {
	CurveId = curveId;
    }

    public Timestamp getAsOfDate() {
	return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
	this.asOfDate = asOfDate;
    }

    public Double getTerm() {
	return term;
    }

    public void setTerm(Double term) {
	this.term = term;
    }

    public Double getValue() {
	return value;
    }

    public void setValue(Double value) {
	this.value = value;
    }

    public Timestamp getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
	this.creationDate = creationDate;
    }

}
