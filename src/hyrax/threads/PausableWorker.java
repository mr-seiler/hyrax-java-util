package hyrax.threads;

/**
 * Questionable.  Sound enough concept, questionable implementation.
 * Need to closely evaluate my use of locks sometime when not sleepy.
 *
 * TODO can `pauseRequested` and `stopped` be locked by the same object?  If so, can it be `this`?
 * 
 * @author Matthew Seiler
 * @version 4/30/2015
 */
public abstract class PausableWorker  extends StoppableWorker {

    private boolean pauseRequested = false;

    @Override
    public void run() {
        try {
            synchronized (locker) {
                stopped = false;
            }
            doPreLoop();
            while (!Thread.interrupted()) {
                checkForPause();
                doInLoop();
            }
        }
        catch (InterruptedException iex) {
            // the worker thread was interrupted, almost certainly a proper stop request
            // ignore exception and clean up in finally
        }
        finally {
            doPostLoop();
            synchronized (locker) {
                stopped = true;
                pauseRequested = false;
            }
        }
    }

    private void checkForPause() throws InterruptedException {
        synchronized (locker) {
            while (pauseRequested) {
                locker.wait();
            }
        }
    }

    public void requestPause() {
        synchronized (locker) {
            pauseRequested = true;
        }
    }

    public void requestResume() {
        synchronized (locker) {
            pauseRequested = false;
            locker.notifyAll();
        }
    }
}
