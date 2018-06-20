

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewTableToDb extends JFrame implements ActionListener,ItemListener{
	
	JTable table;
	JTextArea text,jtext;
	int height = 600;
	JLabel label;
	String title;
	boolean pk_autoincr=false;
	JCC c0 = new JCC();  
	JCC c1 = new JCC();  
	JCC c2 = new JCC();  
	JCC c3 = new JCC();  
	JCC c4 = new JCC();  
	JCC c5 = new JCC();  
	JCC c6 = new JCC();  
	
	JTextField area0 = new JTextField(100);
	JTextField area1 = new JTextField(100);
	JTextField area2 = new JTextField(100);
	JTextField area3 = new JTextField(100);
	JTextField area4 = new JTextField(100);
	JTextField area5 = new JTextField(100);
	JTextField area6 = new JTextField(100);
	
	//JTextField area7 = new JTextField(100);
	JCheckBox box1 ;
	
	public NewTableToDb() 
	{
		
		setLayout(null);
		// 设置主界面名
		setTitle("new table");
		// 设置主界面在屏幕上的位置(相对于屏幕左上角的主界面左上角坐标)
		setLocation(100, 100);
		// 设置主界面的长和宽
		setSize(650, 550);
		
		box1 = new JCheckBox("P_K Auto_Incr");
		
		area0.setBounds(20, 10,150, 30);
		
		area1.setBounds(20, 50,150, 30);
		
		box1.setBounds(390, 50,150, 30);
		add(box1);
		//JTextField area3 = new JTextField(100);
		area2.setBounds(20, 90,150, 30);
		//JTextField area4 = new JTextField(100);
		area3.setBounds(20, 130,150, 30);
		//JTextField area5 = new JTextField(100);
		area4.setBounds(20, 170,150, 30);
		//JTextField area6 = new JTextField(100);
		area5.setBounds(20, 210,150, 30);
		//JTextField area7 = new JTextField(100);
		area6.setBounds(20, 250,150, 30);
		
		label = new JLabel("123", JLabel.CENTER);
		//table = new JTable(student, header);
		//table.setPreferredScrollableViewportSize(new Dimension(width, height));
		//JScrollPane scrollPane = new JScrollPane(table);
		//Container ct = this.getContentPane();
		//ct.add(label, BorderLayout.NORTH);
		//ct.add(scrollPane, BorderLayout.CENTER);
		//table.setSelectionBackground(Color.GREEN);
		//JPanel pl = new JPanel();
		JButton bt = new JButton("To_DB");
		bt.setBounds(20, 300,300, 30);

		text = new JTextArea();
		text.setBounds(20, 350,300, 90);
		text.setEditable(false);
		
		jtext=new JTextArea();
		jtext.setBounds(20,450,300, 30);
		jtext.setEditable(false);
		add(jtext);
		
		//c0.setBounds(180, 10,150, 30);
        //add(c0);  
        c1.setBounds(180, 50,150, 30);
        add(c1); 
        c2.setBounds(180, 90,150, 30);
        add(c2); 
        c3.setBounds(180, 130,150, 30);
        add(c3); 
        c4.setBounds(180, 170,150, 30);
        add(c4); 
        c5.setBounds(180, 210,150, 30);
        add(c5); 
        c6.setBounds(180, 250,150, 30);
        add(c6); 
        
		
		add(label);
		
		add(area0);
		add(area1);
		add(area2);
		add(area3);
		add(area4);
		add(area5);
		add(area6);

		
		add(bt);
		add(text);
		box1.addItemListener(this);
		//ct.add(pl, BorderLayout.SOUTH);
		bt.addActionListener(this);
	}

	
	public static void main(String[] args) 
	{
		NewTableToDb td = new NewTableToDb();
		//td.setBounds(400, 300, 500, 300);
		td.setVisible(true);
		td.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//create table t(t1 int,f varchar,g varchar,h varchar,i varchar,j varchar )
	public String  getSql()
	{
		if(area0.getText()==null)
			return "error";
		String create="create table "+area0.getText()+"(";;
		Set<String> ss=new HashSet();
		ss.add("");
		
		if(ss.add(area1.getText()))
			create+=area1.getText() + " "+ c1.getN()+"";
		if(pk_autoincr)
			create+=" PRIMARY KEY AUTO_INCREMENT,";
		else
			create+=",";
		if(ss.add(area2.getText()))
			create+=area2.getText() + " "+ c2.getN()+",";
		
		if(ss.add(area3.getText()))
			create+=area3.getText() + " "+ c3.getN()+",";
		
		if(ss.add(area4.getText()))
			create+=area4.getText() + " "+ c4.getN()+",";
		
		if(ss.add(area5.getText()))
			create+=area5.getText() + " "+ c5.getN()+",";
		
		if(ss.add(area6.getText()))
			create+=area6.getText() + " "+ c6.getN()+",";
		
 
		String st2=create.substring(0, create.length()-1)+");";
			  
		return st2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//int num = table.getColumnCount();
		//text.setText(getSql());
		String s=getSql();
		//System.out.println(s);
		text.setText(s);
		jtext.setText(savetable(s));
		
		text.setLineWrap(true);        //激活自动换行功能 
		text.setWrapStyleWord(true);  
		text.setForeground(Color.red);
		text.setEditable(true);

	}
	
	 static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	 static String DB_URL = "jdbc:mysql://localhost:3306/";
	 static String USER = "root";
	 static String PASS = "123456";
	
	static String filepath="bin/a.properties";
	public static Properties prop = new Properties();
	
	public static String getValue(String k, String filepath){
        InputStream in;
       try {
           in = new BufferedInputStream(new FileInputStream(filepath));
           prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                if (key.equals(k)){
                    return prop.getProperty(key);
                }
            }
            in.close();
       } catch (Exception e) {
           e.printStackTrace();
       }  
       return "";
   }
   /**
    * 设置键值
    * @param filepath
    * @param map 
    */
   public static void setValue(String filepath, Map<String, String> map){
       ///保存属性到b.properties文件
       FileOutputStream oFile ;
       try {
           //System.out.println("s"+filepath);
           oFile = new FileOutputStream(filepath, false);
           //true表示追加打开
          // System.out.println(map.get("key")+",,,"+map.get("value"));
           prop.setProperty(map.get("key"), map.get("value"));
           //prop.put(map.get("key"), map.get("value"));
           prop.store(oFile, "The New properties file");
           oFile.close();
       } catch (Exception e) {
           // TODO 自动生成的 catch 块
           e.printStackTrace();
       }
   }
   
   static String dbname;
	
	static String savetable(String insert) 
	{

		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->savetable");
			
			//System.out.println("连接数据库...");
			Connection conn = null;
			Statement stmt = null;

			DB_URL=getValue("DB_URL",filepath);
			USER=getValue("USER",filepath);
			PASS=getValue("PASS",filepath);
			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// 执行查询
			//System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();

			// System.out.println("ID: " + id);
			// list.add(id);
			String dsql1 = "use " + dbname;
			ResultSet rs2 = stmt.executeQuery(dsql1);
			// rs2.last();
			String dsql2 = insert;
			 stmt.execute(dsql2);

			// DefaultMutableTreeNode com_1 = new DefaultMutableTreeNode(id);
			int index = 0;
			// rs.close();
			stmt.close();
			conn.close();
			return "success";
		}

		// 完成后关闭

		catch (SQLException se) {
			// 处理 JDBC 错误

			se.printStackTrace();
			return se.getMessage();
		}
		// td.removeAll();
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		 if(e.getItemSelectable()==box1){ //获取可选项
			 pk_autoincr=!pk_autoincr;
		    }
	}
	
}
