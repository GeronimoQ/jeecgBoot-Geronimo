package org.jeecg.modules.demo.geronimoEmail.service;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/22
 INSTR: ---------------DEFAULT---------------
 *
 ***/
public interface  IMailService {

	public final String SERVER_COMPANY_1="TENCENT";

	/**
	 * 开启IMAP SSL 服务
	 * @param username 用户名/邮箱地址
	 * @param authCode 授权码
	 * @param isTestConnect 是否只是测试连接
	 * @return 开启成功返回True，登录服务成功
	 */
	public boolean startService(String username, String authCode,boolean isTestConnect);

	/**
	 * 关闭服务器
	 *
	 */
	public void stopService();
}
