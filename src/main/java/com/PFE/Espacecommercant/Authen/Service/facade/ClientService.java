package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();
    void activateClient(Integer id);
    Client findByemail(String email);
    Optional<Client> findByid(Integer id);

    void delete(Integer id);
    ClientResponseDto update(ClientReqDto clientRequestDto, Integer id) ;
    Client updateenabled(Integer id);
    Client updatenotenabled(Integer id);
    public AuthenticationResponse saveClient(ClientRequestDto request) ;
    public AuthenticationResponse saveClientSadmin(ClientReqDto request) ;
    public ClientResponseDto updateCommercant(ClientRequestDto clientRequestDto, Integer id);
    public List<ClientResponseDto> findall();



    }
