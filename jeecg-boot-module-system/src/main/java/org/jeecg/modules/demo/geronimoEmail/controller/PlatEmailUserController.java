package org.jeecg.modules.demo.geronimoEmail.controller;

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
import org.jeecg.modules.demo.geronimoEmail.entity.PlatEmailUser;
import org.jeecg.modules.demo.geronimoEmail.service.IMailService;
import org.jeecg.modules.demo.geronimoEmail.service.IPlatEmailUserService;
import org.jeecg.modules.demo.geronimoEmail.service.impl.TencentImapsService;
import org.jeecg.modules.demo.geronimoEmail.util.MailUtil;
import org.jeecg.modules.demo.geronimoEmail.vo.EmailLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: email用户表
 * @Author: jeecg-boot
 * @Date: 2022-03-20
 * @Version: V1.0
 */
@Api(tags = "email用户表")
@RestController
@RequestMapping("/geronimoEmail/platEmailUser")
@Slf4j
public class PlatEmailUserController extends JeecgController<PlatEmailUser, IPlatEmailUserService> {
	@Autowired
	private IPlatEmailUserService platEmailUserService;

	private IMailService mailService;

	/**
	 * 分页列表查询
	 *
	 * @param platEmailUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "email用户表-分页列表查询")
	@ApiOperation(value = "email用户表-分页列表查询", notes = "email用户表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlatEmailUser platEmailUser,
								   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlatEmailUser> queryWrapper = QueryGenerator.initQueryWrapper(platEmailUser, req.getParameterMap());
		Page<PlatEmailUser> page = new Page<PlatEmailUser>(pageNo, pageSize);
		IPage<PlatEmailUser> pageList = platEmailUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param platEmailUser
	 * @return
	 */
	@AutoLog(value = "email用户表-添加")
	@ApiOperation(value = "email用户表-添加", notes = "email用户表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlatEmailUser platEmailUser) {
		platEmailUserService.save(platEmailUser);
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param platEmailUser
	 * @return
	 */
	@AutoLog(value = "email用户表-编辑")
	@ApiOperation(value = "email用户表-编辑", notes = "email用户表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlatEmailUser platEmailUser) {
		platEmailUserService.updateById(platEmailUser);
		return Result.OK("编辑成功!");
	}



	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "email用户表-通过id删除")
	@ApiOperation(value = "email用户表-通过id删除", notes = "email用户表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		platEmailUserService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "email用户表-批量删除")
	@ApiOperation(value = "email用户表-批量删除", notes = "email用户表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		this.platEmailUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "email用户表-通过id查询")
	@ApiOperation(value = "email用户表-通过id查询", notes = "email用户表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		PlatEmailUser platEmailUser = platEmailUserService.getById(id);
		if (platEmailUser == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(platEmailUser);
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param platEmailUser
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, PlatEmailUser platEmailUser) {
		return super.exportXls(request, platEmailUser, PlatEmailUser.class, "email用户表");
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
		return super.importExcel(request, response, PlatEmailUser.class);
	}

	@AutoLog(value = "email用户表-注册EMAIL")
	@ApiOperation(value = "email用户表-EMAIL注册", notes = "email用户表-EMAIL注册")
	@PostMapping("/emailRegister")
	public Result<?> registerEmailAccount(@RequestBody EmailLoginVo emailLogin) {
		boolean isConnect = false;
//		查看是否能启动服务器，能启动则入库
		switch (emailLogin.getCompany()) {
			case IMailService.SERVER_COMPANY_1:
				TencentImapsService service = new TencentImapsService();
				isConnect = service.startService(emailLogin.getUsername(), emailLogin.getAuthCode(), true);
				service.stopService();
				break;
			default:
				return Result.error("邮件服务商不存在于系统中");
		}
		if (!isConnect)
			return Result.error("用户名或密码错误");
//		对象map
		PlatEmailUser platEmailUser = new PlatEmailUser();
		platEmailUser.setEmailAddress(emailLogin.getUsername());
		platEmailUser.setAuthCode(emailLogin.getAuthCode());
		platEmailUser.setUsable(isConnect ? 1 : 0);
		platEmailUser.setUsername(MailUtil.getEmailAddUsername(emailLogin.getUsername()));
		boolean save = platEmailUserService.save(platEmailUser);
		if (!save)
			return Result.error("EMail用户注册失败，请联系管理员");
		return Result.error("用户添加成功");
	}

}
