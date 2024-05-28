package nz.ac.canterbury.dataprovenancedemo;

public class DummyClassForTestingProvenance {
    public static String dummyStaticMethod(String x) {
        String retVal = x + "<this added to the end by dummyStaticMethod()>";
        System.out.println("DummyClassForTestingProvenance.dummyStaticMethod(x=" + x + ") called, will return " + retVal + "!");
        return retVal;
    }
}
