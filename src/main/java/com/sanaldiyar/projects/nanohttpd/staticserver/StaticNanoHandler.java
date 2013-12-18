/*
Nano HTTPD Static Web Server
Copryright © 2013 Kazım SARIKAYA

This program is licensed under the terms of Sanal Diyar Software License. Please
read the license file or visit http://license.sanaldiyar.com
 */
package com.sanaldiyar.projects.nanohttpd.staticserver;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoHandler;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.Request;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.Response;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.StatusCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A static web server handler. Handles static files to serve.
 *
 * @author kazim
 */
public class StaticNanoHandler implements NanoHandler {

    private final static Logger logger = LoggerFactory.getLogger(StaticNanoHandler.class);

    private final String wellcome;
    private final String documentRoot;

    /**
     * Handler contructor
     *
     * @param wellcome index page
     * @param documentRoot the folder path of site
     */
    public StaticNanoHandler(String wellcome, String documentRoot) {
        this.wellcome = wellcome;
        this.documentRoot = documentRoot;
    }

    /**
     * Handles requests. The requested path is appended to the document root.
     * The content type is determined by the extension of the requested path.
     * Request data omitted. Response is constructed by only file contents.
     *
     * @param request the nano request
     * @param response the nano reponse
     */
    @Override
    public void handle(Request request, Response response) {
        try {
            String path = request.getPath().getPath();
            if (path.equals("/")) {
                path += wellcome;
            }
            logger.debug("the path requested: " + path);
            File f = new File(documentRoot + path);
            if (!f.exists()) {
                return;
            }
            String ext = path.substring(path.lastIndexOf(".") + 1);
            response.setStatusCode(StatusCode.SC200);
            response.guessAndAddContentType(ext);
            InputStream is = new FileInputStream(f);
            byte[] data = new byte[8192];
            int r;
            OutputStream os = response.getResponseStream();
            while ((r = is.read(data)) > 0) {
                os.write(data, 0, r);
            }
            os.close();
        } catch (Exception ex) {
        }

    }

}
