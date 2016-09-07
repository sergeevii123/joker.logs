package joker.demoadditionalinfo;

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
public class AdditionalInfoController {

    private static Random r = new Random();

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String work() throws InterruptedException {
        log.info("start");
        Thread.sleep(500);
        if (r.nextInt(3) == 2) {
            //simulating exception
            log.info("exception");
            return "";
        }
        log.info("done");
        return "additional";
    }

}