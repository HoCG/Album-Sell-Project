package MGR;

public interface Manageable {
    boolean matches(String kwd);
	void getDataForOid(int oid);
	void print();
	String getName();
	Boolean createData();  //����� ����� ���� Boolean���� ���� �Լ����� ����.
	void UpdateData();
	void DeleteData();
}
