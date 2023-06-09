package kr.ac.kumoh.ordersystem.config.auth;

import kr.ac.kumoh.ordersystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    //private PrincipalDetailsService principalDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String email = token.getName();
        String password = (String) token.getCredentials();

        PrincipalDetails userDetailsVO = (PrincipalDetails) principalDetailsService.loadUserByUsername(email);
        System.out.println(userDetailsVO.getUsername());
        if (!passwordEncoder.matches(password, userDetailsVO.getPassword())) {
            throw new BadCredentialsException(userDetailsVO.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(email, password, userDetailsVO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}