package joker.demodashboardapi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
//@FeignClient(url="${baseInfo}", name="baseinfo")
@FeignClient("CustomerInfo")
public interface CustomerInfo {
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    String getCustomerInfo();
}
