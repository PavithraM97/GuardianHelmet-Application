package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.firestore.FirebaseFirestore;

public class ViewArticlesActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_articles);


        webView = findViewById(R.id.webView);

        // Enable JavaScript (if required)
        webView.getSettings().setJavaScriptEnabled(true);

        // Replace 'yourDocumentUrl' with the actual URL of your Word document
        String yourDocumentUrl = "https://www.researchgate.net/publication/319215798_SAFETY_MANAGEMENT_IN_CONSTRUCTION_PROJECTS";

        // Construct the Google Docs Viewer URL
        String documentUrl = "https://www.researchgate.net/publication/319215798_SAFETY_MANAGEMENT_IN_CONSTRUCTION_PROJECTS" + yourDocumentUrl;

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(documentUrl);

    }
}