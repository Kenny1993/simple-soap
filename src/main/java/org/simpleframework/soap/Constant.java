package org.simpleframework.soap;

/**
 * SOAP 配置常量
 * Created by Why on 2017/3/10.
 */
public interface Constant {
    /**
     * SOAP Servlet 拦截路径
     */
    String SERVLET_URL_PATTERN = "/soap/*";

    /**
     * 是否启用日志
     */
    String LOG = "simple.soap.log";
}
