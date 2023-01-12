package com.example.gestion_navette_v1.repositories;

import com.example.gestion_navette_v1.entities.City;
import com.example.gestion_navette_v1.entities.Client;
import com.example.gestion_navette_v1.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRequestRepository extends JpaRepository<Request, Long> {
  public List<Request> findByRequestingClientsEquals(Client client);
  public List<Request> findByOpenTrue();
  public Request findByDepartureCityAndArrivalCity(City departureCity, City arrivalCity);
  
}

