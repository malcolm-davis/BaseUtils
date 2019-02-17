package base.utils.test;


public class PrintTestExecutionListener implements TestExecutionListener {

    @Override
    public void event(TestEvent event, EventStatus status, int threadId,
            int executionCount, long runtime, long totalExecuteTime) {

        if(TestEvent.Start.equals(event)) {
            System.out.printf("Start:    id=%4d,  cnt=%4d\r\n",
                Integer.valueOf(threadId), Integer.valueOf(executionCount));
        } else {
            System.out.printf("Executed: id=%4d,  cnt=%4d,  %s,  runtime=%d,  totalThreadRuntime=%d\r\n",
                Integer.valueOf(threadId), Integer.valueOf(executionCount), status,
                Long.valueOf(runtime), Long.valueOf(totalExecuteTime));
        }

    }

}
