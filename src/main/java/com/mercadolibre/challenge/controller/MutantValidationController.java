package com.mercadolibre.challenge.controller;

import com.mercadolibre.challenge.dto.MutantValidationRequest;
import com.mercadolibre.challenge.dto.StatsResult;
import com.mercadolibre.challenge.dto.ValidationError;
import com.mercadolibre.challenge.service.MutantValidationService;
import com.mercadolibre.challenge.service.StatsDnaHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
public class MutantValidationController {

    @Autowired
    private MutantValidationService service;

    @Autowired
    private StatsDnaHistoryService statsDnaHistoryService;

    @PostMapping("/mutant/")
    public ResponseEntity<Map<String, Boolean>> isMutant(@Valid @RequestBody MutantValidationRequest request) throws ValidationError {
        service.validateStructure(request.getDna());
        Boolean result = service.isMutant(request.getDna());

        if (result) {
            return ResponseEntity.ok().body(Collections.singletonMap("is_mutant", true));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("is_mutant", false));
        }
    }

    @GetMapping("/stats")
    public StatsResult stats() {

        return statsDnaHistoryService.getStatsDnaValidationHistory();
    }


}
