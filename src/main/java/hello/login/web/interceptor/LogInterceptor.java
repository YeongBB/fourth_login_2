package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();     // 싱글톤이기 때문에 밖에 생성 X

        request.setAttribute(LOG_ID, uuid);    // setAttribute 로 보냄

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;     //호출할 컨트롤러 메서드의 모든 정보가포함되어 있다.
                 // ture -> 계속 진행, false -> 종료
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);   // getAttribute 로 받음

        log.info("RESPONSE [{}][{}][{}]",logId, requestURI, handler);

        if(ex != null){
            log.error("afterCompletion error!!", ex);
        }
    }
}
