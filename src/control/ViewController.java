package control;

import view.MainFrame;
import view.StartPanel;

public class ViewController {

	private MainFrame p1;
	private StartPanel startPanel;

	public ViewController() {

		p1 = new MainFrame(this);
		startPanel = new StartPanel(this);

	}

	public void showPanelPC1(String name) {

		p1.showPanelMF(name);

	}
}
