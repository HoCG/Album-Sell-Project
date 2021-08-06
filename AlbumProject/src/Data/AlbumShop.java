package Data;

import MGR.Factory;
import MGR.Manageable;
import MGR.Manager;
import Presentation.*;

import java.util.ArrayList;
import java.util.Scanner;

//��ȸ�� ����, ȸ�� ����, ȸ�� ���� ����, �߰������� ��üŬ���� ����°� ����غ���
//����Ʈ�� �����ǵ��� ���α׷� �����غ���.

public class AlbumShop extends Manager {
	public static Manager<Artist> ArtistMgr = new Manager<>();
	public static Manager<Album> AlbumMgr = new Manager<>();
	public static Manager<User> UserMgr = new Manager<>();
	public static Manager<OrderedAlbum> OrderedAlbumMgr = new Manager<>();
	public static Manager<Genre> GenreMgr = new Manager<>();

	public static String[][] ArtistToTable() {
		String[][] table = new String[ArtistMgr.mList.size()][4];
		int count = 0;
		for (Artist art : ArtistMgr.mList) {
			table[count] = art.getArtistArray();
			count++;
		}
		return table;
	}

	public static String[][] ArtistToTable(ArrayList<Artist> ArtistList) {
		String[][] table = new String[ArtistMgr.mList.size()][4];
		int count = 0;
		for (Artist art : ArtistList) {
			table[count] = art.getArtistArray();
			count++;
		}
		return table;
	}

	public static String[][] AlbumToTable() {
		String[][] table = new String[AlbumMgr.mList.size()][5];
		int count = 0;
		for (Album abm : AlbumMgr.mList) {
			table[count] = abm.getAlbumArray();
			count++;
		}
		return table;
	}

	public static String[][] AlbumToTable(ArrayList<Album> AlbumList) {
		String[][] table = new String[AlbumMgr.mList.size()][5];
		int count = 0;
		for (Album abm : AlbumList) {
			table[count] = abm.getAlbumArray();
			count++;
		}
		return table;
	}

	public static String[][] UserToTable() {
		String[][] table = new String[UserMgr.mList.size()][7];
		int count = 0;
		for (User ue : UserMgr.mList) {
			table[count] = ue.getUserArray();
			count++;
		}
		return table;
	}

	public static String[][] UserToTable(ArrayList<User> UserList) {
		String[][] table = new String[UserMgr.mList.size()][7];
		int count = 0;
		for (User ue : UserList) {
			table[count] = ue.getUserArray();
			count++;
		}
		return table;
	}

	public static String[][] AlbumOrderToTable() {
		String[][] table = new String[OrderedAlbumMgr.mList.size()][6];
		int count = 0;
		for (OrderedAlbum ao : OrderedAlbumMgr.mList) {
			table[count] = ao.getOrderedAlbumArray();
			count++;
		}
		return table;
	}

	public static String[][] AlbumOrderToTable(ArrayList<OrderedAlbum> OrderedAlbumList) {
		String[][] table = new String[OrderedAlbumMgr.mList.size()][6];
		int count = 0;
		for (OrderedAlbum ao : OrderedAlbumList) {
			table[count] = ao.getOrderedAlbumArray();
			count++;
		}
		return table;
	}

	public static void run() {
		UserMgr.readAll(new Factory() {
			public Manageable create() {
				return new User();
			}
		});
		ArtistMgr.readAll(new Factory() {
			public Manageable create() {
				return new Artist();
			}
		});
		GenreMgr.readAll(new Factory() {
			public Manageable create() {
				return new Genre();
			}
		});
		AlbumMgr.readAll(new Factory() {
			public Manageable create() {
				return new Album();
			}
		});
		OrderedAlbumMgr.readAll(new Factory() {
			public Manageable create() {
				return new OrderedAlbum();
			}
		});

		/*
		 * //�׽�Ʈ �κ�. Scanner scan = new Scanner(System.in); ArtistMgr.printAll();
		 * UserMgr.printAll(); System.out.println("�ٹ� �̸� �Է�:"); String name =
		 * scan.next(); System.out.println("���� ���� �Է�: "); int count = scan.nextInt();
		 * Album m2 = AlbumMgr.find(name); Album m = new Album(m2, count);
		 * AlbumMgr.UpdateData(m); System.out.println("�Ϸ�");
		 */
	}
}
