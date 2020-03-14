package com.ywl5320.wlmedia.sample.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wanli on 2020/3/10
 */
public class FormatUtil {

    public static String formatHomePage(String html, String uri)
    {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("bx-sya");
        int size = elements.size();
        for(int i = 0; i < size; i++)
        {
            Element element = elements.get(i);
            Elements hd = element.getElementsByClass("hd");
            Element hd1 = hd.get(0);
            buffer.append("{\"type\":\"");
            buffer.append(hd1.text());
            buffer.append("\",\"values\":[");
            Element bd = element.getElementsByClass("bd").get(0);
            Elements a = bd.select("a");
            int s = a.size();
            for(int j = 0; j < s; j++)
            {
                Element aa = a.get(j);
                buffer.append("{\"name\":\"");
                buffer.append(aa.select("i").text());
                buffer.append("\",\"url\":\"");
                buffer.append(uri);
                buffer.append(aa.attr("href"));
                buffer.append("\"");
                if(j == s - 1)
                {
                    buffer.append("}");
                }
                else
                {
                    buffer.append("},");
                }
            }
            if(i == size - 1)
            {
                buffer.append("]}");
            }
            else
            {
                buffer.append("]},");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public static String formatHomePage2(String html, String uri)
    {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("filter-list");
        int size = elements.size();
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for(int i = 0; i < size; i++)
        {
            Elements e = elements.get(i).select("a");
            int s = e.size();
            for(int j = 0; j < s; j++)
            {
                String menu = e.get(j).text();
                if(menu.contains("其他") || menu.contains("科教台") || menu.contains("财经台")|| menu.contains("少儿台")|| menu.contains("影视台")|| menu.contains("新闻台")|| menu.contains("体育台")|| menu.contains("综合台")|| menu.contains("国外电视台"))
                {
                    break;
                }
                buffer.append("{\"name\":\"");
                buffer.append(menu);
                buffer.append("\",\"url\":\"");
                String url = e.get(j).attr("href").replace("../", "");
                buffer.append(uri);
                if(!url.startsWith("/"))
                {
                    buffer.append("/");
                }
                buffer.append(url);
                buffer.append("\"");
                if((i == size - 1) && (j == s - 1))
                {
                    buffer.append("}");
                }
                else
                {
                    buffer.append("},");
                }
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public static String formatHomePage3(String html, String uri)
    {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("p-list-sya");
        int size = elements.size();
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for(int i = 0; i < size; i++)
        {
            Elements e = elements.get(i).select("a");
            int s = e.size();
            for(int j = 0; j < s; j++)
            {
                buffer.append("{\"name\":\"");
                buffer.append(e.get(j).text());
                buffer.append("\",\"url\":\"");
                String url = e.get(j).attr("href").replace("../", "");
                if(!url.startsWith("/"))
                {
                    buffer.append("/");
                }
                buffer.append(uri);
                buffer.append(url);
                buffer.append("\"");
                if((i == size - 1) && (j == s - 1))
                {
                    buffer.append("}");
                }
                else
                {
                    buffer.append("},");
                }
            }
        }
        buffer.append("]");
        return buffer.toString();
    }





    public static String formatLiveMenu(String html)
    {
        Document document = Jsoup.parse(html);
        Elements menus = document.getElementsByClass("tab-list-syb").select("li");
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        int size = menus.size();
        for(int i = 0; i < size; i++)
        {
            String menu = menus.get(i).text();
            buffer.append("{\"name\":\"");
            buffer.append(menu);
            buffer.append("\",\"id\":");
            buffer.append(menus.get(i).attr("data-player"));
            if(i == size - 1)
            {
                buffer.append("}");
            }
            else
            {
                buffer.append("},");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public static String getRealUrl01(String html)
    {
        String f = html.replaceAll(" ", "").replaceAll("\n", "");
        String[] urls = f.split("'");
        for(int i = 0; i < urls.length; i++)
        {
            String url = urls[i];
            String[]u = url.split("\\$");
            if(u.length == 3)
            {
                return u[1];
            }
        }
        return "";
    }

    public static String getRealUrl02(String html)
    {
        String f = html.replaceAll(" ", "").replaceAll("\n", "");
        String[] urls = f.split("'");
        for(int i = 0; i < urls.length; i++)
        {
            String url = urls[i];
            System.out.println(url);
            if(url.startsWith("http") && (url.contains(".flv") || url.contains(".m3u8")))
            {
                return url;
            }
        }

        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("copyright");
        for(Element element : elements)
        {
            Elements ea = element.select("a");
            for(Element eeaa : ea)
            {
                String url = eeaa.attr("href");
                if(url.startsWith("http") && (url.endsWith(".flv") || url.endsWith(".m3u8")))
                {
                    return url;
                }
            }
        }
        return "";
    }

    public static String formatIPTV(String html, String host)
    {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("2u");
        int size = elements.size();
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for(int i = 0; i < size; i++)
        {
            Elements e = elements.get(i).select("a");
            int s = e.size();
            for(int j = 0; j < s; j++)
            {
                if(e.get(j).text().contains("移动"))
                {
                    buffer.append("{\"status\":0,\"name\":\"");
                    buffer.append(elements.get(i).select("p").text());
                    buffer.append("\",\"url\":\"");
                    String url = e.get(j).attr("href").replace("../", "");
                    if(!url.startsWith("/"))
                    {
                        buffer.append("/");
                    }
                    buffer.append(host);
                    buffer.append(url);
                    buffer.append("\"");
                    if((i == size - 1) && (j == s - 1))
                    {
                        buffer.append("}");
                    }
                    else
                    {
                        buffer.append("},");
                    }
                }
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
