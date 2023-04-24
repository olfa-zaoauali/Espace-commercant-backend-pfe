package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.EmailResponse;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.UserService;
import com.PFE.Espacecommercant.Authen.users.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final ModelMapper modelMapper ;
    @Autowired private final UserRepository userRepository;

    @Override
    public EmailResponse FindUser(String tenantId) {
        User user = userRepository.findByTenantId(tenantId);
        String email=user.getEmail();
        EmailResponse emailResponse=new EmailResponse();
         emailResponse.setEmail(email);
        return emailResponse;
    }
}
