package com.example.gestion_navette_v1.security.repositories;

import com.example.gestion_navette_v1.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserRepository extends JpaRepository<AppUser, Long> {
  AppUser findAppUserByEmail(String email);
}
