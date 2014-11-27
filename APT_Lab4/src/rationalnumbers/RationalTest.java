package rationalnumbers;
import junit.framework.TestCase;

public class RationalTest extends TestCase {

    protected Rational HALF;

    protected void setUp() {
      HALF = new Rational( 1, 2 );
    }

    // Create new test
    public RationalTest (String name) {
        super(name);
    }

    public void testEquality() {
        assertEquals(new Rational(1,3), new Rational(1,3));
        assertEquals(new Rational(1,3), new Rational(2,6));
        assertEquals(new Rational(3,3), new Rational(1,1));
    }

    // Test for nonequality
    public void testNonEquality() {
        assertFalse(new Rational(2,3).equals(
            new Rational(1,3)));
    }

    public void testAccessors() {
    	assertEquals(new Rational(2,3).numerator(), 2);
    	assertEquals(new Rational(2,3).denominator(), 3);
    }

    public void testRoot() {
        Rational s = new Rational( 1, 4 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        assertTrue( sRoot.isLessThan( HALF.plus( Rational.getTolerance() ) ) 
                        && HALF.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }
    
    /*
     * Cole Tests
     */
    public void testSetTolerance() {
    	Rational tolerance = Rational.getTolerance();
    	Rational.setTolerance(tolerance);
    }
    
    public void testEqualsNull() {
    	Rational s = new Rational(1, 4);
    	assertFalse(s.equals(null));
    }
    
    public void testEqualsInstanceOf() {
    	Rational s = new Rational(1, 4);
    	assertFalse(s.equals(new Object()));
    }
    
    public void testPlus() {
    	Rational s = new Rational(1,4);
    	Rational t = new Rational(1, 4);
    	Rational res = new Rational(1, 2);
    	assertTrue(s.plus(t).equals(res) && t.plus(s).equals(res));
    }
    
    public void testTimes() {
    	Rational s = new Rational(1,4);
    	Rational t = new Rational(1, 4);
    	Rational res = new Rational(1, 16);
    	assertTrue(s.times(t).equals(res) && t.times(s).equals(res));
    }
    
    public void testTimesIdentity() {
    	Rational s = new Rational(1,4);
    	Rational t = new Rational(1, 1);
    	assertTrue(s.times(t).equals(s));
    }
    
    public void testMinus() {
    	Rational s = new Rational(1,4);
    	Rational t = new Rational(1, 8);
    	Rational res = new Rational(1, 8);
    	assertTrue(s.minus(t).equals(res));
    }
    
    public void testDivides() {
    	Rational s = new Rational(3,4);
    	Rational t = new Rational(3,4);
    	Rational res = new Rational(1, 1);
    	assertTrue(s.divides(t).equals(res) && t.divides(s).equals(res));
    }
    
    public void testRootTooLow() {
    	Rational s = new Rational(-5, 1);
    	try {
    		s.root();
    		fail();
    	} catch (IllegalArgumentToSquareRootException e) {
    		assertTrue(true);
    	}
    }
    
    public void testRootTooHigh() {
    	Rational s = new Rational(46341, 1);
    	try {
    		s.root();
    		fail();
    	} catch (IllegalArgumentToSquareRootException e) {
    		assertTrue(true);
    	}
    }
    
    public void testIsLessThan() {
    	Rational s = new Rational(1, 2);
    	Rational t = new Rational(3, 4);
    	assertTrue(s.isLessThan(t));
    }
    
    public void testAbs() {
    	Rational s = new Rational(-1, 2);
    	Rational res = new Rational(1, 2);
    	assertTrue(s.abs().equals(res));
    }
    
    public void testAbsPositive() {
    	Rational s = new Rational(1, 1);
    	assertTrue(s.abs().equals(s));
    	s = new Rational(-1, -1);
    	assertTrue(s.abs().equals(s));
    }
    
    public void testAbsNegative1() {
    	Rational s = new Rational(-1, 1);
    	Rational pos = new Rational(1, 1);
    	assertTrue(s.abs().equals(pos));
    }
    
    public void testAbsNegative2() {
    	Rational s = new Rational(1, -1);
    	Rational pos = new Rational(1, 1);
    	assertTrue(s.abs().equals(pos));
    }
    
    public void testToString() {
    	Rational s = new Rational(1, 2);
    	assertTrue(s.toString().equals("1/2"));
    }

    public static void main(String args[]) {
        String[] testCaseName = 
            { RationalTest.class.getName() };
        // junit.swingui.TestRunner.main(testCaseName);
        junit.textui.TestRunner.main(testCaseName);
    }
}