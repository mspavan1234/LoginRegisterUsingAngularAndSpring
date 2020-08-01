package com.springboot.projectloginRegister.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.springboot.projectloginRegister.exception.SpringBlogException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@PostConstruct
    public void init() {
		try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springboots.jks");
            keyStore.load(resourceAsStream, "pavan123".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringBlogException("Exception occured while loading keystore");
        }
	}

	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
	}
	
	private Key getPrivateKey() {
		 try {
	            return (PrivateKey) keyStore.getKey("springboots", "pavan123".toCharArray());
	        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
	            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
	        }
	}

	public boolean validateToken(String jwt){
		Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
	}

	private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springboots").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
