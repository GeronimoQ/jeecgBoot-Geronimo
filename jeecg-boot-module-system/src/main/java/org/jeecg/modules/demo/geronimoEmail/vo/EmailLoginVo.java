package org.jeecg.modules.demo.geronimoEmail.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/22
 INSTR: ---------------DEFAULT---------------
 *
 ***/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailLoginVo {
//	用户名
	private String username;
//	授权码
	private String authCode;
//	邮箱服务者
	private String company;
}
