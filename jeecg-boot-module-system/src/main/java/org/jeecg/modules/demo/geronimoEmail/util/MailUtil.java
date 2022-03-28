package org.jeecg.modules.demo.geronimoEmail.util;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/22
 INSTR: ---------------DEFAULT---------------
 *
 ***/
public class MailUtil {


	/**
	 * 返回固定格式<公司名@用户名>的用户邮件记录ID（文件夹/ID/...）
	 * @param userAddress 用户邮件地址
	 * @param companyName 邮件服务公司
	 * @return companyName@username
	 */
	public static String generateComDest(String userAddress,String companyName){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(companyName+"@");
//		获取邮箱用户名 @前字符串，以@结尾
		String username = userAddress.substring(0, userAddress.indexOf('@'));
		stringBuilder.append(username);
		return stringBuilder.toString();
	}

	/**
	 * 获取邮件地址的用户名
	 * @param userAddress
	 * @return
	 */
	public static String getEmailAddUsername(String userAddress){
		return userAddress.substring(0, userAddress.indexOf('@'));
	}

//	/**
//	 * 根据用户选择的邮件服务商反馈具体的服务商服务对象
//	 * @param companyName
//	 * @return
//	 */
//	public static void getMailService(String companyName,IMailService service){
//		switch (companyName){
//			case IMailService.SERVER_COMPANY_1: service=(TencentImapsService)service;
//			default: return null;
//		}
//	}

}
