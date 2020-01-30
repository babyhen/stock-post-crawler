import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.common.util.BrowserUtil;
import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import org.junit.Test;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

public class EastMoneyTest {


    @Test
    public void openBrownse() throws JsonProcessingException {
        String stockCode = "300324";
        String startDate = "20200127";
        String endDate = "20200128";
        //
        EastMoneySpider spider = new EastMoneySpider(stockCode);
        spider.start(TimeUtil.parse(startDate, TIME_FORMAT_8), TimeUtil.parse(endDate, TIME_FORMAT_8));
        System.out.println("finish parse "+stockCode);
    }


}




