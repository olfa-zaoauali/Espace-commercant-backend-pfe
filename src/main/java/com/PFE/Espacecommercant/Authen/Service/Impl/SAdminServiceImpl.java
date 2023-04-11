package com.PFE.Espacecommercant.Authen.Service.Impl;

import com.PFE.Espacecommercant.Authen.DTO.SAdminRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.SAdminResponsedto;
import com.PFE.Espacecommercant.Authen.Exceptions.NotFoundException;
import com.PFE.Espacecommercant.Authen.Repository.SAdminRepository;
import com.PFE.Espacecommercant.Authen.Repository.UserRepository;
import com.PFE.Espacecommercant.Authen.Service.facade.SAdminservice;
import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.PFE.Espacecommercant.Authen.users.SAdmin;
import com.PFE.Espacecommercant.Authen.users.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SAdminServiceImpl implements SAdminservice {
    @Autowired
    private final SAdminRepository sAdminRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired private final ModelMapper modelMapper ;

    @Override
    public List<SAdminResponsedto> findAll() {
        return sAdminRepository.findAll()
                .stream().map(el -> modelMapper.map(el, SAdminResponsedto.class)).collect(Collectors.toList());
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


}
