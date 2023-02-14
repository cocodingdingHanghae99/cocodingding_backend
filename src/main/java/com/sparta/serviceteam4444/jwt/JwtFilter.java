package com.sparta.serviceteam4444.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import com.sparta.serviceteam4444.dto.user.ResponseDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        Session 의 토큰 값 가져오기
        String token = jwtUtil.resolveAccessToken(request); String tokken=jwtUtil.resolveRefreshToken(request);
//        log.info("토큰 값: " + token);

        if(token != null) {
            if(!jwtUtil.validateToken(token)){
                log.info("토큰이 상했습니다. 리프레쉬 토큰 값으로 바꿉니다.");
                if(!jwtUtil.validateToken(tokken)) {
//                    log.info("리프레쉬 토큰: "+tokken);
                    jwtExceptionHandler(response, "Time runout. Please login again.", HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {

            String json = new ObjectMapper().writeValueAsString(new ResponseDto(msg)); //new Response?
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

