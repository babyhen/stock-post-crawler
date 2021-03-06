import com.fasterxml.jackson.core.JsonProcessingException;
import com.pawpaw.framework.core.common.util.TimeUtil;
import com.pawpaw.stock.post.eastmoney.EastMoneySpider;
import org.junit.Test;

import java.util.Date;

import static com.pawpaw.framework.core.common.util.TimeUtil.TIME_FORMAT_8;

public class EastMoneyTest {


    @Test
    public void openBrownse() throws JsonProcessingException {
        String stockCode = "300324";
        String startDate = "20200127";
        String endDate = "20200128";
        Date begin = TimeUtil.parseDate(startDate, TIME_FORMAT_8);
        Date end = TimeUtil.parseDate(endDate, TIME_FORMAT_8);
        EastMoneySpider spider = new EastMoneySpider(stockCode,begin,end);
       spider.start();
        System.out.println("finish parse " + stockCode);
    }


}




