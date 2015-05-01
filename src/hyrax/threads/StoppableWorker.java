package hyrax.threads;

/**
 * Encapsulates a single worker thread which can be started and stopped on request.
 *
 * I don't trust my own ability to write concurrent code correctly at this level,
 * so obviously its better to use standard library stuff (Callables, Thread Pools,
 * Executors, etc.) whenever possible.
 *
 * @author Matthew Seiler
 * @version 4/30/2015
 */
public abstract class StoppableWorker implements Runnable {

    /* Worker thread */
    private Thread workerThread;

    /* Flag marks if the worker thread is running or not */
    protected boolean stopped = true;
    // TODO: is it more sensible to make this "running"?

    /* Lock to synchronize access to the above */
    protected final Object locker = new Object();
    // TODO: could we just synchronize on 'this', since we only need the one lock?

    /**
     * Subclasses override this method to define work that is done on the worker thread but before the main work loop begins.
     * This method will be called once each time the worker thread is started. This method should
     * not call methods which throw an interrupted exception.
     */
    protected abstract void doPreLoop();

    /**
     * This defines work which is done on each iteration of the main work loop.  The worker thread will
     * execute this method repeatedly and until it is interrupted by an exception or a call to requestStop()
     *
     * @throws java.lang.InterruptedException - allows this method to call Thread.sleep() or wait()/await()
     */
    protected abstract void doInLoop() throws InterruptedException;

    /**
     * This defines work that is done on the worker thread after the main work loop completes,
     * immediately before the worker thread exits.  It is called once when the worker thread
     * is interrupted via an exception or requestStop().  This method should not call methods
     * which throw an interrupted exception.
     */
    protected abstract void doPostLoop();


    /*
    NOTE: If the interrupted flag is set after doPreLoop() and before the while loop, doInLoop() will never be called (the while will skip)
     */
    @Override
    public void run() {
        try {
            // mark that the worker has started
            synchronized (locker) {
                stopped = false;
            }
            // call the setup method once
            doPreLoop();
            // continue to execute main work until interrupted
            while (!Thread.interrupted()) {
                doInLoop();
            }
        }
        catch (InterruptedException iex) {
            // nop - clean up in finally
        }
        finally {
            // call the teardown method once
            doPostLoop();
            // mark that the worker has stopped
            synchronized (locker) {
                stopped = true;
            }
        }
    }

    public void requestStart() {
        synchronized (locker) {
            if (this.stopped) {
                workerThread = new Thread(this);
                workerThread.start();
            }
        }
    }


    public void requestStop() throws InterruptedException {
        this.requestStop(false);
    }

    public void requestStop(boolean wait) throws InterruptedException {
        // ...did we ask the thread to terminate?
        boolean asked = false;
        // make sure stopped status doesn't change between when we check the flag and when we send the interrupt...
        synchronized (locker) {
            // check id the worker is running.  If it's stopped, this method should nop
            if (!this.stopped) {
                // send the interrupt if the worker is running
                workerThread.interrupt();
                // ...why yes, we DID ask for it to terminate.
                asked = true;
            }
        }
        // if desired, block while the thread finishes
        // (do NOT block while holding locker!)
        if (asked && wait) {
            workerThread.join();
        }
    }

}
