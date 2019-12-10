package club.crabglory.www.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Factory {

    private final ExecutorService executor;
    private static Factory factory;

    public Factory() {
        executor = Executors.newFixedThreadPool(4);
    }

    public static void runOnAsync(Runnable runnable){
        Factory.getInstance().executor.execute(runnable);
    }

    static {
        factory = new Factory();
    }
    static Factory getInstance(){
        return factory;
    }
}
