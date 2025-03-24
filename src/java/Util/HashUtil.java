/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Acer
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HashUtil {

     // Mã hóa mật khẩu với BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12 là cost factor (độ mạnh của mã hóa)
    }
    
    // Kiểm tra mật khẩu nhập vào có khớp với mật khẩu đã hash không
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
//    // Mã hóa mật khẩu
//    public static String hashPassword(String plainTextPassword) {
//        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12)); // rounds = 12
//    }
//
//    // Kiểm tra mật khẩu nhập vào với mật khẩu trong database
//    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
//        return BCrypt.checkpw(plainTextPassword, hashedPassword);
//    }
}