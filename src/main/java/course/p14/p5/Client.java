package course.p14.p5;


import lombok.SneakyThrows;

public class Client {
    @SneakyThrows
    public static void main(String[] args) {
        Prototype a = new APrototype();
        Prototype b = a.clone();

        System.out.println("a的对象引用：" + a);
        System.out.println("b的对象引用：" + b);
    }
}
