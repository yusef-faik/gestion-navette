package com.example.gestion_navette_v1.controllers;

import com.example.gestion_navette_v1.entities.Client;
import com.example.gestion_navette_v1.entities.Offer;
import com.example.gestion_navette_v1.entities.Request;
import com.example.gestion_navette_v1.repositories.ICityRepository;
import com.example.gestion_navette_v1.repositories.IOfferRepository;
import com.example.gestion_navette_v1.repositories.IRequestRepository;
import com.example.gestion_navette_v1.security.CustomUserDetails;
import com.example.gestion_navette_v1.security.IAuthenticationFacade;
import com.example.gestion_navette_v1.security.repositories.IAppUserRepository;
import com.example.gestion_navette_v1.security.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client/")
@RequiredArgsConstructor
public class ClientController {
  final AppUserService appUserService;
  final ICityRepository cityRepository;
  final IAuthenticationFacade authenticationFacade;
  final IOfferRepository offerRepository;
  final IRequestRepository requestRepository;
  final IAppUserRepository appUserRepository;
  
  @PostMapping("/subscribe/{id}")
  public String subscribe(@PathVariable("id") Long offerId){
    Offer offer = offerRepository.findById(offerId).get();
    
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
  
    if (!offer.getClients().contains(client)
            && offer.getClients().size() < offer.getDesiredNumberOfSubscribers()) {
      offer.getClients().add(client);
      offerRepository.save(offer);
    }
    
    return "redirect:/client/my_subscriptions";
  }
  
  @GetMapping("/my_subscriptions")
  public String getMySubscriptions(Model model){
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
    
    model.addAttribute("subscriptions", offerRepository.findByClientsIs(client));
  
    return "my_subscriptions";
  }
  
  @GetMapping("/add_request")
  public String addSubscriptionRequest(Model model){
    model.addAttribute("cities", cityRepository.findAll());
    model.addAttribute("request", new Request());
    
    return "add_request";
  }
  
  @GetMapping("/my_requests")
  public String getMyRequests(Model model){
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
    model.addAttribute("requests", requestRepository.findByRequestingClientsEquals(client));
    model.addAttribute("offers", offerRepository.findAll());
  
    return "my_requests";
  }
  
  @PostMapping("/save_request")
  public String saveOffer(Request request){
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
  
    Request matchingRequest = requestRepository.findByDepartureCityAndArrivalCity(request.getDepartureCity(), request.getArrivalCity());
  
    if (matchingRequest != null) {
      if (!matchingRequest.getRequestingClients().contains(client)) {
        matchingRequest.getRequestingClients().add(client);
        requestRepository.save(matchingRequest);
      }
      return "redirect:/client/my_requests";
    }
  
    request.getRequestingClients().add((client));
    request.setOpen(true);
    requestRepository.save(request);
    
    return "redirect:/client/my_requests";
  }
  
}