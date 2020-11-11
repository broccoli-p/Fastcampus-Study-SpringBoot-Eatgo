package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

class JwtUtilTests {
    private static final String SECRET = "12345678901234567890123456789012";
    private JwtUtil jwtUtil;
    @BeforeEach
    public void setUp() {
         jwtUtil = new JwtUtil(SECRET);

    }
    @Test
    public void createToken() {

        String token = jwtUtil.createToken(1004L, "John", null);

        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.2TxSPnsPgbMASvtong4o8tSsarVRzhVuOq_3FqsEUP4";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name"), is("John"));
        assertThat(claims.get("userid", Long.class), is(1004L));
    }

    @Test
    public void getOwnerClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOjIxLCJuYW1lIjoiSm9obiIsInJlc3RhdXJhbnRJZCI6MTAwNH0.LBnnnDRdW6Oe7e-7mJghOS5s2Ogdl_0v93lfmQEXLUY";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name"), is("John"));
        assertThat(claims.get("userid", Long.class), is(21L));
        assertThat(claims.get("restaurantId", Long.class), is(1004L));
    }
}