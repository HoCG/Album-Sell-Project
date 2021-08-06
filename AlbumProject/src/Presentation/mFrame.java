package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import javax.swing.event.*;

import Data.*;


//������Ʈ ��������: 1.���Ÿ� �ϰ��� ������ ���̺�� ������ ��ȸ������ ������ �߻��ϴ� ������ ����.

public class mFrame extends JFrame {
	AlbumShop albumShop = new AlbumShop();
	mFrame mFrame = this;

	JPanel mPanel;
	JPanel hmPanel;
	JPanel whPanel;

	JLabel title;
	JLabel subTitle;
	JButton[] jitem; // JButton�� ������ ���ϰ��̴� �迭�� ����
	JButton[] inqButton;
	JButton[] menuButton;
	JTable jTable;
	JTextArea search;

	String contents[][];
	String header[];

	public static void main(String[] args) {
		new mFrame();
	}

	public mFrame() { // ����������
		AlbumShop.run();
		this.setSize(1400, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �����г�
		mPanel = new JPanel();
		mPanel.setLayout(null);

		// �����г�
		hmPanel = new hmPanel();
		hmPanel.setLayout(null);
		hmPanel.setSize(1100, 550);
		hmPanel.setLocation(250, 100);

		// ���� ��
		title = new JLabel("�ٹ� �� ������Ʈ ver1.0");
		title.setFont(title.getFont().deriveFont(32.0f));
		title.setSize(500, 50);
		title.setLocation(50, 30);
		mPanel.add(title);
		// �����г� ��ư����
		jitem = new JButton[4];
		jitem[0] = new JButton("��������");
		jitem[1] = new JButton("������");
		jitem[2] = new JButton("����");
		jitem[3] = new JButton("����");
		for (int i = 0; i <= 3; i++) {
			jitem[i].setSize(150, 100);
			jitem[i].setLocation(50, 100 + 150 * i);
			jitem[i].setHorizontalAlignment(SwingConstants.CENTER);
			jitem[i].setVerticalAlignment(SwingConstants.CENTER);
			mPanel.add(jitem[i]);
		}
		mPanel.add(hmPanel); // ����ȭ��

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
	
	
	//�̱��� ����.
	class StoreListener implements ActionListener { //���� ���� �޴�

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}

	}

	class OrderListener implements ActionListener { //����޴�
		Artist savedArtist; //������ ���� Ȱ��� �� ��ü��. ���� ������ ���ؼ��� Ȱ���.
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
			JLabel MainLabel = new JLabel("�α���");
			MainLabel.setFont(MainLabel.getFont().deriveFont(64.0f));
			MainLabel.setBounds(450, 50, 400, 200);
			whPanel.add(MainLabel);

			JLabel lblLogin = new JLabel("�����̸�:");
			lblLogin.setFont(lblLogin.getFont().deriveFont(16.0f));
			lblLogin.setBounds(405, 243, 100, 50);
			whPanel.add(lblLogin);

			JLabel lblPassword = new JLabel("��й�ȣ:");
			lblPassword.setFont(lblLogin.getFont().deriveFont(16.0f));
			lblPassword.setBounds(405, 292, 100, 50); // 51����
			whPanel.add(lblPassword);

			tfUsername = new JTextField();
			tfUsername.setBounds(507, 252, 176, 35);
			whPanel.add(tfUsername);
			
			tfPassword = new JPasswordField();
			tfPassword.setColumns(10);
			tfPassword.setEchoChar('*');
			tfPassword.setBounds(507, 303, 176, 35);
			whPanel.add(tfPassword);


			joinBtn = new JButton("ȸ������");
			joinBtn.setBounds(579, 354, 104, 29);
			whPanel.add(joinBtn);

			loginBtn = new JButton("�α���");
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
							JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "���̵� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
					}
				}
			});

			joinBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					User_Sign_Up();
				}
			});
			mPanel.remove(hmPanel); // �ʱ��г��� hm�г��� ����
			whPanel.repaint(); // �������� ������ ���.���� �гη� �Ѿ�� ����
		}

		void User_Sign_Up() {
			JLabel lblJoin;
			JButton joinCompleteBtn;
			initiatePanel();

			JButton back = new JButton("�ڷΰ���");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginUser(false);
				}
			});

			lblJoin = new JLabel("ȸ������");
			Font f1 = new Font("����", Font.BOLD, 20); // �ü� ���� ����
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

			joinCompleteBtn = new JButton("ȸ�����ԿϷ�");
			joinCompleteBtn.setBounds(506, 363, 139, 29);
			whPanel.add(joinCompleteBtn);

			// ȸ�����ԿϷ� �׼�
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
								//���ο� ������ ������ ��ü�� �迭�� �������.
								User NewUser = new User(NewName, NewID, NewPasswords, NewPhoneNumber);
								AlbumShop.UserMgr.addElement(NewUser);
								JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
								LoginUser(false);
							} else
								JOptionPane.showMessageDialog(null, "��й�ȣ ���̰� �ʹ� ª���ϴ�. �ٽ� �Է����ּ���.");
						} else
							JOptionPane.showMessageDialog(null, "������ ���̵� �ֽ��ϴ�. �ٽ� �Է����ּ���.");
					} else if (NewName.isEmpty())
						JOptionPane.showMessageDialog(null, "�̸��� �Է����ּ���");
					else if (NewID.isEmpty())
						JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
					else if (NewPasswords.isEmpty())
						JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���");
					else if (NewPhoneNumber.isEmpty())
						JOptionPane.showMessageDialog(null, "�ڵ��� ��ȣ�� �Է����ּ���");
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
			String ItemImfor[] = { "��Ƽ��Ʈ ����", "��ü�ٹ� ����", "�帣�� ����" };
			JComboBox ChooseWhatYouWant = new JComboBox(ItemImfor);
			ChooseWhatYouWant.setFont(getFont().deriveFont(17.0f));
			ChooseWhatYouWant.setSize(ItemImfor.length * 70, 30);
			ChooseWhatYouWant.setLocation(25, 75);
			ChooseWhatYouWant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource(); // �޺��ڽ� �˾Ƴ���
					int index = cb.getSelectedIndex();// ���õ� �������� �ε���
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
			setText("��Ƽ��Ʈ�� �����ϱ�");
			selectWantPanel();
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			JButton back = new JButton("�ڷΰ���");

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
						selectAlbum(); // ���� �Լ��� �Ѿ�Բ� ����
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
			setText("��Ƽ��Ʈ �ٹ� ����");
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			JButton back = new JButton("�ڷΰ���");

			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectArtist();
				}
			});

			ArrayList<Album> list = savedArtist.getAlbumList(); // getAlbumList�� �����ǵ��� Artist�� �����.
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
			setText("�ٹ���ü���� �����ϱ�");
			selectWantPanel();
			JPanel buttonBox = new JPanel();
			buttonBox.setLayout(null);
			JButton back = new JButton("�ڷΰ���");

			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginUser(false);
				}
			});

			ArrayList<Album> list = AlbumShop.AlbumMgr.mList; // getAlbumList�� �����ǵ��� Artist�� �����.
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

		// �̱���. ������ ����.
		void select_By_Genre_Album() {
			initiatePanel();
			setText("�帣���� �����ϱ�");
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
					JComboBox cb = (JComboBox) e.getSource(); // �޺��ڽ� �˾Ƴ���
					int index = cb.getSelectedIndex();// ���õ� �������� �ε���
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
			
			JButton back = new JButton("�ڷΰ���");
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
			setText("���� ���� ����");

			JButton back = new JButton("�ڷΰ���");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectArtist();
				}
			});

			JLabel[] explain = new JLabel[7];

			// ȭ�鿡�� ������ �ٹ� Ŀ�� ����.
			ImageIcon icon = new ImageIcon("./IMG/album/" + savedAlbum.getName().replaceAll(" ", "") + ".jpg");
			if (icon == null) {
				icon = new ImageIcon("./IMG/noImage");
			}
			JLabel ForImage = new JLabel();
			ForImage.setIcon(icon);
			ForImage.setSize(300, 300);
			ForImage.setLocation(50, 100);
			whPanel.add(ForImage);

			// ���� ���
			explain[0] = new JLabel("ȸ�����̵� : " + savedUser.getUserId());
			explain[1] = new JLabel("��ǰ�� : " + savedAlbum.getName());
			explain[2] = new JLabel("���� : " + savedAlbum.getPrice());
			explain[3] = new JLabel("���ż��� : ");
			explain[4] = new JLabel("��밡�� ����Ʈ : " + Integer.toString(savedUser.getPoint()));
			explain[5] = new JLabel("����Ʈ ���: ");
			explain[6] = new JLabel("�� ���Ű���: ");

			int count = 0;
			for (JLabel jLabel : explain) {
				jLabel.setSize(300, 50);
				jLabel.setLocation(400, 100 + 50 * count);
				whPanel.add(jLabel);
				count++;
			}
			JTextField UsePoint = new JTextField();
			UsePoint.setBounds(487, 355, 106, 35);

			// �ٹ� ������ �޺��ڽ��� ���.
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
					JComboBox cb = (JComboBox) e.getSource(); // �޺��ڽ� �˾Ƴ���
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

			// ���Ź�ư
			JButton Buy = new JButton("����");
			Buy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Integer.parseInt(AlbumCount.getSelectedItem().toString()) > 0) {
						// ����Ʈ�� ������ ��������.
						int LastPay = 0;
						if (UsePoint.getText().isEmpty()) {
							LastPay = Integer.parseInt(AllPay.getText());
						} else
							LastPay = Integer.parseInt(AllPay.getText()) - Integer.parseInt(UsePoint.getText());
						// �ֹ� ������ �����ϴ� ����
						OrderedAlbum oa = new OrderedAlbum(savedUser.getUserId(),
								Integer.parseInt(AlbumCount.getSelectedItem().toString()), LastPay,
								savedAlbum.getName());
						AlbumShop.OrderedAlbumMgr.addElement(oa);
						// ��� ����Ʈ��ŭ ������ ����Ʈ ����. �̶� ���������� ����Ʈ ������ ���� ������ Ŭ���������� ��ü������ �̷������.
						if (!UsePoint.getText().isEmpty()) {
							savedUser.point = savedUser.getPoint() - Integer.parseInt(UsePoint.getText());
							AlbumShop.UserMgr.UpdateData(savedUser);
						}
						buyComplete();
					} else
						JOptionPane.showMessageDialog(null, "������ ����� �������� �ʾҽ��ϴ�. �ٽ� �������ּ���.");
				}
			});
			Buy.setSize(100, 36);
			Buy.setLocation(487, 455);
			Buy.setHorizontalAlignment(SwingConstants.CENTER);
			Buy.setVerticalAlignment(SwingConstants.CENTER);
			whPanel.add(Buy);

			mPanel.repaint();
		}

		// ���ſϷ�
		void buyComplete() {
			initiatePanel();
			JLabel main = new JLabel("���Ű� �Ϸ�Ǿ����ϴ�.");
			JLabel[] explain = new JLabel[4];

			explain[0] = new JLabel("ȸ�����̵� : " + savedUser.getName());
			explain[1] = new JLabel("�޴��� : " + savedAlbum.getName());
			explain[2] = new JLabel("���� ����Ʈ : " + FinalPay / 1000);
			explain[2] = new JLabel("�� ���Ű���: " + FinalPay);

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
			main.setFont(new Font("���� ���", Font.BOLD, 50));
			main.setLocation(whPanel.getWidth() / 2 - main.getWidth() / 2, whPanel.getHeight() / 2 - main.getHeight());
			whPanel.add(main);
			mPanel.remove(hmPanel);
			mPanel.repaint();
		}
	}

	class InquiryListener implements ActionListener { // ��ȸ�޴�

		@Override
		public void actionPerformed(ActionEvent e) {
			initiatePanel();
			setText("����������");

			inqButton = new JButton[4];
			inqButton[0] = new JButton("�ٹ�");
			inqButton[1] = new JButton("��Ƽ��Ʈ");
			inqButton[2] = new JButton("���ų���");
			inqButton[3] = new JButton("��");

			for (JButton button : inqButton) { // ������ ��ư�� ���� ��������
				button.setSize(150, 100);
				button.setHorizontalAlignment(SwingConstants.CENTER);
				button.setVerticalAlignment(SwingConstants.CENTER);
				whPanel.add(button);
			}

			inqButton[0].setLocation(whPanel.getWidth() / 2 - 75 - 150, 150); // ��ư�� ��ġ�� ����. �̷� �������� ������ ������ �ʿ䰡 ����.
			inqButton[1].setLocation(whPanel.getWidth() / 2 - 75 + 150, 150);
			inqButton[2].setLocation(whPanel.getWidth() / 2 - 75 - 150, 350);
			inqButton[3].setLocation(whPanel.getWidth() / 2 - 75 + 150, 350);

			inqButton[0].addActionListener(new ActionListener() { // �ٹ���ȸ
				@Override
				public void actionPerformed(ActionEvent e) {
					albumInquiryEvent(false);
				}
			});

			inqButton[1].addActionListener(new ActionListener() { // ��Ƽ��Ʈ��ȸ
				@Override
				public void actionPerformed(ActionEvent e) {
					ArtistInquiryEvent(false);
				}
			});

			inqButton[2].addActionListener(new ActionListener() { // ���ų�����ȸ
				@Override
				public void actionPerformed(ActionEvent e) {
					PurchaseInquiryEvent(false);
				}
			});

			inqButton[3].addActionListener(new ActionListener() { // ����ȸ
				@Override
				public void actionPerformed(ActionEvent e) {
					userInquiryEvent(false);
				}
			});

			mPanel.remove(hmPanel);
			mPanel.repaint();
		}
	}

	public void albumInquiryEvent(boolean refresh) { // �޴���ȸ ���� �޼ҵ�
		initiatePanel();
		setText("�޴�");
		getDefaultInqueryMenu(); // �⺻�޴� ��������
		header = new String[] { "�̸�", "�帣", "����", "��Ƽ��Ʈ", "����" }; //�޴��׸�
		if (!refresh) {
			contents = AlbumShop.AlbumToTable(); // ����Ʈ ��������
			jTable = new JTable(contents, header); // ���̺� ����
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		menuButton[1].addActionListener(new ActionListener() { // �߰����
			@Override
			public void actionPerformed(ActionEvent e) {
				AlbumAddDialog AlbumAddDialog = new AlbumAddDialog(mFrame.mFrame, "�޴� �߰�");
				AlbumAddDialog.setVisible(true);
			}
		});
		menuButton[2].addActionListener(new ActionListener() { // �˻����
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<Album> AlbumList = AlbumShop.AlbumMgr.findAll(key);
				contents = AlbumShop.AlbumToTable(AlbumList);
				jTable = new JTable(contents, header); // ���̺� ����
				albumInquiryEvent(true);
			}
		});
		menuButton[3].addActionListener(new ActionListener() { // �������
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

	public void userInquiryEvent(boolean refresh) { // ����ȸ ���� �޼ҵ�
		initiatePanel();
		setText("��");
		getDefaultInqueryMenu(); // �⺻�޴� ��������
		header = new String[] { "�̸�", "ID", "�ڵ��� ��ȣ", "���", "����Ʈ", "��������Ʈ" }; // �� �޴��׸�
		if (!refresh) {
			contents = AlbumShop.UserToTable(); // ����Ʈ ��������
			jTable = new JTable(contents, header); // ���̺� ����
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		menuButton[1].addActionListener(new ActionListener() { // �߰����
			@Override
			public void actionPerformed(ActionEvent e) {
				userAddDialog userAddDialog = new userAddDialog(mFrame, "�մ� �߰�");
				userAddDialog.setVisible(true);
			}
		});
		menuButton[2].addActionListener(new ActionListener() { // �˻�
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<User> UserList = AlbumShop.UserMgr.findAll(key);
				contents = AlbumShop.UserToTable(UserList);
				jTable = new JTable(contents, header); // ���̺� ����
				userInquiryEvent(true);
			}
		});
		menuButton[3].addActionListener(new ActionListener() { // ����
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

	// �̱���
	public void ArtistInquiryEvent(boolean refresh) {
		initiatePanel();
		setText("��Ƽ��Ʈ");
		getDefaultInqueryMenu(); // �⺻�޴� ��������
		header = new String[] { "�̸�", "����", "�����帣", "���ڱ׷���" };
		if (!refresh) {
			contents = AlbumShop.ArtistToTable(); // ����Ʈ ��������
			jTable = new JTable(contents, header); // ���̺� ����
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		menuButton[1].addActionListener(new ActionListener() { // �߰����
			@Override
			public void actionPerformed(ActionEvent e) {
				artistAddDialog artistAddDialog = new artistAddDialog(mFrame, "��Ƽ��Ʈ �߰�");
				artistAddDialog.setVisible(true);
			}
		});
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<Artist> ArtistList = AlbumShop.ArtistMgr.findAll(key);
				contents = AlbumShop.ArtistToTable(ArtistList);
				jTable = new JTable(contents, header); // ���̺� ����
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

	public void PurchaseInquiryEvent(boolean refresh) { // ���ų�����ȸ ���� �޼ҵ�
		mPanel.remove(hmPanel);
		mPanel.remove(whPanel);
		initiatePanel();
		setText("����");
		getDefaultInqueryMenu(); // �⺻�޴� ��������
		header = new String[] { "��ID", "��¥", "���žٹ�", "�ֹ�����", "�Ѱ���" }; // �� �޴��׸�
		if (!refresh) {
			contents = AlbumShop.AlbumOrderToTable(); // ����Ʈ ��������
			jTable = new JTable(contents, header); // ���̺� ����
		}
		JScrollPane jscroll = new JScrollPane(jTable);
		jscroll.setSize(1000, 350);
		jscroll.setLocation(whPanel.getWidth() / 2 - jscroll.getWidth() / 2, 150);

		whPanel.remove(menuButton[1]); // ������ ����
		menuButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = search.getText();
				ArrayList<OrderedAlbum> orderList = AlbumShop.OrderedAlbumMgr.findAll(key);
				contents = AlbumShop.AlbumOrderToTable(orderList);
				jTable = new JTable(contents, header); // ���̺� ����
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

	public void getDefaultInqueryMenu() { // ��ȸ ���� �޴� �ʱ�ȭ. �޴��κ����� �����ϰ� ��밡��.
		menuButton = new JButton[4];
		search = new JTextArea();
		menuButton[0] = new JButton("�ڷ�");
		menuButton[1] = new JButton("�߰�");
		menuButton[2] = new JButton("�˻�");
		menuButton[3] = new JButton("����");
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
		search.setFont(new Font("���� ���", Font.BOLD, 46));
		search.setBorder(new TitledBorder(new LineBorder(Color.black)));
		whPanel.add(search);
	}

	// �г��� ������ ���� ��������.
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


	class AlbumAddDialog extends JDialog { // ���̾Ʒα�
		JButton submit = new JButton("���");
		JTextArea nameT = new JTextArea();
		JTextArea priceT = new JTextArea();
		JTextArea ArtistT = new JTextArea();
		JTextArea countT = new JTextArea();
		JTextArea genreT = new JTextArea();

		JLabel nameL = new JLabel("�̸� : ");
		JLabel priceL = new JLabel("���� : ");
		JLabel ArtistL = new JLabel("��Ƽ��Ʈ : "); // spanL
		JLabel countL = new JLabel("���� : "); // genderL
		JLabel genreL = new JLabel("�帣: ");

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
						JOptionPane.showMessageDialog(mFrame, "�߰��� �����Ͽ����ϴ�.");
					}
					JOptionPane.showMessageDialog(mFrame, "�߰��� �Ϸ�Ǿ����ϴ�.");
					albumInquiryEvent(false);
					if (AlbumShop.ArtistMgr.find(Artist) == null) {
						F.dispose();
						JOptionPane.showMessageDialog(mFrame, "��Ƽ��Ʈ ������ �����ϴ�. �ش� ��Ƽ��Ʈ �������� �߰� ���ֽðھ��?");
						artistAddDialog AAD = new artistAddDialog(frame, "��Ƽ��Ʈ �߰�");
					}
					F.dispose();
				}
			});
		}
	}

	class artistAddDialog extends JDialog {
		JButton submit = new JButton("���");
		JTextArea idT = new JTextArea();
		JTextArea nameT = new JTextArea();
		JTextArea genreT = new JTextArea();

		JLabel idL = new JLabel("��Ƽ��Ʈ�� : ");
		JLabel nameL = new JLabel("���� : ");
		JLabel genreL = new JLabel("�����帣 : ");

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
						JOptionPane.showMessageDialog(mFrame, "�߰��� �����Ͽ����ϴ�.");
					}
					JOptionPane.showMessageDialog(mFrame, "�߰��� �Ϸ�Ǿ����ϴ�.");
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
						JOptionPane.showMessageDialog(mFrame, "�߰��� �����Ͽ����ϴ�.");
					}
					JOptionPane.showMessageDialog(mFrame, "�߰��� �Ϸ�Ǿ����ϴ�.");
					albumInquiryEvent(false);
					F.dispose();
				}
			});
		}
	}

	class userAddDialog extends JDialog {
		JButton submit = new JButton("���");
		JTextArea nameT = new JTextArea();
		JTextArea phoneNumberT = new JTextArea();
		JTextArea IDT = new JTextArea();
		JTextArea passwordT = new JTextArea();

		JLabel nameL = new JLabel("�̸� : ");
		JLabel phoneNumberL = new JLabel("�ڵ��� ��ȣ : ");
		JLabel IDL = new JLabel("���̵� : ");
		JLabel passwordL = new JLabel("��й�ȣ : ");

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
						JOptionPane.showMessageDialog(mFrame, "�߰��� �����Ͽ����ϴ�.");
					}
					JOptionPane.showMessageDialog(mFrame, "�߰��� �Ϸ�Ǿ����ϴ�.");
					userInquiryEvent(false);
					F.dispose();
				}
			});
		}
	}
};