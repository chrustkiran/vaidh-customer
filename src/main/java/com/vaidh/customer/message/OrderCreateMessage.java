package com.vaidh.customer.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderCreateMessage extends FireBaseMessage {
    private Map<Long, Integer> itemWiseQuantity;
    private List<String> images;

    public OrderCreateMessage(String username, Map<Long, Integer> itemWiseQuanity, List<String> images) {
        super.setUsername(username);
        super.setCreatedTime(new Date());
        this.itemWiseQuantity = itemWiseQuanity;
        this.images = images;
    }

    public Map<Long, Integer> getItemWiseQuantity() {
        return itemWiseQuantity;
    }

    public void setItemWiseQuantity(Map<Long, Integer> itemWiseQuantity) {
        this.itemWiseQuantity = itemWiseQuantity;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
