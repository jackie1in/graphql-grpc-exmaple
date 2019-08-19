package com.bd.post.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserFromGatewayAuthenticationToken extends AbstractAuthenticationToken {
	private final String userId;
	public UserFromGatewayAuthenticationToken(Collection<? extends GrantedAuthority> authorities,String userId) {
		super(authorities);
		this.userId = userId;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}
}
