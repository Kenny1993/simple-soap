package org.simpleframework.soap;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.simpleframework.soap.helper.SoapHelper;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;

/**
 * SOAP Servlet
 * Created by Why on 2017/3/10.
 */
@WebServlet(urlPatterns = Constant.SERVLET_URL_PATTERN,loadOnStartup = 0)
public class SoapServlet extends CXFNonSpringServlet{
    @Override
    protected void loadBus(ServletConfig sc) {
        // 初始化 CXF 总线
        super.loadBus(sc);
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        // 发布所有 SOAP 服务
        SoapHelper.publishAllService();
    }
}
