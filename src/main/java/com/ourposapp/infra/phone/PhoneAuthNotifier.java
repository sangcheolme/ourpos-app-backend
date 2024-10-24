package com.ourposapp.infra.phone;

public interface PhoneAuthNotifier {
    void sendOne(String to, String text);
}
