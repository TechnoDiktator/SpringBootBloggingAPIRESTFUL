package com.tarang.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.tarang.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String name);
	

}
