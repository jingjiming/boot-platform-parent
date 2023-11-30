package com.css.common.util;

import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * Created by jiming.jing on 2022/11/8
 */
public class ReadDocUtil {

    /**
     * 读取Doc文档
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readDoc(InputStream is) throws Exception {
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        int paraNum = range.numParagraphs();

        for (int i = 0; i < paraNum; i++) {
            String paragraph = range.getParagraph(i).text().trim();
        }

        return doc.getDocumentText();
    }

    public static String readDocx(InputStream is) throws Exception {
        XWPFDocument docx = new XWPFDocument(is);
        // 获取所有段落
        List<XWPFParagraph> paragraphs = docx.getParagraphs();
        int paraNum = paragraphs.size();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < paraNum; i++) {
            String paragraph = paragraphs.get(i).getParagraphText().trim();
            content.append(paragraph);

        }
        return content.toString();
    }

    /**
     * word 转 html
     * @param inputStream
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws IOException
     */
    public static String docToHtml(InputStream inputStream) throws ParserConfigurationException, TransformerException, IOException {
        HWPFDocument document = new HWPFDocument(inputStream);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.processDocument(document);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream os = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(os);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer serializer = transformerFactory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        String html = baos.toString();
        return html;
    }

    /**
     * docx 转 html
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static String docxToHtml(InputStream inputStream) throws Exception {
        XWPFDocument document = new XWPFDocument(inputStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XHTMLOptions options = XHTMLOptions.create();
        XHTMLConverter.getInstance().convert(document, baos, options);
        return baos.toString();
    }
}
