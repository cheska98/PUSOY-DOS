package view;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import control.ViewController;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ViewController vc;
	
	private RootPanel rootPanel;
	private StartPanel startPanel;
	private GamePanel gamePanel;
	private int width = 1366;
	private int height = 748;
	
	public MainFrame(ViewController vc) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.err.println("Error in UIManager!");
		}

		this.vc = vc;
		initialize();
		
	}
	
	private void initialize() {
		
		setBounds(0, 0, width, height);
		setResizable(false);
		
		rootPanel = new RootPanel();
		startPanel = new StartPanel(vc);
		
		initializePanels();
		
		rootPanel.setBounds(0, 0, width, height);
		startPanel.setBounds(0, 0, width, height);
		
		rootPanel.add(startPanel, "StartPanel");

		rootPanel.showPanel("StartPanel");
		add(rootPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	public void initializePanels() {
		
		gamePanel = new GamePanel(vc);
		gamePanel.setBounds(0, 0, width, height);
		rootPanel.add(gamePanel, "GamePanel");
		
	}
	
	public void showPanelMF(String name) {
		
		rootPanel.showPanel(name);
		
	}
	
	public StartPanel getStartPanel(){
		return this.startPanel;
	}
	
	public GamePanel getGamePanel(){
		return this.gamePanel;
	}
}
