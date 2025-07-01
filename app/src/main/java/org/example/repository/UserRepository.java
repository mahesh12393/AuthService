package org.example.repository;

import org.example.entities.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
//    @Query("SELECT u FROM UserInfo u WHERE u.userName = :username")
    UserInfo findByUsername(String username);
}
