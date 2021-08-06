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

	//배열에 데이터 추가 및 데이터베이스에 객체 추가
	//주의할점은 createData의 선언을 통해 객체를 추가한다는  점. 절대 데이터베이스를 생략하거나에 문제가 아니다!
	public Boolean addElement(T m) {
		if (m.createData()) {
			mList.add(m);
			return true;
		}
		return false;
	}

	public void UpdateData(T m) { // 객체만 전달하면 객체를 데이터베이스에 업데이트 해준다.
		m.UpdateData();
		for (T m2 : mList) {
			if (m2.matches(m.getName())) {
				mList.remove(m2);
				mList.add(m);
				System.out.println("수정완료");
				break;
			}
		}
	}

	public void DeleteData(T m) {
		m.DeleteData();
		for (T m2 : mList) {
			if (m2.matches(m.getName())) {
				mList.remove(m);
				System.out.println("제거완료");
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

	//기존 프로그램과 유사한 readAll 과정.
	public void readAll(Factory<T> fac) {
		T m = null;
		int oid = 1;
		while (true) {
			m = fac.create();
			m.getDataForOid(oid); // 오류. 데이터를 하나하나 지정해주는 방법 고려해보자.(ex. name = c.name)
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

	// 콘솔버전
	public void search(Scanner keyScanner) {
		String kwd = null;
		System.out.println("검색 (end 입력시 종료)");
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
