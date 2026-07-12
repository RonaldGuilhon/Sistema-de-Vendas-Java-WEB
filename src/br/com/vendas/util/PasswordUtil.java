package br.com.vendas.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.vendas.exception.BusinessException;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String criptografar(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new BusinessException("Senha não pode ser vazia.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("Erro ao criptografar senha.", e);
        }
    }

    public static boolean verificar(String senha, String hashArmazenado) {
        return criptografar(senha).equals(hashArmazenado);
    }
}
