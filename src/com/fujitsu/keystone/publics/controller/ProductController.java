/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.service.impl.CoreService;
import com.fujitsu.keystone.publics.service.impl.ProductService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class ProductController extends BaseController {
    @Resource
    CoreService coreService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/product/list/{status}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String getProductList(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable int status,
                                 @RequestParam(value = "groupId", required = false, defaultValue = "0") String groupId,
                                 @RequestParam(value = "orderBy", required = false, defaultValue = "price") String orderBy,
                                 @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                 @RequestParam(value = "minPrice", required = false, defaultValue = "-") String minPrice,
                                 @RequestParam(value = "maxPrice", required = false, defaultValue = "-") String maxPrice
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        Map<String, String> filter = new HashMap<String, String>();
        filter.put("minPrice", minPrice);
        filter.put("maxPrice", maxPrice);
        filter.put("groupId", groupId);
        filter.put("orderBy", orderBy);
        filter.put("sort", sort);

        JSONObject resp = productService.getProductList(request, status, filter);

        return JSONObject.fromObject(resp).toString();
    }

    @RequestMapping(value = "/product/query/{productId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String getProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String productId) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {

        JSONObject resp = productService.getProduct(request, productId);

        return resp.toString();
    }

    @RequestMapping(value = "/product/group/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String getProductGroupList(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {

        JSONObject resp = productService.getProductGroupList();

        return resp.toString();
    }

    @RequestMapping(value = "/product/group/query/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String getProductGroupDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable String groupId) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {

        JSONObject resp = productService.getProductGroupDetail(groupId);

        return resp.toString();
    }
}
