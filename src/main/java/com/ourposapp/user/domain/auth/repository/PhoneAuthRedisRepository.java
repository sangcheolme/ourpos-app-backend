package com.ourposapp.user.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.ourposapp.user.domain.auth.entity.PhoneAuth;

public interface PhoneAuthRedisRepository extends CrudRepository<PhoneAuth, String> {
}
