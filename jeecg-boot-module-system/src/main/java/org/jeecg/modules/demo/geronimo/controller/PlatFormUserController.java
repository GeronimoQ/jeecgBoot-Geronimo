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
import org.jeecg.modules.demo.geronimo.entity.PlatFormGroup;
import org.jeecg.modules.demo.geronimo.entity.PlatFormUser;
import org.jeecg.modules.demo.geronimo.entity.vo.VFormUser;
import org.jeecg.modules.demo.geronimo.entity.vo.VGroupUser;
import org.jeecg.modules.demo.geronimo.entity.vo.VUserPwd;
import org.jeecg.modules.demo.geronimo.service.IPlatFormGroupService;
import org.jeecg.modules.demo.geronimo.service.IPlatFormUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 表单系统用户表
 * @Author: jeecg-boot
 * @Date: 2022-03-29
 * @Version: V1.0
 */
@Api(tags = "表单系统用户表")
@RestController
@RequestMapping("/geronimo/platFormUser")
@Slf4j
public class PlatFormUserController extends JeecgController<PlatFormUser, IPlatFormUserService> {
	@Autowired
	private IPlatFormUserService platFormUserService;
	@Autowired
	IPlatFormGroupService platFormGroupService;

	/**
	 * 分页列表查询
	 *
	 * @param platFormUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-分页列表查询")
	@ApiOperation(value = "表单系统用户表-分页列表查询", notes = "表单系统用户表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlatFormUser platFormUser,
								   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
								   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlatFormUser> queryWrapper = QueryGenerator.initQueryWrapper(platFormUser, req.getParameterMap());
		Page<PlatFormUser> page = new Page<PlatFormUser>(pageNo, pageSize);
		IPage<PlatFormUser> pageList = platFormUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param platFormUser
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-添加")
	@ApiOperation(value = "表单系统用户表-添加", notes = "表单系统用户表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlatFormUser platFormUser) {
		platFormUserService.save(platFormUser);
		return Result.OK("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param platFormUser
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-编辑")
	@ApiOperation(value = "表单系统用户表-编辑", notes = "表单系统用户表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlatFormUser platFormUser) {
		platFormUserService.updateById(platFormUser);
		return Result.OK("编辑成功!");
	}

	/**
	 * 编辑
	 * @param platFormUser
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-邀请入团")
	@ApiOperation(value = "表单系统用户表-邀请入团", notes = "表单系统用户表-邀请入团")
	@PutMapping(value = "/addGroup")
	public Result<?> joinGroup(@RequestBody PlatFormUser platFormUser) {
		QueryWrapper<PlatFormUser> platFormUserQueryWrapper = new QueryWrapper<>();
		platFormUserQueryWrapper.select().eq("user_id",platFormUser.getUserId());
		PlatFormUser one = platFormUserService.getOne(platFormUserQueryWrapper);
		if (one==null)
			return Result.error("用户不存在");
		if (one.getGroupId()!=null&&!one.getGroupId().isEmpty()){
//			已经有团体待接受/正在该团体中
			return Result.error("该用户有没有处理的邀请或已有所属团体");
		}
		UpdateWrapper<PlatFormUser> platFormUserUpdateWrapper = new UpdateWrapper<>();
		platFormUserUpdateWrapper.eq("id",one.getId()).set("group_id",platFormUser.getGroupId()).set("joined",false);
		boolean update = platFormUserService.update(platFormUserUpdateWrapper);
		if (update){
			return Result.OK("邀请成功!");
		}
		return Result.error("邀请失败");
	}

	@AutoLog(value = "表单系统用户表-团体操作")
	@ApiOperation(value = "表单系统用户表-团体操作", notes = "表单系统用户表-团体操作")
	@PutMapping(value = "/groupOption")
	public Result<?> groupOption(@RequestBody VGroupUser vGroupUser){
		QueryWrapper<PlatFormUser> platFormUserQueryWrapper = new QueryWrapper<>();
		platFormUserQueryWrapper.select().eq("user_id",vGroupUser.getUserId());
		PlatFormUser one = platFormUserService.getOne(platFormUserQueryWrapper);
		if (one==null)
			return Result.error("用户不存在");
		switch (vGroupUser.getOpType()){
			case 0:{
//				踢出团体
				if (vGroupUser.getGroupId().equals(one.getGroupId())){
					UpdateWrapper<PlatFormUser> platFormUserUpdateWrapper = new UpdateWrapper<>();
					platFormUserUpdateWrapper.eq("id",one.getId()).set("group_id",null).set("joined",false);
					boolean update = platFormUserService.update(platFormUserUpdateWrapper);
					if (update)
						return Result.OK("执行成功");
					return Result.error("执行失败");
				}else{
					return Result.error("无权管辖");
				}
			}
			case 1:{
//				邀请入团
				if (one.getGroupId().equals(vGroupUser.getGroupId())){
					return Result.error("该用户已经在团体中...");
				}else{
					if (one.getGroupId()==null||one.getGroupId().isEmpty()){
						UpdateWrapper<PlatFormUser> platFormUserUpdateWrapper = new UpdateWrapper<>();
						platFormUserUpdateWrapper.eq("id",one.getId()).set("group_id",vGroupUser.getGroupId()).set("joined",false);
						boolean update = platFormUserService.update(platFormUserUpdateWrapper);
						if (update)
							return Result.OK("执行成功");
						return Result.error("执行失败");
					}else {
						return Result.error("该用户已经在其他团体中...");
					}
				}
			}
			case 2:{
//				接受入团
				if (vGroupUser.getGroupId().equals(one.getGroupId())){
					if (!one.isJoined()){
						UpdateWrapper<PlatFormUser> platFormUserUpdateWrapper = new UpdateWrapper<>();
						platFormUserUpdateWrapper.eq("id",one.getId()).set("joined",true);
						boolean update = platFormUserService.update(platFormUserUpdateWrapper);
						if (!update)
							return Result.error("执行失败");
					}
					return Result.OK("你已经加入该团体了~");
				}else {
					return Result.error("无权管辖");
				}
			}
			case 3:{
//				退出团体
				if (vGroupUser.getGroupId().equals(one.getGroupId())){
					UpdateWrapper<PlatFormUser> platFormUserUpdateWrapper = new UpdateWrapper<>();
					platFormUserUpdateWrapper.eq("id",one.getId()).set("joined",false);
					boolean update = platFormUserService.update(platFormUserUpdateWrapper);
					if (update)
						return Result.OK("执行成功");
					return Result.error("执行失败");
				}else{
					return Result.error("无权管辖");
				}
			}
			default:return Result.error("参数错误");
		}
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-通过id删除")
	@ApiOperation(value = "表单系统用户表-通过id删除", notes = "表单系统用户表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		platFormUserService.removeById(id);
		return Result.OK("删除成功!");
	}
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-id修改密码")
	@ApiOperation(value = "表单系统用户表-id修改密码", notes = "表单系统用户表-id修改密码")
	@PostMapping(value = "/changePwd")
	public Result<?> changePwd(@RequestBody VUserPwd vUserPwd) {
		String newPwd= vUserPwd.getNewPwd();
		String oldPwd= vUserPwd.getOldPwd();
		String id= vUserPwd.getId();;
		if (newPwd!=null&&!newPwd.trim().isEmpty()){
			PlatFormUser byId = platFormUserService.getById(id);
			if (byId.getPassword().equals(oldPwd)) {
				byId.setPassword(newPwd);
				boolean update = platFormUserService.updateById(byId);
				if (update)
					return Result.OK("密码修改成功");
				return Result.error("密码修改失败");
			}else {
				return Result.error("原密码错误!");
			}
		}
		return Result.error("密码不可为空!");
	}
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-批量删除")
	@ApiOperation(value = "表单系统用户表-批量删除", notes = "表单系统用户表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		this.platFormUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "表单系统用户表-通过id查询")
	@ApiOperation(value = "表单系统用户表-通过id查询", notes = "表单系统用户表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		PlatFormUser platFormUser = platFormUserService.getById(id);
		platFormUser.setPassword(null);
		if (platFormUser == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(platFormUser);
	}

	@AutoLog(value = "表单系统用户表-通过GroupId查询")
	@ApiOperation(value = "表单系统用户表-通过GroupId查询", notes = "表单系统用户表-通过GroupId查询")
	@GetMapping(value = "/queryByGroupId")
	public Result<?> queryByGroupId(@RequestParam(name = "id", required = true) String groupId,@RequestParam(name = "type", required = true)int type) {
		QueryWrapper<PlatFormUser> platFormUserQueryWrapper = new QueryWrapper<>();
		platFormUserQueryWrapper.select("id","user_id","user_name","group_id","joined");
		switch (type){
//			全查
			case 0:{
				platFormUserQueryWrapper.eq("group_id",groupId);
				break;
			}
//				已经加入
			case 1:{
				platFormUserQueryWrapper.eq("group_id",groupId).eq("joined",true);
				break;
			}
//				未加入
			case 2:{
				platFormUserQueryWrapper.eq("group_id",groupId).eq("joined",false);
				break;
			}
			default: return Result.error("参数错误");
		}
		List<PlatFormUser> list = platFormUserService.list(platFormUserQueryWrapper);
		if (list == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(list);
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param platFormUser
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, PlatFormUser platFormUser) {
		return super.exportXls(request, platFormUser, PlatFormUser.class, "表单系统用户表");
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
		return super.importExcel(request, response, PlatFormUser.class);
	}

	@AutoLog(value = "表单系统用户表-登录")
	@ApiOperation(value = "表单系统用户表-登录", notes = "表单系统用户表-登录")
	@PostMapping(value = "/login")
	public Result<?> login(@RequestBody PlatFormUser platFormUser) {
		String userId = platFormUser.getUserId();
		if (userId != null) {
			QueryWrapper<PlatFormUser> platFormUserQueryWrapper = new QueryWrapper<>();
			platFormUserQueryWrapper.select().eq("user_id", userId).eq("password", platFormUser.getPassword());
			PlatFormUser one = platFormUserService.getOne(platFormUserQueryWrapper);
			if (one==null){
				return Result.error("用户名或密码错误");
			}
//			查询团体信息
			PlatFormGroup byId = platFormGroupService.getById(one.getGroupId());
			VFormUser vFormUser = new VFormUser(one.getId(), one.getUserId(), one.getUserName(), byId, one.isJoined());
			return Result.OK("登录成功",vFormUser);
		}
		return Result.error("登陆失败");
	}

	@AutoLog(value = "表单系统用户表-查询团体成员")
	@ApiOperation(value = "表单系统用户表-查询团体成员", notes = "表单系统用户表-查询团体成员")
	@GetMapping(value = "/listGroupUser")
	public Result<?> listSameGroupUser(@RequestParam String groupId){
		if (groupId != null && !groupId.isEmpty()) {
			QueryWrapper<PlatFormUser> platFormUserQueryWrapper = new QueryWrapper<>();
			platFormUserQueryWrapper.select("id","user_id","user_name","group_id","joined").eq("group_id",groupId);
			List<PlatFormUser> list = platFormUserService.list(platFormUserQueryWrapper);
			return  Result.OK(list);
		}
		return Result.error("查询失败");
	}


}
