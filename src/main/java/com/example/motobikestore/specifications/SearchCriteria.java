package com.example.motobikestore.specifications;

import lombok.Getter;

@Getter
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
    public SearchCriteria() {
    }

    public void setKey(String key) {
        if (key.matches("^[a-zA-Z0-9]+$")){
            this.key = key;
        }
        else
            this.key = "";
            System.out.println("Key error!");
    }

    public void setOperation(String operation) {
//        if (key.matches(".*[><=].*")){
//            this.key = key;
//        }
//        else
//            this.key = "";
        this.operation = operation;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
