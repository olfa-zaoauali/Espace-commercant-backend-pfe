package com.PFE.Espacecommercant.Authen.users;

public class UserMapper {
    public static User toUser(Admin admin){
        User user = new User();
        user.setEmail(admin.getEmail());
        user.setPassword(admin.getPassword());
        user.setEnabled(admin.getEnabled());
        user.setImage(admin.getLogo());
        user.setTenantId(admin.getTenantId());
        user.setRole(Role.ADMIN);
        return (user);
    }
}
