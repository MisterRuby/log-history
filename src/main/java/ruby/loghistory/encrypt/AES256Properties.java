package ruby.loghistory.encrypt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aes256")
public record AES256Properties (String alg, String key) {}
