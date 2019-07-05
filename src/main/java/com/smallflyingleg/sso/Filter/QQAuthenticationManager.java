package com.smallflyingleg.sso.Filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallflyingleg.sso.pojo.QQUserInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QQAuthenticationManager implements AuthenticationManager {

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    /**
     * 获取 QQ 登录信息的 API 地址
     */
    private final static String userInfoUri = "https://graph.qq.com/user/get_user_info";

    /**
     * 获取 QQ 用户信息的地址拼接
     */
    private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName() != null && auth.getCredentials() != null) {
            QQUserInfo user = null;
            try {
                user = getUserInfo(auth.getName(), (String) (auth.getCredentials()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new UsernamePasswordAuthenticationToken(user, null, AUTHORITIES);
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    private QQUserInfo getUserInfo(String accessToken, String openId) throws Exception {
        String url = String.format(USER_INFO_API, userInfoUri, accessToken, "101698287", openId);
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }
        String resultText = document.text();
        JSONObject json = JSON.parseObject(resultText);

        QQUserInfo user = new QQUserInfo();
        user.setNickname(json.getString("nickname"));
//        user.setEmail("暂无。。。。");
        //user.setGender(json.getString("gender"));
        //user.setProvince(json.getString("province"));
        //user.setYear(json.getString("year"));
//        user.setAvatar(json.getString("figureurl_qq_2"));

        return user;
    }
}
