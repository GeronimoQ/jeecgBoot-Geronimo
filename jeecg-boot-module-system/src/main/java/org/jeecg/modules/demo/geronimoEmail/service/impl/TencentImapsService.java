package org.jeecg.modules.demo.geronimoEmail.service.impl;

import com.geronimo.config.GeronimoMail;
import com.geronimo.config.MailPropertiesConfig;
import com.geronimo.config.imap.ImapClient;
import com.geronimo.config.imap.ImapConfig;
import com.geronimo.entity.UserEntity;
import org.jeecg.modules.demo.geronimoEmail.service.ITencentImapsService;
import org.jeecg.modules.demo.geronimoEmail.util.MailUtil;

/***
 PROJECT: jeecg-boot
 *
 USER: Tardis
 DATE: 2022/3/22
 INSTR: ---------------DEFAULT---------------
 *
 ***/

public class TencentImapsService extends ITencentImapsService {

	private GeronimoMail mailClient = null;

	@Override
	public boolean startService(String username, String authCode, boolean isTestConnect) {
		initConfig(username,authCode);
		if (isTestConnect) {
			return mailClient.startClient(true);
		} else {
			return mailClient.startClient();
		}
	}

	private void initConfig(String username, String authCode) {
		UserEntity userEntity = new UserEntity(username, authCode);
		String destDir = MailUtil.generateComDest(username, COMPANY_NAME);

		MailPropertiesConfig mailPropertiesConfig = new MailPropertiesConfig();
		mailPropertiesConfig.setReadFeedback(IS_READ_FEEDBACK);
		mailPropertiesConfig.setMailImapHost(RECEIVE_HOST);
		mailPropertiesConfig.setMailImapPort(RECEIVE_PORT);
		mailPropertiesConfig.setMailStoreProtocol(ImapConfig.STORE_PROTOCOL);
		mailPropertiesConfig.setDestDir(destDir);
		ImapClient imapClient = new ImapClient(userEntity, mailPropertiesConfig);
		mailClient = imapClient;
	}


	@Override
	public void stopService() {
		if (mailClient != null)
			mailClient.stopClient();
	}
}
