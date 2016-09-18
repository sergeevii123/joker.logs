package joker.customerinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

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
        return Observable.zip(
                Observable.just(baseInfo.getBaseInfo()),
                Observable.just(additionalInfo.getAdditionalInfo()),
                (a, b) -> a.concat(" ").concat(b)
        )
                .doOnError(e -> log.error("exception with zip ", e))
                .onErrorReturn(e -> "")
                .doOnCompleted(() -> log.info("done"))
                .toBlocking()
                .single();
    }

}
