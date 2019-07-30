package codeaction.eden.virecg.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 单元测试夹具 即在单元测试执行时启动spring上下文容器，以便使用容器的依赖注入等功能 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@ActiveProfiles("dev")
//@Transactional
//@Rollback(true)
public class SpringJUnit4Base {
  @Test
  public void test() {}
}
