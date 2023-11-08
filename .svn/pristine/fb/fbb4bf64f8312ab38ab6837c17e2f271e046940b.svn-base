package egovframework.lab.dataaccess.service.transaction;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/context-*" })
public class TransactionTest {

	Logger logger = LoggerFactory.getLogger(TransactionTest.class);

	@Resource(name = "dataSource")
	DataSource dataSource;

	@Resource(name = "transactionPart")
	TransactionPart transactionPart;

	/**
	 * 트랜잭션 종료 후에 실행, propagation 속성값에 따른 Commit/Rollback 여부를 확인하기 위한 로직
	 * 
	 * 시나리오1. insertEmp() 정상 종료 시 무조건 DB에 반영되고(commit), 
	 * 				 updateEmp()는 기존 로직 처리결과에 따라 반영여부 결정 (success->commit, fail->rollback)
	 * 
	 * 시나리오2. insertEmp()는 독립적인 트랜잭션 발생(Propagation.REQUIRES_NEW), 
	 * 				 updateEmp()는 기존 트랜잭션에 포함(Propagation.REQUIRED)
	 * 
	 * 시나리오3. insertEmp() -> updateEmp() -> 예외발생
	 * 
	 * 예상결과4. insertEmp() 결과 Commit, updateEmp() 결과 Rollback
	 * 
	 * */

	@Test
	public void testPropagation() {

		try {

			transactionPart.createTransaction();

		} catch(Exception ex) {

			String sql = "SELECT EMP_NO, EMP_NAME FROM EMP WHERE EMP_NO=8000";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

			if(resultList.size() > 0) {
				for(Map<String, Object> result : resultList) {
					logger.debug("### 사원번호 8000 조회결과: " + result);
					assertEquals("홍길동", result.get("EMP_NAME"));
				}
			} else {
				logger.debug("### 사원번호 8000에 해당하는 레코드가 없습니다. ### insertEmp(), updateEmp() rollback");
			}
		}
	}

}
