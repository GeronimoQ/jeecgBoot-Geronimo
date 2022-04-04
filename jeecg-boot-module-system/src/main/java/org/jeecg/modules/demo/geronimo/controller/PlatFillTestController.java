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
import org.jeecg.modules.demo.geronimo.entity.PlatFillTest;
import org.jeecg.modules.demo.geronimo.entity.PlatTaskTest;
import org.jeecg.modules.demo.geronimo.service.IPlatFillTestService;
import org.jeecg.modules.demo.geronimo.service.IPlatTaskTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 用户填报表
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
@Api(tags="用户填报表")
@RestController
@RequestMapping("/geronimo/platFillTest")
@Slf4j
public class PlatFillTestController extends JeecgController<PlatFillTest, IPlatFillTestService> {
	@Autowired
	private IPlatFillTestService platFillTestService;

	@Autowired
	private IPlatTaskTestService platTaskTestService;
	
	/**
	 * 分页列表查询
	 *
	 * @param platFillTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "用户填报表-分页列表查询")
	@ApiOperation(value="用户填报表-分页列表查询", notes="用户填报表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlatFillTest platFillTest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlatFillTest> queryWrapper = QueryGenerator.initQueryWrapper(platFillTest, req.getParameterMap());
		Page<PlatFillTest> page = new Page<PlatFillTest>(pageNo, pageSize);
		IPage<PlatFillTest> pageList = platFillTestService.page(page, queryWrapper);

		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param platFillTest
	 * @return
	 */
	@AutoLog(value = "用户填报表-添加")
	@ApiOperation(value="用户填报表-添加", notes="用户填报表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlatFillTest platFillTest) {
		PlatTaskTest byId = platTaskTestService.getById(platFillTest.getTaskId());
		if (byId.isDisabled()){
			return Result.error("该任务已经停止填报!");
		}
		boolean b = platFillTestService.saveWithCheckUni(platFillTest);
		if (b)
		return Result.OK("填报成功!");
		return Result.error("填报失败!");
	}
	
	/**
	 *  编辑
	 *
	 * @param platFillTest
	 * @return
	 */
	@AutoLog(value = "用户填报表-编辑")
	@ApiOperation(value="用户填报表-编辑", notes="用户填报表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlatFillTest platFillTest) {
		platFillTestService.updateById(platFillTest);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户填报表-通过id删除")
	@ApiOperation(value="用户填报表-通过id删除", notes="用户填报表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		platFillTestService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户填报表-批量删除")
	@ApiOperation(value="用户填报表-批量删除", notes="用户填报表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.platFillTestService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户填报表-通过id查询")
	@ApiOperation(value="用户填报表-通过id查询", notes="用户填报表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlatFillTest platFillTest = platFillTestService.getById(id);
		if(platFillTest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(platFillTest);
	}

	@AutoLog(value = "用户填报表-通过Uid和TaskId查询")
	@ApiOperation(value="用户填报表-通过Uid和TaskId查询", notes="用户填报表-通过Uid和TaskId查询")
	@GetMapping(value = "/queryByUIdAndTId")
	public Result<?> queryByUIdAndTId(@RequestParam String userId,@RequestParam String taskId) {
		QueryWrapper<PlatFillTest> platFillTestQueryWrapper = new QueryWrapper<>();
		HashMap alleq=new HashMap();
		alleq.put("user_id",userId);
		alleq.put("task_id",taskId);
		platFillTestQueryWrapper.select().allEq(alleq);
		PlatFillTest one = platFillTestService.getOne(platFillTestQueryWrapper);
		if(one==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(one);
	}


    /**
    * 导出excel
    *
    * @param request
    * @param platFillTest
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PlatFillTest platFillTest) {
        return super.exportXls(request, platFillTest, PlatFillTest.class, "用户填报表");
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
        return super.importExcel(request, response, PlatFillTest.class);
    }

	 @AutoLog(value = "用户填报表-查询填报历史")
	 @ApiOperation(value="用户填报表-查询填报历史", notes="用户填报表-查询填报历史")
	 @GetMapping(value = "/queryHistoryByUId")
	 public Result<?> queryHistoryByUId(@RequestParam(name="id",required=true) String userId) {
		try {
			QueryWrapper<PlatFillTest> platFillTestQueryWrapper = new QueryWrapper<>();
			platFillTestQueryWrapper.select().eq("user_id", userId);
			List<PlatFillTest> platFillTests = platFillTestService.list(platFillTestQueryWrapper);
			ArrayList<String> taskIdList = new ArrayList<>();
			for (PlatFillTest fill : platFillTests) {
				taskIdList.add(fill.getTaskId());
			}
			List<PlatTaskTest> platTaskTests = platTaskTestService.listByIds(taskIdList);
			if (platTaskTests.isEmpty())
				return Result.OK("您没有填报任何任务哦~");
			return Result.OK(platTaskTests);
		}catch (Exception e){
			return Result.error("即找不到月亮也找不到太阳~");
		}

	 }

}
