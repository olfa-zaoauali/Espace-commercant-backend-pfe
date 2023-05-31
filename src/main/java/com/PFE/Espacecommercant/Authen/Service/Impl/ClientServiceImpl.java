package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.*;
import com.PFE.Espacecommercant.Authen.Service.facade.ClientService;
import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.model.Mail;
import com.PFE.Espacecommercant.Authen.model.Modules;
import com.PFE.Espacecommercant.Authen.model.PasswordGenerate;
import com.PFE.Espacecommercant.Authen.users.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final CommercantRepository commercantRepository;
    @Autowired
    private final SAdminRepository sAdminRepository;
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final ModuleRepository moduleRepository;
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
                .tenantId(UUID.randomUUID().toString())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(PasswordGenerate.generatepassword())
                .telephone(request.getTelephone())
                .logo(request.getLogo())
                .company(request.getCompany())
                .domain(request.getDomain())
                .nbEmployer(request.getNbEmployer())
                .adresse(request.getAdresse())
                .ville(request.getVille())
                .pays(request.getPays())
                .dateExpiration(LocalDate.now().plusDays(15))
                .dateCreation(LocalDate.now())
                .enabled(false)
                .verified(false)
                .build();
        Optional<Commercant> commercant= commercantRepository.findByTenantId(request.getCommercantId());
        client.setCommercant(commercant.get());
        //updated 26/04/2023
        client.setEmailCommercant(client.getCommercant().getEmail());
        //fin update
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
                .tenantId(UUID.randomUUID().toString())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(PasswordGenerate.generatepassword())
                .telephone(request.getTelephone())
                .logo(request.getLogo())
                .company(request.getCompany())
                .domain(request.getDomain())
                .nbEmployer(request.getNbEmployer())
                .adresse(request.getAdresse())
                .ville(request.getVille())
                .pays(request.getPays())
                .dateExpiration(LocalDate.now().plusDays(15))
                .dateCreation(LocalDate.now())
                .enabled(false)
                .verified(false)
                .build();
        Optional<SAdmin> sAdmin= sAdminRepository.findByTenantId(request.getSAdminId());
        client.setSAdmin(sAdmin.get());
        //updated 26/04/2023
        client.setEmailCommercant(client.getSAdmin().getEmail());
        //fin update
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
        mail.setMailContent("Cher(e) " + client.getFirstname() + " " + client.getLastname() + ",\n\n" +
                "Nous vous remercions d'avoir exprimé votre intérêt pour notre démo et votre demande de compte." +
                " Nous sommes ravis de vous informer que votre compte a été créé avec succès et est maintenant prêt à être activé. \n\n"+
                "Voici les informations de connexion à votre compte :\n\n" +
                "Nom d'utilisateur :"+client.getEmail() +"\n\n"+
                "Mot de passe temporaire :  " + client.getPassword() +
                "\n\n Nous vous informons que votre compte sera activé pendant une durée de 7 jours," +
                        " à compter de la réception de cet e-mail. Après cette période, votre compte sera désactivé automatiquement." +
                " Nous espérons sincèrement que vous apprécierez votre expérience avec nos modules WIND-ERP. \n\n"+"Merci encore pour votre intérêt. " +
                        "Nous sommes impatients de vous accompagner dans votre parcours avec nos modules WIND-ERP.\n\n"+
                "Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter.\n\n"+"Cordialement,\n\n"+"L'équipe de WIND-ERP"
        );
        mailService.sendEmail(mail);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    @Override
    public ClientResponseDto findByemail(String email) {
        Client client= clientRepository.findByemail(email);
       ClientResponseDto response= ClientResponseDto.ClientToClientDto(client);
        return response;
    }

    @Override
    public Optional<Client> findByid(Integer id) {
        Optional<Client> client= clientRepository.findById(id);
        return client;     }

    @Override
    public ClientResponseDto findByTeantId(String tenantId) {
        Client client=clientRepository.findByTenantId(tenantId);
        ClientResponseDto dto=ClientResponseDto.ClientToClientDto(client);
        return dto;
    }
    @Override
    public Client updateCompte(ClientReqDto clientReqDto, String tenantId) {
        Client client = clientRepository.findByTenantId(tenantId);
        Client cliententity = ClientReqDto.DtoToClient(clientReqDto);
        cliententity.setId(client.getId());
        cliententity.setTenantId(client.getTenantId());
        cliententity.setLogo(client.getLogo());
        cliententity.setPassword(passwordEncoder.encode(client.getPassword()));
        cliententity.setDateExpiration(client.getDateExpiration());
        cliententity.setDateCreation(client.getDateCreation());
        cliententity.setEmailCommercant(client.getEmailCommercant());
        String email = client.getEmail();
        User user = userRepository.findByEmail(email);
        user.setEmail(email);
        user.setEmail(cliententity.getEmail());
        user.setEnabled(cliententity.getEnabled());
        user.setTenantId(cliententity.getTenantId());
        user.setImage(cliententity.getLogo());
        userRepository.save(user);
        if(client.getCommercant()!=(null)){
            cliententity.setCommercant(client.getCommercant());
            Client updated = clientRepository.save(cliententity);
            return updated;
        }
        if(client.getSAdmin()!=(null)){
            cliententity.setSAdmin(client.getSAdmin());
            Client updated = clientRepository.save(cliententity);
            return updated;
        }
        return null;
    }
    @Override
    public ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByTenantId(tenantId);
        Client client= clientRepository.findByTenantId(tenantId);
        if (passwordEncoder.matches(changePasswordRequest.getPasswordActuel(), user.getPassword())) {
            if (changePasswordRequest.getPasswordConfirm().equals(changePasswordRequest.getPasswordNew())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getPasswordNew()));
                client.setPassword(user.getPassword());
                clientRepository.save(client);
                userRepository.save(user);
                return  changePasswordRequest;
            }
            return null;
        }
        return null;
    }
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
        cliententity.setAdresse(clientRequestDto.getAdresse());
        cliententity.setVille(clientRequestDto.getVille());
        cliententity.setPays(clientRequestDto.getPays());
        cliententity.setEmailCommercant(clientRequestDto.getEmailCommercant());
        cliententity.setLastname(clientRequestDto.getLastname());
        cliententity.setFirstname(clientRequestDto.getFirstname());
        cliententity.setLogo(clientRequestDto.getLogo());
        cliententity.setAdresse(clientRequestDto.getAdresse());
        cliententity.setVille(clientRequestDto.getVille());
        cliententity.setPays(clientRequestDto.getPays());
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
        cliententity.setAdresse(clientRequestDto.getAdresse());
        cliententity.setVille(clientRequestDto.getVille());
        cliententity.setPays(clientRequestDto.getPays());
        cliententity.setEmailCommercant(clientRequestDto.getEmailCommercant());
        cliententity.setLastname(clientRequestDto.getLastname());
        cliententity.setFirstname(clientRequestDto.getFirstname());
        cliententity.setLogo(clientRequestDto.getLogo());
        cliententity.setDateExpiration(client.getDateExpiration());
        cliententity.setAdresse(clientRequestDto.getAdresse());
        cliententity.setVille(clientRequestDto.getVille());
        cliententity.setPays(clientRequestDto.getPays());
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
        mail.setMailContent("Cher(e) " + client.getFirstname() + " " + client.getLastname() + ",\n\n" +
                "Nous vous remercions d'avoir exprimé votre intérêt pour notre démo et votre demande de compte." +
                " Nous sommes ravis de vous informer que votre compte a été créé avec succès et est maintenant prêt à être activé. \n\n"+
                "Voici les informations de connexion à votre compte :\n\n" +
                "Nom d'utilisateur :"+client.getEmail() +"\n\n"+
                "Mot de passe temporaire :  " + client.getPassword() +
                "\n\n Nous vous informons que votre compte sera activé pendant une durée de 7 jours," +
                " à compter de la réception de cet e-mail. Après cette période, votre compte sera désactivé automatiquement." +
                " Nous espérons sincèrement que vous apprécierez votre expérience avec nos modules WIND-ERP. \n\n"+"Merci encore pour votre intérêt. " +
                "Nous sommes impatients de vous accompagner dans votre parcours avec nos modules WIND-ERP.\n\n"+
                "Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter.\n\n"+"Cordialement,\n\n"+"L'équipe de WIND-ERP"
        );
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
        return client;
    }
    @Override
    public RegisterAdminResponsedto ClientToAdmin(RegisterRequest request, Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        Admin admin= ClientReqDto.ClientToAdmin(client);
        admin.setPassword(PasswordGenerate.generatepassword());
        admin.setDateExpiration(LocalDate.now().plusMonths(1));
        admin.setMatricule(request.getMatricule());
        admin.setBatinda(request.getBatinda());
        admin.setLogo(request.getLogo());
        admin.setEnabled(false);
        List<Modules> modulesList = moduleRepository.findAllById(request.getModuleId());
        List<Modules> adminModules = new ArrayList<>();
        adminModules.addAll(modulesList);
        admin.setModules(adminModules);
        admin.setApayer(admin.totalprix());
        if (client.getCommercant()!=(null)){
        Commercant commercant= client.getCommercant();
        double commission= commercant.getPay()+admin.totalprix() * (commercant.getPourcentage() / 100);
        commercant.setPay(commission);
        commercantRepository.save(commercant);
        }
        Admin updated=adminRepository.save(admin);
        return modelMapper.map(updated,RegisterAdminResponsedto.class);
    }
}

