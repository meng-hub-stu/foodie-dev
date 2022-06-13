package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.entity.StuUser;
import com.imooc.mapper.StuUserMapper;
import com.imooc.service.IStuUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Mengdl
 * @date 2022/06/08
 */
@Service
@RequiredArgsConstructor
public class StuUserServiceImpl extends ServiceImpl<StuUserMapper, StuUser> implements IStuUserService {

}
