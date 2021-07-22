package course.p14.p5;

/**
 * @author 微信公众号：微观技术
 */
public interface Prototype extends Cloneable {

    public Prototype clone() throws CloneNotSupportedException;
}
