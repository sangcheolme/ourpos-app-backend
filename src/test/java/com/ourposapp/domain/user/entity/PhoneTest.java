package com.ourposapp.domain.user.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ourposapp.domain.common.Phone;

class PhoneTest {

    @DisplayName("phone을 생성할 때 하이픈이 있으면 제거한다.")
    @Test
    void removeHyphens() {
        // given
        Phone phone1 = Phone.of("010-1234-1234");
        Phone phone2 = Phone.of("01012341234");

        // when
        String phoneNumber1 = phone1.getPhoneNumber();
        String phoneNumber2 = phone2.getPhoneNumber();

        // then
        assertThat(phoneNumber1).isEqualTo("01012341234");
        assertThat(phoneNumber2).isEqualTo("01012341234");
    }

    @DisplayName("'-'가 포함된 포맷의 전화번호 반환")
    @Test
    void getRawPhoneNumber() {
        // given
        Phone phone = Phone.of("01012341234");

        // when
        String rawPhoneNumber = phone.getPhoneNumber();

        // then
        assertThat(rawPhoneNumber).isEqualTo("01012341234");
    }
    
    @DisplayName("'-'가 포함된 포맷의 전화번호 반환")
    @Test
    void getFormattedPhoneNumberWithHyphens() {
        // given
        Phone phone = Phone.of("01012341234");
        
        // when
        String formattedPhoneNumberWithHyphens = phone.getFormattedPhoneNumberWithHyphens();

        // then
        assertThat(formattedPhoneNumberWithHyphens).isEqualTo("010-1234-1234");
    }
    
}