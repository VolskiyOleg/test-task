package com.volskyioleh.testtask.model.db.entities;

public enum TaskStatus {
    SCHEDULED("Scheduled"),
    DONE("Done"),
    DISMISSED("Dismissed");

    private final String text;

    TaskStatus (final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
