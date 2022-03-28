package org.jeecg.modules.demo.geronimoEmail.entity;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/22
 INSTR: ---------------DEFAULT---------------
 *
 ***/

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: email用户表
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
@Data
@TableName("plat_email_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="plat_email_user对象", description="email用户表")
public class PlatEmailUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**邮箱地址*/
	@Excel(name = "邮箱地址", width = 15)
	@ApiModelProperty(value = "邮箱地址")
	private java.lang.String emailAddress;
	/**邮箱用户名*/
	@Excel(name = "邮箱用户名", width = 15)
	@ApiModelProperty(value = "邮箱用户名")
	private java.lang.String username;
	/**授权码*/
	@Excel(name = "授权码", width = 15)
	@ApiModelProperty(value = "授权码")
	private java.lang.String authCode;
	/**授权码可用*/
	@Excel(name = "授权码可用", width = 15)
	@ApiModelProperty(value = "授权码可用")
	private java.lang.Integer usable;
}

