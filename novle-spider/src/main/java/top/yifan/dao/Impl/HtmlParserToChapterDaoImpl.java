package top.yifan.dao.Impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.yifan.dao.HtmlParserDao;
import top.yifan.entity.Chapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * html 解析实现类，将解析的数据封装成 Chapter 对象
 *
 * @author star
 **/
public class HtmlParserToChapterDaoImpl implements HtmlParserDao<Map<String, Object>> {

    @Override
    public Map<String, Object> parserHtmlSource(String htmlSource) {
        // 创建章节 map 集合
        Map<String, Object> chapterMap = new HashMap<>();
        // 将字符串解析成 document 对象
        Document document = Jsoup.parse(htmlSource);
        // 获取 meta 标签
        Element metaElement = document.selectFirst("meta[property='og:novel:status']");
        chapterMap.put("status", metaElement.attr("content"));
        // 获取所有包含章节的标签元素
        Elements elements = document.select("div#list dl a");
        // 创建集合，存放章节对象
        LinkedList<Chapter> chapterList = new LinkedList<>();
        // 章节对象
        Chapter chapter = null;
        for (Element element : elements) {
            chapter = new Chapter();
            chapter.setTitle(element.text());
            chapter.setChapterUrl(element.attr("href"));
            // 添加到集合
            chapterList.add(chapter);
        }
        chapterMap.put("chapters", chapterList);
        return chapterMap;
    }

}
