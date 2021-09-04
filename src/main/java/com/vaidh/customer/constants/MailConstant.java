package com.vaidh.customer.constants;

public interface MailConstant {
    String CHANGE_PASSWORD_SUBJ = "Confirmation code for changing password";

    static String getForgetPasswordMessage(String generatedCode) {
        StringBuilder sb = new StringBuilder("Hi! ").append("\n")
                .append("Your generated code is ").append(generatedCode);
        return sb.toString();
    }
}
