package com.PFE.Espacecommercant.Authen.users;

public class ClientToUser {
    public static User toUser(Client client){
        User user = new User();
        user.setEmail(client.getEmail());
        user.setPassword(client.getPassword());
        user.setEnabled(client.getEnabled());
        user.setImage(client.getLogo());
        user.setTenantId(client.getTenantId());
        user.setRole(Role.CLIENT);
        return (user);
    }
}
