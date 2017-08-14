package com.onefactor.dsp.nginx;

import io.thedocs.soyuz.log.LoggerEvents;
import io.thedocs.soyuz.to;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Возвращает nginx'у реальный путь до html файлов. Запрещает доступ, если пользователь не авторизован
 */
@RestController()
public class NginxRouterController {

    private static final LoggerEvents loge = LoggerEvents.getInstance(NginxRouterController.class);

    @Autowired
    private NginxRouter router;
//    @Autowired
//    private AuthService authService;

    @RequestMapping("/api/nginx/route")
    public void route(HttpServletRequest req, HttpServletResponse resp) {
        String url = req.getHeader("X-Original-URI");

        if (url.startsWith("/app/") && !isAuthenticated()) {
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            resp.setHeader("location", "/auth/login.html");

            loge.trace("nginx.route", to.map("url", url, "result", "forbidden.login"));
        } else {
            String route = router.getRoutePathForUrl(url);

            resp.setContentType("text/plain");
            resp.setHeader("route", route);

            loge.trace("nginx.route", to.map("url", url, "result", route));
        }
    }

    private boolean isAuthenticated() {
        return false;
        // authService.isAuthenticated();
    }
}
