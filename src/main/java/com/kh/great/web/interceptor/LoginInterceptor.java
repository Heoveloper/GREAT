package com.kh.great.web.interceptor;

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

        //log.info("인증체크 = {}", requestURI);

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

        //세션정보가 있으면 세션정보(true), 없으면 null(false) 반환
        HttpSession session = request.getSession(false); //세션 조회
        if (session == null || session.getAttribute("loginMember") == null) {
            log.info("미인증 사용자의 요청!");
            //response.sendRedirect("/login?requestURI=" + requestURI);
            response.sendRedirect("/login?redirectUrl=" + redirectUrl);
            return false;
        }

        return true;
    }
}