package org.model.person;

public class PhoneNumber {
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        if (checkPhoneForm(phoneNum)) this.phoneNum = phoneNum;
    }

    public static boolean checkPhoneForm(String phoneNum) {
        String phone = phoneNum.replaceAll("\\s+", "");
        int l = phone.length();
        if (l != 10) {
            System.out.println("Số điện thoại không hợp lệ");
            return false;
        } else {
            for (int i = 0; i < l; i++) {
                if (phone.charAt(i) > '9' || phone.charAt(i) < '0') {
                    System.out.println("Số điện thoại không hợp lệ");
                    return false;
                }
            }
        }
        return true;
    }

    public PhoneNumber(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
