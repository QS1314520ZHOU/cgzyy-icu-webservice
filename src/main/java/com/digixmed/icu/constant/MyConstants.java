package com.digixmed.icu.constant;

public class MyConstants {
    public static int successFlag = -1;
    public static String returnMessage = "";
    public static String returnData = "";
    public static final String FLAG_VALID = "valid";
    public static final String FLAG_IN_VALID = "invalid";
    public static final String HOSPITALCODE_DEFAULT = "BASELINEHOSPITAL";
    public static final String HOSPITALNAME_DEFAULT = "基线医院";
    public static final String DEPTCODE_DEFAULT = "SHOUSHUMAZUIDEPT";
    public static final String DEPTNAME_DEFAULT = "手术麻醉科";
    public static final String DONG_HUA = "donghua";
    public static final String backAddress = "https://10.120.120.202:1443/csp/hsb/DHC.Published.RICUSystems.BS.RICUSystems.CLS?WSDL=1";
    public static final String messageBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dhcc=\"http://www.dhcc.com.cn\">\n   <soapenv:Header/>\n   <soapenv:Body>\n      <dhcc:HIPMessageServer>\n         <!--Optional:-->\n         <dhcc:action>ServiceCode</dhcc:action>\n         <!--Optional:-->\n         <dhcc:message><![CDATA[messageBody]]></dhcc:message>\n      </dhcc:HIPMessageServer>\n   </soapenv:Body>\n</soapenv:Envelope>";
}