package com.digixmed.icu.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YhylResponseUtilTest {

    @Test
    void testBasicStructure() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse("MSG001", "SUCCESS", "ACCEPTED", "数据已接收");
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();

        assertEquals("YHYL", root.getName());
        assertEquals("MSG001", root.attributeValue("MessageId"));

        Element result = root.element("Result");
        assertNotNull(result);
        assertEquals("SUCCESS", result.attributeValue("status"));
        assertEquals("ACCEPTED", result.attributeValue("subStatus"));
        assertEquals("数据已接收", result.attributeValue("message"));
    }

    @Test
    void testCreateTimeFormat() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse("", "SUCCESS", "SUCCESS", "ok");
        Document doc = DocumentHelper.parseText(xml);
        String createTime = doc.getRootElement().element("Result").attributeValue("createTime");

        // 必须是 14 位纯数字 yyyyMMddHHmmss
        assertNotNull(createTime);
        assertEquals(14, createTime.length());
        assertTrue(createTime.matches("\\d{14}"), "createTime 应为14位数字，实际: " + createTime);
    }

    @Test
    void testXmlDeclaration() {
        String xml = YhylResponseUtil.buildYhylResponse("X", "S", "S", "m");
        assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"),
                "应包含 XML 声明");
    }

    @Test
    void testMessageIdFallback() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse(null, "FAILED", "FAILED", "err");
        Document doc = DocumentHelper.parseText(xml);
        assertEquals("", doc.getRootElement().attributeValue("MessageId"));

        xml = YhylResponseUtil.buildYhylResponse("", "FAILED", "FAILED", "err");
        doc = DocumentHelper.parseText(xml);
        assertEquals("", doc.getRootElement().attributeValue("MessageId"));
    }

    @Test
    void testSpecialCharEscape() throws Exception {
        String rawMsg = "含特殊字符: & < > \" ' 和[{}]=";
        String xml = YhylResponseUtil.buildYhylResponse("ID1", "SUCCESS", "SUCCESS", rawMsg);

        // XML 必须能正常解析（不因特殊字符崩溃）
        Document doc = DocumentHelper.parseText(xml);
        Element result = doc.getRootElement().element("Result");

        // 解析后应还原为原始文本
        assertEquals(rawMsg, result.attributeValue("message"));
    }

    @Test
    void testAmpersandEscape() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse("A&B", "SUCCESS", "SUCCESS", "a&b");
        // 原始 XML 字符串中应包含转义后的 &amp;
        assertTrue(xml.contains("A&amp;B"), "MessageId 中的 & 应被转义");
        assertTrue(xml.contains("a&amp;b"), "message 中的 & 应被转义");

        // 解析后还原
        Document doc = DocumentHelper.parseText(xml);
        assertEquals("A&B", doc.getRootElement().attributeValue("MessageId"));
        assertEquals("a&b", doc.getRootElement().element("Result").attributeValue("message"));
    }

    @Test
    void testAngleBracketEscape() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse("", "SUCCESS", "SUCCESS", "异常<EOF>");
        assertTrue(xml.contains("&lt;EOF&gt;"), "< > 应被转义");

        Document doc = DocumentHelper.parseText(xml);
        assertEquals("异常<EOF>", doc.getRootElement().element("Result").attributeValue("message"));
    }

    @Test
    void testQuoteEscape() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse("", "SUCCESS", "SUCCESS", "他说\"你好\"");
        assertTrue(xml.contains("&quot;"), "双引号应被转义为 &quot;");

        Document doc = DocumentHelper.parseText(xml);
        assertEquals("他说\"你好\"", doc.getRootElement().element("Result").attributeValue("message"));
    }

    @Test
    void testNullMessage() throws Exception {
        String xml = YhylResponseUtil.buildYhylResponse("X", "ERR", "ERR", null);
        Document doc = DocumentHelper.parseText(xml);
        assertEquals("", doc.getRootElement().element("Result").attributeValue("message"));
    }
}
