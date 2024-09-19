package com.ourposapp.api.login.repository;

import org.springframework.data.repository.CrudRepository;

import com.ourposapp.api.login.dto.PhoneAuth;

public interface PhoneAuthRepository extends CrudRepository<PhoneAuth, String> {
}
