package com.vaidh.customer.message;

public class CancelledOrderMessage extends FireBaseMessage {
    String note;

    public CancelledOrderMessage() {}
    public CancelledOrderMessage(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
