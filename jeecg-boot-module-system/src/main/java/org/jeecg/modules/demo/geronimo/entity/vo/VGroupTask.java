package org.jeecg.modules.demo.geronimo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

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
public class VGroupTask {
	/**主键*/
	private String id;
	/**创建人*/
	private ArrayList<String> userId;
	/**创建日期*/

	private Date createTime;
	/**任务标题*/

	private String title;
	/**人物介绍*/

	private String instr;
	/**模板ID*/

	private String modelId;
	/**是否在用*/

	private boolean disabled;
}
