package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer TradeId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotNull(message = "Buy quantity can't be null")
    @Digits(integer = 4, fraction = 2, message = "Maximum 4 digits + 2 digits after dot")
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
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

    public Trade() {
	super();
    }

    public Trade(String account, String type) {
	this.account = account;
	this.type = type;
    }

    public Trade(String account, String type, Double buyQuantity) {
	this.account = account;
	this.type = type;
	this.buyQuantity = buyQuantity;
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

    public Double getBuyQuantity() {
	return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
	this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {
	return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
	this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {
	return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
	this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
	return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
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
