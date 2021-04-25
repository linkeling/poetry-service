package com.poetry.admin.enums;

public enum EnumYesNoInfo {
    EDUCATION_1(1,"是"),
    EDUCATION_0(0,"否");

    private  Integer value;
    private  String name;

    EnumYesNoInfo(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(Integer value) {
        EnumYesNoInfo[] enumYesNoInfos = values();
        for (EnumYesNoInfo enumYesNoInfo:enumYesNoInfos) {
            if (enumYesNoInfo.getValue().equals(value)) {
                return enumYesNoInfo.getName();
            }
        }
        return null;
    }

    public static Integer getValue(String name) {
        EnumYesNoInfo[] enumYesNoInfos = values();
        for (EnumYesNoInfo enumYesNoInfo:enumYesNoInfos) {
            if (enumYesNoInfo.getValue().equals(name)) {
                return enumYesNoInfo.getValue();
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
