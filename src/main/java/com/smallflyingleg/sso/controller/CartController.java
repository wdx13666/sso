package com.smallflyingleg.sso.controller;

import com.alibaba.fastjson.JSON;
import com.smallflyingleg.sso.service.CartService;
import com.smallflyingleg.sso.utils.CookieUtil;
import com.smallflyingleg.sso.vo.Cart;
import com.smallflyingleg.sso.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    /**
     * 购物车列表
     *
     * @param
     * @return
     */
    @GetMapping("/findCartList")
    public List<Cart> findCartList() {
        String username = "wdx";
        //读取本地购物车
        String cartListString = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if (cartListString == null || cartListString.equals("")) {
            cartListString = "[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
        if (!username.equals("wdx")){   //如果未登录
            return cartList_cookie;
        }else {     //已登陆
            List<Cart> cartList_redis =cartService.findCartListFromRedis(username);//从redis中提取
            if (cartList_cookie.size() > 0){//如果本地存在购物车
                //合并购物车
                cartList_redis = cartService.mergeCartList(cartList_redis,cartList_cookie);
                //清除本地cookie的数据
                CookieUtil.deleteCookie(request,response,"cartList");
                //将合并后的数据存入redis
                cartService.saveCartListToRedis(username, cartList_redis);
            }
            return cartList_redis;
        }

    }

    /**
     * 添加商品到购物车
     *
     * @param itemId
     * @param num
     * @return
     */
    @PostMapping("/addGoodsToCartList")
    public Boolean addGoodsToCartList(Long itemId, Integer num) {
        try {
            List<Cart> cartList = findCartList();//获取购物车列表
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);
            String username = "wdx";
            if (!username.equals("wdx")) {//如果未登录ss
                CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList), 3600 * 24, "UTF-8");
                System.out.println("向cookie存入数据");
            }else {//如果是已登录，保存到redis
                cartService.saveCartListToRedis(username,cartList);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}