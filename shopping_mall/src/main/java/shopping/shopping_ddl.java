package shopping;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("mall")
public class shopping_ddl {
	@Resource(name="Template2")
	private SqlSessionTemplate tm3;
}
