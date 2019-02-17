package base.utils.test.stress;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>StressTest class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class StressTest {

    /**
     * <p>newTest.</p>
     *
     * @return a {@link org.elasolutions.utils.test.stress.StressTest} object.
     */
    public static StressTest newTest() {
        return new StressTest();
    }

    /**
     * During the run(...) process, the classToInstantiate will be copied.
     * Some test situations require a StressAction per-thread rather than a single StressAction.
     *
     * @param classToInstantiate to create
     */
    public void setStressActionClass( Class  classToInstantiate) {
        for(  Class interfaceClass : classToInstantiate.getInterfaces() ) {
            if( interfaceClass.isInstance(StressAction.class) ) {
                m_classToInstantiate = classToInstantiate;
                break;
            }
        }
    }

    /**
     * <p>Run the test</p>
     * <p>0 for min &amp; max delay indicates no delay</p>
     * <p>min==max indicates a set delay time</p>
     * @param name a {@link java.lang.String} object.
     * @param numberOfThreads a int.
     * @param numberOfActionsPerThread a int.
     * @param minDelayBetweenActions in milliseconds
     * @param maxDelayBetweenActions in milliseconds
     * @param action a {@link org.elasolutions.utils.test.stress.StressAction} object.
     */
    public void run(final String name, final int numberOfThreads,
            final int numberOfActionsPerThread, final long minDelayBetweenActions,
            final long maxDelayBetweenActions, final StressAction action) {

        if (minDelayBetweenActions>maxDelayBetweenActions) {
            throw new IllegalArgumentException("Min can not be greater than max.");
        }

        Gson gson = new GsonBuilder().create();
        for(int count = 0; count < numberOfThreads; count++) {
            StressAction actionToUse = action;
            if( m_classToInstantiate!=null ) {
                actionToUse = (StressAction)gson.fromJson(gson.toJson(action), m_classToInstantiate);
            }
            final StressThead thread =
                    new StressThead(name, count, numberOfActionsPerThread,
                        minDelayBetweenActions, maxDelayBetweenActions, actionToUse);
            thread.setPrintToSystemOut(printToSystemOut());
            getStressThreads().add(thread);
        }

        final long start = System.currentTimeMillis();
        for (final StressThead thread : getStressThreads()) {
            thread.start();
        }

        long totalAverageThreadTimes = 0;

        int cnt = 1;
        for (final StressThead thread : getStressThreads()) {
            try {
                thread.join();
                totalAverageThreadTimes = totalAverageThreadTimes + thread.averageRuntime();

                if (printToSystemOut()) {
                    String result = "Thread: " + cnt + ",  average runtime: "
                            + thread.averageRuntime();
                    System.out.println(result);
                }
                cnt++;
            } catch (final InterruptedException excep) {
                excep.printStackTrace();
            }
        }
        for (final StressThead thread : getStressThreads()) {
            thread.cleanup();
        }

        m_averageThreadTimes = (totalAverageThreadTimes / (cnt - 1));

        m_totalRuntime = System.currentTimeMillis() - start;
    }

    /**
     * <p>getStressThreads.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<StressThead> getStressThreads() {
        if (m_testsList == null) {
            m_testsList = new ArrayList<StressThead>();
        }
        return m_testsList;
    }

    /**
     * Display individual execution results as the stress is executing.
     * This will impact the results due to the additional load placed on the system.
     *
     * @param displayFlag
     * void
     */
    public void setDisplayDuringExecution(boolean displayFlag) {
        m_displayResultsDuringExecution = displayFlag;
    }

    /**
     * <p>printToSystemOut.</p>
     *
     * @return a boolean.
     */
    public boolean printToSystemOut() {
        return m_displayResultsDuringExecution;
    }

    /**
     * <p>getTotalRuntime.</p>
     *
     * @return a long.
     */
    public long getTotalRuntime() {
        return m_totalRuntime;
    }

    /**
     * <p>getAverageThreadTime.</p>
     *
     * @return a long.
     */
    public long getAverageThreadTime() {
        return m_averageThreadTimes;
    }

    private List<StressThead> m_testsList;

    private boolean m_displayResultsDuringExecution;

    private long m_totalRuntime;

    private long m_averageThreadTimes;

    private Class m_classToInstantiate;

    private StressTest() {

    }

}
