package ml.socshared.adapter.fb.domain.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page<T> {
    private Integer totalCount;
    private Integer page;
    private Integer size;
    private Boolean hasPrev;
    private Boolean hasNext;
    private List<T> objects;
}
