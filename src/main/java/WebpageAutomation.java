import com.example.Learning.ScrapData;
import com.example.Learning.ScrapDataCsv;
import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;


public class WebpageAutomation {
    public static void main(String[] args) throws InterruptedException {
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\siddh\\Downloads\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        LinkedHashMap<Integer, List<String>> pageLinks = new LinkedHashMap<>();
        ArrayList<String> links = null;
        for (int i = 1; i < 3; i++) {
            links = new ArrayList<>();
            driver.get("https://himachaltourism.gov.in/accommodation-result/?cat_id=2&district=select&tehsil&submit&cpage=" + i);

            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            for (WebElement link : allLinks)
            {
                String hyperlink = link.getAttribute("href");
                if (hyperlink != null && hyperlink.contains("single-user-result?id="))
                {
                    links.add(hyperlink);
                }

                List<WebElement> allLinks1 = driver.findElements(By.tagName("homestay-Data"));
                for (WebElement link1 : allLinks1)
                {
                    String hyperlink1 = link1.getAttribute("href");
                    if (hyperlink1 != null && hyperlink.contains("single-user-result?id="))
                    {
                        links.add(hyperlink1);
                    }

                    pageLinks.put(i, links);
            }
        }
        System.out.println(" --------------------- LINKS ----------------------------");
        System.out.println(String.join(", ", pageLinks.values().stream().map(Object::toString).toList()));
        Set<String> allLinks = pageLinks.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        readData(allLinks);
       /* allLinks.forEach(data->{
            scrapHotelData(data);
        });*/

        createCsv(allLinks);
        driver.close();

    }

    public static void createCsv(Set<String> list) {
        try{
            File file=new File("/home/jyoti/Desktop/ScrapData.csv");
            FileWriter outputFile=new FileWriter(file);
            CSVWriter writer=new CSVWriter(outputFile);
            String[] header={"link"};
            writer.writeNext(header);
            list.forEach(s ->
            {
                String[] strArray=new String[] {s};
                writer.writeNext(strArray);
            });
           /* list.forEach(scrapData->{
              String[] strArray=  new String[] {scrapData};
              strings.add(strArray);

               *//* writer.writeNext(Collections.singletonList(scrapData).toArray(String[]::new));*//*
            });*/
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static boolean getNextPageLinks(WebDriver driver) {
        //Get the current page number, = 1
        //build next page number, = Current page + 1
        //check if next page number exists, if not return false, check class present on page
        //click on next page
        String current = driver.findElement(By.className("current")).getText();
        if(current!=null) {
            int currentPageNum = Integer.parseInt(current);
            int nextPageNum = currentPageNum + 1;
            var optionalNextPage = driver.findElements(By.className("hppagination")).stream().filter(webElement -> webElement.getText() .equalsIgnoreCase(""+nextPageNum)).findAny();
            if(optionalNextPage.isPresent()) {
                WebElement webElement = optionalNextPage.get();
                webElement.click();
                List<WebElement> allLinks = driver.findElements(By.tagName("a"));
                List<String> results = new ArrayList<>();
                for (WebElement link : allLinks) {
                    String hyperlink = link.getAttribute("href");
                    if (hyperlink != null && hyperlink.contains("single-user-result?id=")) {
                        results.add(hyperlink);
                    }
                }
                results.forEach(s -> {
                    System.out.println("All results below: " + s);
                });
                System.out.println(results);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static List<ScrapData> readData(Set<String> url) {

        var res = url.stream().map(WebpageAutomation::scrapHotelData).toList();
        ScrapDataCsv.createCsv(res);
        return res;
    }


    private static ScrapData scrapHotelData(String url) {
        int i=1;
        ScrapData scrapData=new ScrapData();
        scrapData.setId(i++);
        try {
            Document doc = Jsoup.connect(url).get();
            doc.select(".singleproptitle").forEach(element -> {
                scrapData.setTitle(element.text());
                scrapData.setParam("name", element.ownText());
            });
            doc.select(".propinfo > div").forEach(element -> {
                String key = element.select("span").text();
                if (!key.trim().isBlank()) {
                    String value = element.ownText();
                    if (value.isBlank()) {
                        var anchorElement = element.select("a");
                        if (anchorElement.hasText()) {
                            value = anchorElement.text();
                        }
                    }
                    scrapData.setParam(key, value);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        /*createCsv(url);*/
        return scrapData;
    }


}

