package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;
    @NotNull(message = "curve Id can't be null")
    private Integer CurveId;
    private Timestamp asOfDate;
    @NotNull(message = "term can't be null")
    @DecimalMin("1.00")
    @Digits(integer = 4, fraction = 2, message = "Maximum 4 digits + 2 digits after dot")
    private Double term;
    @NotNull(message = "value can't be null")
    @DecimalMin("1.00")
    @Digits(integer = 4, fraction = 2, message = "Maximum 4 digits + 2 digits after dot")
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
