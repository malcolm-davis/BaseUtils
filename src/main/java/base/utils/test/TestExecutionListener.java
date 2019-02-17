package base.utils.test;


public interface TestExecutionListener {
    void event(TestEvent event, EventStatus status,
            int threadId, int executionCount, long runtime, long totalThreadRuntime);
}
