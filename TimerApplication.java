package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TimerApplication {

	// fields for various elements contained in the main window
	private JFrame frmTimer;
	private JTextField inputSeconds;
	private int timeLeft;
	private Timer timer;
	private JLabel timeLeftText;
	private JLabel alertEnd;
	private JButton pauseTimer;
	private JLabel errorMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimerApplication window = new TimerApplication();
					window.frmTimer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimerApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// set the size, color, etc. of the window
		frmTimer = new JFrame();
		frmTimer.setTitle("Timer");
		frmTimer.getContentPane().setBackground(new Color(255, 255, 255));
		frmTimer.setResizable(false);
		frmTimer.setBounds(100, 100, 300, 300);
		frmTimer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTimer.getContentPane().setLayout(null);
		
		// initialize the timer to fire every second when started
		this.timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleTimerEvent(e);
			}
		});
		
		// the label and its styling to dispay the time left
		timeLeftText = new JLabel("0");
		timeLeftText.setFont(new Font("Courier", Font.PLAIN, 90));
		timeLeftText.setForeground(new Color(0, 0, 0));
		timeLeftText.setHorizontalAlignment(SwingConstants.CENTER);
		timeLeftText.setBounds(6, 36, 288, 88);
		frmTimer.getContentPane().add(timeLeftText);
		
		// the button that allows the user to set the start time of the timer
		//
		// when the button reads "set", the user's input is stored in a varable
		// and the pause+play/reset buttons are displayed
		//
		// when the button reads "reset", the start time is set to 0 and the
		// pause+play/reset buttons are hidden as the set button and textbox return
		JButton setTimer = new JButton("Set");
		setTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setTimer.getText() == "Set") {
					int getInput = Integer.parseInt(inputSeconds.getText());
					if(getInput > 0 && getInput <= 9999) {
						timeLeft = getInput;
						timeLeftText.setText(Integer.toString(timeLeft));
						setTimer.setText("Reset");
						pauseTimer.setVisible(true);
						inputSeconds.setText("");
						inputSeconds.setVisible(false);
						errorMessage.setVisible(false);
					} else {
						errorMessage.setVisible(true);
					}
				} else if (setTimer.getText() == "Reset") {
					timer.stop();
					timeLeftText.setText("0");
					setTimer.setText("Set");
					pauseTimer.setText("Start");
					inputSeconds.setVisible(true);
					alertEnd.setVisible(false);
					pauseTimer.setVisible(false);
				}
			}
		});
		setTimer.setBounds(100, 243, 100, 29);
		frmTimer.getContentPane().add(setTimer);
		
		// start/stop the timer depending on what the button reads
		pauseTimer = new JButton("Start");
		pauseTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pauseTimer.getText() == "Start") {
					timer.start();
					pauseTimer.setText("Pause");
				} else if (pauseTimer.getText() == "Pause") {
					timer.stop();
					pauseTimer.setText("Start");
				}
			}
		});
		pauseTimer.setBounds(100, 202, 100, 29);
		pauseTimer.setVisible(false);
		frmTimer.getContentPane().add(pauseTimer);
		
		// the textbox where users enter the start time of the timer
		inputSeconds = new JTextField();
		inputSeconds.setBounds(100, 205, 100, 26);
		frmTimer.getContentPane().add(inputSeconds);
		inputSeconds.setColumns(10);
		
		// initially hidden, label alerts the user when the time is up
		alertEnd = new JLabel("Time's up!");
		alertEnd.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		alertEnd.setHorizontalAlignment(SwingConstants.CENTER);
		alertEnd.setBounds(66, 156, 166, 41);
		alertEnd.setVisible(false);
		frmTimer.getContentPane().add(alertEnd);
		
		// error message that is displayed if a proper number is not entered
		errorMessage = new JLabel("Please enter a number between 1-9999");
		errorMessage.setForeground(Color.RED);
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setBounds(6, 181, 288, 16);
		errorMessage.setVisible(false);
		frmTimer.getContentPane().add(errorMessage);
	}
	
	// when the timer event fires, decrement the seconds left by 1
	// if there is no time left, stop the timer, alert the user,
	// and hide the pause+play button
	protected void handleTimerEvent(ActionEvent e) {
		if(timeLeft <= 1) {
			timer.stop();
			alertEnd.setVisible(true);
			pauseTimer.setVisible(false);
		}
		timeLeft -= 1;
		timeLeftText.setText(Integer.toString(timeLeft));
		frmTimer.revalidate();
	}
}
