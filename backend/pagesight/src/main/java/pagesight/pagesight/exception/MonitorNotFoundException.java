package pagesight.pagesight.exception;

public class MonitorNotFoundException extends RuntimeException {

    public MonitorNotFoundException(Long monitorId) {
        super("Monitor not found with id: " + monitorId);
    }
}
