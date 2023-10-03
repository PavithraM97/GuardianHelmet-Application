package com.example.guardianhelmet;

public class FirestoreVideoModel {
    private String title;
    private String url;

    public FirestoreVideoModel() {
        // Default constructor required for Firestore
    }

    public FirestoreVideoModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
