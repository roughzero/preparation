package rough.preparation.utils;

public class TestResultObject {
    String code;
    String year;
    String rate;

    public TestResultObject() {

    }

    public TestResultObject(String code, String year, String rate) {
        this.setCode(code);
        this.setYear(year);
        this.setRate(rate);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
