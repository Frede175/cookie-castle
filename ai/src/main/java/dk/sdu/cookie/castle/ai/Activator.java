package dk.sdu.cookie.castle.ai;

import dk.sdu.cookie.castle.common.services.AIService;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private static AStar aStar;
    private static ServiceRegistration<AIService> aiServiceServiceRegistration;
    private static ServiceRegistration<IEntityProcessingService> iEntityProcessingServiceServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        //We create a new AStar object, and register it in the bundcontext
        //We register the AIService and the EntetyProcessingService for the AI, when the bundle is registered/started
        aStar = new AStar();
        aiServiceServiceRegistration = bundleContext.registerService(AIService.class, aStar, null);
        iEntityProcessingServiceServiceRegistration = bundleContext.registerService(IEntityProcessingService.class, new AIProcessing(), null);
    }

    public static AStar getaStar() {
        return aStar;
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        iEntityProcessingServiceServiceRegistration.unregister();
        aiServiceServiceRegistration.unregister();
    }
}
