package ul.ulstu.tamada;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(JUnitPlatform.class)
@DataJpaTest
class TamadaApplicationTests {

    @Test
    void twoPlusTwoEqualsFour() {
        Assertions.assertEquals(4, 2 + 2);
    }
}
