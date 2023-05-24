package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.*;
import com.PFE.Espacecommercant.Authen.Service.facade.CommercantService;
import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.model.Cashout;
import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.users.*;
import com.PFE.Espacecommercant.Authen.model.Mail;
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
public class CommercantServiceImpl implements CommercantService {
    @Autowired
    private final CommercantRepository commercantRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private MailService mailService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Commercant> findAll() {
        return commercantRepository.findAll();

    }
    @Override
    public void activateCommercant(Integer id) {
        Mail mail = new Mail();

        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found " + id));

        commercant.setEnabled(true);
        String email = commercant.getEmail();
        User user = userRepository.findByEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("Confirmation d'inscription à notre plateforme");
        mail.setMailContent("Cher(e) " + commercant.getFirstname() + " " + commercant.getLastname() + ",\n\n" +
                "Nous sommes ravis de vous accueillir sur notre plateforme. Votre compte commerçant a été créé avec succès et est prêt à être utilisé.\n\n"+
                "Voici les informations de connexion à votre compte :\n\n" +
                        "Nom d'utilisateur :"+commercant.getEmail() +"\n\n"+
                "Mot de passe temporaire :  " + commercant.getPassword() +
                "\n\n Nous vous conseillons de changer votre mot de passe temporaire dès que possible pour des raisons de sécurité." +
                " Pour ce faire, veuillez vous connecter à votre compte et modifier votre mot de passe dans les paramètres de compte.\n\n"+
                "Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter.\n\n"+"Cordialement,\n\n"+"L'équipe de WIND-ERP"
        );
        mailService.sendEmail(mail);
        commercant.setPassword(passwordEncoder.encode(commercant.getPassword()));
        commercantRepository.save(commercant);


    }

    @Override
    public Optional<Commercant> findByemail(String email) {
        Optional<Commercant> commercant = commercantRepository.findByemail(email);
        return commercant;
    }

    @Override
    public Optional<Commercant> findByid(Integer id) {
        Optional<Commercant> commercant = commercantRepository.findById(id);
        return commercant;
    }

    @Override
    public Commercant findByTeantId(String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId).orElse(null);
        return commercant;
    }

    @Override
    public void delete(Integer id) {
        commercantRepository.deleteById(id);
    }
    @Override
    public CommercantResponsedto updateCompte(UpdateCommeracantdto commercantRequestdto, String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("User id not found " + tenantId));
        Commercant commercantentity = UpdateCommeracantdto.mapperdtotocom(commercantRequestdto);
        commercantentity.setId(commercant.getId());
        commercantentity.setTenantId(commercant.getTenantId());
        commercantentity.setImage(commercant.getImage());
        commercantentity.setPassword(commercant.getPassword());
        commercantentity.setPourcentage(commercant.getPourcentage());
        commercantentity.setPay(commercant.getPay());
        commercantentity.setEnabled(commercant.getEnabled());
        commercantentity.setDateCreation(commercant.getDateCreation());
        String email = commercant.getEmail();
        User user = userRepository.findByEmail(email);
        user.setEmail(email);
        user.setEmail(commercantentity.getEmail());
        user.setEnabled(commercantentity.getEnabled());
        user.setTenantId(commercant.getTenantId());
        user.setImage(commercant.getImage());
        userRepository.save(user);
        if(commercant.getSadmin()!=(null)){
             commercantentity.setSadmin(commercant.getSadmin());
            Commercant updated = commercantRepository.save(commercantentity);
            CommercantResponsedto responsedto=CommercantResponsedto.mapperfromcomToDto(updated);
            return responsedto;
        }
        if (commercant.getAdmin()!=(null)){
            commercantentity.setAdmin(commercant.getAdmin());
            Commercant updated = commercantRepository.save(commercantentity);
            CommercantResponsedto responsedto=CommercantResponsedto.mapperfromEntityToDto(updated);
            return responsedto;
        }
        return null;
    }
    @Override
    public ChangePasswordRequest changerPassword(String tenantId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByTenantId(tenantId);
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("User id not found " + tenantId));
        if (passwordEncoder.matches(changePasswordRequest.getPasswordActuel(), user.getPassword())) {
            if (changePasswordRequest.getPasswordConfirm().equals(changePasswordRequest.getPasswordNew())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getPasswordNew()));
                commercant.setPassword(user.getPassword());
                commercantRepository.save(commercant);
                userRepository.save(user);
                return  changePasswordRequest;
            }
            return null;
        }
        return null;
    }
    @Override
    public Commercant changerImage(String tenantId,String Image){
        User user = userRepository.findByTenantId(tenantId);
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("User id not found " + tenantId));
        user.setImage(Image);
        userRepository.save(user);
        commercant.setImage(Image);
        commercantRepository.save(commercant);
        return commercant;
    }
    @Override
    public CommercantResponsedto update(CommercantRequestdto commercantRequestdto, Integer id) {
        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found " + id));
        String email = commercant.getEmail();
        User user = userRepository.findByEmail(email);
        Commercant commercantentity = modelMapper.map(commercantRequestdto, Commercant.class);
        commercantentity.setPays(commercantRequestdto.getPays());
        commercantentity.setTenantId(commercant.getTenantId());
        commercantentity.setDateCreation(commercant.getDateCreation());
        Optional<Admin> admin = adminRepository.findByTenantId(commercantRequestdto.getAdmin());
        commercantentity.setAdmin(admin.get());
        commercantentity.setId(id);
        commercantentity.setPassword(commercant.getPassword());
        commercantentity.setPourcentage(commercant.getPourcentage());
        commercantentity.setPay(commercant.getPay());
        commercantentity.setEnabled(commercant.getEnabled());
        user.setEmail(email);
        Commercant updated = commercantRepository.save(commercantentity);
        user.setEmail(commercantentity.getEmail());
        user.setImage(commercantentity.getImage());
        user.setPassword(passwordEncoder.encode(commercantentity.getPassword()));
        user.setEnabled(commercantentity.getEnabled());
        userRepository.save(user);
        return CommercantResponsedto.mapperfromEntityToDto(updated);
    }

    @Override
    public CommercantResponsedto updatecommercant(CommercantReqdto commercantReqtdto, Integer id) {
        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found " + id));
        String email = commercant.getEmail();
        User user = userRepository.findByEmail(email);
        Commercant commercantentity = Commercant.mapperdtotocom(commercantReqtdto);
        commercantentity.setPays(commercantReqtdto.getPays());
        commercantentity.setTenantId(commercant.getTenantId());
        commercantentity.setDateCreation(commercant.getDateCreation());
        //Optional<SAdmin> sadmin = sAdminRepository.findByTenantId(commercantReqtdto.getSadminId());
        commercantentity.setSadmin(commercant.getSadmin());
        commercantentity.setId(id);
        commercantentity.setPassword(commercant.getPassword());
        commercantentity.setPourcentage(commercant.getPourcentage());
        commercantentity.setPay(commercant.getPay());
        commercantentity.setEnabled(commercant.getEnabled());
        user.setEmail(email);
        Commercant updated = commercantRepository.save(commercantentity);
        user.setEmail(commercantentity.getEmail());
        user.setImage(commercantentity.getImage());
        user.setPassword(passwordEncoder.encode(commercantentity.getPassword()));
        user.setEnabled(commercantentity.getEnabled());
        userRepository.save(user);
        return CommercantResponsedto.mapperfromcomToDto(updated);
    }

    @Override
    public Commercant updateenabled(Integer id) {
        Mail mail = new Mail();

        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found " + id));
        commercant.setEnabled(true);
        commercantRepository.save(commercant);
        String email = commercant.getEmail();
        User user = userRepository.findByEmail(email);
        user.setEnabled(commercant.getEnabled());
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("revalidation de compte");
        mail.setMailContent("Cher(e) " + commercant.getFirstname() + " " + commercant.getLastname() + ",\n\n" +
                "Nous sommes ravis de vous accueillir sur notre plateforme. Votre compte commerçant a été créé avec succès et est prêt à être utilisé.\n\n"+
                "Voici les informations de connexion à votre compte :\n\n" +
                "Nom d'utilisateur :"+commercant.getEmail() +"\n\n"+
                "Mot de passe temporaire :  " + commercant.getPassword() +
                "\n\n Nous vous conseillons de changer votre mot de passe temporaire dès que possible pour des raisons de sécurité." +
                " Pour ce faire, veuillez vous connecter à votre compte et modifier votre mot de passe dans les paramètres de compte.\n\n"+
                "Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter.\n\n"+"Cordialement,\n\n"+"L'équipe de WIND-ERP"
        );
        mailService.sendEmail(mail);
        commercant.setPassword(passwordEncoder.encode(commercant.getPassword()));
        commercantRepository.save(commercant);
        return commercant;
    }

    @Override
    public Commercant updatenotenabled(Integer id) {
        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found " + id));
        commercant.setEnabled(false);
        commercantRepository.save(commercant);
        String email = commercant.getEmail();
        User user = userRepository.findByEmail(email);
        user.setEnabled(commercant.getEnabled());
        userRepository.save(user);
        return commercant;
    }

    @Override
    public List<Client> SearchAllclient(String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId).orElse(null);
        if (commercant == null) {
            return (List<Client>) ResponseEntity.notFound().build();
        }
        return commercant.getClients();
    }
    @Override
    public List<Cashout> SearchAllCashouts(String tenantId){
        Commercant commercant = commercantRepository.findByTenantId(tenantId).orElse(null);
        if (commercant == null) {
            return (List<Cashout>) ResponseEntity.notFound().build();
        }
        return commercant.getCashouts();
    }

    @Override
    public double calculCommission(String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("User id not found "));
        double revenu = 0;
        double commission=commercant.getPay();
        for (Client client : commercant.getClients()) {
            Optional<Admin> admin= adminRepository.findByemail(client.getEmail());
            if (admin.isPresent()){
                  double total = admin.get().getApayer();
                  revenu = revenu + total;
                  commission = revenu * (commercant.getPourcentage() / 100);
            }
            else {
            revenu=revenu+0;
            }
        }
        return commission;
    }
    @Override
    public double calculRevenu(String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Commercant id not found "));
        double revenu = 0;
        for (Client client : commercant.getClients()) {
            Optional<Admin> admin1= adminRepository.findByemail(client.getEmail());
            if (admin1.isPresent()){
            Admin admin = adminRepository.findByemail(client.getEmail())
                    .orElseThrow(() -> new NotFoundException("admin id not found "));
            double total = admin.getApayer();
            revenu = revenu + total;}

        }
        return revenu;
    }
    @Override
    public int calculNbAdmins(String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Commercant id not found "));
        int nombre = 0;
        for (Client client : commercant.getClients()) {
            if (client.isVerified()){
                nombre=nombre+1;
                }
            nombre=nombre+0;
        }
        return nombre;
    }
    @Override
    public int calculNbClients(String tenantId) {
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Commercant id not found "));
        int nombre = 0;
        for (Client client : commercant.getClients()) {
            nombre=nombre+1;
        }
        return nombre;
    }
    @Override
    public List<FactureResponseDto> ConsulterFactures(String tenantId){
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Commercant id not found "));
        List<FactureResponseDto> factureList=new ArrayList<>();
        for (Client client : commercant.getClients()){
          if (adminRepository.findByTenantId(client.getTenantId()).isPresent()){
                Admin admin=adminRepository.findByTenantId(client.getTenantId()).orElse(null);
                List<Facture> factures = admin.getFactures();
                for (Facture facture: factures){
                    factureList.add(FactureResponseDto.mapperfromEntityToDto(facture));
                }
            }
          else {
              if (client.isVerified()){
                  List<Facture> factures = client.getFactures();
                  for (Facture facture: factures){
                      factureList.add(FactureResponseDto.EntityToDto(facture));
                  }
              }
          }
        }
        return factureList;
    }
    @Override
    public List<Admin> getAdminsOfCommercant(String tenantId){
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Commercant id not found "));
        List<Admin> admins= new ArrayList<>();
        for (Client client : commercant.getClients()) {
            Admin admin1= adminRepository.findByemail(client.getEmail()).orElse(null);
            if (admin1!=null){
                admins.add(admin1);
            }
        }
        return admins;
    }
    @Override
    public RegisterAdminResponsedto getInfoAdmin(String tenantId){
        Commercant commercant = commercantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Commercant id not found "));
        RegisterAdminResponsedto responsedto=new RegisterAdminResponsedto();
        if(commercant.getSadmin()!=(null)){
            SAdmin sAdmin=commercant.getSadmin();
            responsedto.setId(sAdmin.getId());
            responsedto.setFirstname(sAdmin.getFirstname());
            responsedto.setLastname(sAdmin.getLastname());
            responsedto.setMatricule("1493899B");
            responsedto.setCompany("Wind Consulting");
            responsedto.setDomain("Informatique");
            responsedto.setAdresse("Mahdia Rue Chaanbi, Rondpoint Ibn El Jazzar Immeuble Ben Nahia");
            responsedto.setVille(sAdmin.getVille());
            responsedto.setPays(sAdmin.getPays());
            responsedto.setLogo(sAdmin.getImage());
            responsedto.setTenantId(sAdmin.getTenantId());
            responsedto.setTelephone(sAdmin.getTelephone());
            responsedto.setEmail(sAdmin.getEmail());
            responsedto.setEnabled(sAdmin.getEnabled());

        }
        if (commercant.getAdmin()!=(null)){
            Admin admin=commercant.getAdmin();
            responsedto.setId(admin.getId());
            responsedto.setFirstname(admin.getFirstname());
            responsedto.setLastname(admin.getLastname());
            responsedto.setMatricule(admin.getMatricule());
            responsedto.setCompany(admin.getCompany());
            responsedto.setDomain(admin.getDomain());
            responsedto.setAdresse(admin.getAdresse());
            responsedto.setVille(admin.getVille());
            responsedto.setPays(admin.getPays());
            responsedto.setLogo(admin.getLogo());
            responsedto.setTelephone(admin.getTelephone());
            responsedto.setTenantId(admin.getTenantId());
            responsedto.setEmail(admin.getEmail());
            responsedto.setEnabled(admin.getEnabled());
        }
        return responsedto;
    }


}
