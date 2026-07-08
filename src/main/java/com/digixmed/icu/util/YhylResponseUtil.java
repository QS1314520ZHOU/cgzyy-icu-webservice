package com.digixmed.icu.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * YHYL XML 统一响应构建工具
 * 所有推送响应统一为：
 * <?xml version="1.0" encoding="UTF-8"?>
 * <YHYL MessageId="">
 *     <Result createTime="yyyyMMddHHmmss" status="..." subStatus="..." message="..."/>
 * </YHYL>
 */
public class YhylResponseUtil {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 构建 YHYL XML 响应
     *
     * @param messageId 请求 XML 的 MessageId，解析不到时传 ""
     * @param status    主状态
     * @param subStatus 细分状态
     * @param message   结果/原因文本
     * @return 完整 XML 字符串
     */
    public static String buildYhylResponse(String messageId, String status, String subStatus, String message) {
        String createTime = LocalDateTime.now().format(FMT);
        String safeMsg = xmlAttrEscape(message != null ? message : "");
        String safeId = xmlAttrEscape(messageId != null ? messageId : "");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<YHYL MessageId=\"" + safeId + "\">\n"
                + "\t<Result createTime=\"" + createTime
                + "\" status=\"" + status
                + "\" subStatus=\"" + subStatus
                + "\" message=\"" + safeMsg + "\"/>\n"
                + "</YHYL>\n";
    }

    /**
     * XML 属性值转义
     */
    private static String xmlAttrEscape(String s) {
        if (s == null) return "";
        return s
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
