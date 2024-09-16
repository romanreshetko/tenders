package io.codefresh.gradleexample.data.models;

public enum TenderStatus {

    CREATED("CREATED"),
    PUBLISHED("PUBLISHED"),
    CLOSED("CLOSED");

    public final String value;

    private TenderStatus(String val) {
        value = val;
    }
}
