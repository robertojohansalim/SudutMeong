package AbstractClass;

import java.security.ProtectionDomain;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Controllers.EmployeeHandler;
import Controllers.LoginHandler;

public abstract class AbstractView extends JFrame{

    private int width;
    private int height;

    protected JMenuBar menuBar;
    protected JMenu menu1;
    protected JMenuItem menuItem_logout;

    public AbstractView(int width, int height){
        this.width = width;
        this.height = height;
        addComponents();
        addListeners();
        configureFrame();
    }

    public abstract void addComponents();
    public abstract void addListeners();

    protected void configureFrame(){
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // protected void configureMenuBar(){
	// 	menuBar = new JMenuBar();
	// 	menuItem_logout = new JMenuItem("Logout");
	// 	menuBar.add(menuItem_logout);

	// 	setJMenuBar(menuBar);
	// }

    /**Close Window: setVisible(false) */
    protected void closeView(){
        setVisible(false);
    }

}
