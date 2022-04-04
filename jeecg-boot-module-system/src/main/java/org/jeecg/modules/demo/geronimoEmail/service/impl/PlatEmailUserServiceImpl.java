package org.jeecg.modules.demo.geronimoEmail.service.impl;

import org.jeecg.modules.demo.geronimoEmail.entity.PlatEmailUser;
import org.jeecg.modules.demo.geronimoEmail.mapper.PlatEmailUserMapper;
import org.jeecg.modules.demo.geronimoEmail.service.IPlatEmailUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: email用户表
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
@Service
public class PlatEmailUserServiceImpl extends ServiceImpl<PlatEmailUserMapper, PlatEmailUser> implements IPlatEmailUserService {

}
