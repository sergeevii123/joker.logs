package joker.customerinfo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("additionalinfo")
public interface AdditionalInfo {
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    String getAdditionalInfo();
}
