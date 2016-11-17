import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;

/**
 * Created by YUAN on 2016/11/16.
 */
@Data
public class DoubanBook {

	@ExtractBy("//div[@class='info']/h2/a/text()")
	private String name;


	@ExtractBy("//div[@class='star']/span[@class='rating_nums']/text()")
	private Double score;

	@ExtractBy(value = "//div[@class='star']/span[@class='pl']/text()", notNull = true)
	private String comments;
}