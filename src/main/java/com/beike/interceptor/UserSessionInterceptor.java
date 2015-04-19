package com.beike.interceptor;

import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by huahui.yang on 4/19/15.
 */
public class UserSessionInterceptor implements HandlerInterceptor {
    private static Logger logger = Logger.getLogger(UserSessionInterceptor.class);

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        String username = (String)session.get("username");
        logger.info("get username from session: " + username);
        String result= "";
        String message = "";
        if(username == null || "".equals(username)) {
            result = "failed";
            message = "no username in session:" + username;
            showRejectInfo(response, message, result);
            return false;
        } else {
            result = "success";
            message = "find username in session:" + username;
            showRejectInfo(response, message, result);
            return true;
        }
    }

    //show interceptor info
    private void showRejectInfo(HttpServletResponse response, String message, String result) {
        logger.info(result);
        String json = "{\"result\":\""+result+"\",\"message\":"+message+"}";
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(json);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}