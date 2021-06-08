package course.p14.p2;

import com.alibaba.fastjson.JSON;

/**
 * @author 微信公众号：微观技术
 */

public class PersonBuilderTest {

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("Tom哥")
                .age(18)
                .address("杭州")
                .build();
        System.out.println(JSON.toJSONString(person));
    }

}