package hu.boldizsartompe.photofeed.presenter.rx;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExecutor implements Executor {

    private static final int INITIAL_POOL_SIZE = 0;
    private static final int MAX_POOL_SIZE = 2;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> workQueue;

    private final ThreadPoolExecutor threadPoolExecutor;

    private static JobExecutor instance;

    public static JobExecutor getInstance(){
        if(instance == null) instance = new JobExecutor();
        return instance;
    }

    private JobExecutor() {
        this.workQueue = new LinkedBlockingQueue<>();
        this.threadPoolExecutor = new ThreadPoolExecutor(
                INITIAL_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue);
    }

    @Override
    public void execute(@NonNull Runnable runnable) {

        if (runnable == null) {
            throw new IllegalArgumentException("Runnable cannot be null");
        }
        threadPoolExecutor.execute(runnable);

    }
}
