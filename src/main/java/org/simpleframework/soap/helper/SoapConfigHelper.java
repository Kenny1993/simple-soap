package org.simpleframework.soap.helper;

import org.simpleframework.helper.ConfigHelper;
import org.simpleframework.soap.Constant;

/**
 * 获取配置文件信息
 * Created by Why on 2017/3/10.
 */
public final class SoapConfigHelper {
    /**
     * 是否启用日志
     */
    public static boolean isLog(){
        return ConfigHelper.getBoolean(Constant.LOG);
    }
}
