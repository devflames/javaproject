/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.linecorp.bot.client;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LineSignatureValidator {
	private static final String HASH_ALGORITHM = "HmacSHA256";
	private final SecretKeySpec secretKeySpec;

	public String toString() {
		return "LineSignatureValidator(secretKeySpec=" + this.secretKeySpec + ")";
	}

	public LineSignatureValidator(byte[] channelSecret) {
		this.secretKeySpec = new SecretKeySpec(channelSecret, "HmacSHA256");
	}

	public boolean validateSignature(byte[] content, String headerSignature) {
		if (content == null) {
			throw new NullPointerException("content");
		}
		if (headerSignature == null) {
			throw new NullPointerException("headerSignature");
		}
		byte[] signature = generateSignature(content);
		byte[] decodeHeaderSignature = Base64.getDecoder().decode(headerSignature);
		return MessageDigest.isEqual(decodeHeaderSignature, signature);
	}

	public byte[] generateSignature(byte[] content) {
		if (content == null) {
			throw new NullPointerException("content");
		}
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(this.secretKeySpec);
			return mac.doFinal(content);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new IllegalStateException(e);
		}
	}
}

