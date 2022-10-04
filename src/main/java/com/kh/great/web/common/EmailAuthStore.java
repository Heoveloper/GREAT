package com.kh.great.web.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class EmailAuthStore {
    private HashMap<String, String> store = new LinkedHashMap<>();

    public void add(String email, String authNo) {
        store.put(email, authNo);
    }

    public String get(String email) {
        return (String) store.get(email);
    }

    public void remove(String email) {
        store.remove(email);
    }

    public boolean isExist(String email, String authNo){

        boolean isExist = false;
        //이메일이 없을경우 false
        if(get(email) == null) return isExist;

        //이메일이 존재하고 인증번호가 같으면 true
        if(get(email).equals(authNo)){
            isExist = true;
        }
        return isExist;
    }
}
