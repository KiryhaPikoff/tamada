package ul.ulstu.tamada.configuration.security.keys.keyloader;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IKeysLoader {

    PrivateKey getPrivateKey();

    PublicKey getPublicKey();
}
