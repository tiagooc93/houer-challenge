package com.example.houer_challenge.controller;


import com.example.houer_challenge.dto.SchoolCreateResponseDTO;
import com.example.houer_challenge.dto.SchoolDeleteDTO;
import com.example.houer_challenge.dto.SchoolUpdateDTO;
import com.example.houer_challenge.model.School;
import com.example.houer_challenge.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school")
@CrossOrigin
@Slf4j
public class SchoolController {
    
    @Autowired
    SchoolService schoolService;

    @PostMapping("/create")
    public ResponseEntity<SchoolCreateResponseDTO> createSchool() {
        log.info("POST /api/school/create");

        Long id = schoolService.createSchool();
        log.info("Returning id after creation: {}", id);
        SchoolCreateResponseDTO schoolCreate= new SchoolCreateResponseDTO(id);
        return ResponseEntity.ok(schoolCreate);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        log.info("POST /api/school/upload");

        try {
            List<Map<String, String>> csvData = SchoolService.parseCsv(file);
            if (csvData.isEmpty()) {
                return ResponseEntity.status(400).body("CSV is empty or invalid!");
            }
            schoolService.saveAllCsvSchools(csvData);
            return ResponseEntity.ok("CSV processed and saved successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to process CSV: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<List<Map<String, String>>> getCsvData() {
        log.info("GET /api/school/data");
        List<School> schools = schoolService.getAllSchools();

        if (schools.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<Map<String, String>> response = SchoolService.convertSchoolsToMap(schools);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSchool(@RequestBody SchoolDeleteDTO schoolDeleteDTO){
        log.info("DELETE /api/school/delete");
        log.info("School Delete DTO: {}", schoolDeleteDTO.toString());
        try {
            schoolService.deleteSchool(schoolDeleteDTO);
            return ResponseEntity.ok("Schools deleted !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server internal error");

        }
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<String> updateSchool(@RequestBody SchoolUpdateDTO updateSchoolDTO){
        log.info("PUT /api/school/update");
        try {
            schoolService.updateSchool(updateSchoolDTO);
            return ResponseEntity.ok("Schools updated !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server internal error");
        }
    }

}
