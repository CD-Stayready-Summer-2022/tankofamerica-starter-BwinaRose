package com.codedifferently.tankofamerica.domain.user.repos;

import com.codedifferently.tankofamerica.domain.user.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
}