package fr.volax.vgiveall.users;

import fr.volax.vgiveall.VGiveall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserWrapper {
    private final List<User> usersInMemory = new ArrayList<>();

    public static User getUser(UUID userUUID){
        File userFile = new File(VGiveall.getInstance().usersFolder + "/" + userUUID.toString() + ".yml");

        return new User();
    }
}
