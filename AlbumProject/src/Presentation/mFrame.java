package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import javax.swing.event.*;

import Data.*;


//프로젝트 문제사항: 1.구매를 하고나서 구매후 테이블로 내역을 조회했을때 오류가 발생하는 문제가 있음.

public class mFrame extends JFrame {
	AlbumShop albumShop = new AlbumShop();
	mFrame mFrame = this;

	JPanel mPanel;
	JPanel hmPanel;
	JPanel whPanel;

	JLabel title;
	JLabel subTitle;
	JButton[] jitem; // JButton이 여러개 쓰일것이니 배열로 지정
	JButton[] inqButton;
	JButton[] menuButton;
	JTable jTable;
	JTextArea search;

	String contents[][];
	String header[];

	public static void main(String[] args) {
		new mFrame();
	}

	public mFrame() { // 메인프레임
		AlbumShop.run();
		this.setSize(1400, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 메인패널
		mPanel = new JPanel();
		mPanel.setLayout(null);

		// 서브패널
		hmPanel = new hmPanel();
		hmPanel.setLayout(null);
		hmPanel.setSize(1100, 550);
		hmPanel.setLocation(250, 100);

		// 메인 라벨
		title = new JLabel("앨범 샵 프로젝트 ver1.0");
		title.setFont(title.getFont().deriveFont(32.0f));
		title.setSize(500, 50);
		title.setLocation(50, 30);
		mPanel.add(title);
		// 메인패널 버튼구성
		jitem = new JButton[4];
		jitem[0] = new JButton("정보관리");
		jitem[1] = new JButton("재고관리");
		jitem[2] = new JButton("구매");
		jitem[3] = new JButton("종료");
		for (int i = 0; i <= 3; i++) {
			jitem[i].setSize(150, 100);
			jitem[i].setLocation(50, 100 + 150 * i);
			jitem[i].setHorizontalAlignment(SwingConstants.CENTER);
			jitem[i].setVerticalAlignment(SwingConstants.CENTER);
			mPanel.add(jitem[i]);
		}
		mPanel.add(hmPanel); // 시작화면

		jitem[0].addActionListener(new InquiryListener());
		jitem[1].addActionListener(new StoreListener());
		jitem[2].addActionListener(new OrderListener());
		jitem[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		this.add(mPanel);
		this.setVisible(true);
	}
	
	
	//미구현 내용.
	class StoreListener implements ActionListener { //제고 관리 메뉴

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}

	}

	class OrderListener implements ActionListener { //예약메뉴
		Artist savedArtist; //예약을 위해 활용될 각 객체들. 오직 예약을 위해서만 활용됨.
		String date;
		Album savedAlbum;
		User savedUser;
		int FinalPay;

		@Override
		public void actionPerformed(ActionEvent e) {
			initiatePanel();
			LoginUser(false);
			// selectDesigner();
			// selectMenu();
			// reservationComplete();
		}

		void LoginUser(boolean b) {
			JTextField tfUsername;
			JPasswordField tfPassword;
			JButton loginBtn, joinBtn;
			initiatePanel();
			JLabel MainLabel = new JLabel("로그인");
			MainLabel.setFont(MainLabel.getFont().deriveFont(64.0f));
			MainLabel.setBounds(450, 50, 400, 200);
			whPanel.add(MainLabel);

			JLabel lblLogin = new JLabel("유저이름:");
			lblLogin.setFont(lblLogin.getFont().deriveFont(16.0f));
			lblLogin.setBounds(405, 243, 100, 50);
			whPanel.add(lblLogin);

			JLabel lblPassword = new JLabel("비밀번호:");
			lblPassword.setFont(lblLogin.getFont().deriveFont(16.0f));
			lblPassword.setBounds(405, 292, 100, 50); // 51차이
			whPanel.add(lblPassword);

			tfUsername = new JTextField();
			tfUsername.setBounds(507, 252, 176, 35);
			whPanel.add(tfUsername);
			
			tfPassword = new JPasswordField();
			tfPassword.setColumns(10);
			tfPassword.setEchoChar('*');
			tfPassword.setBounds(507, 303, 176, 35);
			whPanel.add(tfPassword);


			joinBtn = new JButton("회원가입");
			joinBtn.setBounds(579, 354, 104, 29);
			whPanel.add(joinBtn);

			loginBtn = new JButton("로그인");
			loginBtn.setBounds(458, 354, 106, 29);
			whPanel.add(loginBtn);

			loginBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String ID = tfUsername.getText();
					User user = AlbumShop.UserMgr.find(ID);
					String password = tfPassword.getText();
					if (AlbumShop.UserMgr.find(ID) != null) {
						if (password.equals(user.getPassword())) {
							savedUser = AlbumShop.UserMgr.find(user.getUserId());
							selectArtist();
						} else {
							JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다. 다시 입력해주세요.");
					}
				}
			});

			joinBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					User_Sign_Up();
				}
			});
			mPanel.remove(hmPanel); // 초기패널인 hm패널을 제거
			whPanel.repaint(); // 마찬가지 유용한 기능.다음 패널로 넘어가는 과정
		}

		void User_Sign_Up() {
			JLabel lblJoin;
			JButton joinCompleteBtn;
			initiatePanel();

			JButton back = new JButton("뒤로가기");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginUser(false);
				}
			});

			lblJoin = new JLabel("회원가입");
			Font f1 = new Font("돋움", Font.BOLD, 20); // 궁서 바탕 돋움
			lblJoin.setFont(f1);
			lblJoin.setBounds(459, 41, 101, 20);
			whPanel.add(lblJoin);
			JLabel Sign_Up_Labels = new JLabel();
			String[] Sign_Up_Labels_Name = { "Name", "ID", "Password", "PhoneNumber" };
			JTextField[] Sign_Up_TextFields = new JTextField[4];

			for (int i = 0; i < 4; i++) {
				Sign_Up_Labels = new JLabel(Sign_Up_Labels_Name[i]);
				Sign_Up_Labels.setBounds(369, 113 + i * 50, 90, 20);
				Sign_Up_TextFields[i] = new JTextField();
				Sign_Up_TextFields[i].setColumns(10);
				Sign_Up_TextFields[i].setBounds(459, 106 + i * 50, 186, 35);
				whPanel.add(Sign_Up_Labels);
				whPanel.add(Sign_Up_TextFields[i]);
			}

			joinCompleteBtn = new JButton("회원가입완료");
			joinCompleteBtn.setBounds(506, 363, 139, 29);
			whPanel.add(joinCompleteBtn);

			// 회원가입완료 액션
			joinCompleteBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String NewName = Sign_Up_TextFields[0].getText();
					String NewID = Sign_Up_TextFields[1].getText();
					String NewPasswords = Sign_Up_TextFields[2].getText();
					String NewPhoneNumber = Sign_Up_TextFields[3].getText();
					if (NewName.isEmpty() == false && NewID.isEmpty() == false && NewPasswords.isEmpty() == false
							&& NewPhoneNumber.isEmpty() == false) {
						if (AlbumShop.UserMgr.find(Sign_Up_TextFields[1].getText()) == null) {
							if (NewPasswords.length() >= 8) {
								//새로운 유저를 생성후 객체를 배열에 집어넣음.
								User NewUser = new User(NewName, NewID, NewPasswords, NewPhoneNumber);
								AlbumShop.UserMgr.addElement(NewUser);
								JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
								LoginUser(false);
							} else
								JOptionPane.showMessageDialog(null, "비밀번호 길이가 너무 짧습니다. 다시 입력해주세요.");
						} else
							JOptionPane.showMessageDialog(null, "동일한 아이디가 있습니다. 다시 입력해주세요.");
					} else if (NewName.isEmpty())
						JOptionPane.showMessageDialog(null, "이름을 입력해주세요");
					else if (NewID.isEmpty())
						JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
					else if (NewPasswords.isEmpty())
						JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요");
					else if (NewPhoneNumber.isEmpty())
						JOptionPane.showMessageDialog(null, "핸드폰 번호를 입력해주세요");
				}
			});

			back.setSize(100, 36);
			back.setLocation(30, 500);
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.setVerticalAlignment(SwingConstants.CENTER);
			whPanel.add(back);
			mPanel.repaint();
		}

		public void selectWantPanel() {
			String ItemImfor[] = { "아티스트 선택", "전체앨범 선택", "장르별 선택" };
			JComboBox ChooseWhatYouWant = new JComboBox(ItemImfor);
			ChooseWhatYouWant.setFont(getFont().deriveFont(17.0f));
			ChooseWhatYouWant.setSize(ItemImfor.length * 70, 30);
			ChooseWhatYouWant.setLocation(25, 75);
			ChooseWhatYouWant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource(); // 콤보박스 알아내기
					int index = cb.getSelectedIndex();// 선택된 아이템의 인덱스
					if (index == 0)
						selectArtist();
					else if (index == 1)
						select_By_Album_Own();
					else
						select_By_Genre_Album();
				}
			});
			whPanel.add(ChooseWhatYouWant);
		}
		
		void selectArtist() {
			initiatePanel();
			setText("아티스트로 선택하기");
			selectWantPanel();
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			JButton back = new JButton("뒤로가기");

			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginUser(false);
				}
			});

			ArrayList<Artist> list = AlbumShop.ArtistMgr.mList;
			int count = list.size();

			// buttonBox.setSize(1000, 352 * (count / 3 + 1));
			buttonBox.setPreferredSize(new Dimension(1000, 352 * (count / 3 + 1)));
			JScrollPane scroll = new JScrollPane(buttonBox);
			scroll.setSize(1050, 400);
			scroll.setLocation(whPanel.getWidth() / 2 - scroll.getWidth() / 2,
					whPanel.getHeight() / 2 - scroll.getHeight() / 2);
			whPanel.add(scroll);
			JButton[] desButton = new JButton[count];
			JLabel[] desLabel = new JLabel[count];

			int t = 0;
			for (Artist art : list) {
				ImageIcon icon;
				String desId = art.getName();
				icon = new ImageIcon("./IMG/Icon_Artist/" + desId + ".jpg");
				if (icon == null) {
					icon = new ImageIcon("./IMG/noImage");
				}
				desButton[t] = new JButton(icon);
				desButton[t].setSize(102, 102);
				desButton[t].setLocation(60 + (t % 7) * 130, 60 + (t / 7) * 150);
				desLabel[t] = new JLabel(art.getName());
				desLabel[t].setSize(102, 32);
				desLabel[t].setLocation(80 + (t % 7) * 130, 155 + (t / 7) * 150);
				buttonBox.add(desButton[t]);
				buttonBox.add(desLabel[t]);

				desButton[t].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						savedArtist = art;
						selectAlbum(); // 다음 함수로 넘어가게끔 구성
					}
				});
				t++;
			}

			back.setSize(100, 36);
			back.setLocation(30, 500);
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.setVerticalAlignment(SwingConstants.CENTER);
			whPanel.add(back);
			mPanel.repaint();
		}

		void selectAlbum() {
			initiatePanel();
			setText("아티스트 앨범 선택");
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			JButton back = new JButton("뒤로가기");

			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectArtist();
				}
			});

			ArrayList<Album> list = savedArtist.getAlbumList(); // getAlbumList로 상응되도록 Artist에 만들것.
			int count = list.size();

			// buttonBox.setSize(1000, 352 * (count / 3 + 1));
			buttonBox.setPreferredSize(new Dimension(1000, 352 * (count / 3 + 1)));
			JScrollPane scroll = new JScrollPane(buttonBox);
			scroll.setSize(1050, 400);
			scroll.setLocation(whPanel.getWidth() / 2 - scroll.getWidth() / 2,
					whPanel.getHeight() / 2 - scroll.getHeight() / 2);

			whPanel.add(scroll);
			JButton[] desButton = new JButton[count];
			JLabel[] desLabel = new JLabel[count];

			int t = 0;
			for (Album am : list) {
				ImageIcon icon;
				String albumName = am.getName();
				icon = new ImageIcon("./IMG/Icon_Album/" + albumName.replaceAll(" ", "") + ".jpg");
				if (icon == null) {
					icon = new ImageIcon("./IMG/noImage");
				}
				desButton[t] = new JButton(icon);
				desButton[t].setSize(102, 102);
				desButton[t].setLocation(60 + (t % 7) * 130, 60 + (t / 7) * 150);
				desLabel[t] = new JLabel(am.getName());
				desLabel[t].setSize(102, 32);
				desLabel[t].setLocation(80 + (t % 7) * 130, 155 + (t / 7) * 150);
				buttonBox.add(desButton[t]);
				buttonBox.add(desLabel[t]);
				desButton[t].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						savedAlbum = am;
						FinalBuy();
					}
				});
				t++;
			}

			back.setSize(100, 36);
			back.setLocation(30, 500);
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.setVerticalAlignment(SwingConstants.CENTER);

			whPanel.add(back);
			mPanel.repaint();
		}

		void select_By_Album_Own() {
			initiatePanel();
			setText("앨범전체에서 선택하기");
			selectWantPanel();
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			JButton back = new JButton("뒤로가기");

			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginUser(false);
				}
			});

			ArrayList<Album> list = AlbumShop.AlbumMgr.mList; // getAlbumList로 상응되도록 Artist에 만들것.
			int count = list.size();

			// buttonBox.setSize(1000, 352 * (count / 3 + 1));
			buttonBox.setPreferredSize(new Dimension(1000, 352 * (count / 3 + 1)));
			JScrollPane scroll = new JScrollPane(buttonBox);
			scroll.setSize(1050, 400);
			scroll.setLocation(whPanel.getWidth() / 2 - scroll.getWidth() / 2,
					whPanel.getHeight() / 2 - scroll.getHeight() / 2);

			whPanel.add(scroll);
			JButton[] desButton = new JButton[count];
			JLabel[] desLabel = new JLabel[count];

			int t = 0;
			for (Album am : list) {
				ImageIcon icon;
				String albumName = am.getName();
				icon = new ImageIcon("./IMG/Icon_Album/" + albumName.replaceAll(" ", "") + ".jpg");
				if (icon == null) {
					icon = new ImageIcon("./IMG/noImage");
				}
				desButton[t] = new JButton(icon);
				desButton[t].setSize(102, 102);
				desButton[t].setLocation(60 + (t % 7) * 130, 60 + (t / 7) * 150);
				desLabel[t] = new JLabel(am.getName());
				desLabel[t].setSize(102, 32);
				desLabel[t].setLocation(80 + (t % 7) * 130, 155 + (t / 7) * 150);
				buttonBox.add(desButton[t]);
				buttonBox.add(desLabel[t]);
				desButton[t].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						savedAlbum = am;
						FinalBuy();
					}
				});
				t++;
			}

			back.setSize(100, 36);
			back.setLocation(30, 500);
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.setVerticalAlignment(SwingConstants.CENTER);

			whPanel.add(back);
			mPanel.repaint();

		}

		// 미구현. 오류로 인해.
		void select_By_Genre_Album() {
			initiatePanel();
			setText("장르별로 선택하기");
			selectWantPanel();
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			
			ArrayList<Album> list = AlbumShop.AlbumMgr.mList;
			int count = list.size();
			
			buttonBox.setPreferredSize(new Dimension(1000, 352 * (count / 3 + 1)));
			JScrollPane scroll = new JScrollPane(buttonBox);
			scroll.setSize(1050, 400);
			scroll.setLocation(whPanel.getWidth() / 2 - scroll.getWidth() / 2,
					whPanel.getHeight() / 2 - scroll.getHeight() / 2);
			whPanel.add(scroll);
			
			JButton[] desButton = new JButton[count];
			JLabel[] desLabel = new JLabel[count];

			
			int i = 0;
			String[] ItemImfor = new String[AlbumShop.GenreMgr.mList.size()];
			for (Genre g : AlbumShop.GenreMgr.mList) {
				ItemImfor[i] = g.getName();
				i++;
			}
			
			JComboBox ChooseWhatYouWant = new JComboBox(ItemImfor);
			ChooseWhatYouWant.setFont(getFont().deriveFont(17.0f));
			ChooseWhatYouWant.setSize(ItemImfor.length * 70, 30);
			ChooseWhatYouWant.setLocation(235, 75);
			ChooseWhatYouWant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource(); // 콤보박스 알아내기
					int index = cb.getSelectedIndex();// 선택된 아이템의 인덱스
					int t = 0;
					for (Album am : list) {
						if (am.getGenre().equals(ItemImfor[index])) {
							ImageIcon icon;
							String albumName = am.getName();
							System.out.println(albumName);
							icon = new ImageIcon("./IMG/Icon_Album/" + albumName.replaceAll(" ", "") + ".jpg");
							if (icon == null) {
								icon = new ImageIcon("./IMG/noImage");
							}
							desButton[t] = new JButton(icon);
							desButton[t].setSize(102, 102);
							desButton[t].setLocation(60 + (t % 7) * 130, 60 + (t / 7) * 150);
							desLabel[t] = new JLabel(am.getName());
							desLabel[t].setSize(102, 32);
							desLabel[t].setLocation(80 + (t % 7) * 130, 155 + (t / 7) * 150);
							scroll.add(desButton[t]);
							scroll.add(desLabel[t]);
							desButton[t].addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									savedAlbum = am;
									FinalBuy();
								}
							});
							t++;
						}
					}
				}
			});
			whPanel.add(ChooseWhatYouWant);
			
			JButton back = new JButton("뒤로가기");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginUser(false);
				}
			});
			back.setSize(100, 36);
			back.setLocation(30, 500);
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.setVerticalAlignment(SwingConstants.CENTER);

			whPanel.add(back);
			mPanel.repaint();
		}

		void FinalBuy() {
			initiatePanel();
			setText("최종 구매 결정");

			JButton back = new JButton("뒤로가기");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectArtist();
				}
			});

			JLabel[] explain = new JLabel[7];

			// 화면에서 보여줄 앨범 커버 사진.
			ImageIcon icon = new ImageIcon("./IMG/album/" + savedAlbum.getName().replaceAll(" ", "") + ".jpg");
			if (icon == null) {
				icon = new ImageIcon("./IMG/noImage");
			}
			JLabel ForImage = new JLabel();
			ForImage.setIcon(icon);
			ForImage.setSize(300, 300);
			ForImage.setLocation(50, 100);
			whPanel.add(ForImage);

			// 정보 출력
			explain[0] = new JLabel("회원아이디 : " + savedUser.getUserId());
			explain[1] = new JLabel("상품명 : " + savedAlbum.getName());
			explain[2] = new JLabel("가격 : " + savedAlbum.getPrice());
			explain[3] = new JLabel("구매수량 : ");
			explain[4] = new JLabel("사용가능 포인트 : " + Integer.toString(savedUser.getPoint()));
			explain[5] = new JLabel("포인트 사용: ");
			explain[6] = new JLabel("총 구매가격: ");

			int count = 0;
			for (JLabel jLabel : explain) {
				jLabel.setSize(300, 50);
				jLabel.setLocation(400, 100 + 50 * count);
				whPanel.add(jLabel);
				count++;
			}
			JTextField UsePoint = new JTextField();
			UsePoint.setBounds(487, 355, 106, 35);

			// 앨범 갯수를 콤보박스로 출력.
			String[] ItemImfor = new String[savedAlbum.getCount()];
			for (int i = 1; i < savedAlbum.getCount() + 1; i++) {
				ItemImfor[i - 1] = Integer.toString(i);
			}
			JComboBox AlbumCount = new JComboBox(ItemImfor);
			int RealAlbumCount = 0;
			JLabel AllPay = new JLabel();
			AlbumCount.setBounds(487, 255, 106, 35);
			AlbumCount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					whPanel.remove(AllPay);
					JComboBox cb = (JComboBox) e.getSource(); // 콤보박스 알아내기
					int index = savedAlbum.getPrice() * (cb.getSelectedIndex() + 1);
					if (!UsePoint.getText().isEmpty()) {
						FinalPay = index - Integer.parseInt(UsePoint.getText());
					} else
						FinalPay = index;
					AllPay.setText(Integer.toString(FinalPay));
					AllPay.setSize(300, 50);
					AllPay.setLocation(487, 400);
					whPanel.add(AllPay);
				}
			});

			whPanel.add(AlbumCount);
			whPanel.add(UsePoint);

			back.setSize(100, 36);
			back.setLocation(30, 500);
			back.setHorizontalAlignment(SwingConstants.CENTER);
			back.setVerticalAlignment(SwingConstants.CENTER);
			whPanel.add(back);

			// 구매버튼
			JButton Buy = new JButton("구매");
			Buy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Integer.parseInt(AlbumCount.getSelectedItem().toString()) > 0) {
						// 포인트를 제외한 최종가격.
						int LastPay = 0;
						if (UsePoint.getText().isEmpty()) {
							LastPay = Integer.parseInt(AllPay.getText());
						} else
							LastPay = Integer.parseInt(AllPay.getText()) - Integer.parseInt(UsePoint.getText());
						// 주문 정보를 저장하는 과정
						OrderedAlbum oa = new OrderedAlbum(savedUser.getUserId(),
								Integer.parseInt(AlbumCount.getSelectedItem().toString()), LastPay,
								savedAlbum.getName());
						AlbumShop.OrderedAlbumMgr.addElement(oa);
						// 사용 포인트만큼 유저의 포인트 감소. 이때 주의할점은 포인트 누적의 경우는 데이터 클래스내에서 자체적으로 이루어진다.
						if (!UsePoint.getText().isEmpty()) {
							savedUser.point = savedUser.getPoint() - Integer.parseInt(UsePoint.getText());
							AlbumShop.UserMgr.UpdateData(savedUser);
						}
						buyComplete();
					} else
						JOptionPane.showMessageDialog(null, "개수를 제대로 지정하지 않았습니다. 다시 지정해주세요.");
				}
			});
			Buy.setSize(100, 36);
			Buy.setLocation(487, 455);
			Buy.setHorizontalAlignment(SwingConstants.CENTER);
			Buy.setVerticalAlignment(SwingConstants.CENTER);
			whPanel.add(Buy);

			mPanel.repaint();
		}

		// 구매완료
		void buyComplete() {
			initiatePanel();
			JLabel main = new JLabel("구매가 완료되었습니다.");
			JLabel[] explain = new JLabel[4];

			explain[0] = new JLabel("회원아이디 : " + savedUser.getName());
			explain[1] = new JLabel("메뉴명 : " + savedAlbum.getName());
			explain[2] = new JLabel("누적 포인트 : " + FinalPay / 1000);
			explain[2] = new JLabel("총 구매가격: " + FinalPay);

			for (int i = 0; i < 3; i++) {
				explain[i].setSize(300, 50);
				explain[i].setHorizontalAlignment(SwingConstants.CENTER);
				explain[i].setVerticalAlignment(SwingConstants.CENTER);
				explain[i].setLocation(whPanel.getWidth() / 2 - explain[i].getWidth() / 2, 400 + (50 * i / 2));
				explain[i].setBackground(Color.white);
				whPanel.add(explain[i]);
			}

			main.setSize(740, 100);
			main.setHorizontalAlignment(JLabel.CENTER);
			main.setFont(new Font("맑은 고딕", Font.BOLD, 50));
			main.setLocation(whPanel.getWidth() / 2 - main.getWidth() / 2, whPanel.getHeight() / 2 - main.getHeight());
			whPanel.add(main);
			mPanel.remove(hmPanel);
			mPanel.repaint();
		}
	}

	class InquiryListener implements ActionListener { // 조회메뉴

		@Override
		public void actionPerformed(ActionEvent e) {
			initiatePanel();
			setText("관리페이지");

			inqButton = new JButton[4];
			inqButton[0] = new JButton("앨범");
			inqButton[1] = new JButton("아티스트");
			inqButton[2] = new JButton("구매내역");
			inqButton[3] = new JButton("고객");

			for (JButton button : inqButton) { // 각각의 버튼의 형태 생성과정
				button.setSize(150, 100);
				button.setHorizontalAlignment(SwingConstants.CENTER);
				button.setVerticalAlignment(SwingConstants.CENTER);
				whPanel.add(button);
			}

			inqButton[0].setLocation(whPanel.getWidth() / 2 - 75 - 150, 150); // 버튼의 위치를 지정. 이런 수학적인 조정을 공부할 필요가 있음.
			inqButton[1].setLocation(whPanel.getWidth() / 2 - 75 + 150, 150);
			inqButton[2].setLocation(whPanel.getWidth() / 2 - 75 - 150, 350);
			inqButton[3].setLocation(whPanel.getWidth() / 2 - 75 + 150, 350);

			inqButton[0].addActionListener(new ActionListener() { // 앨범조회
				@Override
				public void actionPerformed(ActionEvent e) {
					albumInquiryEvent(false);
				}
			});

			inqButton[1].addActionListener(new ActionListener() { // 아티스트조회
				@Override
				public void actionPerformed(ActionEvent e) {
					ArtistInquiryEvent(false);
				}
			});

			inqButton[2].addActionListener(new ActionListener() { // 구매내역조회
				@Override
				public void actionPerformed(ActionEvent e) {
					PurchaseInquiryEvent(false);
				}
			});

			inqButton[3].addActionListener(new ActionListener() { // 고객조회
				@Override
				public void actionPerformed(ActionEvent e) {
					userInquiryEvent(false);
				}
			});

			mPanel.remove(hmPanel);
			mPanel.repaint();
		}
	}

	public void albumInquiryEvent(boolean refresh) { // 메뉴조회 실행 메소드
		initiatePanel();
		setText("메뉴");
		getDefaultInqueryMenu(); // 기본메뉴 가져오기
		header = new String[] { "이름", "장르", "가격", "아티스트", "개수" }; //메뉴항목
		if (!refresh) {
			contents = AlbumShop.AlbumToTable(); // 리스트 가져오기
			jTable = new JTable(contents, header); // 테이블 설정
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		menuButton[1].addActionListener(new ActionListener() { // 추가기능
			@Override
			public void actionPerformed(ActionEvent e) {
				AlbumAddDialog AlbumAddDialog = new AlbumAddDialog(mFrame.mFrame, "메뉴 추가");
				AlbumAddDialog.setVisible(true);
			}
		});
		menuButton[2].addActionListener(new ActionListener() { // 검색기능
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<Album> AlbumList = AlbumShop.AlbumMgr.findAll(key);
				contents = AlbumShop.AlbumToTable(AlbumList);
				jTable = new JTable(contents, header); // 테이블 설정
				albumInquiryEvent(true);
			}
		});
		menuButton[3].addActionListener(new ActionListener() { // 삭제기능
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = jTable.getSelectedRow();
				String name = contents[index][0];
				Album selectedAlbum = AlbumShop.AlbumMgr.find(name);
				AlbumShop.AlbumMgr.DeleteData(selectedAlbum);
				albumInquiryEvent(false);
			}
		});
		whPanel.add(jscroll);
		mPanel.repaint();
	}

	public void userInquiryEvent(boolean refresh) { // 고객조회 실행 메소드
		initiatePanel();
		setText("고객");
		getDefaultInqueryMenu(); // 기본메뉴 가져오기
		header = new String[] { "이름", "ID", "핸드폰 번호", "등급", "포인트", "누적포인트" }; // 고객 메뉴항목
		if (!refresh) {
			contents = AlbumShop.UserToTable(); // 리스트 가져오기
			jTable = new JTable(contents, header); // 테이블 설정
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		menuButton[1].addActionListener(new ActionListener() { // 추가기능
			@Override
			public void actionPerformed(ActionEvent e) {
				userAddDialog userAddDialog = new userAddDialog(mFrame, "손님 추가");
				userAddDialog.setVisible(true);
			}
		});
		menuButton[2].addActionListener(new ActionListener() { // 검색
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<User> UserList = AlbumShop.UserMgr.findAll(key);
				contents = AlbumShop.UserToTable(UserList);
				jTable = new JTable(contents, header); // 테이블 설정
				userInquiryEvent(true);
			}
		});
		menuButton[3].addActionListener(new ActionListener() { // 삭제
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = jTable.getSelectedRow();
				String ID = contents[index][0];
				User selectedUser = AlbumShop.UserMgr.find(ID);
				AlbumShop.UserMgr.DeleteData(selectedUser);
				userInquiryEvent(false);
			}
		});
		whPanel.add(jscroll);
		mPanel.repaint();
	}

	// 미구현
	public void ArtistInquiryEvent(boolean refresh) {
		initiatePanel();
		setText("아티스트");
		getDefaultInqueryMenu(); // 기본메뉴 가져오기
		header = new String[] { "이름", "본명", "메인장르", "디스코그래피" };
		if (!refresh) {
			contents = AlbumShop.ArtistToTable(); // 리스트 가져오기
			jTable = new JTable(contents, header); // 테이블 설정
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		menuButton[1].addActionListener(new ActionListener() { // 추가기능
			@Override
			public void actionPerformed(ActionEvent e) {
				artistAddDialog artistAddDialog = new artistAddDialog(mFrame, "아티스트 추가");
				artistAddDialog.setVisible(true);
			}
		});
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<Artist> ArtistList = AlbumShop.ArtistMgr.findAll(key);
				contents = AlbumShop.ArtistToTable(ArtistList);
				jTable = new JTable(contents, header); // 테이블 설정
				ArtistInquiryEvent(true);
			}
		});
		menuButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = jTable.getSelectedRow();
				String name = contents[index][1];
				Artist selected = AlbumShop.ArtistMgr.find(name);
				AlbumShop.ArtistMgr.DeleteData(selected);
				ArtistInquiryEvent(false);
			}
		});
		whPanel.add(jscroll);
		mPanel.repaint();
	}

	public void PurchaseInquiryEvent(boolean refresh) { // 구매내역조회 실행 메소드
		mPanel.remove(hmPanel);
		mPanel.remove(whPanel);
		initiatePanel();
		setText("예약");
		getDefaultInqueryMenu(); // 기본메뉴 가져오기
		header = new String[] { "고객ID", "날짜", "구매앨범", "주문개수", "총가격" }; // 고객 메뉴항목
		if (!refresh) {
			contents = AlbumShop.AlbumOrderToTable(); // 리스트 가져오기
			jTable = new JTable(contents, header); // 테이블 설정
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		whPanel.remove(menuButton[1]); // 예약기능 삭제
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<OrderedAlbum> orderList = AlbumShop.OrderedAlbumMgr.findAll(key);
				contents = AlbumShop.AlbumOrderToTable(orderList);
				jTable = new JTable(contents, header); // 테이블 설정
				PurchaseInquiryEvent(true);
			}
		});
		menuButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = jTable.getSelectedRow();
				String name = contents[index][1];
				OrderedAlbum selected = AlbumShop.OrderedAlbumMgr.find(name);
				AlbumShop.OrderedAlbumMgr.DeleteData(selected);
				PurchaseInquiryEvent(false);
			}
		});
		whPanel.add(jscroll);
		mPanel.repaint();
	}

	public void getDefaultInqueryMenu() { // 조회 공통 메뉴 초기화. 메뉴부분으로 유용하게 사용가능.
		menuButton = new JButton[4];
		search = new JTextArea();
		menuButton[0] = new JButton("뒤로");
		menuButton[1] = new JButton("추가");
		menuButton[2] = new JButton("검색");
		menuButton[3] = new JButton("삭제");
		menuButton[0].addActionListener(new InquiryListener());
		for (JButton button : menuButton) {
			button.setSize(60, 60);
			button.setHorizontalAlignment(SwingConstants.CENTER);
			button.setVerticalAlignment(SwingConstants.CENTER);
			whPanel.add(button);
		}

		menuButton[0].setLocation(50, 90);
		menuButton[2].setLocation(880, 90);
		menuButton[1].setLocation(940, 90);
		menuButton[3].setLocation(1000, 90);
		search.setSize(650, 60);
		search.setLocation(200, 90);
		search.setFont(new Font("맑은 고딕", Font.BOLD, 46));
		search.setBorder(new TitledBorder(new LineBorder(Color.black)));
		whPanel.add(search);
	}

	// 패널의 제목을 위에 개시해줌.
	void setText(String title) {
		subTitle = new JLabel(title);
		subTitle.setFont(subTitle.getFont().deriveFont(32.0f));
		subTitle.setSize(title.length() * 50, 100);
		subTitle.setLocation(whPanel.getWidth() / 2 - subTitle.getWidth() / 2, 0);
		whPanel.add(subTitle);
	}

	public void initiatePanel() {
		if (mPanel.isAncestorOf(whPanel))
			mPanel.remove(whPanel);
		whPanel = new whPanel();
		whPanel.setLayout(null);
		whPanel.setSize(1100, 550);
		whPanel.setLocation(250, 100);
		;
		mPanel.add(whPanel);
	}


	class AlbumAddDialog extends JDialog { // 다이아로그
		JButton submit = new JButton("등록");
		JTextArea nameT = new JTextArea();
		JTextArea priceT = new JTextArea();
		JTextArea ArtistT = new JTextArea();
		JTextArea countT = new JTextArea();
		JTextArea genreT = new JTextArea();

		JLabel nameL = new JLabel("이름 : ");
		JLabel priceL = new JLabel("가격 : ");
		JLabel ArtistL = new JLabel("아티스트 : "); // spanL
		JLabel countL = new JLabel("갯수 : "); // genderL
		JLabel genreL = new JLabel("장르: ");

		public AlbumAddDialog(JFrame frame, String title) {
			super(frame, title);
			JDialog F = this;
			setLayout(null);
			setSize(200, 230);
			add(submit);
			add(nameT);
			add(priceT);
			add(ArtistT);
			add(countT);
			add(genreT);

			add(nameL);
			add(priceL);
			add(ArtistL);
			add(countL);
			add(genreL);

			nameL.setLocation(10, 10);
			nameL.setSize(100, 30);
			nameT.setLocation(50, 10);
			nameT.setSize(100, 20);
			priceL.setLocation(10, 50);
			priceL.setSize(100, 30);
			priceT.setLocation(50, 50);
			priceT.setSize(100, 20);
			ArtistL.setLocation(10, 90);
			ArtistL.setSize(100, 30);
			ArtistT.setLocation(50, 90);
			ArtistT.setSize(100, 20);
			countL.setLocation(10, 130);
			countL.setSize(100, 30);
			countT.setLocation(50, 130);
			countT.setSize(100, 20);
			genreL.setLocation(10, 170);
			genreL.setSize(100, 30);
			genreT.setLocation(50, 170);
			genreT.setSize(100, 20);
			submit.setLocation(50, 210);
			submit.setSize(100, 30);

			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = nameT.getText();
					String price = priceT.getText();
					String Artist = ArtistT.getText();
					String count = countT.getText();
					String genre = genreT.getText();
					try {
						Album album = new Album(name, genre, Integer.parseInt(price), Artist, Integer.parseInt(count));
						AlbumShop.AlbumMgr.addElement(album);

					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(mFrame, "추가에 실패하였습니다.");
					}
					JOptionPane.showMessageDialog(mFrame, "추가가 완료되었습니다.");
					albumInquiryEvent(false);
					if (AlbumShop.ArtistMgr.find(Artist) == null) {
						F.dispose();
						JOptionPane.showMessageDialog(mFrame, "아티스트 정보가 없습니다. 해당 아티스트 정보까지 추가 해주시겠어요?");
						artistAddDialog AAD = new artistAddDialog(frame, "아티스트 추가");
					}
					F.dispose();
				}
			});
		}
	}

	class artistAddDialog extends JDialog {
		JButton submit = new JButton("등록");
		JTextArea idT = new JTextArea();
		JTextArea nameT = new JTextArea();
		JTextArea genreT = new JTextArea();

		JLabel idL = new JLabel("아티스트명 : ");
		JLabel nameL = new JLabel("본명 : ");
		JLabel genreL = new JLabel("메인장르 : ");

		public artistAddDialog(JFrame frame, String title) {
			super(frame, title);
			JDialog F = this;
			setLayout(null);
			setSize(200, 150);
			add(submit);
			add(nameT);
			add(idT);
			add(genreT);
			add(nameL);
			add(idL);
			add(genreL);

			idL.setLocation(10, 10);
			idL.setSize(100, 30);
			idT.setLocation(50, 10);
			idT.setSize(100, 20);

			nameL.setLocation(10, 50);
			nameL.setSize(100, 30);
			nameT.setLocation(50, 50);
			nameT.setSize(100, 20);

			genreL.setLocation(10, 90);
			genreL.setSize(100, 30);
			genreT.setLocation(50, 90);
			genreT.setSize(100, 20);

			submit.setLocation(50, 130);
			submit.setSize(100, 30);

			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = idT.getText();
					String name = nameT.getText();
					String genre = genreT.getText();
					try {
						Artist element = new Artist(name, id, genre);
						AlbumShop.ArtistMgr.addElement(element);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(mFrame, "추가에 실패하였습니다.");
					}
					JOptionPane.showMessageDialog(mFrame, "추가가 완료되었습니다.");
					albumInquiryEvent(false);
					F.dispose();
				}
			});
		}

		public artistAddDialog(JFrame frame, String title, String ArtistName) {
			super(frame, title);
			JDialog F = this;
			setLayout(null);
			setSize(200, 150);
			add(submit);
			add(nameT);
			add(idT);
			add(genreT);
			add(nameL);
			add(idL);
			add(genreL);

			idL.setLocation(10, 10);
			idL.setSize(100, 30);
			idT.setLocation(50, 10);
			idT.setSize(100, 20);

			nameL.setLocation(10, 50);
			nameL.setSize(100, 30);
			nameT.setLocation(50, 50);
			nameT.setSize(100, 20);

			genreL.setLocation(10, 90);
			genreL.setSize(100, 30);
			genreT.setLocation(50, 90);
			genreT.setSize(100, 20);

			submit.setLocation(50, 130);
			submit.setSize(100, 30);

			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = idT.getText();
					String name = nameT.getText();
					String genre = genreT.getText();
					try {
						Artist element = new Artist(name, id, genre);
						AlbumShop.ArtistMgr.addElement(element);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(mFrame, "추가에 실패하였습니다.");
					}
					JOptionPane.showMessageDialog(mFrame, "추가가 완료되었습니다.");
					albumInquiryEvent(false);
					F.dispose();
				}
			});
		}
	}

	class userAddDialog extends JDialog {
		JButton submit = new JButton("등록");
		JTextArea nameT = new JTextArea();
		JTextArea phoneNumberT = new JTextArea();
		JTextArea IDT = new JTextArea();
		JTextArea passwordT = new JTextArea();

		JLabel nameL = new JLabel("이름 : ");
		JLabel phoneNumberL = new JLabel("핸드폰 번호 : ");
		JLabel IDL = new JLabel("아이디 : ");
		JLabel passwordL = new JLabel("비밀번호 : ");

		public userAddDialog(JFrame frame, String title) {
			super(frame, title);
			JDialog F = this;
			setLayout(null);
			setSize(200, 270);
			add(submit);
			add(nameT);
			add(phoneNumberT);
			add(nameL);
			add(phoneNumberL);

			nameL.setLocation(10, 10);
			nameL.setSize(100, 30);
			nameT.setLocation(50, 10);
			nameT.setSize(100, 20);

			phoneNumberL.setLocation(10, 50);
			phoneNumberL.setSize(100, 30);
			phoneNumberT.setLocation(50, 50);
			phoneNumberT.setSize(100, 20);

			IDL.setLocation(10, 90);
			IDL.setSize(100, 30);
			IDT.setLocation(50, 90);
			IDT.setSize(100, 20);

			passwordL.setLocation(10, 130);
			passwordL.setSize(100, 30);
			passwordT.setLocation(50, 130);
			passwordT.setSize(100, 20);

			submit.setLocation(50, 210);
			submit.setSize(100, 30);
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = nameT.getText();
					String phonenumber = phoneNumberT.getText();
					String ID = IDT.getText();
					String password = IDT.getText();
					try {
						User user = new User(name, phonenumber, ID, password);
						AlbumShop.UserMgr.addElement(user);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(mFrame, "추가에 실패하였습니다.");
					}
					JOptionPane.showMessageDialog(mFrame, "추가가 완료되었습니다.");
					userInquiryEvent(false);
					F.dispose();
				}
			});
		}
	}
};