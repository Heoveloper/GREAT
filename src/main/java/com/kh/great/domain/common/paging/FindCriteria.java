
package com.kh.great.domain.common.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindCriteria extends PageCriteria {
    private String searchType;  //검색 유형
    private String keyword;     //검색어

    public FindCriteria(RecordCriteria rc, int pageCount) {
        super(rc, pageCount);
    }
}
