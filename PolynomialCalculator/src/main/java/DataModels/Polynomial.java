package DataModels;

import DataModels.Monomial;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private Map<Integer, Monomial> monomials = new HashMap<>();

    public Polynomial() {
        this.monomials = new HashMap<>();
    }

    public Polynomial(Map<Integer, Monomial> monomials) {
        this.monomials = monomials;
    }

    public Polynomial(Monomial monomial) {
        this.monomials.put(monomial.getDegree(), monomial);
    }

    public Polynomial(String string) {
        if(string==null){
            throw new IllegalArgumentException("This polynomial is null.");
        }

        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c) && c != 'x' && c != 'X' && c != '^' && c != '+' && c != '-' && c != '*' && c != ' ') {
                throw new IllegalArgumentException("This is not a polynomial");
            }
        }

        String polynomialInput = string.replaceAll("\\s+", "");
        String patternString = "[+-]?(\\d+)?\\*?([xX])?\\^?(\\d+)?";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(polynomialInput);
        while (matcher.find()) {
            if (!matcher.group().isEmpty()) {
                Monomial monomial;
                if (matcher.group(2) != null && matcher.group(3) == null) {
                    monomial = new Monomial("1", matcher.group(1));
                } else if (matcher.group(2) == null && matcher.group(3) == null) {
                    monomial = new Monomial(matcher.group(3), matcher.group(1));
                } else {
                    monomial = new Monomial(matcher.group(3), matcher.group(1));
                }
                if (matcher.group().startsWith("-")) {
                    monomial.setCoefficient(-1 * monomial.getCoefficient());
                }
                this.monomials.put(monomial.getDegree(), monomial);
            }
        }
    }

    public void setMonomials(Map<Integer, Monomial> monomials) {
        this.monomials = monomials;
    }

    public Map<Integer, Monomial> getMonomials() {
        return monomials;
    }

    public int getDegree() {
        List<Monomial> monoms = new ArrayList<>(this.monomials.values());

        int degree = -1;
        for (Monomial monom : monoms)
            if (monom.getDegree() > degree)
                degree = monom.getDegree();
        return degree;
    }

    public void deleteZeros() {
        Iterator<Map.Entry<Integer, Monomial>> iterator = this.monomials.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Monomial> entry = iterator.next();
            Monomial monom = entry.getValue();

            if (monom.getCoefficient() == 0) {
                iterator.remove();
            }
        }
    }

    @Override
    public String toString() {
        this.deleteZeros();
        if (this.monomials.isEmpty())
            return String.valueOf(0);

        String result = "";
        List<Monomial> monoms = new ArrayList<>(this.monomials.values());
        Collections.sort(monoms);
        for (int i = monoms.size() - 1; i >= 0; i--) {
            if (i != 0) {
                if (monoms.get(i - 1).getCoefficient() > 0)
                    result += monoms.get(i) + "+";
                else
                    result += monoms.get(i);
            } else result += monoms.get(i);

        }
        return result;
    }
}
