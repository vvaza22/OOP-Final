package Account;

public class Account {
    private String firstName;
    private String lastName;
    private String userName;
    private String imageLink;
    private String passHash;

    public Account(String firstName, String lastName, String userName, String imageLink, String passHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.imageLink = imageLink;
        this.passHash = passHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getImage() {
        return imageLink;
    }

}
