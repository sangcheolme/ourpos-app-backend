package com.ourposapp.infra.phone;

import org.springframework.data.repository.CrudRepository;

public interface PhoneAuthRedisRepository extends CrudRepository<PhoneAuth, String> {
}
