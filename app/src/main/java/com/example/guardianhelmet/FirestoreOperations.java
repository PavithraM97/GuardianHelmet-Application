package com.example.guardianhelmet;

public interface FirestoreOperations {
    void addMessageListener(OnMessagesListener listener);
    void removeMessageListener();
    void sendMessage(String senderId, String messageText, long timestamp);
}
