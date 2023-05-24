package com.PFE.Espacecommercant.Authen.users;

import com.PFE.Espacecommercant.Authen.model.Facture;
import com.PFE.Espacecommercant.Authen.model.Modules;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Admin")
@Entity
public class Admin implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id ;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tenantId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String telephone;
    private String company;
    private String domain;
    private String matricule;
    private String batinda;
    private Boolean enabled;
    private Integer nbEmployer;
    private String adresse;
    private String ville;
    private String pays;
    private double apayer;
    private String logo;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate dateExpiration;
    // @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    // @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateCreation = LocalDate.now();
    @OneToMany(mappedBy ="partenaire",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Facture> factures;
    @OneToMany(mappedBy ="admin",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Commercant>  commercants;
    @ManyToMany
    @JoinTable(name = "AdminModules",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private List<Modules> modules;
    //private String commercantsId;
    //private String SadminId;
    public double totalprix(){
        double totale= 0;
        for (Modules module: modules){
            double prix= module.getPrix();
            totale=totale+prix;
        }
        return totale;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
