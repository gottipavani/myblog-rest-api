package com.myblogrestapi.repository;


import com.myblogrestapi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users>findByEmail(String email);
    Optional<Users>findByUsernameOrEmail(String username, String email);
    Optional<Users>findByUsername(String username);
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);

}
