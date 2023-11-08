package egovframework.lab.dataaccess.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.lab.dataaccess.service.EmpService;
import egovframework.lab.dataaccess.service.EmpVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

@Service("empService")
public class EmpServiceImpl extends EgovAbstractServiceImpl implements EmpService {

	// TODO [Step 2-1] EmpServiceImpl 추가 작성	
    @Resource(name = "empDAO")
    EmpDAO empDAO;

	// TODO [Step 4-2] EmpServiceImpl의 주석 EmpMapper -> EmpDAO로 변경 후 다시 테스트

    // TODO [Step 3-1] EmpServiceImpl 추가 작성
    //@Resource(name = "empMapper")
    //EmpMapper empDAO;

 	@Resource(name = "primaryTypeSequenceIds")
    EgovIdGnrService egovIdGnrService; // primaryTypeSequenceIds 는 Sequence 기반으로 key 생성

	// insert Emp
    //@Transactional(value="txManager", propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    @Transactional(value="txManager", propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public BigDecimal insertEmp(EmpVO empVO) throws Exception {

		// IDGeneration Service 를 사용하여 key 생성
		BigDecimal generatedEmpNo = egovIdGnrService.getNextBigDecimalId();
		egovLogger.debug("EmpServiceImpl.insertEmp - generated empNo : " + generatedEmpNo);

		empVO.setEmpNo(generatedEmpNo);

		empDAO.insertEmp(empVO);

		return generatedEmpNo;
	}

	// update Emp
    @Transactional(value="txManager", propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateEmp(EmpVO empVO) throws Exception {
		empDAO.updateEmp(empVO);
	}

	// delete Emp
	public void deleteEmp(EmpVO empVO) throws Exception {
		empDAO.deleteEmp(empVO);
	}

	// select Emp
	public EmpVO selectEmp(EmpVO empVO) throws Exception {
		EmpVO resultVO;
		resultVO = empDAO.selectEmp(empVO);

		if (resultVO == null) {
			throw processException("info.nodata.msg");
		}

		return resultVO;
	}

	// list Emp
	public List<EmpVO> selectEmpList(EmpVO searchVO) throws Exception {
		return empDAO.selectEmpList(searchVO);
	}

}
