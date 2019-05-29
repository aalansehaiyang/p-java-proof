package proof.chapter9;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Callable;

/**
 * @author onlyone
 */

@Data
@AllArgsConstructor
public class TaskCallable implements Callable<String> {

    private String taskName;
    private Long   costTime;

    @Override
    public String call() throws Exception {
        Thread.sleep(costTime);
        return taskName;
    }
}
