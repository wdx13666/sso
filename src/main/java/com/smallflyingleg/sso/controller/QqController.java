package com.smallflyingleg.sso.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import com.smallflyingleg.sso.pojo.QQUserInfo;
import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.service.SysUserService;
import com.smallflyingleg.sso.utils.Constants;
import com.smallflyingleg.sso.utils.HttpClientUtils;
import com.smallflyingleg.sso.utils.URLEncodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QqController {

    //QQ ：读取Appid相关配置信息静态类
    @Autowired
    private Constants constants;

    @Autowired
    private SysUserService sysUserService;


    /**
     * 获得跳转到qq登录页的url,前台直接a连接访问
     *
     * @param session
     * @return
     * @throws Exception
     * @author wdx
     * @date 2019年6月18日 下午8:29:20
     */
    @GetMapping("/getQQCode")
    public StringBuilder getCode(HttpSession session) throws Exception {
        // 拼接url
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/authorize?");
        url.append("response_type=code");
        url.append("&client_id=" + constants.getQqAppId());
        // 回调地址 ,回调地址要进行Encode转码
        String redirect_uri = constants.getQqRedirectUrl();
        // 转码
        url.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(redirect_uri));
        url.append("&state=ok");
        return url;
    }


    /**
     * 开始登录
     * 实际业务：
     * @param code
     * @param : accessToken，expiresIn，refreshToken，openId
     * @return
     * @throws Exception
     * @token 过期调用刷新 token重新获取token信息
     */
    @GetMapping("/QQLogin")
    public QQUserInfo QQLogin(String code) throws Exception {
        if (code != null) {
            System.out.println(code);
        }
        //获取tocket
        Map<String, Object> qqProperties = getToken(code);
        // 获取openId(每个用户的openId都是唯一不变的)
        String openId = getOpenId(qqProperties);
        qqProperties.put("openId", openId);
        // tocket过期刷新token
        // Map<String, Object> refreshToken = refreshToken(qqProperties); /
        // /获取数据
        QQUserInfo userInfo = getUserInfo(qqProperties);
        //根据openId和数据库进行比对
        SysUser sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("open_id", openId));
        //不存在保存用户信息
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setFullName(userInfo.getNickname());
            sysUser.setOpenId(openId);
            sysUserService.insert(sysUser);
        }
        return userInfo;
    }

    /**
     * 获得token信息（授权，每个用户的都不一致） --> 获得token信息该步骤返回的token期限为一个月
     *
     * @param (<String,String> qqProperties)
     * @return
     * @throws Exception
     * @author wangsong
     * @date 2019年6月18日 下午8:56:45
     */
    public Map<String, Object> getToken(String code) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=authorization_code");
        url.append("&client_id=" + constants.getQqAppId());
        url.append("&client_secret=" + constants.getQqAppSecret());
        url.append("&code=" + code);
        // 回调地址
        String redirect_uri = constants.getQqRedirectUrl();
        // 转码
        url.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(redirect_uri));
        // 获得token
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        System.out.println("url:" + url.toString());
        // 把token保存
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        // token信息
        Map<String, Object> qqProperties = new HashMap<String, Object>();
        qqProperties.put("accessToken", accessToken);
        qqProperties.put("expiresIn", String.valueOf(expiresIn));
        qqProperties.put("refreshToken", refreshToken);
        return qqProperties;
    }

    /**
     * 刷新token 信息（token过期，重新授权） *
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/refreshToken")
    public Map<String, Object> refreshToken(Map<String, Object> qqProperties) throws Exception {
        // 获取refreshToken
        String refreshToken = (String) qqProperties.get("refreshToken");
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=refresh_token");
        url.append("&client_id=" + constants.getQqAppId());
        url.append("&client_secret=" + constants.getQqAppSecret());
        url.append("&refresh_token=" + refreshToken);
        System.out.println("url:" + url.toString());
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        // 把新获取的token存到map中
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String newRefreshToken = StringUtils.substringAfterLast(items[2], "=");
        //重置信息
        qqProperties.put("accessToken", accessToken);
        qqProperties.put("expiresIn", String.valueOf(expiresIn));
        qqProperties.put("refreshToken", newRefreshToken);
        return qqProperties;
    }

    /**
     * 获取用户openId（根据token）
     *
     * @throws Exception
     */
    public String getOpenId(Map<String, Object> qqProperties) throws Exception {
        // 获取保存的用户的token
        String accessToken = (String) qqProperties.get("accessToken");
        if (!StringUtils.isNotEmpty(accessToken)) {
            return "未授权";
        }
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/me?");
        url.append("access_token=" + accessToken);
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        return openId;
    }

    /*根据token,openId获取用户信息*/
    public QQUserInfo getUserInfo(Map<String, Object> qqProperties) throws Exception {
        // 取token
        String accessToken = (String) qqProperties.get("accessToken");
        String openId = (String) qqProperties.get("openId");
        if (!StringUtils.isNotEmpty(accessToken) || !StringUtils.isNotEmpty(openId)) {
            return null;
        }
        // 拼接url
        StringBuilder url = new StringBuilder("https://graph.qq.com/user/get_user_info?");
        url.append("access_token=" + accessToken);
        url.append("&oauth_consumer_key=" + constants.getQqAppId());
        url.append("&openid=" + openId);
        // 获取qq相关数据
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        Object json = JSON.parseObject(result, QQUserInfo.class);
        QQUserInfo userInfo = (QQUserInfo) json;
        return userInfo;
    }


    /* *
     * 登录回调
     */
 /*   @RequestMapping("/index")
    public String qqCallBack(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            String accessToken = null, openID = null;
            long tokenExpireIn = 0L;

            if (accessTokenObj.getAccessToken().equals("")) {

                return "404";
            }

            accessToken = accessTokenObj.getAccessToken();
            tokenExpireIn = accessTokenObj.getExpireIn();


            // 利用获取到的accessToken 去获取当前用的openid
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();


            String icon = null, nickName = null, sex = null;
            // 去获取用户在Qzone的昵称等信息
            com.qq.connect.api.qzone.UserInfo qzoneUserInfo =
                    new com.qq.connect.api.qzone.UserInfo(accessToken, openID);
            UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

            if (userInfoBean.getRet() == 0) {
                nickName = userInfoBean.getNickname();
                sex = userInfoBean.getGender();
                if (userInfoBean.getAvatar().getAvatarURL100() == null) {
                    icon = userInfoBean.getAvatar().getAvatarURL50();
                }
                icon = userInfoBean.getAvatar().getAvatarURL100();
                // userInfoBean.getAvatar().getAvatarURL50();
                // userInfoBean.getAvatar().getAvatarURL100();
            }
            // 自己网站的用户实体类，根据openId查询是否已存在
            QQUserInfo userInfo = new QQUserInfo();
            userInfo.setOpenId(openID);
            QQUserInfo localUser = userInfoService.selectByOpenId(userInfo);
            if (localUser != null) {
                // 老用户处理
                //你需要的业务操作
            } else {
                // 新用户处理
                UserInfo userInfos = new UserInfo();
                userInfos.setUserId(Tools.getUniqueId());
                userInfos.setUserName(nickName);
                userInfos.setSex(sex);
                userInfoService.save(userInfos);
                //你需要的业务操作
            }

        } catch (QQConnectException e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }*/
}
