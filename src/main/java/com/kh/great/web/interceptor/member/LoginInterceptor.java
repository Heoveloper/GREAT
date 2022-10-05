package com.kh.great.web.interceptor.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String redirectUrl = null;
        String requestURI = request.getRequestURI();
        log.info("인증체크 = {}", requestURI);

        //세션 조회
//        HttpSession session = request.getSession(false);
//
//        if (session == null || session.getAttribute("loginMember") == null) {
//            log.info("미인증 사용자의 요청");
//
//            //로그인 화면으로 redirect
//            response.sendRedirect("/login?requestURI=" + requestURI);
//            return false;
//        }
//
//        return true;
//    }

        if (request.getQueryString() != null) {
            String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");
            StringBuffer str = new StringBuffer();
            redirectUrl = str.append(requestURI)
                             .append("&")
                             .append(queryString)
                             .toString();
        } else {
            redirectUrl = requestURI;
        }

        //세션정보가 있으면 반환 없으면 null반환
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            log.info("미인증 요청");
            response.sendRedirect("/login?redirectUrl=" + redirectUrl);
            return false;
        }
        return true;
    }
}
