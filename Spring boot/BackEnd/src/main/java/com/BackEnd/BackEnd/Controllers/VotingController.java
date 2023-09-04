package com.BackEnd.BackEnd.Controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voting")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping("/vote")
    //public ResponseEntity<String> vote(@RequestBody int candidateName) {
    public ResponseEntity<String> vote() {
        BigInteger bigInteger = BigInteger.valueOf(1);
        try {
            votingService.voteForCandidate(bigInteger);
            return ResponseEntity.ok("Voto registrado con éxito!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}