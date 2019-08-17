package com.bd.post.security;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Nullable;
import java.util.Collections;

public class DecodedFromGatewayAuthenticationReader implements GrpcAuthenticationReader {
	private Metadata.Key<String> userKey;
	private Metadata.Key<String> authorityKey;

	public DecodedFromGatewayAuthenticationReader(String userKey) {
		this.userKey =  Metadata.Key.of(userKey, Metadata.ASCII_STRING_MARSHALLER);
	}

	@Nullable
	@Override
	public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) throws AuthenticationException {
		final String userId = headers.get(userKey);
		return new UserFromGatewayAuthenticationToken(null,userId);
	}

	public void setUserKey(String userKey) {
		this.userKey = Metadata.Key.of(userKey, Metadata.ASCII_STRING_MARSHALLER);
	}
}
