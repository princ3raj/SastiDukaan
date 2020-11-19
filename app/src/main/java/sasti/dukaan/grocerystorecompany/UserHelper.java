package sasti.dukaan.grocerystorecompany;

public class UserHelper {

    String first_name, last_name,username,phone_number,password;


    public UserHelper() {
    }

    public UserHelper(String first_name, String last_name, String username, String phone_number, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.phone_number = phone_number;
        this.password = password;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
