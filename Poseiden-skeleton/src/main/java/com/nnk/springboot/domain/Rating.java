package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

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
