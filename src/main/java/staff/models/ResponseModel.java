package staff.models;

import lombok.ToString;

@lombok.Getter
@lombok.Setter
@ToString
public class ResponseModel<T> implements java.io.Serializable {
    private Integer status;
    private T dataObj;
}
