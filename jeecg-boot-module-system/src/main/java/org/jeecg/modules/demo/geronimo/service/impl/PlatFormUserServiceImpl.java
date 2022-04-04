package org.jeecg.modules.demo.geronimo.service.impl;

import org.jeecg.modules.demo.geronimo.entity.PlatFormUser;
import org.jeecg.modules.demo.geronimo.mapper.PlatFormUserMapper;
import org.jeecg.modules.demo.geronimo.service.IPlatFormUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 表单系统用户表
 * @Author: jeecg-boot
 * @Date:   2022-03-29
 * @Version: V1.0
 */
@Service
public class PlatFormUserServiceImpl extends ServiceImpl<PlatFormUserMapper, PlatFormUser> implements IPlatFormUserService {

}
