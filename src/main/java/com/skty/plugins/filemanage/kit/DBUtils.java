package com.skty.plugins.filemanage.kit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 数据库相关工具类
 *
 * @author zhaoyun
 * @date 2020/8/18 16:01
 */
public abstract class DBUtils {

    private DBUtils() {

    }

    /**
     * 执行数据库相关的一些大型数据的操作。
     * 比如需要删除一张表的中的数据，但是先查出另一张表中的id到内存中，如果一次查询出来，数据量太大将会占用很大的内存
     * <p>
     * 所以这边可以使用这个方法来分页查询出指定的数据，分页进行操作数据
     */
    public static <T> void bigOperation(Supplier<Page<T>> supplier, Consumer<List<T>> consumer) {
        Page<T> page;
        do {
            page = supplier.get();
            List<T> records = page.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                consumer.accept(records);
            }
        } while (page.hasNext());
    }

}
