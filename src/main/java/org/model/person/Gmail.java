package org.model.person;

public class Gmail {

    private String gmail;


    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        if (checkGamilForm(gmail)) this.gmail = gmail;
    }

    public static boolean checkGamilForm(String gmail) {
        String gmail1 = gmail.trim();
        int l = gmail1.length();
        for (int i = 0; i < l; i++) {
            if (gmail1.charAt(i) == ' ') {
                System.out.println("Gmail không được chứa dấu cách !!!");
                return false;
            }
        }
        if (l < 16) {
            System.out.println("Tên Gmail phải có ít nhất 16 kí tự !!!");
            return false;
        } else {
            String last10chars = gmail1.substring(l - 10);
            if (!last10chars.equals("@gmail.com")) {
                System.out.println("Gmail không đúng định dạng!!!");
                System.out.println("Định dạng chuẩn: ...@gmail.com");
                return false;
            }
        }
        return true;
    }

    public Gmail(String gmail) {
        setGmail(gmail);
    }

}

