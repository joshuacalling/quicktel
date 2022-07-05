package telnetApp;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.opencsv.CSVWriter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;
import java.io.*;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Button;
import javax.swing.SwingConstants;

public class FWCR_SpreadSheet {

	private JFrame frmFwcrSpreadSheet;
	private JTextField destHostNameInput;
	private JTextField destIpInput;
	private JTextField destPortInput;
	private JTextField protocolInput;
	private JTextArea ruleDescriptionInput;

	public String stageDestHostName;
	public String stageDestIp;
	public String stageDestPort;
	public String stageProtocol;
	public String stageRuleDescription;

	public String prodDestHostName;
	public String prodDestIp;
	public String prodDestPort;
	public String prodProtocol;
	public String prodRuleDescription;

	private String envSelected = "";
	final JFileChooser fc = new JFileChooser();
	private File selectedFile;
	private ArrayList<String> spreadSheetCount = new ArrayList<>();
	private JTextField textFieldPath;

	/**
	 * Launch the application.
	 * 
	 * @param servers
	 */
	public static void newModal(String[] servers) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FWCR_SpreadSheet window = new FWCR_SpreadSheet(servers);
					window.frmFwcrSpreadSheet.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param servers
	 */
	public FWCR_SpreadSheet(String[] servers) {
		initialize(servers);

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param servers
	 */
	private void initialize(String[] servers) {

		frmFwcrSpreadSheet = new JFrame();
		frmFwcrSpreadSheet.getContentPane().setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		frmFwcrSpreadSheet.getContentPane().setForeground(new Color(255, 255, 255));
		frmFwcrSpreadSheet.setTitle("FWCR Spreadsheet Creator");
		frmFwcrSpreadSheet.getContentPane().setBackground(new Color(0, 128, 128));
		frmFwcrSpreadSheet.getContentPane().setLayout(null);

		JLabel lblDestinationHostName = new JLabel("Dest. Host Name:");
		lblDestinationHostName.setForeground(new Color(255, 255, 255));
		lblDestinationHostName.setBounds(35, 175, 196, 20);
		lblDestinationHostName.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationHostName);

		JLabel lblDestinationIps = new JLabel("Destination IP(s):");
		lblDestinationIps.setForeground(new Color(255, 255, 255));
		lblDestinationIps.setBounds(35, 225, 196, 20);
		lblDestinationIps.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationIps);

		JLabel lblDestinationHostName_1_1 = new JLabel("Destination Port:");
		lblDestinationHostName_1_1.setForeground(new Color(255, 255, 255));
		lblDestinationHostName_1_1.setBounds(35, 318, 196, 20);
		lblDestinationHostName_1_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationHostName_1_1);

		destHostNameInput = new JTextField();
		destHostNameInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		destHostNameInput.setBounds(216, 172, 306, 26);
		frmFwcrSpreadSheet.getContentPane().add(destHostNameInput);
		destHostNameInput.setColumns(10);

		destIpInput = new JTextField();
		destIpInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		destIpInput.setBounds(216, 225, 306, 65);
		frmFwcrSpreadSheet.getContentPane().add(destIpInput);
		destIpInput.setColumns(10);

		destPortInput = new JTextField();
		destPortInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		destPortInput.setBounds(216, 315, 306, 26);
		destPortInput.setColumns(10);
		frmFwcrSpreadSheet.getContentPane().add(destPortInput);

		JLabel lblMultipleIpsMust = new JLabel("Multiple IPs must be");
		lblMultipleIpsMust.setForeground(new Color(255, 255, 255));
		lblMultipleIpsMust.setBounds(35, 249, 196, 32);
		frmFwcrSpreadSheet.getContentPane().add(lblMultipleIpsMust);

		JLabel lblCommaSeperated = new JLabel("comma seperated.");
		lblCommaSeperated.setForeground(new Color(255, 255, 255));
		lblCommaSeperated.setBounds(35, 270, 196, 32);
		frmFwcrSpreadSheet.getContentPane().add(lblCommaSeperated);

		JLabel lblDestinationHostName_1_1_1 = new JLabel("Std. Protocol Name:");
		lblDestinationHostName_1_1_1.setForeground(new Color(255, 255, 255));
		lblDestinationHostName_1_1_1.setBounds(35, 357, 196, 20);
		lblDestinationHostName_1_1_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationHostName_1_1_1);

		JLabel lblDestinationHostName_1_1_1_1 = new JLabel("Rule Description:");
		lblDestinationHostName_1_1_1_1.setForeground(new Color(255, 255, 255));
		lblDestinationHostName_1_1_1_1.setBounds(35, 396, 196, 20);
		lblDestinationHostName_1_1_1_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationHostName_1_1_1_1);

		protocolInput = new JTextField();
		protocolInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		protocolInput.setBounds(216, 354, 306, 26);
		protocolInput.setColumns(10);
		frmFwcrSpreadSheet.getContentPane().add(protocolInput);

		ruleDescriptionInput = new JTextArea();
		ruleDescriptionInput.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		ruleDescriptionInput.setBounds(216, 396, 306, 73);
		frmFwcrSpreadSheet.getContentPane().add(ruleDescriptionInput);

		disableInputs();

		JButton btnCreate = new JButton("Create");
		btnCreate.setForeground(new Color(255, 255, 255));
		btnCreate.setBackground(new Color(0, 128, 128));
		btnCreate.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
		btnCreate.setBounds(216, 583, 141, 33);
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (confirmModal(spreadSheetCount)) {
						if (spreadSheetCount.contains("Stage")) {
							String filePath = selectedFile.getAbsolutePath();
							filePath = formatFilePath(filePath, "Stage");
							File file = new File(filePath);
							FileWriter outputfile = new FileWriter(file);
							CSVWriter writer = new CSVWriter(outputfile);

							String[] Title = { "Ingram Micro Firewall Rule Request Form (Stage)" };

							String[] headers = { "#", "Physical Location", "Device Name", "Device Type",
									"Device Function", "Device Status", "IP Address", "Physical Location",
									"Device Name", "Device Type", "Device Function", "Device Status", "IP Address",
									"Port(s)", "Base Protocol", "Data Type", "Standard Protocol Name",
									"Rule Description" };

							System.out.println("Server List: " + servers);
							String[] modServerArray = Arrays.copyOfRange(servers, 1, servers.length);
							String[] modServerArrayTempList;
							modServerArrayTempList = formatServers(modServerArray, "Stage");
							// ------------------------------------All Source
							// Variables---------------------------------------------------------------
							String sourcePhysicalLocation = "CHICAGO";
							List<String> sourceDeviceName = Arrays.asList(modServerArrayTempList);
							String sourceDeviceType = "App Server";
							String sourceDeviceFunction = "PCG DMZ Servers";
							String sourceDeviceStatus = "PROD"; // or 'STAGE' for test
							ArrayList<String> SourceIPAddress = getIpFromHostName(sourceDeviceName);
							// ------------------------------------All Destination
							// Variables---------------------------------------------------------------
							String destinationPhysicalLocation = "Internet";
							String destinationDeviceName = stageDestHostName;
							String destinationDeviceType = "B2B User";
							String destinationDeviceFunction = "B2B Server";
							String destinationDeviceStatus = "PROD"; // or 'STAGE' for test
							String destinationIpAddress = stageDestIp;
							List<String> ipArray = setIpList(destinationIpAddress);

							// -----------------------------Port & Protocol
							// Details------------------------------------------------------------------------
							String port = stageDestPort;
							String baseProtocol = "TCP";
							String dataType = "Proprietary";
							String stdProtocolName = stageProtocol;
							String ruleDescription = stageRuleDescription;
							// ---------------------------------------------------------------------------------------------------------------------------

							writer.writeNext(Title);
							writer.writeNext(headers);

							for (String tempDestinationIP : ipArray) {
								for (int x = 0; x < sourceDeviceName.size(); x++) {
									String[] row = { String.valueOf(x + 1), sourcePhysicalLocation,
											sourceDeviceName.get(x), sourceDeviceType, sourceDeviceFunction,
											sourceDeviceStatus, SourceIPAddress.get(x), destinationPhysicalLocation,
											destinationDeviceName, destinationDeviceType, destinationDeviceFunction,
											destinationDeviceStatus, tempDestinationIP, port, baseProtocol, dataType,
											stdProtocolName, ruleDescription };
									writer.writeNext(row);
								}
								String[] blankRow = { "" };
								writer.writeNext(blankRow);
								writer.writeNext(blankRow);
							}
							writer.close();
						}
						if (spreadSheetCount.contains("Prod")) {
							String filePath = selectedFile.getAbsolutePath();
							filePath = formatFilePath(filePath, "Production");
							File file = new File(filePath);
							FileWriter outputfile = new FileWriter(file);
							CSVWriter writer = new CSVWriter(outputfile);
							String[] Title = { "Ingram Micro Firewall Rule Request Form (Prod)" };

							String[] headers = { "#", "Physical Location", "Device Name", "Device Type",
									"Device Function", "Device Status", "IP Address", "Physical Location",
									"Device Name", "Device Type", "Device Function", "Device Status", "IP Address",
									"Port(s)", "Base Protocol", "Data Type", "Standard Protocol Name",
									"Rule Description" };

							System.out.println("Server List: " + servers);
							String[] modServerArray = Arrays.copyOfRange(servers, 1, servers.length); // To remove
																										// "Please
																										// Select" from
																										// the server
																										// list"
							String[] modServerArrayTempList;
							modServerArrayTempList = formatServers(modServerArray, "Production");
							// ------------------------------------All Source
							// Variables---------------------------------------------------------------
							String sourcePhysicalLocation = "CHICAGO";
							List<String> sourceDeviceName = Arrays.asList(modServerArrayTempList);
							String sourceDeviceType = "App Server";
							String sourceDeviceFunction = "PCG DMZ Servers";
							String sourceDeviceStatus = "PROD"; // or 'STAGE' for test
							ArrayList<String> SourceIPAddress = getIpFromHostName(sourceDeviceName);
							// ------------------------------------All Destination
							// Variables---------------------------------------------------------------
							String destinationPhysicalLocation = "Internet";
							String destinationDeviceName = prodDestHostName;
							String destinationDeviceType = "B2B User";
							String destinationDeviceFunction = "B2B Server";
							String destinationDeviceStatus = "PROD"; // or 'STAGE' for test
							String destinationIpAddress = prodDestIp; // test IP for now. Later will be an arrayList
							List<String> ipArray = setIpList(destinationIpAddress);

							// -----------------------------Port & Protocol
							// Details------------------------------------------------------------------------
							String port = prodDestPort; // needs to be arrayList
							String baseProtocol = "TCP";
							String dataType = "Proprietary";
							String stdProtocolName = prodProtocol; // will be a variable by user
							String ruleDescription = prodRuleDescription;// need to add variable here
							// ---------------------------------------------------------------------------------------------------------------------------

							writer.writeNext(Title);
							writer.writeNext(headers);
							for (String tempDestinationIP : ipArray) {

								for (int x = 0; x < sourceDeviceName.size(); x++) {
									String[] row = { String.valueOf(x + 1), sourcePhysicalLocation,
											sourceDeviceName.get(x), sourceDeviceType, sourceDeviceFunction,
											sourceDeviceStatus, SourceIPAddress.get(x), destinationPhysicalLocation,
											destinationDeviceName, destinationDeviceType, destinationDeviceFunction,
											destinationDeviceStatus, tempDestinationIP, port, baseProtocol, dataType,
											stdProtocolName, ruleDescription };
									writer.writeNext(row);
								}
								String[] blankRow = { "" };
								writer.writeNext(blankRow);
								writer.writeNext(blankRow);
							}
							writer.close();
						}
						String filePathMessage = selectedFile.getAbsolutePath();
						successMessage(filePathMessage, spreadSheetCount);
					}
				} catch (Exception excep) {
					System.out.println(excep);
				}
			}

			private String[] formatServers(String[] modServerArray, String env) {
				if (env.equalsIgnoreCase("Stage")) {
					modServerArray = Arrays.copyOfRange(modServerArray, 0, 2);
				} else {
					modServerArray = Arrays.copyOfRange(modServerArray, 2, modServerArray.length);
				}
				return modServerArray;
			}

			private String formatFilePath(String filePath, String env) {
				try {
//					if (filePath.contains(".csv")) {
//						if (env.equalsIgnoreCase("Stage")) {
//							filePath.replace(".csv", "_STAGE.csv");
//						} else {
//							filePath.replace(".csv", "_PRODUCTION.csv");
//						}
//					} else {
//						if (env.equalsIgnoreCase("Stage")) {
//							filePath = filePath + "_STAGE.csv";
//						} else {
//							filePath = filePath + "_PRODUTION.csv";
//						}
//					}
					if (env.equalsIgnoreCase("Stage")) {
						filePath = filePath + "_STAGE.csv";
					} else {
						filePath = filePath + "_PRODUTION.csv";
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				return filePath;
			}

			private boolean confirmModal(ArrayList<String> spreadSheetCount) {
				int input = JOptionPane.showConfirmDialog(null,
						"FWCR Spread sheet(s) will be created for " + spreadSheetCount + "\n Do you wish to proceed?");
				if (input == 0) {
					return true;
				} else if (input == 1) {
					return false;
				} else {
					return false;
				}
			}

			private void successMessage(String filePathMessage, ArrayList<String> spreadSheetCount) {
				JOptionPane.showMessageDialog(frmFwcrSpreadSheet,
						spreadSheetCount.size() + " Spreadsheet(s) have been created in location: " + filePathMessage);
			}

			private ArrayList<String> getIpFromHostName(List<String> sourceDeviceName) throws UnknownHostException {
				ArrayList<String> sourceDeviceIpList = new ArrayList<String>();
				InetAddress temp;
				for (int i = 0; i < sourceDeviceName.size(); i++) {
					temp = InetAddress.getByName(sourceDeviceName.get(i));
					sourceDeviceIpList.add((temp.getHostAddress()).toString());
				}
				System.out.println(sourceDeviceIpList);
				return sourceDeviceIpList;
			}
		});

		frmFwcrSpreadSheet.getContentPane().add(btnCreate);

		JRadioButton rdbtnStage = new JRadioButton("Stage");
		rdbtnStage.setBounds(216, 120, 126, 29);
		JRadioButton rdbtnProduction = new JRadioButton("Production");
		rdbtnProduction.setBounds(381, 120, 141, 29);

		rdbtnStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rdbtnStage.isEnabled()) {
						rdbtnProduction.setEnabled(false);
						btnCreate.setEnabled(false);
						enableInputs();
						clearInputs();
					} else {
						rdbtnProduction.setEnabled(true);
						btnCreate.setEnabled(true);
						disableInputs();
						clearInputs();
					}

				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		});
		rdbtnStage.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		frmFwcrSpreadSheet.getContentPane().add(rdbtnStage);

		rdbtnProduction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rdbtnProduction.isEnabled()) {
						rdbtnStage.setEnabled(false);
						btnCreate.setEnabled(false);
						enableInputs();
						clearInputs();
					} else {
						rdbtnStage.setEnabled(true);
						btnCreate.setEnabled(true);
						disableInputs();
						clearInputs();
					}
				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		});
		rdbtnProduction.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		frmFwcrSpreadSheet.getContentPane().add(rdbtnProduction);

		JLabel lblPleaseSelect = new JLabel("Please Select:");
		lblPleaseSelect.setForeground(new Color(255, 255, 255));
		lblPleaseSelect.setBounds(35, 124, 155, 20);
		lblPleaseSelect.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblPleaseSelect);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setBackground(new Color(0, 128, 128));
		btnSave.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
		btnSave.setBounds(47, 583, 141, 32);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// if stage is selected
					if (rdbtnProduction.isEnabled() == false) {
						envSelected = "Stage";
						spreadSheetCount.add(envSelected);
						stageDestHostName = String.valueOf(destHostNameInput.getText());
						stageDestIp = String.valueOf(destIpInput.getText());
						stageDestPort = String.valueOf(destPortInput.getText());
						stageProtocol = String.valueOf(protocolInput.getText());
						stageRuleDescription = String.valueOf(ruleDescriptionInput.getText());
						rdbtnProduction.setEnabled(true);
						btnCreate.setEnabled(true);
						disableInputs();
					}
//					if prod is selected
					else if (rdbtnStage.isEnabled() == false) {
						envSelected = "Prod";
						spreadSheetCount.add("Prod");
						prodDestHostName = String.valueOf(destHostNameInput.getText());
						prodDestIp = String.valueOf(destIpInput.getText());
						prodDestPort = String.valueOf(destPortInput.getText());
						prodProtocol = String.valueOf(protocolInput.getText());
						prodRuleDescription = String.valueOf(ruleDescriptionInput.getText());
						rdbtnStage.setEnabled(true);
						btnCreate.setEnabled(true);
						disableInputs();
					}
					System.out.println(spreadSheetCount);

				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		});
		frmFwcrSpreadSheet.getContentPane().add(btnSave);

		JLabel lblDestinationHostName_1_1_1_1_1 = new JLabel("O/P File name");
		lblDestinationHostName_1_1_1_1_1.setForeground(new Color(255, 255, 255));
		lblDestinationHostName_1_1_1_1_1.setBounds(35, 493, 141, 20);
		lblDestinationHostName_1_1_1_1_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationHostName_1_1_1_1_1);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 545, 736, 22);
		frmFwcrSpreadSheet.getContentPane().add(horizontalStrut);

		Button buttonPath = new Button("Select...");
		buttonPath.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		buttonPath.setForeground(new Color(0, 0, 0));
		buttonPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fc.setCurrentDirectory(new File("C:\\Users"));
					int result = fc.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						selectedFile = fc.getSelectedFile();
						System.out.println("User selected file path: " + selectedFile.getAbsolutePath());
						textFieldPath.setText(selectedFile.getAbsolutePath());
					}
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		buttonPath.setBounds(409, 493, 113, 32);
		frmFwcrSpreadSheet.getContentPane().add(buttonPath);

		JButton btnResetAll = new JButton("Close");
		btnResetAll.setForeground(new Color(255, 255, 255));
		btnResetAll.setBackground(new Color(0, 128, 128));
		btnResetAll.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
		btnResetAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnResetAll.setBounds(381, 583, 141, 33);
		frmFwcrSpreadSheet.getContentPane().add(btnResetAll);

		textFieldPath = new JTextField();
		textFieldPath.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		textFieldPath.setBackground(new Color(245, 245, 245));
		textFieldPath.setBounds(216, 493, 187, 32);
		frmFwcrSpreadSheet.getContentPane().add(textFieldPath);
		textFieldPath.setColumns(10);
		textFieldPath.setEnabled(false);

		JLabel lblDestinationHostName_1_1_1_1_1_1 = new JLabel("& path:");
		lblDestinationHostName_1_1_1_1_1_1.setForeground(new Color(255, 255, 255));
		lblDestinationHostName_1_1_1_1_1_1.setFont(new Font("Bernard MT Condensed", Font.BOLD, 20));
		lblDestinationHostName_1_1_1_1_1_1.setBounds(35, 512, 141, 20);
		frmFwcrSpreadSheet.getContentPane().add(lblDestinationHostName_1_1_1_1_1_1);

		JLabel lblNewLabel = new JLabel("FWCR Spreadsheet Creator");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Bernard MT Condensed", Font.PLAIN, 45));
		lblNewLabel.setBounds(44, 31, 542, 53);
		frmFwcrSpreadSheet.getContentPane().add(lblNewLabel);
		frmFwcrSpreadSheet.setBounds(100, 100, 608, 692);
		frmFwcrSpreadSheet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	protected List<String> setIpList(String destinationIpAddress) {
		List<String> ipArray = Arrays.asList(destinationIpAddress.split("\\s*,\\s*"));
		if (ipArray.size() > 1) {
			return ipArray;
		}
		return ipArray;
	}

	protected void clearInputs() {
		destHostNameInput.setText("");
		destIpInput.setText("");
		destPortInput.setText("");
		protocolInput.setText("");
		ruleDescriptionInput.setText("");
	}

	protected void enableInputs() {
		destHostNameInput.setEnabled(true);
		destIpInput.setEnabled(true);
		destPortInput.setEnabled(true);
		protocolInput.setEnabled(true);
		ruleDescriptionInput.setEnabled(true);
	}

	protected void disableInputs() {
		destHostNameInput.setEnabled(false);
		destIpInput.setEnabled(false);
		destPortInput.setEnabled(false);
		protocolInput.setEnabled(false);
		ruleDescriptionInput.setEnabled(false);
	}
}
