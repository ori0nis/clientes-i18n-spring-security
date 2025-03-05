package gm.Clientesi18NSecurity.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// En lugar de crear todo el flujo para crear nuevos usuarios, solo creamos los passwords por separado
public class EncriptarPassword {

    public static void main(String[] args) {
        var password = "123";
        System.out.println("Password sin encriptar: " + password);
        System.out.println("Password encriptado: " + encriptarPassword(password));
    }

    public static String encriptarPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
