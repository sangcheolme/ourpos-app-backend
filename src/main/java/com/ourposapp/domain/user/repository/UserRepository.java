package com.ourposapp.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ourposapp.domain.common.Phone;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.entity.UserAddress;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select c from User c left join fetch c.userAddresses ca where c.id = :userId")
    Optional<User> findUserWithAddress(@Param("userId") Long userId);

    @Query("select ca from UserAddress ca join fetch ca.user c where c.id = :userId")
    List<UserAddress> findUserAddresses(@Param("userId") Long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByRefreshToken(String refreshToken);

    boolean existsByPhone(Phone phone);

    boolean existsByUsername(String username);
}
