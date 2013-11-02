/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanaldiyar.projects.staticserver;

import com.sanaldiyar.projects.nanohttpd.NanoHandler;
import com.sanaldiyar.projects.nanohttpd.Request;
import com.sanaldiyar.projects.nanohttpd.Response;
import com.sanaldiyar.projects.nanohttpd.StatusCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kazim
 */
public class StaticNanoHandler implements NanoHandler {

    private final static Logger logger = LoggerFactory.getLogger(StaticNanoHandler.class);

    private String wellcome;
    private String documentRoot;

    public StaticNanoHandler(String wellcome, String documentRoot) {
        this.wellcome = wellcome;
        this.documentRoot = documentRoot;
    }

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
