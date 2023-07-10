package com.example.mvc;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    public DocumentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDocument_ValidDocument_ReturnsCreatedStatus() throws ClassNotFoundException {
        Document document = new Document();
        document.setId(6);
        document.setContent("Test Content");

        when(documentService.createDocument(any(Document.class))).thenReturn(document.getId());

        ResponseEntity<Document> response = documentController.createDocument(document);

        verify(documentService, times(1)).createDocument(document);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Document created with ID: " + document.getId(), response.getBody());
    }

    @Test
    public void getDocument_ExistingDocument_ReturnsDocument() throws Exception {
        Document document = new Document();
        document.setId(1);
        document.setContent("Test Content");

        when(documentService.getDocument(1)).thenReturn(document);

        ResponseEntity<Document> response = (ResponseEntity<Document>) documentController.getDocument(1);

        verify(documentService, times(1)).getDocument(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(document, response.getBody());
    }

    @Test
    public void getDocument_NonexistentDocument_ReturnsNotFoundStatus() throws Exception {
        when(documentService.getDocument(1)).thenReturn(null);

        ResponseEntity<Document> response = (ResponseEntity<Document>) documentController.getDocument(1);

        verify(documentService, times(1)).getDocument(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void updateDocument_ValidDocument_ReturnsOkStatus() throws Exception {
        Document document = new Document();
        document.setId(1);
        document.setContent("Updated Content");

        ResponseEntity<Document> response = documentController.updateDocument(1, document);

        verify(documentService, times(1)).updateDocument(document.getId(),document);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Document updated successfully", response.getBody());
    }

    @Test
    public void updateDocument_NonexistentDocument_ReturnsNotFoundStatus() throws Exception {
        Document document = new Document();
        document.setId(1);
        document.setContent("Updated Content");

        doThrow(IllegalArgumentException.class).when(documentService).updateDocument(1,document);

        ResponseEntity<Document> response = documentController.updateDocument(1, document);

        verify(documentService, times(1)).updateDocument(document.getId(),document);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteDocument_ExistingDocument_ReturnsOkStatus() throws Exception {
        ResponseEntity<String> response = documentController.deleteDocument(1);

        verify(documentService, times(1)).deleteDocument(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Document deleted successfully", response.getBody());
    }

    @Test
    public void deleteDocument_NonexistentDocument_ReturnsNotFoundStatus() throws Exception {
        doThrow(IllegalArgumentException.class).when(documentService).deleteDocument(1);

        ResponseEntity<String> response = documentController.deleteDocument(1);

        verify(documentService, times(1)).deleteDocument(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Add more test cases for other methods in DocumentController
}
