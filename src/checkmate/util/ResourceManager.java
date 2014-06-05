/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkmate.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhasme
 */
public class ResourceManager {

    private Properties configs;

    public ResourceManager() {
        loadConfigFile();
    }

    private void loadConfigFile() {
        try {
            InputStream inStream = this.getClass().getResourceAsStream(ProjectInfo.configFileName);
            configs = new Properties();
            configs.load(inStream);
        } catch (IOException ex) {
            Logger.getLogger(ResourceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStringConfig(String name) {
        return configs.getProperty(name);
    }

    public int getIntConfig(String name) {
        String strValue = configs.getProperty(name);
        int value = 0;
        try {
            value = Integer.parseInt(strValue);
        } catch (NumberFormatException ex) {
            value = (strValue != null && strValue.equals(""))? 0 : Integer.MIN_VALUE;
        }
        return value;
    }
    
    public double getDoubleConfig(String name) {
        String strValue = configs.getProperty(name);
        double value = 0.00;
        try {
            value = Double.parseDouble(strValue);
        } catch (NumberFormatException ex) {
            value = (strValue != null && strValue.equals(""))? 0.00 : Double.MIN_VALUE;
        }
        return value;
    }

}
