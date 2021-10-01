/*
 * Created on 2011-10-20
 */
package rough.preparation.batch;

/**
 * 步进任务分发器.
 * 
 * @author rough
 */
public class StepTaskProvider extends AbstractTaskProvider<Integer> {
    private static final int DEFAULTS_TASKS = 100;
    private static final int DEFAULTS_STARTS = 1;
    private static final int DEFAULTS_STEP = 1;

    private int tasks;
    private int start;
    private int step;
    private int current;
    private int providedTasks = 0;

    public int getTasks() {
        return tasks;
    }

    public void setTasks(int tasks) {
        this.tasks = tasks;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Constructor.
     * 
     */
    public StepTaskProvider() {
        this(DEFAULTS_TASKS, DEFAULTS_STARTS, DEFAULTS_STEP);
    }

    /**
     * Constructor.
     * 
     * @param tasks 任务数
     * @param start 初始值
     * @param step 步进值
     */
    public StepTaskProvider(int tasks, int start, int step) {
        setTasks(tasks);
        setStart(start);
        setStep(step);
        this.current = start;
    }

    /**
     * @see rough.preparation.batch.AbstractTaskProvider#doGetNextTask()
     */
    @Override
    protected Integer doGetNextTask() {
        if (providedTasks < tasks) {
            int result = current;
            current += step;
            providedTasks++;
            return result;
        }

        return null;
    }
}
