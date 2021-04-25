package com.poetry.admin.enums;

public enum EnumPoetryType {
    type_1(1,"小学"),
    type_2(2,"初中"),
    type_3(3,"高中"),
    type_4(4,"课外");

    private  Integer value;
    private  String name;

    EnumPoetryType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(Integer value) {
        EnumPoetryType[] enumPoetryTypes = values();
        for (EnumPoetryType enumPoetryType : enumPoetryTypes) {
            if (enumPoetryType.getValue().equals(value)) {
                return enumPoetryType.getName();
            }
        }
        return null;
    }


    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}
