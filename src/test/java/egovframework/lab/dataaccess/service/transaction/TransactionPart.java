package egovframework.lab.dataaccess.service.transaction;

import java.text.ParseException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.lab.dataaccess.service.EmpService;
import egovframework.lab.dataaccess.service.EmpVO;

/**
 * 트랜잭션 발생 클래스 
 */
@Service("transactionPart")
public class TransactionPart {

	Logger logger = LoggerFactory.getLogger(TransactionPart.class);

	@Resource(name = "empService")
	EmpService empService;

	@Transactional(rollbackFor = Exception.class)
	public void createTransaction() throws Exception { // 트랜잭션 시작, 새로운 트랜잭션 생성

		EmpVO vo = makeVO();

		// insert - Propagation.REQUIRES_NEW => 기존 트랜잭션 보류, if 정상종료 -> 바로 커밋
		empService.insertEmp(vo);
		EmpVO afterInsert = empService.selectEmp(vo);
		logger.debug("###### insert ###### EMP_NO={}, EMP_NAME={}", afterInsert.getEmpNo(), afterInsert.getEmpName());

		// update - Propagation.REQUIRED => 기존 트랜잭션 보류, 정상종료 -> 커밋 보류
		afterInsert.setEmpName("김민수");
		empService.updateEmp(afterInsert);
		EmpVO afterUpdate = empService.selectEmp(afterInsert);
		logger.debug("###### update ###### EMP_NO={}, EMP_NAME={}", afterUpdate.getEmpNo(), afterUpdate.getEmpName());

		logger.debug("###### 예외발생 ######");
		throw new Exception();

		// 스프링 트랜잭션은 unchecked exceptions (subclasses of java.lang.RuntimeException) 만 롤백시키고, checked exception가 발생하면 커밋시킨다.
		// checked exception에 대해서도 롤백을 지정하려면, rollbackFor 속성에 명시적으로 선언해줘야한다.
		// throw new RuntimeException();
	}

	/** 사원정보 생성 */
	public EmpVO makeVO() throws ParseException {
		EmpVO vo = new EmpVO();
		// empNo는 Biz. 서비스 내에서 IDGeneration Service 에 의해 key를 생성하고 설정
		vo.setEmpName("홍길동");
		vo.setJob("개발자");
		return vo;
	}

}
