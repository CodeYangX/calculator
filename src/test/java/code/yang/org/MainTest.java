package code.yang.org;

import org.junit.Assert;
import org.junit.Test;

public class MainTest {

  @Test
  public void testPositive() {
    String exp = "1+1";
    double expected = 2;
    double result = Main.count(exp);
    Assert.assertEquals(expected, result, 0);

    exp = "1-1";
    expected = 0;
    result = Main.count(exp);
    Assert.assertEquals(expected, result, 0);

    exp = "-1*2-1/(3-1)";
    expected = -2.5;
    result = Main.count(exp);
    Assert.assertEquals(expected, result, 0);

    exp = "12*3";
    expected = 36;
    result = Main.count(exp);
    Assert.assertEquals(expected, result, 0);

    exp = "12.3/3";
    expected = 4.1;
    result = Main.count(exp);
    Assert.assertEquals(expected, result, 1E-10);

    exp = "2*PI";
    expected = 6.283185307179586;
    result = Main.count(exp);
    Assert.assertEquals(expected, result, 1E-10);

    exp = "1+20*(1+2)/4+sin(30)";
    expected = 16.5;
    result = Main.count(exp);
    Assert.assertEquals(expected, result, 0);


  }

  @Test
  public void testNegative() {}

}
