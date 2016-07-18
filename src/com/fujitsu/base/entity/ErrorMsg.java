/**
 *
 */
package com.fujitsu.base.entity;


/**
 * @author Barrie
 */
public class ErrorMsg extends BaseEntity {
    private String errcode;
    private String errmsg;
    private Object data;

    public ErrorMsg() {
    }

    public ErrorMsg(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public ErrorMsg(String errcode, String errmsg, Object data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
