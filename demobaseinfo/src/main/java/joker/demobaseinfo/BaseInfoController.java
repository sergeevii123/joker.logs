package joker.demobaseinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilyasergeev on 16/08/16.
 */
@Slf4j
@RestController
public class BaseInfoController {

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String getInfo() throws InterruptedException {
        log.info("start");
        Thread.sleep(500);
        log.info("done");
        return "base";
    }

}
