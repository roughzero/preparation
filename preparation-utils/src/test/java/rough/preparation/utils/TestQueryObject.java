package rough.preparation.utils;

public class TestQueryObject {
    String name;
    String code;
    String year;

    public TestQueryObject(String name, String code, String year) {
        this.name = name;
        this.code = code;
        this.year = year;
    }

    public TestQueryObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
