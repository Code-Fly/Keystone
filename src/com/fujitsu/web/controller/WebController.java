/**
 *
 */
package com.fujitsu.web.controller;

import com.fujitsu.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/web")
public class WebController extends BaseController {


    @RequestMapping(value = {"{page}"})
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                        @PathVariable String page
    ) {
        return "/web/" + page;
//        return "redirect:http://www.baidu.com";
    }
}
