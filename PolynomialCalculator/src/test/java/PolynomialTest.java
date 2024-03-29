import DataModels.Polynomial;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PolynomialTest {

    @Test
    public void testValidFormat() {
        Polynomial polynomial = new Polynomial("4*x^ 5 - 3*x^4    + x^2-8*x+1");
        assertEquals("4*x^5-3*x^4+x^2-8*x+1", polynomial.toString());
    }

    @Test
    public void testInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            Polynomial polynomial = new Polynomial("4*x^ 5 - 3*x^4  +a+  + x^2-8*x+1");
        });
    }

    @Test
    public void testNullPolynomial() {
        assertThrows(IllegalArgumentException.class, () -> {
            Polynomial polynomial = new Polynomial((String) null);
        });
    }

    @Test
    public void testDeleteZeros(){
        Polynomial polynomial = new Polynomial("4*x^ 5 - 3*x^4    + 0*x^2-8*x+0");
        assertEquals("4*x^5-3*x^4-8*x", polynomial.toString());
    }
}
