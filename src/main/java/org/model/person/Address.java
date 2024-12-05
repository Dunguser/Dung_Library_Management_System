package org.model.person;

public class Address {
    private String houseNumber;
    private String streetName;
    private String wardName;
    private String districtName;
    private String cityName;

    public Address(String houseNumber, String streetName, String wardName, String districtName, String cityName) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.wardName = wardName;
        this.districtName = districtName;
        this.cityName = cityName;
    }

    public Address(String streetName, String ward_name, String districtName, String cityName) {
        this.streetName = streetName;
        this.wardName = ward_name;
        this.districtName = districtName;
        this.cityName = cityName;
    }

    public Address(String ward_name, String districtName, String cityName) {
        this.wardName = ward_name;
        this.districtName = districtName;
        this.cityName = cityName;
    }

    public Address(String fullAddress) {
        String[] fulladdress = fullAddress.split("/");
        this.houseNumber = fulladdress[0];
        this.streetName = fulladdress[1];
        this.wardName = fulladdress[2];
        this.districtName = fulladdress[3];
        this.cityName = fulladdress[4];

    }

    public String getHouseNumber() {
        return houseNumber;
    }


    public String getCityName() {
        return cityName;
    }


    public String getDistrictName() {
        return districtName;
    }


    public String getWardName() {
        return wardName;
    }


    public String getStreetName() {
        return streetName;
    }

    public static boolean checkAddressFormat(String address) {
        String[] a = address.split("/");
        if (a.length != 5) return false;
        if (a[0].isEmpty()) return false;
        if (a[1].isEmpty()) return false;
        if (a[2].isEmpty()) return false;
        if (a[3].isEmpty()) return false;
        return !a[4].isEmpty();
    }


    public String getFullAddress() {
        return houseNumber + "/" + streetName + "/"
                + wardName + "/" + districtName + "/" + cityName;
    }
}

