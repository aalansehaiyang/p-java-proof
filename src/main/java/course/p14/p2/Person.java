package course.p14.p2;

/**
 * @author 微信公众号：微观技术
 */

public class Person {
    private String name;
    private int age;
    private String address;

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    private Person(PersonBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.address = builder.address;
    }

    // 建造者
    static class PersonBuilder {

        private String name;
        private int age;
        private String address;

        public PersonBuilder() {
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

}
