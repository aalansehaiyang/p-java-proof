package proof.chapter7;

import proof.chapter7.param.Param1;
import proof.chapter7.param.Param2;
import proof.chapter7.param.Param3;

import java.util.Optional;

/**
 * @author onlyone
 */
public class Optional1 {

    public static void main(String[] ARGS) {

        // map函数（有值）
        Param3 param3 = new Param3().setName("Tom GE");
        Param2 param2 = new Param2();
        param2.setParam3(param3);
        Param1 param1 = new Param1();
        param1.setParam2(param2);
        String result1 = Optional.of(param1).map(Param1::getParam2).map(Param2::getParam3).map(Param3::getName).orElse("默认name");
        System.out.println(result1);

        // map函数（无值）
        Param2 param2_1 = new Param2();
        Param1 param1_1 = new Param1();
        param1_1.setParam2(param2_1);
        String result1_1 = Optional.of(param1_1).map(Param1::getParam2).map(Param2::getParam3).map(Param3::getName).orElse(null);
        System.out.println(result1_1);
    }
}
