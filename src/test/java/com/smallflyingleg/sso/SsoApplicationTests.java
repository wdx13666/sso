package com.smallflyingleg.sso;

import com.smallflyingleg.sso.mapper.SysUserMapper;
import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.service.SysUserService;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsoApplicationTests {

    @Autowired
    private SysUserMapper mapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private static RedisTemplate redisTemplate;

    public static void main(String[] args) {
        String username = "wdx";
        if (username.equals("wdx")){
            String token = UUID.randomUUID().toString();
            redisTemplate.boundSetOps("a" + ":" + token).add("2312");
        }
    }

    @Test
    public void contextLoads() {
        SysUser sysUsers = sysUserService.selectById(30);
        System.out.println(sysUsers);
    }


    @Test
    public void add() {
        boolean sysUsers = sysUserService.insert(new SysUser().setFullName("123"));
        System.out.println(sysUsers);
    }

}
