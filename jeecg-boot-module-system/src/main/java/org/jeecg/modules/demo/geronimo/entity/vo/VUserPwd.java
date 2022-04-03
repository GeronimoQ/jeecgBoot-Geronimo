package org.jeecg.modules.demo.geronimo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/31
 INSTR: ---------------DEFAULT---------------
 *
 ***/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VUserPwd {
	private String id;
	private String newPwd;
	private String oldPwd;
}
