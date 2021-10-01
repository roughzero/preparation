package rough.preparation.utils.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class SampleBean {
    private String name;
    private BigDecimal salary;
    private Date birthday;
}
