package party.ciao.blogs.common;

import lombok.Data;

import java.util.HashMap;

// 分页
@Data
public class QueryPageParam {

    // 默认
    private static int PAGE_SIZE = 5;
    private static int Page_NUM = 1;

    private int pageSize = PAGE_SIZE;
    private int pageNum = Page_NUM;

    private HashMap param = new HashMap<>();
}
