package com.example.taxibooking.security;

import com.example.taxibooking.exceptions.TaxiBookingApiException;
import com.example.taxibooking.services.implementations.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtDecoder jwtDecoder;
	private final CustomUserDetailsService customUserDetailsService;

	public JwtAuthenticationFilter(JwtDecoder jwtDecoder, CustomUserDetailsService customUserDetailsService) {
		this.jwtDecoder = jwtDecoder;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, TaxiBookingApiException {

		final String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer"))
		{
			String jwt = null;

			try {
				jwt = getJwtFromRequest(request);
			} catch (Exception e) {
				throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "Could not set user authentication in security context : " + e.getMessage());
			}
			if (StringUtils.hasText(jwt)) {
				try {
					Jwt decodedJwt = jwtDecoder.decode(jwt);
					String subject = null;
					try {
						subject = decodedJwt.getSubject();
					} catch (Exception e) {
						throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "No subject found from access token.");
					}

					UserDetails userDetails = null;

					try {
						userDetails = customUserDetailsService.loadUserByUsername(subject);
					} catch (UsernameNotFoundException e) {
						throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "No user found from access token.");
					}
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				} catch (JwtException e) {
					throw new TaxiBookingApiException(HttpStatus.UNAUTHORIZED, "Invalid access token : " + e.getMessage());
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
