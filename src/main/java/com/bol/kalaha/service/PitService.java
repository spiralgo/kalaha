package com.bol.kalaha.service;

import com.bol.kalaha.repository.PitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PitService {

    @Autowired
    private PitRepository pitRepository;
    public PitService(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }
}
