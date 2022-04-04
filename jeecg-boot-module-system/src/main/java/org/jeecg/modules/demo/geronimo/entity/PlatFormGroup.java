package org.jeecg.modules.demo.geronimo.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 表单系统团体表
 * @Author: jeecg-boot
 * @Date:   2022-03-29
 * @Version: V1.0
 */
@Data
@TableName("plat_form_group")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="plat_form_group对象", description="表单系统团体表")
public class PlatFormGroup implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**团体名*/
	@Excel(name = "团体名", width = 15)
    @ApiModelProperty(value = "团体名")
    private java.lang.String groupName;
	/**团体介绍*/
	@Excel(name = "团体介绍", width = 15)
    @ApiModelProperty(value = "团体介绍")
    private java.lang.String groupInstr;
	/**拥有者*/
	@Excel(name = "拥有者", width = 15)
    @ApiModelProperty(value = "拥有者")
    private java.lang.String owner;
}
