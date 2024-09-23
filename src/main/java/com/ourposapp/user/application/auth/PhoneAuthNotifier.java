package com.ourposapp.user.application.auth;

public interface PhoneAuthNotifier {
    void sendOne(String to, String text);
}
