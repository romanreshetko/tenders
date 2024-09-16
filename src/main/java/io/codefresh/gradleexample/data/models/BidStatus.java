package io.codefresh.gradleexample.data.models;

public enum BidStatus {

    CREATED("CREATED"),
    PUBLISHED("PUBLISHED"),
    CANCELED("CANCELED"),

    APPROVED("APPROVED"),

    REJECTED("REJECTED");

    public final String value;

    private BidStatus(String val) {
        value = val;
    }
}
