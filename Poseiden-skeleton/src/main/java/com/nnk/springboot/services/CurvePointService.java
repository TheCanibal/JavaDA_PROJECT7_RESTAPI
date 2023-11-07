package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curveRepository;

    public List<CurvePoint> getAllCurvePoints() {
	return curveRepository.findAll();
    }

    public Optional<CurvePoint> getCurveById(Integer id) {
	return curveRepository.findById(id);
    }

    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
	return curveRepository.save(curvePoint);
    }

    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
	return curveRepository.save(curvePoint);
    }

    public void deleteCurvePoint(CurvePoint curvePoint) {
	curveRepository.delete(curvePoint);
    }
}
