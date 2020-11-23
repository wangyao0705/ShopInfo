package shopDate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ShopDates {
	public static void main(String[] args) throws MalformedURLException, IOException {

		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			//URL を指定したWeb ページを解析
			String url = "https://tabelog.com/rstLst/chinese/" + i + "/";

			Document document = Jsoup.parse(new URL(url), 30000);
			Elements elements = document.getElementsByClass("list-rst__rst-data");

			for (Element el : elements) {
				//店舗URL を取り出したWeb ページを解析
				String url2 = el.getElementsByClass("list-rst__rst-name-target cpy-rst-name").eq(0).attr("href");
				Document document1 = Jsoup.parse(new URL(url2), 30000);
				Element element1 = document1.getElementById("rst-data-head");
				Elements elements1 = element1.getElementsByClass("c-table c-table--form rstinfo-table__table").eq(0);
				for (Element ele : elements1) {
					String name = ele.getElementsByTag("td").eq(0).text();
					String tel = ele.getElementsByTag("td").eq(2).text();
					String address = ele.getElementsByTag("td").eq(4).eq(0).toggleClass("listlink").text();
					list.add(name + "," + tel + "," + address.substring(0, address.lastIndexOf("大きな地図を見る 周辺のお店を探す")));
				}
			}
		}

		ShopDB db = new ShopDB();
		try {
			db.getDBcon();
			for (String str : list) {
				String[] strs = str.split(",");
				Shop shop = new Shop();
				shop.setName(strs[0]);
				shop.setTel(strs[1]);
				shop.setAddress(strs[2]);
				db.insertShopDate(shop);
			}
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.closeDb();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
