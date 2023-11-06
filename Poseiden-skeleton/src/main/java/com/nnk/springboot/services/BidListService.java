package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> getAllBidLists() {
	return bidListRepository.findAll();
    }

    public Optional<BidList> getBidListById(Integer id) {
	return bidListRepository.findById(id);
    }

    public BidList addBidList(BidList bid) {
	return bidListRepository.save(bid);
    }

    public BidList updateBidList(BidList bid) {
	return bidListRepository.save(bid);
    }

    public void deleteBidList(BidList bid) {
	bidListRepository.delete(bid);
    }

}