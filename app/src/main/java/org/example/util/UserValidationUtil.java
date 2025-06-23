package org.example.util;

import org.example.model.UserInfoDto;

public class UserValidationUtil {

    public static Boolean isValidEmail(UserInfoDto userInfoDto){
        //        validates email address got from the signed up user
        String emailAddress = userInfoDto.getEmail();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return emailAddress != null && emailAddress.matches(regex);
    }

    public static Boolean isValidPhoneNumber(UserInfoDto userInfoDto){
        //        validates phone number got from the signed up user
        String phone_number = userInfoDto.getPhoneNumber();
        Boolean valid_phone = phone_number!=null && phone_number.matches("^[6-9]\\d{9}$");
        return valid_phone;
    }
}
