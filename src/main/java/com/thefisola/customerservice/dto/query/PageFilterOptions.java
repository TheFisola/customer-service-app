package com.thefisola.customerservice.dto.query;

import com.thefisola.customerservice.constant.CommonConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageFilterOptions implements Serializable {
    private int pageNumber = CommonConstants.DEFAULT_PAGE_NUMBER;
    private int pageSize = CommonConstants.DEFAULT_PAGE_SIZE;
}
