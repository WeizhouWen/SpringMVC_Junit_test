package com.example.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) throws ClassNotFoundException {
        try {
            Integer id = documentService.createDocument(document);
            //return ResponseEntity.status(HttpStatus.CREATED).body("Document created with ID: " + id);
            return (ResponseEntity<Document>) ResponseEntity.status(HttpStatus.CREATED).body(document);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(document);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocument(@PathVariable Integer id) {
        try {
            Document document = documentService.getDocument(id);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() throws ClassNotFoundException {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Integer id, @RequestBody Document document) throws Exception {
        try {
            boolean updated = documentService.updateDocument(id, document);
            if (updated) {
                return ResponseEntity.ok(document);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(document);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Integer id) {
        try {
            boolean deleted = documentService.deleteDocument(id);
            if (deleted) {
                return ResponseEntity.ok("Document deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}