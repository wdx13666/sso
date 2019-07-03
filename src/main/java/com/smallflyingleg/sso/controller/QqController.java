package com.smallflyingleg.sso.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class QqController {


    /**
     * QQ登录
     */
    @RequestMapping("/qqLogin")
    public void qqLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录回调
     */
    @RequestMapping("/index")
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
            /*UserInfo userInfo = new UserInfo();
            userInfo.setOpenId(openID);
            UserInfo localUser = userInfoService.selectByOpenId(userInfo);
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
            }*/

        } catch (QQConnectException e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }
}
