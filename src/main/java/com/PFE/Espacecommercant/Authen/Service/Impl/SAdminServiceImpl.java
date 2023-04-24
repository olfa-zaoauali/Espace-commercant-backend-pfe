package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.ClientResponseDto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.CommercantRepository;
import com.PFE.Espacecommercant.Authen.Repository.SAdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.SAdminservice;
import com.PFE.Espacecommercant.Authen.users.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SAdminServiceImpl implements SAdminservice {
    @Autowired
    private final SAdminRepository sAdminRepository;
    @Autowired
    private final CommercantRepository commercantRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired private final ModelMapper modelMapper ;

    @Override
    public List<SAdmin> findAll() {
        return sAdminRepository.findAll();
    }

    @Override
    public Optional<SAdmin> findByemail(String email) {
        Optional<SAdmin> sAdmin= sAdminRepository.findByemail(email);
        return sAdmin;
    }

    @Override
    public void delete(Integer id) {
        sAdminRepository.deleteById(id);

    }

    @Override
    public SAdminResponsedto update(SAdminRequestdto sAdminRequestdto, Integer id) {
        SAdmin sAdmin = sAdminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        String email=sAdmin.getEmail();
        User user= userRepository.findByEmail(email);
        SAdmin sadminentity = modelMapper.map(sAdminRequestdto,SAdmin.class);
        sadminentity.setTenantId(sAdmin.getTenantId());
        sadminentity.setId(id);
        user.setEmail(email);
        SAdmin updated=sAdminRepository.save(sadminentity);
        userRepository.save(user);
        return modelMapper.map(updated, SAdminResponsedto.class);
    }

    @Override
    public SAdmin updateenabled(Integer id) {
        SAdmin sAdmin = sAdminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        sAdmin.setEnabled(true);
        sAdminRepository.save(sAdmin);
        String email=sAdmin.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(sAdmin.getEnabled());
        userRepository.save(user);
        return sAdmin;
    }

    @Override
    public SAdmin updatenotenabled(Integer id) {
        SAdmin sAdmin = sAdminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        sAdmin.setEnabled(false);
        sAdminRepository.save(sAdmin);
        String email=sAdmin.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(sAdmin.getEnabled());
        userRepository.save(user);
        return sAdmin;
    }

    @Override
    public List<Commercant> SearchAllCommercant(String tenantId) {
        SAdmin sadmin = sAdminRepository.findByTenantId(tenantId).orElse(null);
        if (sadmin == null) {
            return (List<Commercant>) ResponseEntity.notFound().build();
        }
        return sadmin.getCommercants();
    }
    @Override
    public List<ClientResponseDto> SearchAllClients(String tenantId) {
        SAdmin sadmin = sAdminRepository.findByTenantId(tenantId).orElse(null);
        if (sadmin == null) {
            return (List<ClientResponseDto>) ResponseEntity.notFound().build();
        }
        List<ClientResponseDto> clientResponseDtoList=new ArrayList<>();
        for (Client client: sadmin.getClients()){
            ClientResponseDto clientResponseDto= ClientResponseDto.ClientToClientDto(client);
            clientResponseDtoList.add(clientResponseDto);
        }
        return clientResponseDtoList ;
    }
    @Override
    public List<ClientResponseDto> getAllClients(String tenantId){
        SAdmin sadmin = sAdminRepository.findByTenantId(tenantId).orElse(null);
        List<ClientResponseDto> clients= new ArrayList<>();
        List<Commercant> commercants= sadmin.getCommercants();

        for (Commercant commercant : commercants){
             for (Client client: commercant.getClients()){
                ClientResponseDto clientResponseDto= ClientResponseDto.ClientToClientDto(client);
                 clients.add(clientResponseDto);
             }
        }
        for (Client client: sadmin.getClients()){
            ClientResponseDto clientResponseDto1= ClientResponseDto.ClientToClientDto(client);
            clients.add(clientResponseDto1);
        }
        return clients;

    }
    @Override
    public List<SAdminResponsedto> findall(){
        List<SAdmin> sAdminList= sAdminRepository.findAll();
        List<SAdminResponsedto> sAdminResponsedtoList=new ArrayList<>();
        for (SAdmin sAdmin:sAdminList ){
            SAdminResponsedto sAdminResponsedto=SAdminResponsedto.sadminTosadminDto(sAdmin);
            sAdminResponsedtoList.add(sAdminResponsedto);
        }
        return sAdminResponsedtoList;
    }





}
