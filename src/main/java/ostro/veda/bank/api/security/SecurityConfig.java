package ostro.veda.bank.api.security;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {
    public static String PREFIX = "Bearer";
    public static String KEY = System.getenv("SECRET_KEY");
    public static Long EXPIRATION = 3600000L;
}
