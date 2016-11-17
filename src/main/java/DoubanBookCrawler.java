import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by YUAN on 2016/11/16.
 */
public class DoubanBookCrawler {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("DoubanBook.xlsx");
		XSSFWorkbook workBook = new XSSFWorkbook();
		int j =0;
		XSSFSheet sheet =workBook.createSheet("DoubanSheet");

		OOSpider ooSpider = OOSpider.create(Site.me().setSleepTime(100), DoubanBook.class);
		ooSpider.thread(5);
		ooSpider.run();
		List<DoubanBook> doubanBooks =new ArrayList<>();
		//single download
		for (int i = 0; i < 1000; i++) {
			DoubanBook doubanBook = ooSpider.get("https://book.douban.com/subject_search?start="+i+"&search_text=%E4%BA%92%E8%81%94%E7%BD%91%20%E7%BC%96%E7%A8%8B%20%E7%AE%97%E6%B3%95");
			if(doubanBook!=null){
			if("(少于10人评价)".equals(doubanBook.getComments().trim())||"(目前无人评价)".equals(doubanBook.getComments().trim())){
				continue;
			}
			doubanBooks.add(doubanBook);
			}else {
				break;
			}
		};
		doubanBooks.sort(Comparator.comparing(DoubanBook::getScore).reversed());
		for (DoubanBook d:doubanBooks ) {
			System.out.println(d);

			XSSFRow row = sheet.createRow(j);
			XSSFCell cell0 = row.createCell(0);
			XSSFCell cell1 = row.createCell(1);
			XSSFCell cell2 = row.createCell(2);
			XSSFCell cell3 = row.createCell(3);
			cell0.setCellValue(++j);
			cell1.setCellValue(d.getName());
			cell2.setCellValue(d.getScore());
			cell3.setCellValue(d.getComments());

		}

		workBook.write(fos);






	}

}
