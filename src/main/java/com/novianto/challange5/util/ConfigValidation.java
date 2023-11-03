package com.novianto.challange5.util;

import org.springframework.stereotype.Component;

@Component
public class ConfigValidation {

    public static Integer  STATUS_CODE_NOT_FOUND =404;
    public static Integer STATUS_CODE_SUCCESS = 200;
    public static Integer STATUS_CODE_BAD_REQUEST = 400;
    public static Integer STATUS_CODE_INTERNAL_SERVER_ERROR = 500;
    public static String MERCHANT_REQUIRED = "Merchant is Required";
    public static String USER_REQUIRED = "User is Required";
    public static String ORDER_REQUIRED = "Order is Required";
    public static String PRODUCT_REQUIRED = "Product is Required";
    public static String ID_USER_NOT_FOUND = "Id User Tidak Ditemukan";
    public static String ID_MERCHANT_NOT_FOUND = "Id Merchant Tidak Ditemukan";
    public static String ID_PRODUCT_NOT_FOUND = "Id Product Tidak Ditemukan";
    public static String ID_ORDER_NOT_FOUND = "Id Order Tidak Ditemukan";
    public static String ID_ORDER_DETAIL_NOT_FOUND = "Id Order Detail Tidak Ditemukan";
    public  static  String MERCHANT_NAME_NOT_VALID = "Merchant Name Not Valid.";
    public  static  String USERNAME_NOT_VALID = "Username Not Valid.";
    public  static  String EMAIL_NOT_VALID = "Email Address Not Valid.";
    public  static  String USER_DATA_INVALID = "User Data is Invalid.";
    public  static  String QUANTITY_NOT_VALID = "Quantity Data is Invalid.";
    public  static  String MERCHANT_DATA_INVALID = "Merchant Data is Invalid.";
    public  static  String PRODUCT_DATA_INVALID = "Product Data is Invalid.";
    public  static  String ORDER_DATA_INVALID = "Order Data is Invalid.";
    public  static  String ORDER_DETAIL_DATA_INVALID = "Order Detail Data is Invalid.";
    public  static  String PRODUCT_NAME_NOT_VALID = "Product Name Not Invalid.";
    public  static  String PRICE_NOT_VALID = "Product Price Not Invalid.";
    public  static  String ORDER_TIME_NOT_VALID = "Order Time Not Invalid.";
    public  static  String ORDER_COMPLETED_NOT_VALID = "Order Completed Not Invalid.";
    public  static  String SUCCESS = "Success.";

}
