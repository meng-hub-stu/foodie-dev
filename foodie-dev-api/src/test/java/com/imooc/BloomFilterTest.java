package com.imooc;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author Mengdl
 * @date 2021/11/08
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//@SpringBootTest
public class BloomFilterTest {

    @Test
    public void getHello() throws Exception{
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")),
                10000, 0.01);
        bloomFilter.put("1");
        boolean isExist = bloomFilter.mightContain("imooc" + "1");
        System.out.println(isExist);
        System.out.println("imooc\" + \"123");
    }

}
