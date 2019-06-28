package com.smallflyingleg.sso;

import com.smallflyingleg.sso.mapper.SysUserMapper;
import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import sun.applet.Main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SsoApplicationTests {

    @Autowired
    private SysUserMapper mapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private  RedisTemplate redisTemplate;



    @Test
    public void contextLoads() {
        SysUser sysUsers = sysUserService.selectById(30);
        String username = "wdx";
       /* if (username.equals("wdx")){
            String token = UUID.randomUUID().toString();
            redisTemplate.boundValueOps("a").set("2312");
        }
        String a = redisTemplate.boundValueOps("a").get().toString();
        redisTemplate.delete("a");*/
       redisTemplate.boundSetOps("nameset:hh").add("曹操");
       redisTemplate.boundSetOps("nameset:hh").add("刘备 ");
       redisTemplate.boundSetOps("nameset:hh").add("关于");
       redisTemplate.boundSetOps("nameset:hh").remove("关于");
        Set nameset = redisTemplate.boundSetOps("nameset:hh").members();
        System.out.println(nameset);
    }


    @Test
    public void add() {
        boolean sysUsers = sysUserService.insert(new SysUser().setFullName("123"));
        System.out.println(sysUsers);
    }




}
