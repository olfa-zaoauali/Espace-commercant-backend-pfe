package com.PFE.Espacecommercant.Authen.model;

import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cashout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double cashout;
    private LocalTime temp;

    private LocalDate dateDeCreation;
    private double Montant;
    private boolean verified;
    @ManyToOne
    @JoinColumn(name="commercant_id")
    @JsonBackReference
    private Commercant commercant;
    @OneToOne
    @JoinColumn(name="facture_id")
    @JsonBackReference
    private Facture facture;

}
