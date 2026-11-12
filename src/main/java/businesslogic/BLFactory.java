package businesslogic;

public class BLFactory {
    
    public BLFacade getBusinessLogicFactory(boolean isLocal) {
        if (isLocal) {
            return new BLFacadeImplementation();
        } else {
            return new BLFacadeImplementation();
        }
    }
}