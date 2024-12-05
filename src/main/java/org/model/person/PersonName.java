package org.model.person;

public class PersonName {
    private String firstName;//ten
    private String lastName;// ho


    public PersonName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public PersonName(String fullName) {
        String[] fullname = fullName.split(" ");
        int l = fullname.length;

        this.firstName = fullname[l - 1];
        this.lastName = "";

        for (int i = 0; i < l - 1; ++i) {
            this.lastName += fullname[i] + " ";
        }

        this.lastName = this.lastName.trim();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String fullName() {
        return lastName + " " + firstName;
    }
}
