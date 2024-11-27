package ma.zyn.app.utils.transverse.cloud;
public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(String message) {
        super(message);
    }
}
