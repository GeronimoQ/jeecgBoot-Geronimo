package org.jeecg.modules.demo.geronimo.entity;

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
 * @Description: 表单系统用户表
 * @Author: jeecg-boot
 * @Date:   2022-03-29
 * @Version: V1.0
 */
@Data
@TableName("plat_form_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="plat_form_user对象", description="表单系统用户表")
public class PlatFormUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**学号/工号*/
	@Excel(name = "学号/工号", width = 15)
    @ApiModelProperty(value = "学号/工号")
    private java.lang.String userId;
	/**学生姓名*/
	@Excel(name = "学生姓名", width = 15)
    @ApiModelProperty(value = "学生姓名")
    private java.lang.String userName;
	/**登录密码*/
	@Excel(name = "登录密码", width = 15)
    @ApiModelProperty(value = "登录密码")
    private java.lang.String password;
	/**所属团体*/
	@Excel(name = "所属团体", width = 15)
    @ApiModelProperty(value = "所属团体")
    private java.lang.String groupId;
	/**是否加入*/
	@Excel(name = "是否加入", width = 15)
    @ApiModelProperty(value = "是否加入")
    private boolean joined;
}
