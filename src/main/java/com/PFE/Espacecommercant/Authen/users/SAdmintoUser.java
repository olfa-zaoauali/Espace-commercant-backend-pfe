package com.PFE.Espacecommercant.Authen.users;

public class SAdmintoUser {
    public static User toUser(SAdmin sAdmin){
        User user = new User();
        user.setEmail(sAdmin.getEmail());
        user.setPassword(sAdmin.getPassword());
        user.setEnabled(sAdmin.getEnabled());
        user.setImage(sAdmin.getImage());
        user.setTenant_id(sAdmin.getId());
        user.setRole(Role.SADMIN);
        return (user);
    }
}
