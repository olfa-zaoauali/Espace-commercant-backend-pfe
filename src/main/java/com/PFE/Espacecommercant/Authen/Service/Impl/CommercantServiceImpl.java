package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.CommercantRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.CommercantResponsedto;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.CommercantRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.CommercantService;
import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.model.PasswordGenerate;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.model.Mail;
import com.PFE.Espacecommercant.Authen.users.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommercantServiceImpl implements CommercantService {
    @Autowired
    private final CommercantRepository commercantRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired private final ModelMapper modelMapper ;
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
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));

        commercant.setEnabled(true);
        String email=commercant.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("validation de compte");
        mail.setMailContent("Bonjour  notre chers Commerçant " + commercant.getFirstname() +" "+ commercant.getLastname() +",\n\n" +
                "Votre compte a été activé avec succès.\n\n" + "Votre Mot de passe:  " + commercant.getPassword()+
                "\n\n Vous pouvez vous connectez maintenant.");
        mailService.sendEmail(mail);
        commercant.setPassword(passwordEncoder.encode(commercant.getPassword()));
        commercantRepository.save(commercant);


    }

    @Override
    public Optional<Commercant> findByemail(String email) {
        Optional<Commercant> commercant= commercantRepository.findByemail(email);
        return commercant;
    }

    @Override
    public Optional<Commercant> findByid(Integer id) {
        Optional<Commercant> commercant= commercantRepository.findById(id);
        return commercant;
    }

    @Override
    public void delete(Integer id) {
           commercantRepository.deleteById(id);
    }
    @Override
    public CommercantResponsedto update(CommercantRequestdto commercantRequestdto, Integer id) {
        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        String email=commercant.getEmail();
        User user= userRepository.findByEmail(email);
        Commercant commercantentity = modelMapper.map(commercantRequestdto,Commercant.class);
        Optional<Admin> admin= adminRepository.findById(commercantRequestdto.getAdmin());
        commercantentity.setAdmin(admin.get());
        commercantentity.setId(id);
        commercantentity.setPassword(PasswordGenerate.generatepassword());
        user.setEmail(email);
        Commercant updated=commercantRepository.save(commercantentity);
        user.setEmail(commercantentity.getEmail());
        user.setImage(commercantentity.getImage());
        user.setPassword(passwordEncoder.encode(commercantentity.getPassword()));
        user.setEnabled(commercantentity.getEnabled());
        userRepository.save(user);
        return CommercantResponsedto.mapperfromEntityToDto(updated) ;
    }
    @Override
    public Commercant updateenabled(Integer id) {
        Mail mail = new Mail();

        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        commercant.setEnabled(true);
        commercantRepository.save(commercant);
        String email=commercant.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(commercant.getEnabled());
        userRepository.save(user);
        mail.setMailFrom("zaoualiolfa2000@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("revalidation de compte");
        mail.setMailContent("Bonjour  notre chers Commerçant " + commercant.getFirstname() +" "+ commercant.getLastname() +",\n\n" +
                "Votre compte a été activé de nouveau avec succès.\n\n" + "Votre Mot de passe:  " + commercant.getPassword()+
                "\n\n Vous pouvez vous connectez maintenant.");
        mailService.sendEmail(mail);
        commercant.setPassword(passwordEncoder.encode(commercant.getPassword()));
        commercantRepository.save(commercant);
        return commercant;
    }
    @Override
    public Commercant updatenotenabled(Integer id) {
        Commercant commercant = commercantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found "+ id));
        commercant.setEnabled(false);
        commercantRepository.save(commercant);
        String email=commercant.getEmail();
        User user= userRepository.findByEmail(email);
        user.setEnabled(commercant.getEnabled());
        userRepository.save(user);
        return commercant;
    }
}
