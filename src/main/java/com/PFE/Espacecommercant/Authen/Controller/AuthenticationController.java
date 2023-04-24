package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Service.Impl.AuthenticationService;
import com.PFE.Espacecommercant.Authen.users.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    ServletContext context;
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



    @GetMapping("/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User Response=service.findByemail(email);
        return ResponseEntity.ok(Response);
    }
    @PostMapping(value="/registerSAdmin",consumes = "multipart/form-data")
    public ResponseEntity<AuthenticationResponse> registerSAdmin(@RequestPart String request,@RequestPart("image") MultipartFile image) throws MessagingException,IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = image.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));

        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,image.getBytes());


        }catch(Exception e) {
            e.printStackTrace();
        }
        SAdminRequestdto sAdminRequestdto = objectMapper.readValue(request, SAdminRequestdto.class);
        sAdminRequestdto.setImage(filenameimage);

        return ResponseEntity.ok(service.registerSAdmin(sAdminRequestdto));
    }

    @PostMapping(value="/registeradmin",consumes = "multipart/form-data")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestPart String request,@RequestPart("file") MultipartFile batinda,@RequestPart("logo") MultipartFile logo) throws MessagingException,IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenamebatinda = batinda.getOriginalFilename();
        String filenamelogo = logo.getOriginalFilename();
        String newFileName1 = FilenameUtils.getBaseName(filenamebatinda)+"."+FilenameUtils.getExtension(filenamebatinda);
        String newFileName2 = FilenameUtils.getBaseName(filenamelogo)+"."+FilenameUtils.getExtension(filenamelogo);
        File serverFile1 = new File (context.getRealPath("/images/"+File.separator+newFileName1));
        File serverFile2 = new File (context.getRealPath("/images/"+File.separator+newFileName2));

        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile1,batinda.getBytes());
            FileUtils.writeByteArrayToFile(serverFile2,logo.getBytes());



        }catch(Exception e) {
            e.printStackTrace();
        }

        RegisterRequest admin = objectMapper.readValue(request, RegisterRequest.class);
        admin.setBatinda(filenamebatinda);
        admin.setLogo(filenamelogo);
        return ResponseEntity.ok(service.registerAdmin(admin));
    }
    @PostMapping(value="/registercommercant",consumes = "multipart/form-data")
    public ResponseEntity<AuthenticationResponse> registercommercant(@RequestPart String request,@RequestPart("image") MultipartFile image) throws MessagingException,IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = image.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));

        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,image.getBytes());


        }catch(Exception e) {
            e.printStackTrace();
        }

        CommercantRequestdto commercant = objectMapper.readValue(request, CommercantRequestdto.class);
        commercant.setImage(filenameimage);

        return ResponseEntity.ok(service.registerCommercant(commercant));
    }
    @PostMapping(value="/registercommercantadmin",consumes = "multipart/form-data")
    public ResponseEntity<AuthenticationResponse> registercommercantadmin(@RequestPart String request,@RequestPart("image") MultipartFile image) throws MessagingException,IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = image.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));

        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,image.getBytes());


        }catch(Exception e) {
            e.printStackTrace();
        }

        CommercantReqdto commercant = objectMapper.readValue(request, CommercantReqdto.class);
        commercant.setImage(filenameimage);

        return ResponseEntity.ok(service.registerCommercantadmin(commercant));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
