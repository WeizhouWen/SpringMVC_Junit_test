package com.example.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DocumentService {
    @Autowired
    private DocumentDAO documentDAO;

    public Integer createDocument(Document document) throws ClassNotFoundException {
        if (document.getContent() == null) {
            throw new IllegalArgumentException("Document content is required.");
        }

        return documentDAO.createDocument(document);
    }

    public Document getDocument(Integer id) throws Exception {
        Document document = documentDAO.getDocument(id);
        if (document == null) {
            throw new Exception("Document not found.");
        }
        return document;
    }

    public List<Document> getAllDocuments() throws ClassNotFoundException {
        return documentDAO.getAllDocuments();
    }

    public boolean updateDocument(Integer id, Document updatedDocument) throws Exception {
        if (updatedDocument.getContent() == null) {
            throw new IllegalArgumentException("Document content is required.");
        }

        if (!documentDAO.updateDocument(id, updatedDocument)) {
            throw new Exception("Document not found.");
        }
        return true;
    }

    public boolean deleteDocument(Integer id) throws Exception {
        if (!documentDAO.deleteDocument(id)) {
            throw new Exception("Document not found.");
        }
        return true;
    }
}
