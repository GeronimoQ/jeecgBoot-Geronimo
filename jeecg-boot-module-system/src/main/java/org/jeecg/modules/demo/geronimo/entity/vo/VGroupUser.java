package org.jeecg.modules.demo.geronimo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/30
 INSTR: ---------------DEFAULT---------------
 *
 ***/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VGroupUser {
	/**主键*/
	private java.lang.String id;
	/**学号/工号*/
	private java.lang.String userId;
	/**学生姓名*/
	private java.lang.String userName;

	/**所属团体*/
	private java.lang.String groupId;
	/**是否加入*/
	private boolean joined;
    private Integer opType;
}
