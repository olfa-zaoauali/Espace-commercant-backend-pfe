package com.PFE.Espacecommercant.Authen.Service.facade;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;


import java.util.List;
import java.util.Optional;
public interface ClientService {
    List<Client> findAll();
    void activateClient(Integer id);
    ClientResponseDto findByemail(String email);
    Optional<Client> findByid(Integer id);
    Client updateCompte(ClientReqDto clientReqDto, String tenantId);
    ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest);
    ClientResponseDto findByTeantId(String tenantId);
    void delete(Integer id);
    ClientResponseDto update(ClientReqDto clientRequestDto, Integer id) ;
    Client updateenabled(Integer id);
    Client updatenotenabled(Integer id);
    public AuthenticationResponse saveClient(ClientRequestDto request) ;
    public AuthenticationResponse saveClientSadmin(ClientReqDto request) ;
    public ClientResponseDto updateCommercant(ClientRequestDto clientRequestDto, Integer id);
    public List<ClientResponseDto> findall();
    RegisterAdminResponsedto ClientToAdmin(RegisterRequest request, Integer id);



    }
