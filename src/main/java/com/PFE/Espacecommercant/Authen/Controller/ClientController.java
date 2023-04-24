package com.PFE.Espacecommercant.Authen.Controller;

import com.PFE.Espacecommercant.Authen.DTO.*;
import com.PFE.Espacecommercant.Authen.Service.facade.ClientService;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/clients")
@CrossOrigin("http://localhost:4200")
public class ClientController {
    @Autowired
    ServletContext context;
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private ClientService clientService;
    @PostMapping(value="/add",consumes = "multipart/form-data")
    public ResponseEntity<AuthenticationResponse> registerClient(@RequestPart String request, @RequestPart("logo") MultipartFile logo) throws MessagingException, IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = logo.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));

        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,logo.getBytes());


        }catch(Exception e) {
            e.printStackTrace();
        }

        ClientRequestDto client = objectMapper.readValue(request, ClientRequestDto.class);
        client.setLogo(filenameimage);

        return ResponseEntity.ok(clientService.saveClient(client));
    }
    @PostMapping(value="/addclient",consumes = "multipart/form-data")
    public ResponseEntity<AuthenticationResponse> registerClientSadmin(@RequestPart String request, @RequestPart("logo") MultipartFile logo) throws MessagingException, IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = logo.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));

        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,logo.getBytes());


        }catch(Exception e) {
            e.printStackTrace();
        }

        ClientReqDto client = objectMapper.readValue(request, ClientReqDto.class);
        client.setLogo(filenameimage);

        return ResponseEntity.ok(clientService.saveClientSadmin(client));
    }
    //probléme
    @GetMapping("/client")
    public List<Client> getclients(){

        return  clientService.findAll();
    }
    //solution de probléme
    @GetMapping("")
    public List<ClientResponseDto> getallclients(){

        return  clientService.findall();
    }
    @GetMapping("/validation/{id}")
    public String activateCommercant(@PathVariable("id") Integer id) {
        clientService.activateClient(id);
        return "User activated successfully";
    }
    @GetMapping("/{email}")
    public ResponseEntity<Client> findByEmail(@PathVariable String email) {
        Client Response=clientService.findByemail(email);
        return ResponseEntity.ok(Response);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<Optional<Client>> findByid(@PathVariable Integer id) {
        Optional<Client> Response=clientService.findByid(id);
        return ResponseEntity.ok(Response);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        clientService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/enabled/{id}")
    public ResponseEntity<Client> updateenable(@PathVariable Integer id){
      Client clientenabled=clientService.updateenabled(id);
        return ResponseEntity.accepted().body(clientenabled);
    }
    @PutMapping("/notenabled/{id}")
    public ResponseEntity<Client> updatenotenable(@PathVariable Integer id){
       Client clientnotenabled=clientService.updatenotenabled(id);
        return ResponseEntity.accepted().body(clientnotenabled);
    }
    @PutMapping("update/{id}")
    public  ResponseEntity<ClientResponseDto> update(@RequestPart String request, @RequestPart("logo") MultipartFile logo,@PathVariable Integer id) throws MessagingException, IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = logo.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));
        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,logo.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
        }
        ClientReqDto client = objectMapper.readValue(request, ClientReqDto.class);
        client.setLogo(filenameimage);
        ClientResponseDto clientResponseDto=clientService.update(client, id);
        return ResponseEntity.accepted().body(clientResponseDto);
    }
    @PutMapping("updateCommercant/{id}")
    public  ResponseEntity<ClientResponseDto> updateCommercant(@RequestPart String request, @RequestPart("logo") MultipartFile logo,@PathVariable Integer id) throws MessagingException, IOException {
        boolean isExit = new File(context.getRealPath("/images/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filenameimage = logo.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filenameimage)+"."+FilenameUtils.getExtension(filenameimage);
        File serverFile = new File (context.getRealPath("/images/"+File.separator+newFileName));
        try
        {
            System.out.println("image");
            FileUtils.writeByteArrayToFile(serverFile,logo.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
        }
        ClientRequestDto client = objectMapper.readValue(request, ClientRequestDto.class);
        client.setLogo(filenameimage);
        ClientResponseDto clientResponseDto=clientService.updateCommercant(client, id);
        return ResponseEntity.accepted().body(clientResponseDto);
    }
}
