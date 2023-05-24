package com.PFE.Espacecommercant.Authen.model;

import com.PFE.Espacecommercant.Authen.users.Admin;
import com.PFE.Espacecommercant.Authen.users.Client;
import com.PFE.Espacecommercant.Authen.users.Commercant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.regex.Pattern;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Facture implements Serializable {
    @Id
    @GeneratedValue
    private Integer numero;
    private LocalDate dateFacture;
    private double ttc;
    private double ht;
    private double tva;
    private String totalLettre;
    private String reference;
    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonBackReference
    private Client client;
    @ManyToOne
    @JoinColumn(name="admin_id")
    @JsonBackReference
    private Admin partenaire;
    @OneToOne
    @JoinColumn(name="cashout_id")
    @JsonBackReference
    private Cashout cashout;
    private static final String PREFIX = "DEV-";
    private static final int ID_LENGTH = 5;
    private static int lastId = 0;


    public synchronized String generateID() {

        lastId++;
        String id = PREFIX + String.format("%0" + ID_LENGTH + "d", lastId);
        return id;
    }
    public double calculTotal(double ht, double TVA) {
        double ttc= ht*(1+(TVA/100));
        return ttc;
    }
    public String totalEnLettre(double total){
        DecimalFormat DFORMAT = new DecimalFormat("###0.000");
        NumberFormat FORMATTER = new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT);
        String[] s = DFORMAT.format(total).split(Pattern.quote(String.valueOf(DFORMAT.getDecimalFormatSymbols().getDecimalSeparator())));
        BigInteger intPart = new BigInteger(s[0]);
        if ( s.length==1 ) {
            return FORMATTER.format(intPart);
        }
        else {
            BigInteger decPart = new BigInteger(s[1]);
            return FORMATTER.format(intPart)
                    // pour les parties fixes il faudrait faire un resourcebundle
                    + " Dinar"
                    + (intPart.intValue()>1?"s":"")
                    + " et "
                    + FORMATTER.format(decPart)
                    + " millim"
                    + (decPart.intValue()>1?"s":"");
        }
    }
}
