package com.PFE.Espacecommercant.Authen.users;

import com.PFE.Espacecommercant.Authen.model.Facture;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "Client")
@Entity
public class Client  implements UserDetails{

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
        private String logo;
        private Integer nbEmployer;
        private String adresse;
        private String ville;
        private String pays;
        private boolean verified;
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDate dateExpiration;
       // @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
       // @DateTimeFormat(pattern = "dd-MM-yyyy")
        private LocalDate dateCreation = LocalDate.now();

        private boolean enabled;
        private String emailCommercant;

       @ManyToOne
       @JoinColumn(name="commercant_id")
       @JsonBackReference
       private Commercant commercant;
      @ManyToOne
      @JoinColumn(name="sadmin_id")
      @JsonBackReference
      private SAdmin sAdmin;
      @OneToMany(mappedBy ="client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
      @JsonManagedReference
      private List<Facture> factures;


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
    public Boolean getEnabled() {
        return enabled;
    }
}
