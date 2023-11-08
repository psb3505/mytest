package egovframework.lab.dataaccess.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/context-*" })
@Transactional(transactionManager = "txManager")
@Commit
public class EmpServiceTest {

	// TODO [Step 4-1] EmpServiceTest 실행

	@Resource(name = "dataSource")
	DataSource dataSource;

	@Resource(name = "empService")
	EmpService empService;

	@Before
	public void onSetUp() throws Exception {
		// 편의상 각 테스트 메서드 수행 전에
		// 외부의 스크립트 파일(sample_schema_hsql.sql)로 DB를 초기화하도록 설정
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("META-INF/testdata/sample_schema_hsql.sql"));
	}

	/**
	 * 사원정보 생성
	 * 
	 * @throws ParseException
	 */
	public EmpVO makeVO() throws ParseException {
		EmpVO vo = new EmpVO();

		// empNo는 Biz. 서비스 내에서 IDGeneration Service 에 의해 key를 생성하고 설정
		vo.setEmpName("홍길동");
		vo.setJob("개발자");
		vo.setMgr(new BigDecimal(7903));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
		vo.setHireDate(sdf.parse("2009-07-10"));
		vo.setSal(new BigDecimal(2000));
		vo.setComm(new BigDecimal(0));
		vo.setDeptNo(new BigDecimal(20));

		return vo;
	}

	public void checkResult(EmpVO vo, EmpVO resultVO) {
		assertNotNull(resultVO);
		assertEquals(vo.getEmpNo(), resultVO.getEmpNo());
		assertEquals(vo.getEmpName(), resultVO.getEmpName());
		assertEquals(vo.getJob(), resultVO.getJob());
		assertEquals(vo.getMgr(), resultVO.getMgr());
		assertEquals(vo.getHireDate(), resultVO.getHireDate());
		assertEquals(vo.getSal(), resultVO.getSal());
		assertEquals(vo.getComm(), resultVO.getComm());
		assertEquals(vo.getDeptNo(), resultVO.getDeptNo());
	}

	/** 사원정보 입력 */
	@Test
	public void testInsertEmp() throws Exception {
		EmpVO vo = makeVO();

		// insert
		BigDecimal empNo = empService.insertEmp(vo);
		vo.setEmpNo(empNo);

		// select
		EmpVO resultVO = empService.selectEmp(vo);

		// check
		checkResult(vo, resultVO);
	}

	/** 사원정보 수정 */
	@Test
	public void testUpdateEmp() throws Exception {
		EmpVO vo = makeVO();

		// insert
		BigDecimal empNo = empService.insertEmp(vo);
		vo.setEmpNo(empNo);

		// data change
		vo.setEmpName("홍길순");
		vo.setJob("설계자");

		// update
		empService.updateEmp(vo);

		// select
		EmpVO resultVO = empService.selectEmp(vo);

		// check
		checkResult(vo, resultVO);
	}

	/** 사원정보 삭제 */
	@Test
	public void testDeleteEmp() throws Exception {
		EmpVO vo = makeVO();

		// insert
		BigDecimal empNo = empService.insertEmp(vo);
		vo.setEmpNo(empNo);

		// delete
		empService.deleteEmp(vo);

		// select
		try {
			@SuppressWarnings("unused")
			EmpVO resultVO = empService.selectEmp(vo);
			fail("EgovBizException 이 발생해야 합니다.");
		} catch (Exception e) {
			assertNotNull(e);
			// 여기서는 비지니스 단에서 명시적으로 exception 처리하였음
			// AbstractServiceImpl 을 extends 하고
			// processException("info.nodata.msg"); 과 같이 메서드 콜 형태로 처리
			assertTrue(e instanceof EgovBizException);
			assertEquals("info.nodata.msg", ((EgovBizException) e).getMessageKey());
			assertEquals("해당 데이터가 없습니다.", e.getMessage());
		}
	}

	/** 사원정보 목록조회 */
	@Test
	public void testSelectEmpList() throws Exception {
		EmpVO vo = makeVO();

		// insert
		BigDecimal empNo = empService.insertEmp(vo);
		vo.setEmpNo(empNo);

		// 검색조건으로 empNo 설정
		EmpVO searchVO = new EmpVO();
		searchVO.setEmpNo(vo.getEmpNo());

		// selectList
		List<EmpVO> resultList = empService.selectEmpList(searchVO);

		// empNo 조건에 대한 결과는 1건일 것임
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals(1, resultList.size());
		checkResult(vo, resultList.get(0));

		// 검색조건으로 empName 설정 - '%' || #{empName} || '%'
		EmpVO searchVO2 = new EmpVO();
		searchVO2.setEmpName(""); // '%' || '' || '%' // --> '%%'

		// selectList
		List<EmpVO> resultList2 = empService.selectEmpList(searchVO2);

		// like 조건에 대한 결과는 1건 이상일 것임
		assertNotNull(resultList2);
		assertTrue(resultList2.size() > 0);
	}

}
