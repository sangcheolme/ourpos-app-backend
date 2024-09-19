package com.ourposapp.api.login.service;

public interface PhoneAuthNotifier {
    void sendOne(String to, String text);
}
