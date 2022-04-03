package org.jeecg.modules.demo.geronimo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.demo.geronimo.entity.PlatFormGroup;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/29
 INSTR: ---------------DEFAULT---------------
 *
 ***/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VFormUser {
	private String id;
	private String userId;
	private String userName;
	private PlatFormGroup group;
	private boolean joined;
}
