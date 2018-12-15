package com.wyait.manage2.other.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.mapper.FormSellingContractMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/15
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IFormSellingContractServiceTest {
    @Autowired
    private FormSellingContractMapper formSellingContractMapper;

    @Test
    public void testSelect() {


        FormSellingContract u = new FormSellingContract();
        String id = UUID.randomUUID().toString();
        u.setBuyer(id);
        //u.setBuyer("xxxx");
        u.setBuyerEn("xxxx");
        formSellingContractMapper.insert(u);

        QueryWrapper<FormSellingContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer",id);
        FormSellingContract u2 = formSellingContractMapper.selectOne(queryWrapper);

        Assert.assertNotNull(u2);

        System.out.println(("----- selectAll method test ------"));
        List<FormSellingContract> contractList = formSellingContractMapper.selectList(null);
        //Assert.assertEquals(5, userList.size());
        Assert.assertTrue(contractList.size() > 0);


        contractList.forEach(System.out::println);
    }
}