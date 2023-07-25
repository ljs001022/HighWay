package hanium.highwayspring.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("NMA8JPctFuna59f5ALhj1Js2Hsd45Gso0aNks25iuGh9sPklMn0sn8Hbs7V4a5wAsdGWs7qg96xgsSa2g1swQss32gmcXas")
    private String SECRET_KEY;// = "sec";
    @Value("ALhj1Js2Hsd45Gso0aNks25iuGh9sPklMn0sn8Hbs7V4aNMA8JPctFuna59f5ALhj1Js2Hcsd45Gso0aNks25iuGh9sj1Js")
    private String REFRESH_KEY;// = "ref";

//    private final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;   // 30분
//    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주

    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 24 * 365 * 100 * 1000L;   // 30분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 365 * 100 * 1000L;   // 100년
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        System.out.println("SECRET_KEY: "+SECRET_KEY);
        System.out.println("REFRESH_KEY = " + REFRESH_KEY);
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
    }

    // JWT 토큰 생성
    public String createAccessToken(String userId) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("userId", userId);
        Date now = new Date();
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId); //
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);
        Key key = Keys.hmacShaKeyFor(REFRESH_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //HttpServletRequest 객체에서 "ACCESS_TOKEN" 헤더 값을 추출하여 액세스 토큰을 반환. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("ACCESS_TOKEN");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("REFRESH_TOKEN");
    }

    //주어진 토큰으로부터 클레임(Claims)을 추출하는 메서드
    public Claims getClaimsFormToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token) //주어진 토큰을 파싱하여 검증. 토큰의 서명을 확인하고, 토큰이 유효한지 검증.
                .getBody(); //파싱된 토큰의 본문 부분을 반환. 이 부분은 클레임 객체로서, 토큰에 포함된 정보를 나타냅니다.
    }

    public Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(REFRESH_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getTokenExpiration(String token){
        // Claims 객체는 토큰에 저장된 정보를 손쉽게 액세스할 수 있는 메서드와 속성을 제공. 이를 통해 토큰에 저장된 사용자 정보, 권한, 만료일 등의 데이터에 접근할 수 있음.
        Claims claims = getClaimsFormToken(token);
        return claims.getExpiration();
    }

    public boolean isValidAccessToken(String token) {
        System.out.println("isValidToken is : " +token);
        try {
            Claims accessClaims = getClaimsFormToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("userId"));
            return true;
        } catch (ExpiredJwtException exception) { // JWT 토큰의 유효 기간이 만료된 경우
            System.out.println("Token Expired UserID : " + exception.getClaims().get("userId"));
            return false;
        } catch (JwtException exception) { // JWT 관련 예외의 기본 클래스
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }
    public boolean isValidRefreshToken(String token) {
        try {
            Claims accessClaims = getClaimsToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("userId"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get("userId"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }
}