import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;

// results:
// Double is about 8x to 12x times faster sorted in an ArrayList 
public class BigDecimalVsDoubleTest {

    @Test
    public void bigIntTimeCostTest() {
        long start = System.nanoTime();
        BigDecimal biige = new BigDecimal(334232323534.034434343468958030493d);

        ArrayList<BigDecimal> biggis = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            biggis.add(biige.add(new BigDecimal(234.0)));
        }

        biggis.sort(Comparator.comparing(BigDecimal::doubleValue));

        System.out.println(System.nanoTime()-start);
    }

    @Test
    public void doubleTimeCostTest() {
        long start = System.nanoTime();
        double biige = 334232323534.034434343468958030493d;

        ArrayList<Double> biggis = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            biggis.add(biige+234.0);
        }

        biggis.sort(Comparator.comparing(Double::doubleValue));

        System.out.println(System.nanoTime()-start);
    }
}
