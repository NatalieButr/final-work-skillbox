package butrim.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="positions")
@Getter
@Setter
@FieldNameConstants
public class Position implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer position_id;
        private String name;
        private Integer rate;
}