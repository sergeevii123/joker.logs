package jocker.analyser;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * Created by ilyasergeev on 17/08/16.
 */
@Data
public class WorkRecord {
    private Date timestamp;
    private String message;
}
