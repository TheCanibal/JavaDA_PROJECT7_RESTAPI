package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer TradeId;
    private String account;
    private String type;
    private double buyQuantity;
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public Trade(String account, String type) {
	this.account = account;
	this.type = type;
    }

    public Integer getTradeId() {
	return TradeId;
    }

    public void setTradeId(Integer tradeId) {
	TradeId = tradeId;
    }

    public String getAccount() {
	return account;
    }

    public void setAccount(String account) {
	this.account = account;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public double getBuyQuantity() {
	return buyQuantity;
    }

    public void setBuyQuantity(double buyQuantity) {
	this.buyQuantity = buyQuantity;
    }

    public double getSellQuantity() {
	return sellQuantity;
    }

    public void setSellQuantity(double sellQuantity) {
	this.sellQuantity = sellQuantity;
    }

    public double getBuyPrice() {
	return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
	this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
	return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
	this.sellPrice = sellPrice;
    }

    public Timestamp getTradeDate() {
	return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
	this.tradeDate = tradeDate;
    }

    public String getSecurity() {
	return security;
    }

    public void setSecurity(String security) {
	this.security = security;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getTrader() {
	return trader;
    }

    public void setTrader(String trader) {
	this.trader = trader;
    }

    public String getBenchmark() {
	return benchmark;
    }

    public void setBenchmark(String benchmark) {
	this.benchmark = benchmark;
    }

    public String getBook() {
	return book;
    }

    public void setBook(String book) {
	this.book = book;
    }

    public String getCreationName() {
	return creationName;
    }

    public void setCreationName(String creationName) {
	this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
	this.creationDate = creationDate;
    }

    public String getRevisionName() {
	return revisionName;
    }

    public void setRevisionName(String revisionName) {
	this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
	return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
	this.revisionDate = revisionDate;
    }

    public String getDealName() {
	return dealName;
    }

    public void setDealName(String dealName) {
	this.dealName = dealName;
    }

    public String getDealType() {
	return dealType;
    }

    public void setDealType(String dealType) {
	this.dealType = dealType;
    }

    public String getSourceListId() {
	return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
	this.sourceListId = sourceListId;
    }

    public String getSide() {
	return side;
    }

    public void setSide(String side) {
	this.side = side;
    }

}
