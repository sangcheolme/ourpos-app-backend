package com.ourposapp.domain.customer.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @DisplayName("phone을 생성할 때 하이픈이 있으면 제거한다.")
    @Test
    void removeHyphens() {
        // given
        Phone phone1 = new Phone("010-1234-1234");
        Phone phone2 = new Phone("01012341234");

        // when
        String phoneNumber1 = phone1.getRawPhoneNumber();
        String phoneNumber2 = phone2.getRawPhoneNumber();

        // then
        assertThat(phoneNumber1).isEqualTo("01012341234");
        assertThat(phoneNumber2).isEqualTo("01012341234");
    }

    @DisplayName("'-'가 포함된 포맷의 전화번호 반환")
    @Test
    void getRawPhoneNumber() {
        // given
        Phone phone = new Phone("01012341234");

        // when
        String rawPhoneNumber = phone.getRawPhoneNumber();

        // then
        assertThat(rawPhoneNumber).isEqualTo("01012341234");
    }
    
    @DisplayName("'-'가 포함된 포맷의 전화번호 반환")
    @Test
    void getFormattedPhoneNumberWithHyphens() {
        // given
        Phone phone = new Phone("01012341234");
        
        // when
        String formattedPhoneNumberWithHyphens = phone.getFormattedPhoneNumberWithHyphens();

        // then
        assertThat(formattedPhoneNumberWithHyphens).isEqualTo("010-1234-1234");
    }

    @DisplayName("핸드폰을 저장하지 않은 경우 'No Phone Number'를 리턴한다")
    @Test
    void noPhone() {
        // given
        Phone phone = new Phone();

        // when
        String rawPhoneNumber = phone.getRawPhoneNumber();

        // then
        assertThat(rawPhoneNumber).isEqualTo(Phone.NO_PHONE_NUMBER);
    }
    
}