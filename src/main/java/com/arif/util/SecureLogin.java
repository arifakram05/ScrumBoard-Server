package com.arif.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecureLogin {

	private static SecretKey secretKey = null;
	private static String encodedSecretKey = null;

	/*
	 * public static void main(String args[]) throws NoSuchAlgorithmException {
	 * String token = SecureLogin.createJWT("046752", 120000);
	 * SecureLogin.parseJWT(token);
	 * System.out.println("Is token valid : "+SecureLogin.isTokenValid(token,
	 * "046752")); }
	 */

	/**
	 * Construct a JWT token
	 * 
	 * @param associateId
	 *            associate to whom token needs to be served
	 * @param loginDuration
	 *            how long can the user stay logged in
	 * @return JWT token
	 * @throws NoSuchAlgorithmException
	 */
	public static String createJWT(String associateId, long loginDuration) throws NoSuchAlgorithmException {

		if (secretKey == null) {
			// generate secret key using specified algorithm
			secretKey = KeyGenerator.getInstance("AES").generateKey();
			// get base64 encoded version of the key
			encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our encoded secret key
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(encodedSecretKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(associateId).setIssuedAt(now).signWith(signatureAlgorithm,
				signingKey);

		// Set expiration time
		if (loginDuration >= 0) {
			long expMillis = nowMillis + loginDuration;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	/**
	 * Read the JWT token
	 * 
	 * @param jwt
	 *            JWT token
	 */
	public static void parseJWT(String token) {

		// This line will throw an exception if it is not a signed JWS
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(encodedSecretKey))
				.parseClaimsJws(token).getBody();
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
	}

	/**
	 * Verify if the given token and associate ID are related and valid
	 * 
	 * @param token
	 * @param associateIdToVerify
	 * @return boolean value indication the validity of provided associate ID
	 */
	public static boolean isTokenValid(String token, String associateIdToVerify) {
		String knownAssociateId = null;
		try {
			knownAssociateId = Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token).getBody().getId();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return associateIdToVerify.equals(knownAssociateId);
	}

}
