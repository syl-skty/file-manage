package com.skty.plugins.filemanage;

import com.skty.plugins.filemanage.db.entity.Directory;
import com.skty.plugins.filemanage.db.mapper.DirectoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@SpringBootTest
@WebAppConfiguration
public class DaoTest extends BaseTest {
    @Autowired
    private DirectoryMapper directoryMapper;

    @Test
    void testDao() {
        List<Directory> allDir = directoryMapper.getAllDir();
        int size = allDir.size();
        System.out.println(size);
    }
}
