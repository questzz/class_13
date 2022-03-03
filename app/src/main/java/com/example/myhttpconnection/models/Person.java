package com.example.myhttpconnection.models;

public class Person {
    String 이름;
    int 나이;
    String 직업;
    String 취미;
    Boolean 결혼여부;

    public String get이름() {
        return 이름;
    }

    public void set이름(String 이름) {
        this.이름 = 이름;
    }

    public int get나이() {
        return 나이;
    }

    public void set나이(int 나이) {
        this.나이 = 나이;
    }

    public String get직업() {
        return 직업;
    }

    public void set직업(String 직업) {
        this.직업 = 직업;
    }

    public String get취미() {
        return 취미;
    }

    public void set취미(String 취미) {
        this.취미 = 취미;
    }

    public Boolean get결혼여부() {
        return 결혼여부;
    }

    public void set결혼여부(Boolean 결혼여부) {
        this.결혼여부 = 결혼여부;
    }

    @Override
    public String toString() {
        return "Person{" +
                "이름='" + 이름 + '\'' +
                ", 나이=" + 나이 +
                ", 직업='" + 직업 + '\'' +
                ", 취미='" + 취미 + '\'' +
                ", 결혼여부=" + 결혼여부 +
                '}';
    }
}
