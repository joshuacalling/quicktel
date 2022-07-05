package telnetApp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 * @author inperj00
 *
 */

public class MainFrame {

	private JFrame frmLazytelV;
	private JTextField userInput;
	private JPasswordField passwordInput;
	private JTextField urlHostNameInput;
	private JTextField portInput;
	String chosenHost = "";
	InputStream in;
	String mainOutput = "";
	JTextArea outputDisplay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmLazytelV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmLazytelV = new JFrame();
		frmLazytelV.setTitle("QuickTel - \u03B2");
		frmLazytelV.getContentPane().setBackground(new Color(0, 128, 128));
		frmLazytelV.setBounds(100, 100, 1069, 659);
		frmLazytelV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLazytelV.getContentPane().setLayout(null);

		JLabel lblHost = new JLabel("PCG Host:");
		lblHost.setForeground(new Color(255, 255, 255));
		lblHost.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		lblHost.setBounds(28, 121, 99, 31);
		frmLazytelV.getContentPane().add(lblHost);

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		lblNewLabel.setBounds(28, 176, 99, 34);
		frmLazytelV.getContentPane().add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		lblPassword.setBounds(28, 235, 99, 20);
		frmLazytelV.getContentPane().add(lblPassword);

		JLabel lblNewLabel_1 = new JLabel("Domain / IP:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		lblNewLabel_1.setBounds(28, 294, 124, 20);
		frmLazytelV.getContentPane().add(lblNewLabel_1);

		JLabel lblPort = new JLabel("Port: ");
		lblPort.setForeground(new Color(255, 255, 255));
		lblPort.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		lblPort.setBounds(28, 351, 69, 20);
		frmLazytelV.getContentPane().add(lblPort);

		userInput = new JTextField();
		userInput.setToolTipText("Your PCG server username.");
		userInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 22));
		userInput.setBounds(152, 173, 289, 37);
		frmLazytelV.getContentPane().add(userInput);
		userInput.setColumns(10);

		passwordInput = new JPasswordField();
		passwordInput.setToolTipText("Your PCG server password.");
		passwordInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 22));
		passwordInput.setBounds(152, 227, 289, 37);
		frmLazytelV.getContentPane().add(passwordInput);

		urlHostNameInput = new JTextField();
		urlHostNameInput.setToolTipText("Partner's domain/IP.");
		urlHostNameInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 22));
		urlHostNameInput.setColumns(10);
		urlHostNameInput.setBounds(152, 286, 289, 37);
		frmLazytelV.getContentPane().add(urlHostNameInput);

		portInput = new JTextField();
		portInput.setToolTipText("Partner's port number.");
		portInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 22));
		portInput.setColumns(10);
		portInput.setBounds(152, 343, 89, 37);
		frmLazytelV.getContentPane().add(portInput);

		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("QuickTel");
		lblNewJgoodiesTitle.setForeground(new Color(255, 255, 255));
		lblNewJgoodiesTitle.setFont(new Font("Bernard MT Condensed", Font.PLAIN, 45));
		lblNewJgoodiesTitle.setBounds(428, 16, 231, 56);
		frmLazytelV.getContentPane().add(lblNewJgoodiesTitle);

		String servers[] = { "Please Select", "uschezlpcg2101", "uschezlpcg2102", "uschlpcg1101", "uschlpcg1102",
				"uschlpcg1103", "uschlpcg1105", "uschlpcg1106", "uschlpcg1107", "uschlpcg1108", "uschezlpcg1103",
				"uschezlpcg1104", "uschezlpcg1105", "uschezlpcg1106", "uschezlpcg1107", "uschezlpcg1108" };
		JComboBox hostInput = new JComboBox(servers);
		hostInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 22));
		hostInput.setBounds(152, 118, 289, 37);
		frmLazytelV.getContentPane().add(hostInput);
		hostInput.setEditable(true);
		hostInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chosenHost = (String) hostInput.getSelectedItem();
			}
		});

		JButton reset = new JButton("Clear ");
		reset.setForeground(new Color(255, 255, 255));
		reset.setBackground(new Color(0, 128, 128));
		reset.setToolTipText("Clear Output");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputDisplay.setText("");
			}
		});
		reset.setFont(new Font("Baskerville Old Face", Font.BOLD, 22));
		reset.setBounds(258, 412, 183, 37);
		frmLazytelV.getContentPane().add(reset);

		JButton command = new JButton("Execute");
		command.setForeground(new Color(255, 255, 255));
		command.setBackground(new Color(0, 128, 128));
		command.setToolTipText("Perform Telnet Command");
		command.setFont(new Font("Baskerville Old Face", Font.BOLD, 22));
		command.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String host = chosenHost;
					String user = userInput.getText();
					String password = String.valueOf(passwordInput.getPassword());
					String telnet = "echo -e '\\x1dclose\\x0d' | telnet";
					String urlHostName = urlHostNameInput.getText();
					String port = portInput.getText();
					outputDisplay.setText("");
					try {
						if (host != "Please Select" && !host.isEmpty() && host != null && !user.isEmpty()
								&& user != null && !password.isEmpty() && password != null && !urlHostName.isEmpty()
								&& urlHostName != null && !port.isEmpty() && port != null) {
							outputDisplay.append("Establishing connection to " + host + "...\n");
							java.util.Properties config = new java.util.Properties();
							config.put("StrictHostKeyChecking", "no");
							JSch jsch = new JSch();
							Session session = jsch.getSession(user, host, 22);
							session.setPassword(password);
							session.setConfig(config);
							try {
								session.connect();
								System.out.println("Connected to " + host);
								outputDisplay.append("Connected " + user + "@" + host + "\n");
								outputDisplay.append("telnet " + urlHostName + " " + port + "\n");
								Channel channel = session.openChannel("exec");
								((ChannelExec) channel).setCommand("ls");
								((ChannelExec) channel).setCommand(telnet + " " + urlHostName + " " + port);
								channel.setInputStream(null);
								((ChannelExec) channel).setErrStream(System.err);

								StringBuilder outputBuffer = new StringBuilder();
								StringBuilder errorBuffer = new StringBuilder();

								in = channel.getInputStream();
								InputStream err = channel.getExtInputStream();

								channel.connect();
								byte[] tmp = new byte[1024];
								while (true) {
									while (in.available() > 0) {
										int i = in.read(tmp, 0, 1024);
										if (i < 0)
											break;
										mainOutput = new String(tmp, 0, i);
										outputBuffer.append(new String(tmp, 0, i));
									}
									while (err.available() > 0) {
										int i = err.read(tmp, 0, 1024);
										if (i < 0)
											break;
										errorBuffer.append(new String(tmp, 0, i));
									}
									if (channel.isClosed()) {
										if ((in.available() > 0) || (err.available() > 0))
											continue; // new
										System.out.println("exit-status: " + channel.getExitStatus());
										break;
									}
									try {
										Thread.sleep(1000);
									} catch (Exception ee) {
									}
								}

								System.out.println("output: " + outputBuffer.toString());
								System.out.println("error: " + errorBuffer.toString());

								outputDisplay.append(outputBuffer.toString());
								outputDisplay.append(errorBuffer.toString());
								outputDisplay.append("exit-status: " + channel.getExitStatus());

								channel.disconnect();
								session.disconnect();
								System.out.println("DONE");
							} catch (Exception except) {
								except.printStackTrace();
								outputDisplay.append("Authentication Error \n");
							}
						} else {
							outputDisplay.append("Error - All fields are mandatory. \n");
						}
					} catch (Exception emptyField) {
						outputDisplay.append("An error has occured. \n");
					}
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		command.setBounds(29, 412, 183, 37);
		frmLazytelV.getContentPane().add(command);

		outputDisplay = new JTextArea();
		outputDisplay.setToolTipText("Unix O/P");
		outputDisplay.setWrapStyleWord(true);
		outputDisplay.setForeground(new Color(255, 255, 255));
		outputDisplay.setBackground(new Color(0, 0, 0));
		outputDisplay.setEditable(false);
		outputDisplay.setFont(new Font("Monospaced", Font.PLAIN, 16));
		outputDisplay.setBounds(456, 88, 576, 467);
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		outputDisplay.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(30, 144, 255)));
		frmLazytelV.getContentPane().add(outputDisplay);

		JScrollPane scrollPane = new JScrollPane(outputDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frmLazytelV.getContentPane().add(scrollPane);
		scrollPane.setBounds(456, 88, 576, 467);

		JButton btnCreateFwcrSpreadsheet = new JButton("Create FWCR Spreadsheet");
		btnCreateFwcrSpreadsheet.setForeground(new Color(255, 255, 255));
		btnCreateFwcrSpreadsheet.setBackground(new Color(0, 128, 128));
		btnCreateFwcrSpreadsheet.setFont(new Font("Baskerville Old Face", Font.BOLD, 22));
		btnCreateFwcrSpreadsheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FWCR_SpreadSheet spreadSheetInputModal = new FWCR_SpreadSheet(servers);
					spreadSheetInputModal.newModal(servers);

				} catch (Exception error) {
					System.out.println(error);
				}
			}
		});
		btnCreateFwcrSpreadsheet.setBounds(55, 493, 358, 34);
		frmLazytelV.getContentPane().add(btnCreateFwcrSpreadsheet);
		frmLazytelV.setVisible(true);

	}
}
