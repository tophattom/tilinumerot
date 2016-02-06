package fi.rf.jaakko.tilinumerot;

/**
 * Created by jaakko on 6.2.2016.
 */
public class Person {
    private String name;
    private String account;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public boolean setAccount(String account) {
        if (Person.validAccount(account)) {
            this.account = account;
            return true;
        }

        return false;
    }

    private static boolean validAccount(String account) {
        return true;
    }
}
