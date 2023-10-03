package com.example.guardianhelmet;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.InputStream;

public class WordDocumentExtractor extends AsyncTask<Void, Void, String> {
    private Context context;
    private Uri documentUri;
    private TextView textView;

    public WordDocumentExtractor(Context context, Uri documentUri, TextView textView) {
        this.context = context;
        this.documentUri = documentUri;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(documentUri);
            XWPFDocument document = new XWPFDocument(inputStream);

            StringBuilder text = new StringBuilder();

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    text.append(run.getText(0));
                }
                text.append("\n");
            }

            return text.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String extractedText) {
        if (extractedText != null) {
            textView.setText(extractedText);
        } else {
            // Handle the case where text extraction failed
            textView.setText("Failed to extract text from the document.");
        }
    }
}

