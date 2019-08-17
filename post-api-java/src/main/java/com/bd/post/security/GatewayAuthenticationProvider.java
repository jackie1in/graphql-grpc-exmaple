package com.bd.post.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class GatewayAuthenticationProvider implements AuthenticationProvider {
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO 远程获取用户的权限
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserFromGatewayAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
