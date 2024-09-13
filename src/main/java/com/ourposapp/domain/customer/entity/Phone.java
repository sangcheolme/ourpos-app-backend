package com.ourposapp.domain.customer.entity;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Phone {
    public static final String NO_PHONE_NUMBER = "No Phone Number";

    private String phoneNumber;

    public Phone(String phoneNumber) {
        this.phoneNumber = removeHyphens(phoneNumber);
    }

    public String getRawPhoneNumber() {
        return phoneNumber != null ? phoneNumber : NO_PHONE_NUMBER;
    }

    // '-'가 포함된 포맷의 전화번호 반환
    public String getFormattedPhoneNumberWithHyphens() {
        return phoneNumber != null ? addHyphens(phoneNumber) : NO_PHONE_NUMBER;
    }

    // 전화번호에서 '-' 제거
    private String removeHyphens(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        return phoneNumber.replaceAll("-", "");
    }

    // 전화번호에 '-'를 삽입하여 포맷팅
    private String addHyphens(String phoneNumber) {
        if (phoneNumber.length() == 10) { // 예: 02-1234-5678
            return phoneNumber.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "$1-$2-$3");
        } else if (phoneNumber.length() == 11) { // 예: 010-1234-5678
            return phoneNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        }
        return phoneNumber; // 형식에 맞지 않으면 원본 반환
    }
}
