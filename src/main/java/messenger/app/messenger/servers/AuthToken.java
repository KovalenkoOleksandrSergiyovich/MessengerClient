package messenger.app.messenger.servers;

import messenger.app.messenger.models.User;

import java.io.*;

public class AuthToken {
    private static String token;
    private static String username;
    private static int userId;
    private static final String tokenFile = "token.txt";

    static public void setTokenAndName(String token, User user) {
        AuthToken.token = "Bearer " + token;
        AuthToken.username = user.getUsername();
        AuthToken.userId = user.getId();
        AuthToken.saveTokenToFile();
    }

    public AuthToken() {
        AuthToken.getTokenFromFile();
    }

    public static String getToken() {
        return AuthToken.token;
    }
    public static int getUserId() {
        return AuthToken.userId;
    }
    public static String getUsername() {
        return AuthToken.username;
    }

    public static void setUsername(String username) {
        AuthToken.username = username;
    }

    private static void saveTokenToFile() {
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter(AuthToken.tokenFile, false); //true tells to append data.
            out = new BufferedWriter(fstream);
            out.write(AuthToken.getToken());
            out.newLine();
            out.write(AuthToken.getUsername());
            out.newLine();
            out.write(String.valueOf(AuthToken.getUserId()));
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void getTokenFromFile() {
        BufferedReader reader = null;
        try {
            File file = new File(AuthToken.tokenFile);
            if (file.exists()) {
                FileReader fileReader = new FileReader(AuthToken.tokenFile);
                reader = new BufferedReader(fileReader);
                AuthToken.token = reader.readLine();
                AuthToken.username = reader.readLine();
                AuthToken.userId = Integer.parseInt(reader.readLine());
            }
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean hasToken() {
        return AuthToken.token != null;
    }
}
