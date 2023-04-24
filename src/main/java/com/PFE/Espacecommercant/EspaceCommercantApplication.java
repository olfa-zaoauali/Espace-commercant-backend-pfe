package com.PFE.Espacecommercant;
import com.PFE.Espacecommercant.Authen.Service.facade.FilesStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

public class EspaceCommercantApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EspaceCommercantApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();

	}
}
