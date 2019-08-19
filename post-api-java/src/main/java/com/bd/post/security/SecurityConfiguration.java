package com.bd.post.security;

import com.bd.post.security.annotation.NoSecure;
import com.bd.post.security.annotation.NotImpl;
import io.grpc.ServerMethodDefinition;
import net.devh.boot.grpc.server.security.authentication.CompositeGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.AccessPredicateVoter;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.service.GrpcServiceDefinition;
import net.devh.boot.grpc.server.service.GrpcServiceDiscoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Configuration
public class SecurityConfiguration {

	@Autowired
	private GrpcServiceDiscoverer annotationGrpcServiceDiscoverer;

	@Bean
	AccessDecisionManager accessDecisionManager() {
		final List<AccessDecisionVoter<?>> voters = new ArrayList<>();
		voters.add(new AccessPredicateVoter());
		return new UnanimousBased(voters);
	}

	@Bean
	@Lazy
	GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
		final ManualGrpcSecurityMetadataSource source = build(annotationGrpcServiceDiscoverer);
		source.setDefault(AccessPredicate.permitAll());
		return source;
	}

	@Bean
	AuthenticationManager authenticationManager() {
		final List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(new GatewayAuthenticationProvider());
		return new ProviderManager(providers);
	}

	@Bean
	GrpcAuthenticationReader authenticationReader(@Value("${grpc.user-key:userId}") String userKey) {
		final List<GrpcAuthenticationReader> readers = new ArrayList<>();
		readers.add(new DecodedFromGatewayAuthenticationReader(userKey));
		return new CompositeGrpcAuthenticationReader(readers);
	}


	private ManualGrpcSecurityMetadataSource build(GrpcServiceDiscoverer annotationGrpcServiceDiscoverer) {
		final ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
		for (GrpcServiceDefinition grpcServiceDefinition : annotationGrpcServiceDiscoverer.findGrpcServices()) {
			for (ServerMethodDefinition<?, ?> serverMethodDefinition : grpcServiceDefinition.getDefinition().getMethods()) {
				source.set(serverMethodDefinition.getMethodDescriptor(),
					AccessPredicate.hasAuthority(new SimpleGrantedAuthority(serverMethodDefinition.getMethodDescriptor().getFullMethodName())));
			}
		}
		// 去掉NoSecure注解方法的权限
		for (GrpcServiceDefinition grpcServiceDefinition : annotationGrpcServiceDiscoverer.findGrpcServices()) {
			List<Method> methods = getMethodsAnnotatedWith(grpcServiceDefinition.getBeanClazz(), NoSecure.class, NotImpl.class);
			for (Method method : methods) {
				grpcServiceDefinition.getDefinition().getMethods().forEach(serverMethodDefinition -> {
					String[] s = serverMethodDefinition.getMethodDescriptor().getFullMethodName().split("/");
					if (method.getName().equals(s[s.length - 1])) {

						source.remove(serverMethodDefinition.getMethodDescriptor());
					}
				});
			}
		}
		return source;
	}

	// 验证方法签名

	private List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation,final Class<? extends Annotation> another) {
		final List<Method> methods = new ArrayList<>();
		final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(type.getDeclaredMethods()));
		for (final Method method : allMethods) {
			if (method.isAnnotationPresent(annotation) && method.isAnnotationPresent(another)) {
				methods.add(method);
			}
		}
		return methods;
	}


}
