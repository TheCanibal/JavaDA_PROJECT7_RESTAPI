package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;
    @NotBlank(message = "moodysRating is mandatory")
    private String moodysRating;
    @NotBlank(message = "sandPRating is mandatory")
    private String sandPRating;
    @NotBlank(message = "fitchRating is mandatory")
    private String fitchRating;
    @NotNull(message = "order number can't be null")
    @Min(1)
    @Digits(integer = 4, fraction = 0, message = "Maximum 4 digits")
    private Integer orderNumber;

    public Rating() {
	super();
    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
	this.moodysRating = moodysRating;
	this.sandPRating = sandPRating;
	this.fitchRating = fitchRating;
	this.orderNumber = orderNumber;
    }

    public Integer getId() {
	return Id;
    }

    public void setId(Integer id) {
	Id = id;
    }

    public String getMoodysRating() {
	return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
	this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
	return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
	this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
	return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
	this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
	return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
	this.orderNumber = orderNumber;
    }

}
