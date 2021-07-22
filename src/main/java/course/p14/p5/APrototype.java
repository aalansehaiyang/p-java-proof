package course.p14.p5;


public class APrototype implements Prototype {
    @Override
    public Prototype clone() throws CloneNotSupportedException {
        System.out.println("开始克隆《微观技术》对象");
        return (APrototype) super.clone();
    }
}
