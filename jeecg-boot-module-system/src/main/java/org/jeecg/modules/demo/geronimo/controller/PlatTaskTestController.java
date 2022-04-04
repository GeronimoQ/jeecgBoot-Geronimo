package org.jeecg.modules.demo.geronimo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.geronimo.entity.PlatTaskTest;
import org.jeecg.modules.demo.geronimo.entity.vo.VGroupTask;
import org.jeecg.modules.demo.geronimo.service.IPlatTaskTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import utill.qr.QrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 任务表
 * @Author: jeecg-boot
 * @Date: 2022-01-25
 * @Version: V1.0
 */
@Api(tags = "任务表")
@RestController
@RequestMapping("/geronimo/platTaskTest")
@Slf4j
public class PlatTaskTestController extends JeecgController<PlatTaskTest, IPlatTaskTestService> {
	@Autowired
	private IPlatTaskTestService platTaskTestService;

	/**
	 * 分页列表查询
	 *
	 * @param platTaskTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "任务表-分页列表查询")
	@ApiOperation(value = "任务表-分页列表查询", notes = "任务表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlatTaskTest platTaskTest,
								   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlatTaskTest> queryWrapper = QueryGenerator.initQueryWrapper(platTaskTest, req.getParameterMap());
		Page<PlatTaskTest> page = new Page<PlatTaskTest>(pageNo, pageSize);
		IPage<PlatTaskTest> pageList = platTaskTestService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param platTaskTest
	 * @return
	 */
	@AutoLog(value = "任务表-添加")
	@ApiOperation(value = "任务表-添加", notes = "任务表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlatTaskTest platTaskTest) {
		platTaskTestService.save(platTaskTest);
		return Result.OK("添加成功！");
	}

	/**
	 * 添加
	 *
	 * @param vGroupTask
	 * @return
	 */
	@AutoLog(value = "任务表-添加团体任务")
	@ApiOperation(value = "任务表-添加团体任务", notes = "任务表-添加团体任务")
	@PostMapping(value = "/addGroupTask")
	public Result<?> addGroupTask(@RequestBody VGroupTask vGroupTask) {
//		platTaskTestService.save(platTaskTest);
		ArrayList<PlatTaskTest> platTaskTests = new ArrayList<>();
		for (String groupId : vGroupTask.getUserId()){
			PlatTaskTest platTaskTest = new PlatTaskTest();
			platTaskTest.setTitle(vGroupTask.getTitle());
			platTaskTest.setInstr(vGroupTask.getInstr());
			platTaskTest.setCreateTime(vGroupTask.getCreateTime());
			platTaskTest.setDisabled(vGroupTask.isDisabled());
			platTaskTest.setModelId(vGroupTask.getModelId());
			platTaskTest.setUserId(groupId);
			platTaskTests.add(platTaskTest);
		}
		boolean b = service.saveBatch(platTaskTests);
		if (b){
			return Result.OK("添加成功！");
		}
		return Result.error("添加失败");
	}

	@AutoLog(value = "任务表-获取团体任务")
	@ApiOperation(value = "任务表-获取团体任务", notes = "任务表-获取团体任务")
	@GetMapping(value = "/getGroupTaskList")
	public Result<?> getGroupTaskList(@RequestParam String groupId) {
//		platTaskTestService.save(platTaskTest);
		QueryWrapper<PlatTaskTest> platTaskTestQueryWrapper = new QueryWrapper<>();
		platTaskTestQueryWrapper.select().eq("user_id",groupId).eq("disabled",false);
		List<PlatTaskTest> list = platTaskTestService.list(platTaskTestQueryWrapper);
		if (list!=null)
			return Result.OK(list);
		return Result.error("未找到对应数据");
	}

	@AutoLog(value = "任务表-获取任务")
	@ApiOperation(value = "任务表-获取任务", notes = "任务表-获取任务")
	@GetMapping(value = "/getTask")
	public Result<?> getTask(@RequestParam String taskId) {
//		platTaskTestService.save(platTaskTest);
		QueryWrapper<PlatTaskTest> platTaskTestQueryWrapper = new QueryWrapper<>();
		platTaskTestQueryWrapper.select().eq("id",taskId).eq("disabled",false);
		PlatTaskTest one = platTaskTestService.getOne(platTaskTestQueryWrapper);
		if (one!=null)
			return Result.OK(one);
		return Result.error("该任务不存在");
	}

	/**
	 * 编辑
	 *
	 * @param platTaskTest
	 * @return
	 */
	@AutoLog(value = "任务表-编辑")
	@ApiOperation(value = "任务表-编辑", notes = "任务表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlatTaskTest platTaskTest) {
		platTaskTestService.updateById(platTaskTest);
		return Result.OK("编辑成功!");
	}



	@AutoLog(value = "任务表-编辑任务状态")
	@ApiOperation(value = "任务表-编辑任务状态", notes = "任务表-编辑任务状态")
	@PutMapping(value = "/editState")
	public Result<?> editState(@RequestBody PlatTaskTest platTaskTest) {

		UpdateWrapper<PlatTaskTest> platTaskTestUpdateWrapper = new UpdateWrapper<>();
		platTaskTestUpdateWrapper.eq("id",platTaskTest.getId()).set("disabled",platTaskTest.isDisabled());
		boolean update = platTaskTestService.update(platTaskTestUpdateWrapper);
		if (update){
			return Result.OK("任务已禁止填报!");
		}
		return Result.error("任务禁用失败!");
	}
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "任务表-通过id删除")
	@ApiOperation(value = "任务表-通过id删除", notes = "任务表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		platTaskTestService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids 
	 * @return
	 */
	@AutoLog(value = "任务表-批量删除")
	@ApiOperation(value = "任务表-批量删除", notes = "任务表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		this.platTaskTestService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "任务表-通过id查询")
	@ApiOperation(value = "任务表-通过id查询", notes = "任务表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		PlatTaskTest platTaskTest = platTaskTestService.getById(id);
		if (platTaskTest == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(platTaskTest);
	}

	/**
	 * 根据用户ID查询相的任务
	 *
	 * @param userId
	 * @return
	 */
	@AutoLog(value = "任务表-通过userId查询列表")
	@ApiOperation(value = "任务表-通过id查询", notes = "任务表-通过id查询")
	@GetMapping(value = "/queryListByUserId")
	public Result<?> queryListByUserId(@RequestParam(name = "userId", required = true) String userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.select().eq("user_id",userId);
		List list = platTaskTestService.list(queryWrapper);
		if (list==null){
			return Result.error("未找到对应数据");
		}
		return Result.OK(list);
	}

	@AutoLog(value = "任务表-获取任务QR")
	@ApiOperation(value = "任务表-获取任务QR",notes = "任务表-获取任务QR")
	@GetMapping(value = "/taskQR")
	public void getQRImage(HttpServletResponse response,@RequestParam(value = "taskId")String taskId) {
		byte[] qrCode=null;
		try {
			qrCode= QrUtil.generateImageToByte(taskId, 100, 100);
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			response.getOutputStream().write(qrCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@AutoLog(value = "任务表-下载任务QR")
	@ApiOperation(value = "任务表-下载任务QR",notes = "任务表-下载任务QR")
	@GetMapping(value = "/downloadTaskQR")
	public void downloadQRImage(HttpServletResponse response,@RequestParam(value = "taskId")String taskId) {
		byte[] qrCode=null;
		try {
			qrCode= QrUtil.generateImageToByte(taskId, 500, 500);
			//设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename="+taskId+".png");
//			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			response.getOutputStream().write(qrCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param platTaskTest
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, PlatTaskTest platTaskTest) {
		return super.exportXls(request, platTaskTest, PlatTaskTest.class, "任务表");
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
		return super.importExcel(request, response, PlatTaskTest.class);
	}

}
	