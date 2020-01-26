package swing_example;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main {

	private JFrame frame;
	private JButton operationButton;
	private JTextField textFieldKey;
	private JTextField textFieldValue;
	private JLabel lblKey;
	private JLabel lblValue;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField textIP;
	private JTextField textPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		operationButton = new JButton("OK");
		operationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String buttonText = operationButton.getText();
				if (buttonText.equalsIgnoreCase("GET")) {
					String key = textFieldKey.getText().toString();
					String value = RedisCacheManager.getInstance().getRedisCache().get(key);

					if (value == null) {
						errorMessage("Result not found");
						textFieldValue.setVisible(false);
						lblValue.setVisible(false);
					} else {

						lblValue.setVisible(true);
						lblValue.setText("VALUE");
						textFieldValue.setVisible(true);
						textFieldValue.setText(value);
					}
				} else if (buttonText.equalsIgnoreCase("PUT")) {
					try {
						String key = textFieldKey.getText().toString();
						String value = textFieldValue.getText().toString();
						RedisCacheManager.getInstance().getRedisCache().put(key, value);
						errorMessage("Operation successfull");
					} catch (Exception e1) {
						errorMessage(e1.toString());
					}
				}
			}
		});
		operationButton.setBounds(187, 227, 89, 23);
		frame.getContentPane().add(operationButton);

		initializeLabel();

		initializeRedisTypeComBox();

		initializeOperationComBox();

		initializeTextFields();

	}

	private void initializeRedisTypeComBox() {

		final JComboBox<String> redisTypeComBox = new JComboBox<String>();
		redisTypeComBox.setBounds(154, 87, 161, 22);
		redisTypeComBox.addItem("Select redis type");
		redisTypeComBox.addItem("IMSI-MSISDN");
		redisTypeComBox.addItem("MSISDN-IMSI");
		frame.getContentPane().add(redisTypeComBox);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String selectedRedisType = (String) redisTypeComBox.getSelectedItem();
				if (selectedRedisType.equalsIgnoreCase("IMSI-MSISDN")) {
					String IP = textIP.getText().toString();
					String port = textPort.getText().toString();
					
					if (Utils.isStringEmpty(IP, port)) {
						errorMessage("IP and Port fields can not be empty !!");
					} else {
						RedisInitialize.initializeImsiMsisdn(IP, port);
					}
					
				} else if (selectedRedisType.equalsIgnoreCase("MSISDN-IMSI")) {
					String IP = textIP.getText().toString();
					String port = textPort.getText().toString();
					
					if (Utils.isStringEmpty(IP, port)) {
						errorMessage("IP and Port fields can not be empty !!");
					} else {
						RedisInitialize.initializeMsisdnImsi(IP, port);
					}
				}
			}
		};
		redisTypeComBox.addActionListener(actionListener);
	}

	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Warning", JOptionPane.ERROR_MESSAGE);
	}

	private void initializeOperationComBox() {

		final JComboBox<String> operationComBox = new JComboBox<String>();
		operationComBox.setBounds(154, 120, 161, 22);
		operationComBox.addItem("Select operation type");
		operationComBox.addItem("GET");
		operationComBox.addItem("PUT");
		frame.getContentPane().add(operationComBox);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String selectedRedisType = (String) operationComBox.getSelectedItem();
				if (selectedRedisType.equalsIgnoreCase("GET")) {
					operationButton.setText("GET");
					lblKey.setVisible(true);
					lblKey.setText("KEY");
					textFieldKey.setVisible(true);
					lblValue.setVisible(false);
					textFieldValue.setVisible(false);
					
				} else if (selectedRedisType.equalsIgnoreCase("PUT")) {
					warningMessage();
					operationButton.setText("PUT");
					lblValue.setVisible(true);
					lblValue.setText("VALUE");
					textFieldValue.setVisible(true);
					lblKey.setVisible(true);
					lblKey.setText("KEY");
					textFieldKey.setVisible(true);
				}
			}
		};
		operationComBox.addActionListener(actionListener);
	}

	private void warningMessage() {
		JOptionPane.showConfirmDialog(new JFrame(), "MASTER Redis IP-PORT must be defined", "Warning", JOptionPane.WARNING_MESSAGE);
	}
	private void initializeLabel() {

		JLabel redisType = new JLabel("Redis Type");
		redisType.setBounds(36, 91, 99, 14);
		frame.getContentPane().add(redisType);

		JLabel lblNewLabel = new JLabel("Operation");
		lblNewLabel.setBounds(36, 124, 99, 14);
		frame.getContentPane().add(lblNewLabel);

		lblKey = new JLabel("Key");
		lblKey.setBounds(36, 168, 46, 14);
		lblKey.setVisible(false);
		frame.getContentPane().add(lblKey);

		lblValue = new JLabel("Value");
		lblValue.setBounds(36, 199, 46, 14);
		lblValue.setVisible(false);
		frame.getContentPane().add(lblValue);
	}

	private void initializeTextFields() {

		textFieldKey = new JTextField();
		textFieldKey.setBounds(154, 165, 165, 20);
		frame.getContentPane().add(textFieldKey);
		textFieldKey.setColumns(10);
		textFieldKey.setVisible(false);

		textFieldValue = new JTextField();
		textFieldValue.setBounds(154, 196, 165, 20);
		frame.getContentPane().add(textFieldValue);
		textFieldValue.setColumns(10);

		lblNewLabel_1 = new JLabel("IP");
		lblNewLabel_1.setBounds(36, 21, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Port");
		lblNewLabel_2.setBounds(36, 58, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);

		textIP = new JTextField();
		textIP.setBounds(154, 18, 161, 20);
		frame.getContentPane().add(textIP);
		textIP.setColumns(10);

		textPort = new JTextField();
		textPort.setBounds(154, 55, 161, 20);
		frame.getContentPane().add(textPort);
		textPort.setColumns(10);
		textFieldValue.setVisible(false);
	}
}
