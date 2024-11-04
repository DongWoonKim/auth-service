package com.example.spring.authservice.service;

import com.example.spring.authservice.config.jwt.JwtProperties;
import com.example.spring.authservice.enums.Role;
import com.example.spring.authservice.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenProviderService {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(
                new Date(now.getTime() + expiredAt.toMillis()),
                user
        );
    }

    public int validToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey( getSecretKey() ) // SecretKey 객체 사용
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return 1;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            System.out.println("Access Token이 만료되었습니다.");
            return 2;
        } catch (Exception e) {
            // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            System.out.println("err : " + e.getMessage());
            return 3;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        // Claims에서 역할을 추출하고, GrantedAuthority로 변환
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority( claims.get("role").toString() ) // 역할을 GrantedAuthority로 변환
        );

        // UserDetails 객체 생성
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

        // UsernamePasswordAuthenticationToken 생성
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    // 토큰에서 Subject, id, role 값을 추출하는 메서드
    public User getTokenDetails(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())  // 서명 검증을 위한 비밀 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();  // 토큰의 클레임(Claims)을 추출

        return User.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(Role.valueOf(claims.get("role", String.class)))
                .build();
    }

    private String makeToken(Date expire, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expire)
                .setSubject( user.getUserId() )
                .claim("id", user.getId())
                .claim("role", user.getRole().name())
                .claim("userName", user.getUserName())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())  // SecretKey 객체 사용
                .build()                    // 파서 빌드
                .parseClaimsJws(token)      // 토큰 파싱
                .getBody();                 // 클레임 추출
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
