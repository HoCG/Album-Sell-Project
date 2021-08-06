package MGR;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import Data.User;
import Database.Database;

public class Manager<T extends Manageable> {
	public ArrayList<T> mList = new ArrayList<>();

	public T find(String kwd) {
		for (T m : mList)
			if (m.matches(kwd))
				return m;
		return null;
	}

	//�迭�� ������ �߰� �� �����ͺ��̽��� ��ü �߰�
	//���������� createData�� ������ ���� ��ü�� �߰��Ѵٴ�  ��. ���� �����ͺ��̽��� �����ϰų��� ������ �ƴϴ�!
	public Boolean addElement(T m) {
		if (m.createData()) {
			mList.add(m);
			return true;
		}
		return false;
	}

	public void UpdateData(T m) { // ��ü�� �����ϸ� ��ü�� �����ͺ��̽��� ������Ʈ ���ش�.
		m.UpdateData();
		for (T m2 : mList) {
			if (m2.matches(m.getName())) {
				mList.remove(m2);
				mList.add(m);
				System.out.println("�����Ϸ�");
				break;
			}
		}
	}

	public void DeleteData(T m) {
		m.DeleteData();
		for (T m2 : mList) {
			if (m2.matches(m.getName())) {
				mList.remove(m);
				System.out.println("���ſϷ�");
				break;
			}
		}
	}

	public ArrayList<T> findAll(String kwd) {
		ArrayList<T> findList = new ArrayList<>();
		for (T m : mList)
			if (m.matches(kwd))
				findList.add(m);
		return findList;
	}

	//���� ���α׷��� ������ readAll ����.
	public void readAll(Factory<T> fac) {
		T m = null;
		int oid = 1;
		while (true) {
			m = fac.create();
			m.getDataForOid(oid); // ����. �����͸� �ϳ��ϳ� �������ִ� ��� ����غ���.(ex. name = c.name)
			if (m.getName() == null)
				break;
			mList.add(m);
			oid++;
		}
	}

	public void printAll() {
		for (T m : mList) {
			m.print();
		}
	}

	// �ֹܼ���
	public void search(Scanner keyScanner) {
		String kwd = null;
		System.out.println("�˻� (end �Է½� ����)");
		while (true) {
			System.out.print(">> ");
			kwd = keyScanner.next();
			if (kwd.equals("end"))
				break;
			for (T m : mList) {
				if (m.matches(kwd))
					m.print();
			}
		}
	}
}
