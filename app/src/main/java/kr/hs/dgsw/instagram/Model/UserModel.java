package kr.hs.dgsw.instagram.Model;

/**
 * Created by leesojin on 2018. 5. 17..
 */

public class UserModel {
    private String name;
    private String tel;
    private String account;
    private String password;

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
