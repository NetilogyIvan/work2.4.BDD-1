package ru.netology.web.data;

import groovy.lang.GString;
import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


    @Value
    public static class CardInfo {
        String cardNumber;
        String cardID;
    }

    public static CardInfo getFirstCardNumber() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardNumber() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    @Value
    public static class TransferAmount {
        private String amount;
    }

    public static int generateValidAmount(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000);
    }


}

//* login: 'vasya'
//      * password: 'qwerty123'
//    * verification code (hardcoded): '12345'
//  * cards:
//* first:
//* number: '5559 0000 0000 0001'
//* balance: 10 000 RUB
//* second:
//* number: '5559 0000 0000 0002'
//* balance: 10 000 RUB
