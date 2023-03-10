package com.example.gestion_navettes.repositories;

import com.example.gestion_navettes.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IOfferRepository extends JpaRepository<Offer, Long> {
  List<Offer> findByOfferingCompany(Company company);
  List<Offer> findByDepartureCityAndArrivalCityAndStartDateIsGreaterThanEqualAndEndDateIsLessThanEqual(
          City departureCity,
          City arrivalCity,
          Date startsDate,
          Date endDate);
  List<Offer> findOffersByDepartureCityAndArrivalCityAndStartDateIsGreaterThanEqual(
          City departureCity,
          City arrivalCity,
          Date startsDate);
  
  List<Offer> findByClientsIs(Client client);
  
}
