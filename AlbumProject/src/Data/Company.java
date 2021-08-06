package Data;

import MGR.*;

//업체 클래스
//아직 미구현
public class Company extends Manager_Of_SQL_Data implements Manageable{
	private int oid;
	private String Companys_name;
	private String Companys_phonename;
	private String Companys_boss;
	
	@Override
	public boolean matches(String kwd) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void getDataForOid(int oid) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean createData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void UpdateData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void DeleteData() {
		// TODO Auto-generated method stub
		
	}
	
	
}
