package joker.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilyasergeev on 16/08/16.
 */
@Slf4j
@RestController
public class DoSomeWork {

    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public void work() throws InterruptedException {
        log.info("start");
        Thread.sleep(500);
        log.info("done");
    }

}
