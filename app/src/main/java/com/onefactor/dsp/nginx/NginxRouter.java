package com.onefactor.dsp.nginx;

import io.belov.soyuz.route.Router;

import javax.script.ScriptException;
import java.io.*;

/**
 * Возвращает реальные пути до файлов nginx'у
 */
public class NginxRouter {

    private Router router;

    public NginxRouter(String routerPath) {
        try {
            this.router = new Router(new InputStreamReader(getRouterFile(routerPath)));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRoutePathForUrl(String url) {
        return router.getRoutePath(url);
    }

    private InputStream getRouterFile(String routerPath) {
        if (routerPath.startsWith("classpath:")) {
            return this.getClass().getClassLoader().getResourceAsStream(routerPath.substring("classpath:".length()));
        } else {
            try {
                return new FileInputStream(new File(routerPath));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
