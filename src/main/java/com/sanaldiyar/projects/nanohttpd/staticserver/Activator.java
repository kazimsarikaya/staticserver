/*
Nano HTTPD Static Web Server
Copryright © 2013 Kazım SARIKAYA

This program is licensed under the terms of Sanal Diyar Software License. Please
read the license file or visit http://license.sanaldiyar.com
 */
package com.sanaldiyar.projects.nanohttpd.staticserver;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoHandler;
import java.io.FileInputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Static Web Server Activator Bundle
 *
 * @author kazim
 */
public class Activator implements BundleActivator {

    /**
     * Loads configuration file and parses virtual hosts of static files and
     * registers them into the osgi.
     * @param context the osgi context
     * @throws Exception general exception. especially not found staticserver.conf
     */
    @Override
    public void start(BundleContext context) throws Exception {
        Properties conf = new Properties();
        conf.load(new FileInputStream("staticserver.conf"));
        int i = 0;
        while (true) {
            if (!conf.containsKey("staticserver." + i + ".virtualhost")) {
                break;
            }
            String virualhost = conf.getProperty("staticserver." + i + ".virtualhost");
            String documentRoot = conf.getProperty("staticserver." + i + ".documentroot");
            String wellcome = conf.getProperty("staticserver." + i + ".wellcome");

            NanoHandler handler = new StaticNanoHandler(wellcome, documentRoot);
            Dictionary props = new Hashtable();
            props.put("VirtualHost", virualhost);
            context.registerService(NanoHandler.class.getName(), handler, props);
            i++;
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }

}
