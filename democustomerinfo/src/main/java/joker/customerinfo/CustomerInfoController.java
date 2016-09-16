package joker.customerinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilyasergeev on 16/08/16.
 */
@Slf4j
@RestController
public class CustomerInfoController {

    @Autowired
    private BaseInfo baseInfo;

    @Autowired
    private AdditionalInfo additionalInfo;

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String getInfo() {
        log.info("start");
        baseInfo.getBaseInfo();
        additionalInfo.getAdditionalInfo();
        log.info("done");
        return "done";
    }

}
