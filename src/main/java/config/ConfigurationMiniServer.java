package config;

import config.service.ConfigurationMiniService;
import config.service.Logger;
import config.service.impl.ConfigurationMiniServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConfigurationMiniServer {

    public static void main(String[] args){
        try {
            Set<Class> classesToRegister = new HashSet<>();
            classesToRegister.add(Configuration.class);

                ConfigurationMiniService service = new ConfigurationMiniServiceImpl("127.0.0.1","8000");
                service.init();



                for(Class currentClass : classesToRegister){
                    service.registerClass(currentClass);
                }


                Runnable check = new Runnable() {
                    @Override
                    public void run() {
                        Logger.log(Configuration.NUMBER_CONFIG);
                        Logger.log(Configuration.SWITCH_CONFIG);
                    }
                };

            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(check,0,3, TimeUnit.SECONDS);



        } catch (RemoteException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }


    }
}
