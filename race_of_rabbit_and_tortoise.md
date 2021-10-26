# 兔

```java
package wg.application.thread.normal;

public class Rabbit extends Animal implements Runnable {

    private Double step;
    private Double length;
    private boolean flag;

    {
        this.step = 3D;
        this.length = 0D;
        this.flag = true;
    }

    /**
     * 特征
     */
    @Override
    protected void features() {
        System.out.println("rabbit speed fast");
    }

    @Override
    public void run() {
        running();
    }

    public void running() {
        while (!flag) {
            return;
        }
        try {
            length += step;
            System.out.println("rabbit run " + length);
            Thread.sleep(1000);
            running();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止当前线程
     */
    public void stopCurrentThread() {
        this.flag = false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}

```



# 龟

```java
package wg.application.thread.normal;

public class Tortoise extends Animal implements Runnable {

    private Double step;
    private Double length;
    private boolean flag;

    {
        this.step = 1D;
        this.length = 0D;
        this.flag = true;
    }

    @Override
    protected void features() {
        System.out.println("tortoise speed slow");
    }

    @Override
    public void run() {
        running();
    }

    public void running() {
        while (!flag) {
            return;
        }
        try {
            length += step;
            System.out.println("tortoise run " + length);
            Thread.sleep(100);
            running();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止当前线程
     */
    public void stopCurrentThread() {
        this.flag = false;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}

```



# 赛跑

```java
package wg.application.thread.normal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "/race_of_rabbit_and_tortoise")
public class Test {

    /**
     * 赛程
     */
    private static final Double course = 100D;

    @GetMapping(value = "/race")
    public static Map<String, Object> race() {
        HashMap<String, Object> map = new HashMap<>();
        Rabbit rabbit = new Rabbit();
        Tortoise tortoise = new Tortoise();

        // 开始跑
        Thread rabbitThread = new Thread(rabbit);
        Thread tortoiseThread = new Thread(tortoise);
        rabbitThread.start();
        tortoiseThread.start();

        long start = System.currentTimeMillis();
        System.out.println("-------------------------------------------");
        long count = 0;
        for (; ; ) {
            count++;
            // 当比赛结束是 返回 龟兔 所跑的距离
            if (rabbit.getLength().compareTo(course) >= 0 || tortoise.getLength().compareTo(course) >= 0) {
                rabbit.stopCurrentThread();
                tortoise.stopCurrentThread();
                System.out.println("比赛结束");
                System.out.println(System.currentTimeMillis() - start);

                map.put("stepOfRabbit", rabbit.getLength());
                map.put("stepOfTortoise", tortoise.getLength());
                map.put("count", count);


                return map;
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Object> race = race();
        System.out.println(race);
    }
}

```

