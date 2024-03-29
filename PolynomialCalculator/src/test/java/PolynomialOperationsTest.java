import DataModels.Polynomial;
import BusinessLogic.PolynomialsOperations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialOperationsTest {

    @Test
    public void TestValidAddition() {
        Polynomial p1 = new Polynomial("4*x^5-3*x^4+x^2-8*x+1");
        Polynomial p2 = new Polynomial("3*x^4-x^3+x^2+2*x-1");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        op.add();
        assertEquals("4*x^5-x^3+2*x^2-6*x", op.getResult().toString());
    }

    @Test
    public void TestAdditionZero() {
        Polynomial p1 = new Polynomial("4*x^5-3*x^4+x^2-8*x+1");
        Polynomial p2 = new Polynomial("0");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        op.add();
        assertEquals("4*x^5-3*x^4+x^2-8*x+1", op.getResult().toString());
    }

    @Test
    public void TestNullPolynomialAddition() {
        PolynomialsOperations op = new PolynomialsOperations(new Polynomial("4*x^5-3*x^4+x^2-8*x+1"), null);
        assertThrows(IllegalArgumentException.class, () -> {
            op.add();
        });
    }

    @Test
    public void TestValidSubstraction() {
        Polynomial p1 = new Polynomial("4*x^5-3*x^4+x^2-8*x+1");
        Polynomial p2 = new Polynomial("3*x^4-x^3+x^2+2*x-1");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        op.substract();
        assertEquals("4*x^5-6*x^4+x^3-10*x+2", op.getResult().toString());
    }

    @Test
    public void TestZeroSubstraction() {
        Polynomial p1 = new Polynomial("4*x^5-3*x^4+x^2-8*x+1");
        Polynomial p2 = new Polynomial("0");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        op.substract();
        assertEquals("4*x^5-3*x^4+x^2-8*x+1", op.getResult().toString());
    }

    @Test
    public void TestZeroSubstraction2() {
        Polynomial p1 = new Polynomial("4*x^5-3*x^4+x^2-8*x+1");
        Polynomial p2 = new Polynomial("0");
        PolynomialsOperations op = new PolynomialsOperations(p2, p1);
        op.substract();
        assertEquals("-4*x^5+3*x^4-x^2+8*x-1", op.getResult().toString());
    }

    @Test
    public void TestNullSubstraction() {
        PolynomialsOperations op = new PolynomialsOperations(new Polynomial("4*x^5-3*x^4+x^2-8*x+1"), null);
        assertThrows(IllegalArgumentException.class, () -> {
            op.substract();
        });
    }

    @Test
    public void TestValidMultiplication() {
        Polynomial p1 = new Polynomial("x^2-4");
        Polynomial p2 = new Polynomial("x-1");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        op.multiply();
        assertEquals("x^3-x^2-4*x+4", op.getResult().toString());
    }

    @Test
    public void TestZeroMultiplication() {
        Polynomial p1 = new Polynomial("x^2-4");
        Polynomial p2 = new Polynomial("0");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        op.multiply();
        assertEquals("0", op.getResult().toString());
    }

    @Test
    public void TestNullMultiplication() {
        PolynomialsOperations op = new PolynomialsOperations(new Polynomial("4*x^5-3*x^4+x^2-8*x+1"), null);
        assertThrows(IllegalArgumentException.class, () -> {
            op.multiply();
        });
    }

    @Test
    public void TestValidDivision() {
        Polynomial p1 = new Polynomial("x^2-4");
        Polynomial p2 = new Polynomial("x-1");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        Polynomial[] result = op.divide();
        assertEquals("x+1", result[0].toString());
        assertEquals("-3", result[1].toString());
    }

    @Test
    public void TestZeroDivision() {
        Polynomial p1 = new Polynomial("0");
        Polynomial p2 = new Polynomial("x-1");
        PolynomialsOperations op = new PolynomialsOperations(p1, p2);
        Polynomial[] result = op.divide();
        assertEquals("0", result[0].toString());
        assertEquals("0", result[1].toString());
    }

    @Test
    public void TestDivisionbyZero() {
        Polynomial p1 = new Polynomial("0");
        Polynomial p2 = new Polynomial("x-1");
        PolynomialsOperations op = new PolynomialsOperations(p2, p1);
        assertThrows(IllegalArgumentException.class, () -> {
            op.divide();
        });
    }

    @Test
    public void TestNullDivision() {
        PolynomialsOperations op = new PolynomialsOperations(new Polynomial("4*x^5-3*x^4+x^2-8*x+1"), null);
        assertThrows(IllegalArgumentException.class, () -> {
            op.divide();
        });
    }

    @Test
    public void TestValiDifferentiate(){
        Polynomial p1 = new Polynomial("x^2-4");
        PolynomialsOperations op = new PolynomialsOperations(p1,null);
        op.differentiateFirstPolynomial();
        assertEquals("2*x",op.getResult().toString());
    }

    @Test
    public void TestZeroDifferentiate(){
        Polynomial p1 = new Polynomial("0");
        PolynomialsOperations op = new PolynomialsOperations(p1,null);
        op.differentiateFirstPolynomial();
        assertEquals("0",op.getResult().toString());
    }

    @Test
    public void TestNullDifferentiate(){
        PolynomialsOperations op = new PolynomialsOperations(null, null);
        assertThrows(IllegalArgumentException.class, () -> {
            op.differentiateFirstPolynomial();
        });
    }

    @Test
    public void TestValidIntegrate(){
        Polynomial p1 = new Polynomial("x^2-4");
        PolynomialsOperations op = new PolynomialsOperations(p1,null);
        op.integrateFirstPolynomial();
        assertEquals("0.33*x^3-4*x",op.getResult().toString());
    }

    @Test
    public void TestZeroIntegrate(){
        Polynomial p1 = new Polynomial("0");
        PolynomialsOperations op = new PolynomialsOperations(p1,null);
        op.integrateFirstPolynomial();
        assertEquals("0",op.getResult().toString());
    }

    @Test
    public void TestNullIntegrate(){
        PolynomialsOperations op = new PolynomialsOperations(null, null);
        assertThrows(IllegalArgumentException.class, () -> {
            op.integrateFirstPolynomial();
        });
    }
}
