package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.ClientRepository;
import com.PFE.Espacecommercant.Authen.Repository.CommercantRepository;
import com.PFE.Espacecommercant.Authen.Repository.SAdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.ClientService;
import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.model.Mail;
import com.PFE.Espacecommercant.Authen.model.PasswordGenerate;
import com.PFE.Espacecommercant.Authen.users.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final CommercantRepository commercantRepository;
    @Autowired
    private final SAdminRepository sAdminRepository;
    private final JwtService jwtService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired private final ModelMapper modelMapper ;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;


    @Autowired
    private MailService mailService;
    @Override
    public AuthenticationResponse saveClient(ClientRequestDto request)  {
        var client= Client.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(PasswordGenerate.generatepassword())
                .telephone(request.getTelephone())
                .logo(request.getLogo())
                .company(request.getCompany())
                .domain(request.getDomain())
                .nbEmployer(request.getNbEmployer())
                .dateExpiration(LocalDate.now().plusDays(7))
                .dateCreation(LocalDate.now())
                .emailCommercant(request.getEmailCommercant())
                .enabled(false)
                .build();
        Optional<Commercant> commercant= commercantRepository.findByTenantId(request.getCommercantId());
        client.setCommercant(commercant.get());
        clientRepository.save(client);
        User user  = ClientToUser.toUser(client);
        user.setPassword(passwordEncoder.encode(client.getPassword()));
        repository.save(user);
        var jwtToken=jwtService.generateToken(client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public AuthenticationResponse saveClientSadmin(ClientReqDto request) {

        var client= Client.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(PasswordGenerate.generatepassword())
                .telephone(request.getTelephone())
                .logo(request.getLogo())
                .company(request.getCompany())
                .domain(request.getDomain())
                .nbEmployer(request.getNbEmployer())
                .dateExpiration(LocalDate.now().plusDays(7))
                .dateCreation(LocalDate.now())
                .emailCommercant(request.getEmailCommercant())
                .enabled(false)
                .build();
        Optional<SAdmin> sAdmin= sAdminRepository.findByTenantId(request.getSAdminId());
        client.setSAdmin(sAdmin.get());
        clientRepository.save(client);
        User user  = ClientToUser.toUser(client);
        user.setPassword(passwordEncoder.encode(client.getPassword()));
        repository.save(user);
        var jwtToken=jwtService.generateToken(client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    @Override
    public List<ClientResponseDto> findall(){
        List<Client> clientList= clientRepository.findAll();
        List<ClientResponseDto> clientResponseDtos=new ArrayList<>();
        for (Client client:clientList ){
            ClientResponseDto clientResponseDto=ClientResponseDto.ClientToClientDto(client);
            clientResponseDtos.add(clientResponseDto);
        }
        return clientResponseDtos;
    }
    @Override
    public void activateClient(Integer id) {
        Mail mail = new Mail();

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));

        client.setEnabled(true);
        String email=client.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("Acceptation de demande de demo");
        mail.setMailContent("Bonjour  notre chers Client " + client.getFirstname() +" "+ client.getLastname() +",\n\n" +
                "Votre compte a été activé avec succès.\n\n" + "Votre Mot de passe:  " + client.getPassword()+
                "\n\n Vous pouvez vous connectez maintenant.\n\n" +"Bonne journée. \n\n"+"Cordialement \n\n" + "WIND-ERP");
        mailService.sendEmail(mail);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    @Override
    public Client findByemail(String email) {
        Client client= clientRepository.findByemail(email);
        return client;
    }

    @Override
    public Optional<Client> findByid(Integer id) {
        Optional<Client> client= clientRepository.findById(id);
        return client;     }
    @Override
    public void delete(Integer id) {
        clientRepository.deleteById(id);
    }
    @Override
    public ClientResponseDto update(ClientReqDto clientRequestDto, Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        String email=client.getEmail();
        User user= userRepository.findByEmail(email);
        Client cliententity = new Client();
        cliententity.setEmail(clientRequestDto.getEmail());
        cliententity.setDomain(clientRequestDto.getDomain());
        cliententity.setCompany(clientRequestDto.getCompany());
        cliententity.setTelephone(clientRequestDto.getTelephone());
        cliententity.setNbEmployer(clientRequestDto.getNbEmployer());
        cliententity.setEmailCommercant(clientRequestDto.getEmailCommercant());
        cliententity.setLastname(clientRequestDto.getLastname());
        cliententity.setFirstname(clientRequestDto.getFirstname());
        cliententity.setLogo(clientRequestDto.getLogo());
        Optional<SAdmin> sAdmin= sAdminRepository.findByTenantId(clientRequestDto.getSAdminId());
        cliententity.setSAdmin(sAdmin.get());
        cliententity.setId(id);
        cliententity.setEmailCommercant(client.getEmailCommercant());
        cliententity.setDateExpiration(client.getDateExpiration());
        cliententity.setPassword(client.getPassword());
        user.setEmail(email);
        Client updated=clientRepository.save(cliententity);
        user.setEmail(cliententity.getEmail());
        user.setImage(cliententity.getLogo());
        user.setPassword(passwordEncoder.encode(cliententity.getPassword()));
        user.setEnabled(cliententity.getEnabled());
        userRepository.save(user);

        return modelMapper.map(updated,ClientResponseDto.class) ;


    }
    @Override
    public ClientResponseDto updateCommercant(ClientRequestDto clientRequestDto, Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        String email=client.getEmail();
        User user= userRepository.findByEmail(email);
        Client cliententity = new Client();
        cliententity.setEmail(clientRequestDto.getEmail());
        cliententity.setDomain(clientRequestDto.getDomain());
        cliententity.setCompany(clientRequestDto.getCompany());
        cliententity.setTelephone(clientRequestDto.getTelephone());
        cliententity.setNbEmployer(clientRequestDto.getNbEmployer());
        cliententity.setEmailCommercant(clientRequestDto.getEmailCommercant());
        cliententity.setLastname(clientRequestDto.getLastname());
        cliententity.setFirstname(clientRequestDto.getFirstname());
        cliententity.setLogo(clientRequestDto.getLogo());
        cliententity.setDateExpiration(client.getDateExpiration());
        Optional<Commercant> commercant= commercantRepository.findByTenantId(clientRequestDto.getCommercantId());
        cliententity.setCommercant(commercant.get());
        cliententity.setId(id);
        cliententity.setPassword(client.getPassword());
        user.setEmail(email);
        Client updated=clientRepository.save(cliententity);
        user.setEmail(cliententity.getEmail());
        user.setImage(cliententity.getLogo());
        user.setPassword(passwordEncoder.encode(cliententity.getPassword()));
        user.setEnabled(cliententity.getEnabled());
        userRepository.save(user);

        return modelMapper.map(updated,ClientResponseDto.class) ;
    }
    @Override
    public Client updateenabled(Integer id) {
        Mail mail = new Mail();
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        client.setEnabled(true);
        String email=client.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(client.getEnabled());
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("Prolongation de demande de demo");
        mail.setMailContent("Bonjour  notre chers Client " + client.getFirstname() +" "+ client.getLastname() +",\n\n" +
                "Votre compte a été activé avec succès.\n\n" + "Votre Mot de passe:  " + client.getPassword()+
                "\n\n Vous pouvez vous connectez maintenant.\n\n" +"Bonne journée. \n\n"+"Cordialement \n\n" + "WIND-ERP");
        mailService.sendEmail(mail);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        return client;    }
    @Override
    public Client updatenotenabled(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        client.setEnabled(false);
        clientRepository.save(client);
        String email=client.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(client.getEnabled());
        userRepository.save(user);
        return client;    }

}
