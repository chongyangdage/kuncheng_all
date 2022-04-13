package com.kun.oa.util.JsonResult;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
/*** 响应结果类 * @param <E> 响应数据的类型 */
public class JsonResult<E> implements Serializable {
    public JsonResult(Integer state, String message, E data) {
        this.state = state;
        this.message = message;
        this.data = data;

    }

    @Override
    public int hashCode() {

        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println(111);
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println(222);
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    /** 状态码 */
    private Integer state;
    /** 状态描述信息 */
    private String message=null;
    /** 数据 */
    private E data=null;
    public JsonResult() {
        super();
    }
    public JsonResult(Integer state) {
        super();
        this.state = state;
    }
    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }
    public JsonResult(Integer state, E data) {
        super();
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {

        return "JsonResult{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
