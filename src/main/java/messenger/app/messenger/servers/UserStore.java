package messenger.app.messenger.servers;

import messenger.app.messenger.models.User;

import java.util.ArrayList;

public class UserStore {
    static ArrayList<User> users = new ArrayList<User>();

    public static void setUsers(ArrayList<User> users) {
        UserStore.users = users;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
