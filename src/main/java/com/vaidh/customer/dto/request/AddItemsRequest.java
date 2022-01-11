package com.vaidh.customer.dto.request;

import java.util.Map;

public class AddItemsRequest {
    private Map<Long, Integer> items;
    private String referenceId;

    public Map<Long, Integer> getItems() {
        return items;
    }

    public String getReferenceId() {
        return referenceId;
    }
}
