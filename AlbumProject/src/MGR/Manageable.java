package MGR;

public interface Manageable {
    boolean matches(String kwd);
	void getDataForOid(int oid);
	void print();
	String getName();
	Boolean createData();  //참고로 어찌보면 전혀 Boolean같지 않은 함수들의 모음.
	void UpdateData();
	void DeleteData();
}
