package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.CashoutRepository;
import com.PFE.Espacecommercant.Authen.Repository.SAdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.Adminservice;
import com.PFE.Espacecommercant.Authen.Service.facade.CommercantService;
import com.PFE.Espacecommercant.Authen.Service.facade.SAdminservice;
import com.PFE.Espacecommercant.Authen.model.Cashout;
import com.PFE.Espacecommercant.Authen.users.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SAdminServiceImpl implements SAdminservice {
    @Autowired
    private final SAdminRepository sAdminRepository;
    @Autowired
    private final Adminservice adminservice;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;
    @Autowired private final ModelMapper modelMapper ;
    @Autowired private final AdminRepository adminRepository;
    @Autowired private final CommercantService commercantService;
    @Autowired private final CashoutRepository cashoutRepository;

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
    public SAdminResponsedto findByTeantId(String tenantId) {
        SAdmin sAdmin = sAdminRepository.findByTenantId(tenantId).orElse(null);
        SAdminResponsedto dto= SAdminResponsedto.sadminTosadminDto(sAdmin);
        return dto;
    }
    @Override
    public SAdminResponsedto update(SAdminRequestdto sAdminRequestdto, Integer id) {
        SAdmin sAdmin = sAdminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        SAdmin sadminentity = modelMapper.map(sAdminRequestdto,SAdmin.class);
        sadminentity.setPassword(passwordEncoder.encode(sAdmin.getPassword()));
        sadminentity.setImage(sAdmin.getImage());
        sadminentity.setTenantId(sAdmin.getTenantId());
        sadminentity.setId(id);
        SAdmin updated=sAdminRepository.save(sadminentity);
        String email=sAdmin.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEmail(email);
        userRepository.save(user);
        return modelMapper.map(updated, SAdminResponsedto.class);
    }
    @Override
    public ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByTenantId(tenantId);
        SAdmin sAdmin = sAdminRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("User id not found " + tenantId));
        if (passwordEncoder.matches(changePasswordRequest.getPasswordActuel(), user.getPassword())) {
            if (changePasswordRequest.getPasswordConfirm().equals(changePasswordRequest.getPasswordNew())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getPasswordNew()));
                sAdmin.setPassword(user.getPassword());
                sAdminRepository.save(sAdmin);
                userRepository.save(user);
                return  changePasswordRequest;
            }
            return null;
        }
        return null;
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
    @Override
    public double totalRevenu(String tenantId){
       List<Admin> admins= adminRepository.findAll();
       double revenu=0;
       for (Admin admin : admins){
          String id= admin.getTenantId();
          revenu=revenu+adminservice.totalprix(id);
       }
       return  revenu;
    }
    @Override
    public int nbClients(String tenantId){
        SAdmin sAdmin= sAdminRepository.findByTenantId(tenantId).orElse(null);
        int nbClients=0;
        List<Client> clients=new ArrayList<>();
        for (Client client:sAdmin.getClients()){
            if (!client.isVerified()){
                clients.add(client);
            }
        }
        List<Commercant> commercants= sAdmin.getCommercants();
        for (Commercant commercant: commercants){
            for (Client client:commercant.getClients()){
                if (!client.isVerified()){
                    clients.add(client);
                }
            }
        }
        nbClients= nbClients+ clients.size();
        return nbClients;
    }
    @Override
    public Cashout validerCashout(int id){
        Cashout cashout= cashoutRepository.findById(id).orElse(null);
        cashout.setVerified(true);
        cashoutRepository.save(cashout);
        return cashout;
    }
    @Override
    public List<CashoutResponseDto> getAllCashouts(String tenantId){
        SAdmin sAdmin= sAdminRepository.findByTenantId(tenantId).orElse(null);
        List<CashoutResponseDto> cashouts=new ArrayList<>();
        for (Commercant commercant :sAdmin.getCommercants() ){
            for (Cashout cashout:commercant.getCashouts() ){
                cashouts.add(CashoutResponseDto.mapperfromEntityToDto(cashout));
            }
        }
        return cashouts;
    }
    @Override
    public double revenuNet(String tenantId){
        SAdmin sAdmin= sAdminRepository.findByTenantId(tenantId).orElse(null);
        List<Commercant> commercants= sAdmin.getCommercants();
        double revenuNet=0;
        double sommeCommissions=0;
        for ( Commercant commercant : commercants){
            String id= commercant.getTenantId();
            sommeCommissions=sommeCommissions+commercantService.calculCommission(id);
        }
        revenuNet=totalRevenu(tenantId)-sommeCommissions;
        return revenuNet;
    }


}
