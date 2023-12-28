package com.github.koobh95.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * - RestController 내의 API와 매핑된 메서드에서 사용되는 어노테이션이다.
 * - 접근하려는 API(메서드)에 로그인(JWT 인증)이 필요할 경우 이 어노테이션을 선언하여 인증이 필요함을 
 *  명시해주는 역할을 한다.
 * - 이 어노테이션을 사용하는 목적은 크게 두 가지다. 첫 번째는 작성한지 시간이 조금 지난 컨트롤러 클래스가 
 *  있을 때 특정 메서드에 시큐리티가 적용되어 있는지 확인하려면 보안 설정 클래스에 들어가서 확인하는 방법밖에
 *  없기 때문에 매번 직접 찾아야 하는 번거로움을 덜기 위해 사용한다. 두 번째는 Interceptor에서 공통된
 *  작업을 처리할 때 접근하려는 API가 보안이 적용된 API인지 확인해야 하는 경우가 있기 때문에 이를 구분하는
 *  용도로 사용된다.
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
	
}
