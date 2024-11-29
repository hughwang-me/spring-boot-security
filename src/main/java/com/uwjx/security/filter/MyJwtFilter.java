package com.uwjx.security.filter;

import com.alibaba.fastjson.JSON;
import com.uwjx.security.exception.TokenErrorException;
import com.uwjx.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class MyJwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.warn("认证参数头 : {}" , authHeader);
        if (StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.validateToken(token);
                log.warn("TOKEN有效");
//                List<String> roles = jwtUtil.getRolesFromJwt(token);
//                log.warn("用户的角色:{}" , JSON.toJSONString(roles));
//                List<String> roles = jwtUtil.getAuthorities(token);
//                log.warn("用户的角色:{}" , JSON.toJSONString(roles));
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(username, null, null);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);// 将认证信息存储到 SecurityContext 中
            } catch (Exception e) {
                resolver.resolveException(request, response, null, new TokenErrorException(4002 , "无效的TOKEN。"));
                return;
            }
        }else {
            log.warn("没有检测到token参数");
        }
        filterChain.doFilter(request, response);
    }
}
