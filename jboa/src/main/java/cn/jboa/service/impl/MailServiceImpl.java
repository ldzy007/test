package cn.jboa.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cn.jboa.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MailServiceImpl implements MailService{
	private JavaMailSender mailSender;
	private Configuration freeMarkerConfiguration;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}
	private String getMailText(Map tempData){
		String htmlText = "";
		try{
			//获取模板实例
			Template template = freeMarkerConfiguration.getTemplate("mail.ftl");

			//解析模板文件
			htmlText = FreeMarkerTemplateUtils
								.processTemplateIntoString(template, tempData);
		}catch(IOException e){
			e.printStackTrace();
		}catch(TemplateException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return htmlText;
	}
	
	public void sendMail(String fromMail, String toMail,String mailTitle ,Map tempData) throws MessagingException,IOException{
		if(mailSender==null){
			return;
		}
		try{
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(
											mimeMessage, true, "UTF-8");
			helper.setFrom(fromMail);
			helper.setTo(toMail);
			helper.setSubject(mailTitle);
			String mailText = getMailText(tempData);
			if(mailText!=null && mailText!=""){
				helper.setText(mailText,true);
			}else{
				return;
			}
			mailSender.send(mimeMessage);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
}
