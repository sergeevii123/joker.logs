package joker.demodashboardapi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
//@FeignClient(url="${additionalInfo}", name= "additionalinfo")
@FeignClient("TransactionsList")
public interface TransactionsList {
    @RequestMapping(value = "/getTransactions", method = RequestMethod.GET)
    String getTransactions();
}
