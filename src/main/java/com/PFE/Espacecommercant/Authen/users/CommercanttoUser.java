package com.PFE.Espacecommercant.Authen.users;

public class CommercanttoUser {
    public static User toUser(Commercant commercant){
        User user = new User();
        user.setEmail(commercant.getEmail());
        user.setPassword(commercant.getPassword());
        user.setEnabled(commercant.getEnabled());
        user.setImage(commercant.getImage());
        user.setTenantId(commercant.getTenantId());
        user.setRole(Role.COMMERCANT);
        return (user);
    }
}
