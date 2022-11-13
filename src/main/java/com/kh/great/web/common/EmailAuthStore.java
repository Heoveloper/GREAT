package com.kh.great.web.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class EmailAuthStore {
    //이메일, 인증코드 저장할 저장소 생성
    private HashMap<String, String> store = new LinkedHashMap<>();

    //이메일, 인증코드 저장
    public void add(String email, String authNo) {

        store.put(email, authNo);
    }

    //이메일(키)로 인증코드(값) 가져오기
    public String get(String email) {

        return (String) store.get(email); //해당 이메일이 존재하지 않으면 null 반환
    }

    //이메일(키), 인증코드(값) 삭제
    public void remove(String email) {

        store.remove(email);
    }

    //이메일 존재 여부
    public boolean isExist(String email, String authNo) {
        boolean isExist = false;

        //이메일이 존재하지 않으면 false
        if (get(email) == null) return isExist;

        //이메일이 존재하고 인증코드가 같으면 true
        if (get(email).equals(authNo)) {
            isExist = true;
        }

        return isExist;
    }
}