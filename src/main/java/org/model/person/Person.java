package org.model.person;

public class Person {
    protected PersonName personName;
    protected int personAge;
    protected Address personAddress;
    protected Gmail personGmail;
    protected PhoneNumber personPhone;
    protected boolean personSex;
    protected Date personDateBirth;

    public Date getPersonDatebirth() {
        return personDateBirth;
    }

    public void setPersonDatebirth(Date personDatebirth) {
        this.personDateBirth = personDatebirth;
    }

    public void setPersonSex(boolean personSex) {
        this.personSex = personSex;
    }

    public String getPersonSex() {
        if (personSex) return "Nam";
        return "Nữ";
    }

    public Person(PersonName personName, Address personAddress,
                  Gmail personGmail, PhoneNumber personPhone,
                  boolean personSex, Date personDateBirth) {
        this.personName = personName;
        this.personAddress = personAddress;
        this.personGmail = personGmail;
        this.personPhone = personPhone;
        this.personSex = personSex;
        this.personDateBirth = personDateBirth;
    }

    public Person() {
    }

    public Address getPersonAddress() {
        return personAddress;
    }

    public String getPersonAddress2() {
        return personAddress.getHouseNumber()
                + ", " + personAddress.getStreetName()
                + ", " + personAddress.getWardName()
                + ", " + personAddress.getDistrictName()
                + ", " + personAddress.getCityName();
    }

    public void setPersonAddress(Address personAddress) {
        this.personAddress = personAddress;
    }

    public Gmail getPersonGmail() {
        return personGmail;
    }

    public void setPersonGmail(Gmail personGmail) {
        this.personGmail = personGmail;
    }

    public PhoneNumber getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(PhoneNumber personPhone) {
        this.personPhone = personPhone;
    }

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    public void printInformationOfPerson() {
        System.out.printf("Tên: %-20s\n", personName.fullName());
        System.out.printf("Tuổi: %-3d\n", personAge);
        System.out.printf("Giới tính: %-10s\n", this.getPersonSex());
        System.out.printf("Ngày sinh: %-15s\n", personDateBirth.stringDate());
        System.out.printf("Số điện thoại: %-15s\n", personPhone.getPhoneNum());
        System.out.printf("Gmail: %-30s\n", personGmail.getGmail());
        System.out.printf("Địa chỉ: %-50s\n", this.getPersonAddress2());

    }
}




