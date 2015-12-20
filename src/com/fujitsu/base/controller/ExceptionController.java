package com.fujitsu.base.controller;

import com.fujitsu.base.entity.ErrorMsg;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Barrie on 15/12/21.
 */
@Controller
@RequestMapping(value = "/exception")
public class ExceptionController extends BaseController {

    @RequestMapping(value = "/401", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String missingLogin(Exception ex) {
        logger.error("Missing login", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.UNAUTHORIZED.toString(), "Missing login");
        return JSONObject.fromObject(errMsg).toString();
    }

    @RequestMapping(value = "/403", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public String forbiddenDirectoryListing(Exception ex) {
        logger.error("Forbidden directory listing", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.FORBIDDEN.toString(), "Forbidden directory listing");
        return JSONObject.fromObject(errMsg).toString();
    }

    @RequestMapping(value = "/404", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String missingResource(Exception ex) {
        logger.error("Missing resource", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.NOT_FOUND.toString(), "Missing resource");
        return JSONObject.fromObject(errMsg).toString();
    }

    @RequestMapping(value = "/500", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String uncaughtException(Exception ex) {
        logger.error("Uncaught exception", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Uncaught exception");
        return JSONObject.fromObject(errMsg).toString();
    }

    @RequestMapping(value = "/503", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String unsupportedServletMethod(Exception ex) {
        logger.error("Unsupported servlet method", ex);
        ErrorMsg errMsg = new ErrorMsg(HttpStatus.SERVICE_UNAVAILABLE.toString(), "Unsupported servlet method");
        return JSONObject.fromObject(errMsg).toString();
    }
}
