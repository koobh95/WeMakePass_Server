package com.github.koobh95.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Executor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.entity.User;
import com.github.koobh95.data.model.entity.UserCert;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.data.repository.UserCertRepository;
import com.github.koobh95.data.repository.UserRepository;
import com.github.koobh95.exception.MailServiceException;
import com.github.koobh95.service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 메일 관련 비지니스 로직을 처리한다.
 *  
 * @author BH-Ku
 * @since 2023-12-29
 */
@Service("mailService")
@RequiredArgsConstructor
@Log4j2
public class MailServiceImpl implements MailService{
	private final UserCertRepository userCertRepository;
	private final UserRepository userRepository;
	
	private final JavaMailSender javaMailSender;
	private final Executor executor;
	
	/**
	 * - 계정 인증을 수행하지 않은 사용자를 대상으로 인증 메일을 발송한다.
	 * - 이 메서드는 클라이언트 입장에서 다음 두 가지 상황 중 하나에서 실행된다.
	 *  > 회원가입 직후 계정 인증 시도
	 *  > 로그인을 시도했으나 계정 인증을 수행하지 않은 상태라 로그인에 실패하고 계정 인증을 시도
	 * - 기본적으로 계정을 생성한 직후나 로그인을 시도한 후에 접근할 수 있는 API에서만 호출되기 때문에
	 *  계정의 존재 여부는 확인하지 않는다.
	 * - 인증 코드를 발급한 뒤 테이블에 데이터를 삽입 혹은 갱신한 뒤 비동기로 이메일을 전송한다.
	 *  
	 * @param userId 계정 인증을 시도하는 유저의 아이디
	 */
	@Transactional
	@Override
	public void sendAccountCertMail(String userId) {
		final String email = userRepository.findById(userId).get().getEmail();
		final String code = generateCertCode();
		MimeMessage message = createAccountCertMail(email, code);
		Optional<UserCert> userCert = userCertRepository.findById(userId);
		userCert.ifPresentOrElse(e -> e.updateCode(code),
				() -> userCertRepository.save(
						new UserCert(userId, code, LocalDateTime.now())));
		sendMail(message);
	}
	
	/**
	 * - 아이디 찾기 이메일을 전송한다. 
	 * - 사용자가 입력한 이메일과 일치하는 데이터가 DB에 존재하는 경우 이메일로 계정의 아이디를 전송한다. 
	 * 
	 * @param email 아이디 찾기에 사용되는 이메일
	 */
	@Override
	public void sendFindIdMail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		user.orElseThrow(() -> {
			throw new MailServiceException(ErrorCode.EMAIL_NOT_FOUND, 
					"email=" + email);
		});
		
		sendMail(createFindIdMail(email, user.get().getUserId()));
	}

	/**
	 * - 비밀번호 찾기 이메일을 전송한다.
	 * - DB에 사용자 요청한 아이디와 일치하는 데이터가 있을 경우 해당 계정의 이메일로 비밀번호 찾기
	 *  인증 메일을 발송한다.
	 * - 인증 코드를 발급한 뒤 테이블에 데이터를 삽입 혹은 갱신한 뒤 비동기로 이메일을 전송한다.
	 * 
	 * @param userId
	 */
	@Transactional
	@Override
	public void sendFindPasswordMail(String userId) {
		Optional<User> user = userRepository.findById(userId);
		
		user.orElseThrow(() -> {
			throw new MailServiceException(ErrorCode.USER_ID_NOT_FOUND, 
					"id=" + userId);
		});
		
		Optional<UserCert> userCert = userCertRepository.findById(userId);
		String code = generateCertCode();
		userCert.ifPresentOrElse(e -> e.updateCode(code),
				() -> userCertRepository.save(
						new UserCert(userId, code, LocalDateTime.now())));
		sendMail(createFindPasswordCertMail(user.get().getEmail(), code));
	}

	/**
	 * - 클라이언트로부터 전송받은 인증 코드와 데이터베이스에 있는 코드를 비교하여 일치할 경우(인증에 성공할
	 *  경우) 계정의 인증 여부를 업데이트하고 일치하지 않는다면 Exception을 발생시킨다. 
	 * - 클라이언트에서 인증 코드 발송을 요청한 뒤 3분이 지나면 요청을 보내는 버튼 자체를 막아버리기
	 *  때문에 DB에 데이터 존재 여부나 시간은 비교하지 않고 오로지 코드의 일치 여부만 확인한다.
	 * 
	 * @param userId 인증을 시도하는 유저의 ID
	 * @param code 유저가 입력한 인증 코드
	 */
	@Transactional
	@Override
	public void confirm(String userId, String code) {
		UserCert userCert = userCertRepository.findById(userId).get();
		if(!userCert.getCode().equals(code))
			throw new MailServiceException(ErrorCode.CERT_CODE_MISMATCH, 
					"id=" + userId + ", code=" + code);
		userRepository.findById(userId).get().setCertificated();
	}

	/**
	 * 계정 인증 안내 메일을 작성하여 반환한다.
	 * 
	 * @param email 수신자 이메일 주소
	 * @param code 이메일에 포함시킬 인증 코드
	 * @return
	 */
	private MimeMessage createAccountCertMail(String email, String code) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, 
					"UTF-8");
			helper.setTo(email);
			helper.setSubject("We Make Pass 회원가입을 위한 인증 번호 안내드립니다.");
			helper.setText(new StringBuffer()
					.append("<h1>안녕하세요. We Make Pass 입니다.</h1><br><br>")
					.append("본인 인증을 위한 인증 번호 안내드립니다.<br>")
					.append("다음 인증 번호를 3분 이내에 입력해주세요.<br>")
					.append("<h3>" + code + "</h3><br><br>")
					.toString(), true);
		} catch (MessagingException e) {
			log.error("이메일을 생성하던 도중 오류가 발생했습니다.");
			log.error(e.getMessage());
		}

		return mimeMessage;
	}
	
	/**
	 * 아이디 찾기 이메일을 작성하여 반환한다.
	 * 
	 * @param email 수신자 이메일 주소
	 * @param userId 이메일에 작성할 아이디
	 * @return
	 */
	private MimeMessage createFindIdMail(String email, String userId) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,
					"UTF-8");
			helper.setTo(email);
			helper.setSubject("We Make Pass 아이디 찾기 안내입니다.");
			helper.setText(new StringBuffer()
					.append("<h1>안녕하세요. We Make Pass 입니다.</h1><br><br>")
					.append("본 메일은 아이디 찾기 요청으로 발송되었습니다.<br>")
					.append("회원님의 아이디는 다음과 같습니다.<br>")
					.append("<h3>" + userId + "</h3><br>")
					.toString(), true);
		} catch (MessagingException e) {
			log.error("이메일을 생성하던 도중 오류가 발생했습니다.");
			log.error(e.getMessage());
		}

		return mimeMessage;
	}
	
	/**
	 * 
	 * @param email 수신자 이메일 주소
	 * @param code 이메일에 포함시킬 인증 코드
	 * @return
	 */
	private MimeMessage createFindPasswordCertMail(String email, String code) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,
					"UTF-8");
			helper.setTo(email);
			helper.setSubject("We Make Pass 비밀번호 변경 인증번호입니다.");
			helper.setText(new StringBuffer()
					.append("<h1>안녕하세요. We Make Pass 입니다.</h1><br><br>")
					.append("본 메일은 비밀번호 변경을 위해서 발송되었습니다.<br>")
					.append("다음 인증 번호를 3분 이내에 입력해주세요.<br>")
					.append("<h3>" + code + "</h3><br><br>")
					.toString(), true);
		} catch (MessagingException e) {
			log.error("이메일을 생성하던 도중 오류가 발생했습니다.");
			log.error(e.getMessage());
		}

		return mimeMessage;
	}
	
	/**
	 * - 작성된 메일 객체를 파라미터로 받아 메일을 전송한다.
	 * - 메일을 보내는 작업은 비동기로 수행한다. 메일을 전송하는데 약 2~4초 정도가 소요되기 때문에 
	 *  클라이언트에서 느끼는 딜레이를 최소화하기 위해 요청을 처리하는 스레드가 아닌 별도의 스레드로 작업을
	 *  처리한다.
	 *  
	 * @param message HTML로 작성된 메일 정보(제목, 내용, 발신자)를 갖고 있는 객체 
	 */
	private void sendMail(MimeMessage message) {
		Runnable task = () -> javaMailSender.send(message);
		executor.execute(task);
	}
	
	/**
	 * 이메일로 전송할 인증 코드 6자리를 생성하여 반환한다.
	 * 
	 * @return 6자리 숫자로 이루어진 랜덤 문자열 
	 */
	private String generateCertCode() {
		StringBuffer codeBuffer = new StringBuffer();
		
		for (int i = 0; i < 6; i++)
			codeBuffer.append((int) ((Math.random() * 10000) % 10));
		return codeBuffer.toString();
	}
}
