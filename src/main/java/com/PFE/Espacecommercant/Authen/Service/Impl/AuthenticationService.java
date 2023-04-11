package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Repository.AdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.CommercantRepository;
import com.PFE.Espacecommercant.Authen.Repository.SAdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.model.PasswordGenerate;
import com.PFE.Espacecommercant.Authen.users.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final AdminRepository repoadmin ;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CommercantRepository commercantRepository;
    private final SAdminRepository sAdminRepository;
    public AuthenticationResponse registerSAdmin(SAdminRequestdto request) throws MessagingException {
        var sadmin= SAdmin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .image(request.getImage())
                .enabled(true)
                .build();
        sAdminRepository.save(sadmin);
        User user  = SAdmintoUser.toUser(sadmin);
        repository.save(user);
        var jwtToken=jwtService.generateToken(sadmin);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse registerAdmin(RegisterRequest request) throws MessagingException {
        var admin= Admin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .domain(request.getDomain())
                .company(request.getCompany())
                .telephone(request.getTelephone())
                .matricule(request.getMatricule())
                .batinda(request.getBatinda())
                .logo(request.getLogo())
                .enabled(false)
                .build();
        repoadmin.save(admin);
        User user  = UserMapper.toUser(admin);
        repository.save(user);
        var jwtToken=jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse registerCommercant(CommercantRequestdto request) throws MessagingException {
        var commercant= Commercant.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(PasswordGenerate.generatepassword())
                .telephone(request.getTelephone())
                .image(request.getImage())
                .adresse(request.getAdresse())
                .ville(request.getVille())
                .pay(request.getPay())
                .enabled(false)
                .build();
        commercantRepository.save(commercant);
        Optional<Admin> admin= repoadmin.findById(request.getAdmin());
        commercant.setAdmin(admin.get());
        User user  = CommercanttoUser.toUser(commercant);
        user.setPassword(passwordEncoder.encode(commercant.getPassword()));
        repository.save(user);
        var jwtToken=jwtService.generateToken(commercant);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()

                        )
        );
         var user = repository.findByEmail(request.getEmail());
         Role role= user.getRole();
         if (user.getEnabled()==true){
         var jwtToken=jwtService.generateTokenuser(user,  user.getImage(), user.getTenant_id(), role);
         return AuthenticationResponse.builder()
                .token(jwtToken)
                 .enabled(user.getEnabled())
                 .image(user.getImage())
                 .tenantid(user.getTenant_id())
                 .role(user.getRole())
                .build();
         }
        return null;
    }

    public User findByemail(String email) {
        User UserEntity= repository.findByEmail(email);
        return UserEntity;
    }
}
