package org.simpleframework.soap.helper;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;
import org.simpleframework.helper.ClassHelper;
import org.simpleframework.soap.annotation.Soap;
import org.simpleframework.util.CollectionUtil;
import org.simpleframework.util.ReflectionUtil;
import org.simpleframework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * SOAP 助手类
 * Created by Why on 2017/3/10.
 */
public final class SoapHelper {
    private static final String PREFFIX = "/";
    private static final List<Interceptor<? extends Message>> inInterceptorList;
    private static final List<Interceptor<? extends Message>> outInterceptorList;

    static {
        inInterceptorList = new ArrayList<Interceptor<? extends Message>>();
        outInterceptorList = new ArrayList<Interceptor<? extends Message>>();

        //添加 Logging Interceptor
        if (SoapConfigHelper.isLog()) {
            LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
            inInterceptorList.add(loggingInInterceptor);

            LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
            outInterceptorList.add(loggingOutInterceptor);
        }
    }

    /**
     * 发布 SOAP 服务
     */
    public static void publishService(String wsdl, Class<?> serviceClass, Object serviceBean) {
        ServerFactoryBean factory = new ServerFactoryBean();
        factory.setAddress(wsdl);
        factory.setServiceClass(serviceClass);
        factory.setServiceBean(serviceBean);
        factory.setInInterceptors(inInterceptorList);
        factory.setOutInterceptors(outInterceptorList);
    }

    // 发布所有 SOAP 服务
    public static void publishAllService() {
        Set<Class<?>> soapClassSet = SoapClassHelper.getSoapClassSet();
            if (CollectionUtil.isNotEmpty(soapClassSet)) {
                for (Class<?> cls : soapClassSet) {
                    String wsdl = getAddress(cls);
                    Class<?> serviceClass = getSoapInterfaceClass(cls);
                    Object serviceBean = ReflectionUtil.newInstance(cls);
                    publishService(wsdl, serviceClass, serviceBean);
                }
        }
    }

    private static Class<?> getSoapInterfaceClass(Class<?> cls) {
        return cls.getInterfaces()[0];
    }

    private static String getAddress(Class<?> cls) {
        String wsdl;
        Soap soap = cls.getAnnotation(Soap.class);
        String address = soap.value();
        if (StringUtil.isEmpty(address)) {
            wsdl = getSoapInterfaceClass(cls).getSimpleName().toLowerCase();
        } else {
            wsdl = address;
        }
        if (!wsdl.startsWith(PREFFIX)) {
            wsdl = PREFFIX + wsdl;
        }
        return wsdl.replaceAll("\\" + PREFFIX + "+", PREFFIX);
    }

    /**
     * 创建 SOAP 客户端
     */
    public static <T> T createClient(String wsdl, Class<? extends T> serviceClass) {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setAddress(wsdl);
        factory.setServiceClass(serviceClass);
        factory.setInInterceptors(inInterceptorList);
        factory.setOutInterceptors(outInterceptorList);

        return factory.create(serviceClass);
    }
}
