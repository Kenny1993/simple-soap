package org.simpleframework.soap.helper;

import org.simpleframework.helper.ClassHelper;
import org.simpleframework.soap.annotation.Soap;

import java.util.Set;

/**
 * SOAP 类助手类
 * Created by Why on 2017/3/10.
 */
public final class SoapClassHelper {
    private static final Set<Class<?>> SOAP_CLASS_SET;

    static {
        SOAP_CLASS_SET = ClassHelper.getClassSetByAnnotation(Soap.class);
    }

    public static Set<Class<?>> getSoapClassSet() {
        return SOAP_CLASS_SET;
    }
}
