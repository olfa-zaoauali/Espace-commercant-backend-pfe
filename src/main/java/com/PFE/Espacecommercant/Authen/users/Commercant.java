package com.PFE.Espacecommercant.Authen.users;

import com.PFE.Espacecommercant.Authen.DTO.CommercantReqdto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commercant")
public class Commercant implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String tenantId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String telephone;
    private String adresse;
    private String ville;
    private String image;
    private Double pay;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name="admin_id")
    @JsonBackReference
    private Admin admin;
    @ManyToOne
    @JoinColumn(name="sadmin_id")
    @JsonBackReference
    private SAdmin sadmin;
    @OneToMany(mappedBy ="commercant",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Client> clients;


    public static Commercant mapperdtotocom(CommercantReqdto commercantReqtdto){
        Commercant commercantentity = new Commercant();
        commercantentity.setAdresse(commercantReqtdto.getAdresse());
        commercantentity.setEmail(commercantReqtdto.getEmail());
        commercantentity.setImage(commercantReqtdto.getImage());
        commercantentity.setFirstname(commercantReqtdto.getFirstname());
        commercantentity.setLastname(commercantReqtdto.getLastname());
        commercantentity.setVille(commercantReqtdto.getVille());
        commercantentity.setTelephone(commercantReqtdto.getTelephone());
        commercantentity.setPay(commercantReqtdto.getPay());
        return commercantentity;

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
