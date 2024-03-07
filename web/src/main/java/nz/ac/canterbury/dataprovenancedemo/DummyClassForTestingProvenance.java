package nz.ac.canterbury.dataprovenancedemo;

public class DummyClassForTestingProvenance {
    public static int dummyStaticMethod(int x) {
        int retVal = x * 3;
        System.out.println("DummyClassForTestingProvenance.dummyStaticMethod(x=" + x + ") called, will return " + retVal + "!");
        return retVal;
    }
}
