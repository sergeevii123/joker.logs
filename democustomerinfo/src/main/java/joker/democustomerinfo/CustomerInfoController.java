package joker.democustomerinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by ilyasergeev on 16/08/16.
 */
@Slf4j
@RestController
public class CustomerInfoController {

    private static Random r = new Random();

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String getInfo() throws InterruptedException {
        log.info("start");
        if (r.nextInt(3) == 1) {
            //simulating exception
            log.info("exception");
            return "";
        }
        Thread.sleep(500);
        log.info("done");
        return "customer info";
    }

}
