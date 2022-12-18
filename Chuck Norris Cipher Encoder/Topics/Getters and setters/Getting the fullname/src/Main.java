class User {
    private String firstName;
    private String lastName;

    public User() {
        this.firstName = null;
        this.lastName = null;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        if (this.firstName == null && this.lastName == null) {
            return "Unknown";
        }
        if (this.firstName != null && lastName != null) {
            return this.firstName + " " + this.lastName;
        }
        if (this.firstName != null) {
            return this.firstName;
        }
        return this.lastName;
    }
}