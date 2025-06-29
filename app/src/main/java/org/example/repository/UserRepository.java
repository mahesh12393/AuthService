package org.example.repository;

import org.example.entities.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
//    @Query("SELECT u FROM UserInfo u LEFT JOIN FETCH u.roles WHERE u.username = :username")
//    Optional<UserInfo> findByUsername(String username);
}
