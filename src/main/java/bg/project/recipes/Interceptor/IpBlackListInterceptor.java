package bg.project.recipes.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class IpBlackListInterceptor implements HandlerInterceptor {
    private final List<String> blacklistedIpAddresses = new ArrayList<>();

    public IpBlackListInterceptor() {
        blacklistedIpAddresses.add("0:0:0:0:0:0:0:0");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        if (blacklistedIpAddresses.contains(ipAddress)) {
            response.sendRedirect("/error");
        }
        return true;
    }
}