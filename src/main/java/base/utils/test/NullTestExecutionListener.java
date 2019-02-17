package base.utils.test;


public class NullTestExecutionListener implements TestExecutionListener {

    @Override
    public void event(TestEvent event, EventStatus status, int threadId,
            int executionCount, long runtime, long totalExecuteTime) {
    }

}
