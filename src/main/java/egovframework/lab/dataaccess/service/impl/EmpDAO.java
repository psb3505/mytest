package egovframework.lab.dataaccess.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.lab.dataaccess.service.EmpVO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * EgovAbstractMapper를 상속/구현한 DAO 클래스
 * */
@Repository("empDAO")
public class EmpDAO extends EgovAbstractMapper {

	// TODO [Step 2-2] EmpDAO 작성 (EgovAbstractMapper 상속한 DAO) 

	public void insertEmp(EmpVO vo) {
        insert("Emp.insertEmp", vo);
    }

    public int updateEmp(EmpVO vo) {
        return update("Emp.updateEmp", vo);
    }

    public int deleteEmp(EmpVO vo) {
        return delete("Emp.deleteEmp", vo);
    }

    public EmpVO selectEmp(EmpVO vo) {
        return  (EmpVO) selectOne("Emp.selectEmp", vo);
    }

    public List<EmpVO> selectEmpList(EmpVO searchVO) {
        return selectList("Emp.selectEmpList", searchVO);
    }

}
