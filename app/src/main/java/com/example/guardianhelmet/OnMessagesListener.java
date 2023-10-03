package com.example.guardianhelmet;

public interface OnMessagesListener<T> {
    void onMessageAdded(T message);
    void onMessageModified(T message);
    void onMessageRemoved(T message);
}

