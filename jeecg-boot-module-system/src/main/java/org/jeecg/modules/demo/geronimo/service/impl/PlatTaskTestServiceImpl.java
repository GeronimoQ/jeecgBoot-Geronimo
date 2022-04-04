package org.jeecg.modules.demo.geronimo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.geronimo.entity.PlatTaskTest;
import org.jeecg.modules.demo.geronimo.mapper.PlatTaskTestMapper;
import org.jeecg.modules.demo.geronimo.service.IPlatTaskTestService;
import org.springframework.stereotype.Service;

/**
 * @Description: 任务表
 * @Author: jeecg-boot
 * @Date:   2022-01-25
 * @Version: V1.0
 */
@Service
public class PlatTaskTestServiceImpl extends ServiceImpl<PlatTaskTestMapper, PlatTaskTest> implements IPlatTaskTestService {

	@Override
	public byte[] getTaskQR(String taskId) {
//		先查询数据库有没有QR

		return new byte[0];
	}

	@Override
	public boolean editState(PlatTaskTest platTaskTest) {

		return false;
	}
}
