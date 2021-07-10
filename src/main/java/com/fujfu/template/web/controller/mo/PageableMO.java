package com.fujfu.template.web.controller.mo;

import com.fujfu.template.constant.PageConstants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Beldon
 * @create 2019-04-23 10:27
 */
@Getter
@Setter
public class PageableMO implements Serializable {
    private int pageNumber = PageConstants.DEFAULT_PAGE_NUMBER;
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;
    private String direction;
    private String property;
    private int totalPages;
    private long total;

}
