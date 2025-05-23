package com.PFE.Espacecommercant.Authen.Service.Impl;


import com.PFE.Espacecommercant.Authen.Service.facade.MailService;
import com.PFE.Espacecommercant.Authen.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService
{
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(Mail mail)
	{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent());
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
