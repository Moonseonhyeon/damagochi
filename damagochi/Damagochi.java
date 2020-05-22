package damagochi;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.imageio.ImageIO;

public class Damagochi extends JFrame {
	// Damagochi
	private Damagochi damagochi = this;

	private Container backgroundPanel;
	private JPanel imgBackgroundPanel, menuPanel;
	private JLabel laClock;
	// private ImageIcon dogIcon;
	// private Image dogImage;
	private JButton foodButton, workOutButton, sleepButton;
	private Image dayImg = null;
	private Image nightImg = null;
	private Image dogImg = null;
	private Image foodImg = null;
	private Image zzzImg = null;
	private int x, y;
	private boolean day;
	private int foodX;
	private int zzzX, zzzY;
	public int jj;
	public Status status;
	public JLabel laStatus;
	public boolean availble;

	// Graphics buffg

	public void setDay(boolean day) {
		this.day = day;
	}

	// 클래스를 필드멤버로 선언
	// 0 - 기본상태 , 1 - 밥먹기
	protected int stat = 0;
	Thread tF;

	public Damagochi() {
		day = true;
		availble = false;
		x = 200;
		y = 200;
		initObject();
		initDesign();
		move1();
		initListener();
		try {
			dayImg = ImageIO.read(new File("img/day.png"));
			nightImg = ImageIO.read(new File("img/night.png"));
			dogImg = ImageIO.read(new File("img/dog.png"));
			foodImg = ImageIO.read(new File("img/food.png"));
			zzzImg = ImageIO.read(new File("img/zzzzz.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread tC = new Thread(new Clock(laClock, damagochi));
		tC.start();

		status = new Status();
		
		Thread tS = new Thread(new Status());
		tS.start();

		setContentPane(backgroundPanel);

		// 버튼을누르면 Focus가 되어서 미리 false 해놓음
		foodButton.setFocusable(false);
		workOutButton.setFocusable(false);
		sleepButton.setFocusable(false);

		damagochi.setFocusable(true);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (day) {
			g.drawImage(dayImg, 0, 60, getWidth(), getHeight() - 130, this);
		} else {
			g.drawImage(nightImg, 0, 60, getWidth(), getHeight() - 130, this);
		}

		laStatus.setText("배고픔 : "+status.getHungry()+"  체력 : "+status.getStrength());		
		
		g.drawImage(dogImg, x, y, this);
		if (stat == 1) {
			g.drawImage(foodImg, foodX, 250, 50, 50, this);
		}
		if (stat == 3) {
			g.drawImage(zzzImg, zzzX, zzzY, this);
		}
	}

	public void move1() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 이동속도
				int delta = 10;
				int move = 0;
				while (true) {
					if (stat == 1 || stat == 2 || stat == 3) {
						move = 10;
					} else if (stat == 0) {
						move = 0;
						if (x >= 400) {
							delta = delta * (-1);
						} else if (x <= 0) {
							delta = delta * (-1);
						}
						x = x + delta - move;

//						x = x + 10 - 10; ->밥먹기 눌렀을때
//						x = x + 10 - 0; -> stat=0일때
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
					// a 운동하기 눌리를 때마다 // 밥먹기 버튼 stat 1
					// a는 운동하기 버튼 누르면 false 또는 true로 바뀐다.
//					while (stat ==1) { 
//						repaint();
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
				}
			}
		}).start();
	}

	// 객체생성
	private void initObject() {
		backgroundPanel = getContentPane();
		imgBackgroundPanel = new JPanel();
		menuPanel = new JPanel();
		laClock = new JLabel();
		laClock.setBounds(17, 0, 120, 49);
		laClock.setFont(new Font("굴림체", Font.BOLD, 16));
		
		laStatus = new JLabel("여기");
		laStatus.setBounds(300, 0, 120, 49);
		
		foodButton = new JButton("밥먹기");
		foodButton.setPreferredSize(new Dimension(87, 25));
		foodButton.setFont(new Font("굴림", Font.BOLD, 24));
		
		workOutButton = new JButton("운동하기");
		workOutButton.setFont(new Font("굴림", Font.BOLD, 24));
		
		sleepButton = new JButton("잠자기");
		sleepButton.setFont(new Font("굴림", Font.BOLD, 24));

	
	}

	// 디자인
	private void initDesign() {
		// 1. 기본세팅
		setTitle("다마고치");
		setSize(500, 500);
		setResizable(false);// 창크기 조절 못하게
		setLocationRelativeTo(null); // JFrame을 모니터 중앙에 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 2. 패널세팅
		backgroundPanel.setLayout(new BorderLayout());
		imgBackgroundPanel.setLayout(null);
		menuPanel.setLayout(new GridLayout(1, 3));

		// 3. 디자인
		menuPanel.setPreferredSize(new Dimension(0, 70));
		// menuPanel.setFont(new Font("굴림체", Font.BOLD, 16));

		// 4. 패널(컨테이너)에 컴포넌트 추가
		imgBackgroundPanel.add(laClock);
		imgBackgroundPanel.add(laStatus);
		backgroundPanel.add(imgBackgroundPanel, BorderLayout.CENTER);

		// imgBackgroundPanel.add(dogImage);

		menuPanel.add(foodButton);
		menuPanel.add(workOutButton);
		menuPanel.add(sleepButton);
		backgroundPanel.add(menuPanel, BorderLayout.SOUTH);
	}

	private void initListener() {
		// key
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(stat);
				if (stat == 1 && e.getKeyCode() == KeyEvent.VK_LEFT) {
					x = x - 10;
				}
				if (stat == 1 && e.getKeyCode() == KeyEvent.VK_RIGHT) {
					x = x + 10;
				}
				if (stat == 2 && e.getKeyCode() == KeyEvent.VK_SPACE) {
					// ----------
					int jj = y;
					System.out.println(jj);
					System.out.println(y);

					
					availble = false;
					try {
						while (200 > jj - 150) {
							System.out.println("위로 올라간다");
							y -= 1;
							// label.setLocation(x, y);
							repaint();

							Thread.sleep(10);
						}
						while (200 < jj) {
							System.out.println("내려간다");
							y += 1;
							// label.setLocation(x, y);
							repaint();
							Thread.sleep(10);
						}
						// label.setLocation(x, jj);
						 availble = true;
						// this.interrupt();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					repaint();
					// ----------
				}
//				if (stat == 2 && e.getKeyCode() == KeyEvent.VK_UP) {
//					y = y - 10;
//				}
//				if (stat == 2 && e.getKeyCode() == KeyEvent.VK_DOWN) {
//					y = y + 10;
//				}

				if (x <= foodX + 10 && x >= foodX - 10 && stat == 1) {

					stat = 0;
				}
			}
		});
		// 밥먹기
		foodButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stat = 1;
				foodX = (int) (Math.random() * 450);
			}
		});
		// 운동하기
		workOutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int jj = y;
				System.out.println(jj);
				System.out.println(y);

				// ----------
				availble = false;
				try {
					while (200 > jj - 150) {
						System.out.println("위로 올라간다");
						y -= 1;
						// label.setLocation(x, y);
						repaint();

						Thread.sleep(10);
					}
					while (200 < jj) {
						System.out.println("내려간다");
						y += 1;
						// label.setLocation(x, y);
						repaint();
						Thread.sleep(10);
					}
					// label.setLocation(x, jj);
					 availble = true;
					// this.interrupt();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// 잠자기
		sleepButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				stat = 3;
				zzzX = x + 70;
				zzzY = y - 70;
			}
		});

	}

	public static void main(String[] args) {
		new Damagochi();

	}
}//

class Clock implements Runnable {
	private JLabel label;
	private Damagochi damagochi;

	public Clock(JLabel label, Damagochi damagochi) {
		this.label = label;
		this.damagochi = damagochi;
	}

	@Override
	public void run() {
		while (true) {
			// LocalTime time = LocalTime.now();
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			label.setText(now);
			String[] test = now.split(":");
			//System.out.println(test[2]);
			int ss = Integer.parseInt(test[2]);
			if (ss <= 30) {
				// true 아침
				damagochi.setDay(true);
			} else {
				// false 저녁
				damagochi.setDay(false);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
