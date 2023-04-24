package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.DTO.AuthenticationResponse;
import com.PFE.Espacecommercant.Authen.DTO.CommercantReqdto;
import com.PFE.Espacecommercant.Authen.DTO.CommercantRequestdto;
import com.PFE.Espacecommercant.Authen.DTO.CommercantResponsedto;
import com.PFE.Espacecommercant.Authen.Service.facade.CommercantService;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/commercants")
@CrossOrigin("http://localhost:4200")
public class CommercantController {
    @Autowired
    ServletContext context;
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private CommercantService commercantService;
    @GetMapping("")
    public List<Commercant> getcommercants(){

        return  commercantService.findAll();
    }
    @GetMapping("/validation/{id}")
    public String activateCommercant(@PathVariable("id") Integer id) {
        commercantService.activateCommercant(id);
        return "User activated successfully";
    }
    @GetMapping("/clients/{tenantId}")
    public List<Client> getallclients(@PathVariable String tenantId){
        return commercantService.SearchAllclient(tenantId);
    }
    @GetMapping("/{email}")
    public ResponseEntity<Optional<Commercant>> findByEmail(@PathVariable String email) {
        Optional<Commercant> Response=commercantService.findByemail(email);
        return ResponseEntity.ok(Response);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<Optional<Commercant>> findByid(@PathVariable Integer id) {
        Optional<Commercant> Response=commercantService.findByid(id);
        return ResponseEntity.ok(Response);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        commercantService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/enabled/{id}")
    public ResponseEntity<Commercant> updateenable(@PathVariable Integer id){
        Commercant commercantenabled=commercantService.updateenabled(id);
        return ResponseEntity.accepted().body(commercantenabled);
    }
    @PutMapping("/notenabled/{id}")
    public ResponseEntity<Commercant> updatenotenable(@PathVariable Integer id){
        Commercant commercantnotenabled=commercantService.updatenotenabled(id);
        return ResponseEntity.accepted().body(commercantnotenabled);
    }
    @PutMapping("update/{id}")
    public  ResponseEntity<CommercantResponsedto> update(@RequestBody CommercantRequestdto commercantRequestdto,@PathVariable Integer id){
        CommercantResponsedto commercantResponsedto=commercantService.update(commercantRequestdto, id);
        return ResponseEntity.accepted().body(commercantResponsedto);
    }
    @PutMapping (value="/updatecomercant/{id}",consumes = "multipart/form-data")
    public ResponseEntity<CommercantResponsedto> update(@RequestPart String request, @RequestPart("image") MultipartFile image,@PathVariable Integer id) throws MessagingException, IOException {
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
        CommercantResponsedto commercantResponsedto=commercantService.update(commercant, id);
        return ResponseEntity.accepted().body(commercantResponsedto);
    }
    @PutMapping (value="/updatecomercantsadmin/{id}",consumes = "multipart/form-data")
    public ResponseEntity<CommercantResponsedto> updatecommercant(@RequestPart String request, @RequestPart("image") MultipartFile image,@PathVariable Integer id) throws MessagingException, IOException {
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
        CommercantResponsedto commercantResponsedto=commercantService.updatecommercant(commercant, id);
        return ResponseEntity.accepted().body(commercantResponsedto);
    }

}
