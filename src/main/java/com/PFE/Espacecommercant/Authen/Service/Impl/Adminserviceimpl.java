package com.PFE.Espacecommercant.Authen.Service.Impl;
import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.*;
import com.PFE.Espacecommercant.Authen.Service.facade.Adminservice;
import com.PFE.Espacecommercant.Authen.Service.facade.CommercantService;
import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.model.Modules;
import com.PFE.Espacecommercant.Authen.users.*;
import com.PFE.Espacecommercant.Authen.model.Mail;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class Adminserviceimpl implements Adminservice {
    @Autowired private final AdminRepository adminRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired private final ModelMapper modelMapper ;
    @Autowired private final CommercantService commercantService;
    @Autowired private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired private final CommercantRepository commercantRepository;


    @Autowired private final FactureRepository factureRepository;


    @Autowired
    private MailService mailService;
    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
             //   .stream().map(el -> modelMapper.map(el, RegisterAdminResponsedto.class)).collect(Collectors.toList());
    }
    @Override
    public void activateAdmin(Integer id)
    {
        Mail mail = new Mail();
        Admin user = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        Client client=clientRepository.findByemail(user.getEmail());
        user.setEnabled(true);
        String email=user.getEmail();
        User admin= userRepository.findByEmail(email);
        admin.setEnabled(true);

        userRepository.save(admin);
        adminRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("Confirmation d'inscription à notre plateforme");
        mail.setMailContent("Cher(e) " + user.getFirstname() + " " + user.getLastname() + ",\n\n" +
                "Félicitations ! Nous sommes ravis de vous informer que votre compte a été validé avec succès et que vous êtes maintenant officiellement un administrateur dans notre module. " +
                "Nous vous remercions sincèrement d'avoir choisi d'acheter nos modules et de rejoindre notre communauté.\n\n"+
                "Votre compte a été mis à niveau avec succès pour accéder à toutes les fonctionnalités avancées de notre module." +
                " Vous avez désormais le pouvoir de gérer et de personnaliser votre expérience selon vos besoins.\n\n"+
                " Nous vous rappelons que notre équipe est là pour vous soutenir à chaque étape du processus. Si vous avez des questions," +
                " des préoccupations ou si vous avez besoin d'une assistance supplémentaire," +
                " n'hésitez pas à nous contacter.\n\n"+
                " Nous vous souhaitons un grand succès dans l'utilisation de nos modules. Votre satisfaction est notre priorité absolue," +
                " et nous sommes engagés à vous offrir une expérience exceptionnelle.\n\n"+
                "Encore une fois, merci d'avoir choisi nos modules. Nous sommes impatients de vous aider à atteindre vos objectifs et de vous fournir un support de qualité.\n\n"+

                "Cordialement,\n\n"+"L'équipe de WIND-ERP"
        );
        mailService.sendEmail(mail);
        client.setVerified(true);
        clientRepository.save(client);

    }
    @Override
    public RegisterAdminResponsedto findByTeantId(String tenantId) {
        Admin admin = adminRepository.findByTenantId(tenantId).orElse(null);
        RegisterAdminResponsedto dto= RegisterAdminResponsedto.AdminTodto(admin);
        return dto;
    }
    @Override
    public Optional<Admin> findByemail(String email) {
        Optional<Admin> adminEntity= adminRepository.findByemail(email);
        return adminEntity;
    }

    @Override
    public Admin findByCompany(String company) {
        Admin registerAdminResponsedto=adminRepository.findByCompany(company);
        return  registerAdminResponsedto;
    }

    @Override
    public RegisterAdminResponsedto update(RegisterRequest adminRequestDto, Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        Admin adminEntity = modelMapper.map(adminRequestDto,Admin.class);
        adminEntity.setTenantId(admin.getTenantId());
        adminEntity.setEnabled(admin.getEnabled());
        adminEntity.setId(id);
        adminEntity.setLogo(admin.getLogo());
        adminEntity.setBatinda(admin.getBatinda());
        adminEntity.setMatricule(admin.getMatricule());
        adminEntity.setApayer(admin.getApayer());
        adminEntity.setModules(admin.getModules());
        adminEntity.setTelephone(adminRequestDto.getTelephone());
        Admin updated= adminRepository.save(adminEntity);
        String email=admin.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(adminRequestDto.getEnabled());
        user.setEmail(email);
        userRepository.save(user);
        return modelMapper.map(updated,RegisterAdminResponsedto.class);
    }
    @Override
    public ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByTenantId(tenantId);
        Admin admin= adminRepository.findByTenantId(tenantId).orElse(null);
        if (passwordEncoder.matches(changePasswordRequest.getPasswordActuel(), user.getPassword())) {
            if (changePasswordRequest.getPasswordConfirm().equals(changePasswordRequest.getPasswordNew())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getPasswordNew()));
                admin.setPassword(user.getPassword());
                adminRepository.save(admin);
                userRepository.save(user);
                return  changePasswordRequest;
            }
            return null;
        }
        return null;
    }
    @Override
    public Admin updateenabled(Integer id) {
        Mail mail = new Mail();
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        admin.setEnabled(true);
        adminRepository.save(admin);
        String email=admin.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(admin.getEnabled());
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("revalidation de compte");
        mail.setMailContent("Cher(e) " + admin.getFirstname() + " " + admin.getLastname() + ",\n\n" +
                "Nous vous remercions sincèrement d'avoir choisi d'acheter nos modules et de rejoindre notre communauté.\n\n"+
                "Votre compte a été mis à niveau avec succès pour accéder à toutes les fonctionnalités avancées de notre module." +
                " Vous avez désormais le pouvoir de gérer et de personnaliser votre expérience selon vos besoins.\n\n"+
                " Nous vous rappelons que notre équipe est là pour vous soutenir à chaque étape du processus. Si vous avez des questions," +
                " des préoccupations ou si vous avez besoin d'une assistance supplémentaire," +
                " n'hésitez pas à nous contacter.\n\n"+
                " Nous vous souhaitons un grand succès dans l'utilisation de nos modules. Votre satisfaction est notre priorité absolue," +
                " et nous sommes engagés à vous offrir une expérience exceptionnelle.\n\n"+
                "Encore une fois, merci d'avoir choisi nos modules. Nous sommes impatients de vous aider à atteindre vos objectifs et de vous fournir un support de qualité.\n\n"+

                "Cordialement,\n\n"+"L'équipe de WIND-ERP"
        );
        mailService.sendEmail(mail);
        return admin;
    }

    @Override
    public Admin updatenotenabled(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        admin.setEnabled(false);
        adminRepository.save(admin);
        String email=admin.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(admin.getEnabled());
        userRepository.save(user);
        return admin;
    }

    @Override
    public List<Commercant> SearchAllCommercant(String tenantId) {
        Admin admin = adminRepository.findByTenantId(tenantId).orElse(null);
        if (admin == null) {
            return (List<Commercant>) ResponseEntity.notFound().build();
        }
        return admin.getCommercants();
    }

    @Override
    public List<Modules> SearchAllModules(String tenantId) {
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        if (admin == null) {
            return (List<Modules>) ResponseEntity.notFound().build();
        }
        return admin.getModules();
    }
    @Override
    public List<Client> getallclients(String tenantId){
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        if (admin == null) {
            return (List<Client>) ResponseEntity.notFound().build();
        }
        List<Commercant> commercants= admin.getCommercants();
        List<Client> clientList=new ArrayList<>();
        for (Commercant commercant : commercants){
            clientList.addAll(commercant.getClients());
        }
        return clientList;
    }
    @Override
    public double totalprix(String tenantId){
        Admin admin= adminRepository.findByTenantId(tenantId).orElseThrow(() -> new NotFoundException("Module id not found "));
        double totale= 0;
        for (Modules module: admin.getModules()){
               double prix= module.getPrix();
               totale=totale+prix;
        }
        return totale;
    }
    @Override
    public double totalRevenu(String tenantId){
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        List<Commercant> commercants= admin.getCommercants();
        double revenu=0;
        for (Commercant commercant : commercants){
             String id=commercant.getTenantId();
              revenu=revenu+ commercantService.calculRevenu(id);
        }
        return revenu;
    }
    @Override
    public double revenuNet(String tenantId){
        Admin admin= adminRepository.findByTenantId(tenantId).orElse(null);
        List<Commercant> commercants= admin.getCommercants();
        double revenuNet=0;
        double sommeCommissions=0;
        for ( Commercant commercant : commercants){
            String id= commercant.getTenantId();
            sommeCommissions=sommeCommissions+commercantService.calculCommission(id);
        }
        revenuNet=totalRevenu(tenantId)-sommeCommissions;
        return revenuNet;
    }
    @Override
    public int nbAdmin(){
        List<Admin> adminList= adminRepository.findAll();
        int nb= adminList.size();
        return nb;
    }
    @Override
    public int nbClients(String tenantId){
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        List<Client> clients= new ArrayList<>();
        for (Commercant commercant: admin.getCommercants()){
            for(Client client: commercant.getClients()){
                if (!client.isVerified()) {
                    clients.add(client);
                }
            }
        }
        return clients.size();
    }
    @Override
    public int nbClientsVerified(String tenantId){
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        List<Client> clients= new ArrayList<>();
        for (Commercant commercant: admin.getCommercants()){
            for(Client client: commercant.getClients()){
                if (client.isVerified()){
                    clients.add(client);
                }
            }
        }
        return clients.size();
    }
    @Override
    public int nbCommercants(String tenantId){
        Admin admin=adminRepository.findByTenantId(tenantId).orElse(null);
        List<Commercant> commercants=admin.getCommercants();
        return commercants.size();
    }
    @Override
    public FactureResponseDto valideClient(Integer id, FactureRequestDto dto){
        Client client= clientRepository.findById(id).orElse(null);
        client.setVerified(true);
        clientRepository.save(client);
        Facture facture=new Facture();
        facture.setClient(client);
        facture.setDateFacture(LocalDate.now());
        facture.setTva(19);
        facture.setHt(dto.getHt());
        facture.setReference(facture.generateID());
        facture.setTtc(facture.calculTotal(facture.getHt(), facture.getTva()));
        facture.setTotalLettre(facture.totalEnLettre(facture.getTtc()));
        factureRepository.save(facture);
        FactureResponseDto factureResponseDto=FactureResponseDto.EntityToDto(facture);
        Commercant commercant = client.getCommercant();
        double commission= commercant.getPay()+dto.getHt() * (commercant.getPourcentage() / 100);
        commercant.setPay(commission);
        commercantRepository.save(commercant);
        return   factureResponseDto;
    }

}
