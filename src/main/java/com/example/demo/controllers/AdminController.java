package com.example.demo.controllers;

import com.example.demo.entities.InfrastructureSportive;
import com.example.demo.metier.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/infrastructures")
    public ResponseEntity<InfrastructureSportive> createInfrastructure(@RequestBody InfrastructureSportive infrastructure) {
        InfrastructureSportive created = adminService.creerInfrastructureSportive(infrastructure.getNom(), infrastructure.getCapacite(), infrastructure.getAdresse());
        return ResponseEntity.ok(created);
    }

}