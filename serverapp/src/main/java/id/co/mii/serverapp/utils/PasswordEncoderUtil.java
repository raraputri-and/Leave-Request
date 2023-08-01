package id.co.mii.serverapp.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderUtil {


    @Bean //saat server runnning, method yg anotasi bean akan dijalankan secara eksplisit
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
