package com.pantanal.read.server;

import com.pantanal.read.server.bll.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

  @Resource
  private BookService bookService;

  @Test
  public void testImport() {
   bookService.importBook();
  }
}
