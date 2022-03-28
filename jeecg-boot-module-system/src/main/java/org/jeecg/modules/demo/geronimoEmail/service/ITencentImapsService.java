package org.jeecg.modules.demo.geronimoEmail.service;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/22
 INSTR: ---------------DEFAULT---------------
 *
 ***/
public abstract class ITencentImapsService implements IMailService {

	public final String COMPANY_NAME=IMailService.SERVER_COMPANY_1;

	public final String RECEIVE_HOST = "imap.qq.com";
	public final String RECEIVE_PORT = "993";
	public final boolean IS_READ_FEEDBACK=false;



}
