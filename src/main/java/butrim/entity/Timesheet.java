package butrim.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="timesheet")
@Getter
@Setter
@FieldNameConstants
public class Timesheet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer employee_id;
    private Integer task_id;
    private Date date_start;
    private Date date_end;
    public Timesheet getTask() {
        return this;
    }
}
