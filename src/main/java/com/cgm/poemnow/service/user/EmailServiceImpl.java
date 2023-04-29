package com.cgm.poemnow.service.user;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender emailSender;

	public static final String ePw = createKey();

	private MimeMessage createMessage(String to)throws Exception{
		MimeMessage  message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);
		message.setSubject("PoemNow 인증번호가 도착했습니다.");

		String msgg="";
		msgg+= "<div style='margin:100px;'>";
		msgg+= "<h1> PoemNow 이메일 인증입니다. </h1>";
		msgg+= "<br>";
		msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
		msgg+= "<br>";
		msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg+= "<h3 style='color:blue;'>인증번호 8자리입니다.</h3>";
		msgg+= "<div style='font-size:130%'>";
		msgg+= "CODE : <strong>";
		msgg+= ePw+"</strong><div><br/> ";
		msgg+= "</div>";
		message.setText(msgg, "utf-8", "html");//내용
		message.setFrom(new InternetAddress("poemnow23@gmail.com","PoemNow"));//보내는 사람

		return message;
	}

	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) {
			int index = rnd.nextInt(3);

			switch (index) {
				case 0:
					key.append((char) ((int) (rnd.nextInt(26)) + 97));
					break;
				case 1:
					key.append((char) ((int) (rnd.nextInt(26)) + 65));
					break;
				case 2:
					key.append((rnd.nextInt(10)));
					break;
			}
		}

		return key.toString();
	}

	@Override
	public void sendSimpleMessage(String to)throws Exception {
		MimeMessage message = createMessage(to);
		try{
			emailSender.send(message);
		}catch(MailException es){
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

}