package ul.ulstu.tamada.configuration.security.keys.keyloader;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class JksKeysLoader implements IKeysLoader {

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @Value("${jwt.sign-key-password}")
    private String signKeyPassword;
    private KeyStore keystore;
    private final static String ALIAS = "TMD";

    @SneakyThrows
    @PostConstruct
    private void loadKeys() {
        char[] password = signKeyPassword.toCharArray();
        var jks = getClass().getClassLoader().getResourceAsStream("jks/tmd-keys.jks");
        this.keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        this.keystore.load(jks, password);

        privateKey = (PrivateKey) keystore.getKey(ALIAS, password);
        publicKey  = keystore.getCertificate(ALIAS).getPublicKey();
    }

    @Override
    @SneakyThrows
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    @SneakyThrows
    public PublicKey getPublicKey() {
        return publicKey;
    }
}
