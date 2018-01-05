package config;

import config.service.ConfigurationCenterService;
import config.service.Logger;
import config.service.impl.ConfigurationCenterServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ConfigurationCenter {
    public static void main(String[] args){

        try {
            ConfigurationCenterService service = new ConfigurationCenterServiceImpl();

            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://127.0.0.1:8888/cfg_center",service);
            Logger.log("master init success!");

            Scanner scanner = new Scanner(System.in);
            System.out.println("input\n" +
                    "get //to get all configs \n" +
                    "push 127.0.0.1:8000/cfg_miniserver config.Configuration NUMBER_CONFIG 6 java.lang.Integer \r\n");
            while (scanner.hasNext()){
                String command = scanner.nextLine();
                String[] commanArray = command.split(" ");

                String cmd = commanArray[0];

                if(cmd.equals("get")){
                    service.getAllConfig();
                }
                else if(cmd.equals("push")){
                    String server = commanArray[1];
                    String className = commanArray[2];
                    String field = commanArray[3];
                    String value = commanArray[4];
                    String valueType = commanArray[5];

                    ConfigDTO configDTO = new ConfigDTO();
                    configDTO.setFiled(field);
                    configDTO.setClassName(className);
                    configDTO.setServer(server);
                    configDTO.setValue(value);
                    configDTO.setValueType(valueType);

                    int result = service.pushConfig(configDTO);
                    if(result == 200){
                        Logger.log("[success]推送配置 "+field+" ,值为 "+value+" 到服务器"+server+" 成功");
                    }else{
                        Logger.log("[error]推送配置 "+field+" ,值为 "+value+" 到服务器"+server+" 失败");
                    }
                }
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }


    }
}
