package rough.preparation.utils;

import lombok.Data;

/**
 * 测试匹配规则的 DTO
 */
@Data
public class RuleTestDTO {
    private String gender;
    private int age;
    private String level;
    private int value;

    public RuleTestDTO() {}

    public RuleTestDTO(String gender, int age, String level, int value) {
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.value = value;
    }
}
