package Account;

public class Account {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String imageLink;
    private String passHash;
    private String userType;
    private String aboutMe;

    public Account(
            int userId,
            String firstName,
            String lastName,
            String userName,
            String imageLink,
            String passHash,
            String aboutMe,
            String userType
    ) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.imageLink = imageLink;
        this.passHash = passHash;
        this.userType = userType;
        this.aboutMe = aboutMe;
    }

    public static boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z0-9._-]+$");
    }

    public String getFirstName() { return firstName; }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() { return userName; }

    public String getPassHash() {
        return passHash;
    }

    public String getUserType() {
        return userType;
    }

    public String getImage() {
        return imageLink;
    }

    public boolean checkPassword(String password) {
        return Hash.verifyPassword(password, passHash);
    }

    public boolean isAdmin() {
        return userType.equals("admin");
    }

    public String getAboutMe() { return aboutMe; }

    public String setAboutMe(String aboutMe) { return this.aboutMe = aboutMe; }

    public int getUserId() {
        return userId;
    }
}
