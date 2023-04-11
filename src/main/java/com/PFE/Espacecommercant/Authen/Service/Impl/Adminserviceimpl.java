package com.PFE.Espacecommercant.Authen.Service.Impl;
import com.PFE.Espacecommercant.Authen.DTO.RegisterAdminResponsedto;
import com.PFE.Espacecommercant.Authen.DTO.RegisterRequest;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.Adminservice;
import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.model.Mail;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class Adminserviceimpl implements Adminservice {
    @Autowired private final AdminRepository adminRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired private final ModelMapper modelMapper ;
    @Autowired
    private MailService mailService;
    private final Path root = Paths.get("uploads");
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

        user.setEnabled(true);
        String email=user.getEmail();
        User admin= userRepository.findByEmail(email);
        admin.setEnabled(true);
        userRepository.save(admin);
        adminRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("validation de compte");
        mail.setMailContent("Bonjour " + user.getFirstname() + ",\n\n" +
                "Votre compte a été activé avec succès.\n\n" +
                "Merci d'utiliser notre service !");
        mailService.sendEmail(mail);
    }
    @Override
    public Optional<Admin> findByemail(String email) {
        Optional<Admin> adminEntity= adminRepository.findByemail(email);
        return adminEntity;
    }

    @Override
    public RegisterAdminResponsedto update(RegisterRequest adminRequestDto, Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        String email=admin.getEmail();
        User user= userRepository.findByEmail(email);
        Admin adminEntity = modelMapper.map(adminRequestDto,Admin.class);
        adminEntity.setEnabled(adminRequestDto.getEnabled());
        user.setEnabled(adminRequestDto.getEnabled());
        adminEntity.setId(id);
        Admin updated= adminRepository.save(adminEntity);
        user.setEmail(email);
        userRepository.save(user);
        return modelMapper.map(updated,RegisterAdminResponsedto.class);

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
        mail.setMailContent("Bonjour " + admin.getFirstname()+ ",\n\n" +
                "Votre compte a été activé  de nouveau avec succès.\n\n" +
                "Merci d'utiliser notre service !");
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
    public List<Commercant> SearchAllCommercant(Integer id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) {
            return (List<Commercant>) ResponseEntity.notFound().build();
        }
        return admin.getCommercants();
    }


}
