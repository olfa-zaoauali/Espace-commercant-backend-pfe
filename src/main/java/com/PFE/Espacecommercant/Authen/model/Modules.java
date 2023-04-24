package com.PFE.Espacecommercant.Authen.model;

import com.PFE.Espacecommercant.Authen.users.Admin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Modules {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)   private Integer id;
   private  String nom;
   private String prix;




}
