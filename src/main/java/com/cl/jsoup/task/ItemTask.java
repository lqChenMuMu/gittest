package com.cl.jsoup.task;

import com.cl.jsoup.Utils.HttpUtils;
import com.cl.jsoup.pojo.JdItem;
import com.cl.jsoup.service.JdItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private JdItemService jdItemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.def.0.V09--12s0%2C38s0%2C97s0&wq=shouji&s=113&click=0&page=";
        for (int i = 0; i < 10; i += 2) {
            String html = httpUtils.doGet(url+i);
            this.parse(html);
        }

        System.out.println("京东手机数据抓取成功！");
    }

    //解析页面，保存到数据库
    private void parse(String html) throws JsonProcessingException {
        Document doc = Jsoup.parse(html);
        Elements spuElements = doc.select("div#J_goodsList > ul > li");
        for (Element element : spuElements) {
            long spu = Long.parseLong(element.attr("data-spu"));


            Elements skuElements = element.select("li.ps-item");
            for (Element skuElement : skuElements) {
                String skuId = skuElement.select("[data-sku]").attr("data-sku");
                JdItem item = jdItemService.findBySkuId(Long.valueOf(skuId));
                if (null == item) {
                    JdItem jdItem = new JdItem();
                    jdItem.setSku(Long.valueOf(skuId));
                    jdItem.setSpu(spu);
                    String url = "https://item.jd.com/" + skuId + ".html";
                    jdItem.setUrl(url);
                    String picSuffix = skuElement.select("img[data-sku]").first().attr("data-lazy-img");
                    if (StringUtil.isBlank(picSuffix)) {
                        picSuffix = skuElement.select("img[data-sku]").first().attr("data-lazy-img-slave");
                    }
                    String pic = "https:" + picSuffix;
                    pic = pic.replace("/n9/", "/n1/");
                    String image = httpUtils.doGetImage(pic);
                    jdItem.setPic(image);

                    String priceJson = httpUtils.doGet("https://p.3.cn/prices/mgets?skuIds=J_"+skuId);
                    Double price = Double.valueOf(MAPPER.readTree(priceJson).get(0).get("p").textValue());
                    jdItem.setPrice(price);

                    Document document = Jsoup.parse(httpUtils.doGet(url));
                    String title = document.select(".sku-name").text();
                    jdItem.setTitle(title);

                    jdItem.setCreated(new Date());
                    jdItem.setUpdated(new Date());

                    jdItemService.insert(jdItem);
                }
            }
        }
    }
}
