package org.jeecg.modules.demo.geronimo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.geronimo.entity.PlatFormGroup;
import org.jeecg.modules.demo.geronimo.service.IPlatFormGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 表单系统团体表
 * @Author: jeecg-boot
 * @Date: 2022-03-29
 * @Version: V1.0
 */
@Api(tags = "表单系统团体表")
@RestController
@RequestMapping("/geronimo/platFormGroup")
@Slf4j
public class PlatFormGroupController extends JeecgController<PlatFormGroup, IPlatFormGroupService> {
	@Autowired
	private IPlatFormGroupService platFormGroupService;

	/**
	 * 分页列表查询
	 *
	 * @param platFormGroup
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "表单系统团体表-分页列表查询")
	@ApiOperation(value = "表单系统团体表-分页列表查询", notes = "表单系统团体表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlatFormGroup platFormGroup,
								   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlatFormGroup> queryWrapper = QueryGenerator.initQueryWrapper(platFormGroup, req.getParameterMap());
		Page<PlatFormGroup> page = new Page<PlatFormGroup>(pageNo, pageSize);
		IPage<PlatFormGroup> pageList = platFormGroupService.page(page, queryWrapper);
		return Result.OK(pageList);
	}


	@AutoLog(value = "表单系统团体表-owner列表查询")
	@ApiOperation(value = "表单系统团体表-owner列表查询", notes = "表单系统团体表-owner列表查询")
	@GetMapping(value = "/listOwnerGroups")
	public Result<?> queryListByOwnerId(@RequestParam String userId) {
		if (userId != null && !userId.isEmpty()) {
			QueryWrapper<PlatFormGroup> platFormGroupQueryWrapper = new QueryWrapper<>();
			platFormGroupQueryWrapper.select().eq("owner",userId);
			List<PlatFormGroup> list = platFormGroupService.list(platFormGroupQueryWrapper);
			if (list!=null){
				return Result.OK(list);
			}
			return Result.error("查询失败");
		}
		return Result.error("请填入参数");
	}

	/**
	 * 添加
	 *
	 * @param platFormGroup
	 * @return
	 */
	@AutoLog(value = "表单系统团体表-添加")
	@ApiOperation(value = "表单系统团体表-添加", notes = "表单系统团体表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlatFormGroup platFormGroup) {
		platFormGroupService.save(platFormGroup);
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param platFormGroup
	 * @return
	 */
	@AutoLog(value = "表单系统团体表-编辑")
	@ApiOperation(value = "表单系统团体表-编辑", notes = "表单系统团体表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlatFormGroup platFormGroup) {
		platFormGroupService.updateById(platFormGroup);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "表单系统团体表-通过id删除")
	@ApiOperation(value = "表单系统团体表-通过id删除", notes = "表单系统团体表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		platFormGroupService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "表单系统团体表-批量删除")
	@ApiOperation(value = "表单系统团体表-批量删除", notes = "表单系统团体表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		this.platFormGroupService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "表单系统团体表-通过id查询")
	@ApiOperation(value = "表单系统团体表-通过id查询", notes = "表单系统团体表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		PlatFormGroup platFormGroup = platFormGroupService.getById(id);
		if (platFormGroup == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(platFormGroup);
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param platFormGroup
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, PlatFormGroup platFormGroup) {
		return super.exportXls(request, platFormGroup, PlatFormGroup.class, "表单系统团体表");
	}

	/**
	 * 通过excel导入数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, PlatFormGroup.class);
	}

}
