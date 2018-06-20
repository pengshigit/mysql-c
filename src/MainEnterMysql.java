

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainEnterMysql extends JFrame implements TreeSelectionListener,ActionListener{

	JTable jtb,jtb2;
	JTextArea jta;
	public static final String NEW_LIEN = System.getProperty("line.separator");// 得到系统的换行符

	
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	  static String DB_URL = "jdbc:mysql://localhost:3306/";
	  static String USER = "root";
	  static String PASS = "123456";
	static String filepath="bin/a.properties";
	  public static Properties prop = new Properties();
	  
	JTree jtree;
	DefaultTableModel tableModel ;
	static DefaultMutableTreeNode specify = new DefaultMutableTreeNode("database");
	
	MainEnterMysql() {

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// 创建并添加各菜单，注意：菜单的快捷键是同时按下Alt键和字母键，方法setMnemonic('F')是设置快捷键为Alt +Ｆ
		JMenu menuFile = new JMenu("文件(F)"), menuEdit = new JMenu("编辑(E)"), menuView = new JMenu("查看(V)");
		menuFile.setMnemonic('F');
		menuEdit.setMnemonic('E');
		menuView.setMnemonic('V');
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuView);

		JMenu itemOpen = new JMenu("打开");
		itemOpen.setMnemonic('O');
		JMenuItem itemOpen1 = new JMenuItem("打开x");
		JMenuItem itemOpen2 = new JMenuItem("打开y");
		itemOpen.add(itemOpen1);
		itemOpen.add(itemOpen2);
		JMenuItem itemSave = new JMenuItem("刷新");
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuFile.add(itemOpen);
		menuFile.add(itemSave);

		// 添加“编辑”菜单的各菜单项
		JMenuItem itemCopy = new JMenuItem("复制");
		itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuEdit.add(itemCopy);

		// 添加“查看”菜单的各菜单项
		JMenuItem itemStop = new JMenuItem("停止"), itemRefresh = new JMenuItem("刷新");
		itemStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		itemRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		menuView.add(itemStop);
		menuView.add(itemRefresh);
		
		  itemSave.addActionListener(new ActionListener() 
	        {

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					try {
						sql();
						//jtree.setmodel（treemodel）；
						
						jtree.updateUI();
						
						//jtree.validate();
						//jtree.setVisible(true);
						 
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	        	
	        });
	        
		// add();

		  
		  
		String[] title = { "name", "interest","123" };
		String[][] data = { { "张三", "羽毛球","123" }, { "李四", "乒乓球","123"  } };
		cloumn=2;
		
		tableModel= new DefaultTableModel(data,title);
		jtb = new JTable(tableModel);
		jtb2 = new JTable(tableModel);
		JScrollPane leftPanel=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 

		//JTree tree=new JTree(); 
		
		jtree = new JTree(specify);
		//jScrollPane1.getViewport().add(jtree, null);
		//leftPanel.add(jtree); 
		leftPanel.setViewportView(jtree); 
		jtree.addTreeSelectionListener(this);
		
		//add(leftPanel);
		
		
		JScrollPane jsp = new JScrollPane(leftPanel);

		jta = new JTextArea(5, 5);
		for (int i = 0; i < 100; i++) {
			jta.append(i + "床前明月光" + NEW_LIEN);
		}
		jta.setLineWrap(true);// 自动切换到下一行
		
		
		JScrollPane jsp2 = new JScrollPane(jtb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//JScrollPane jsp3 = new JScrollPane(jtb2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,	JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// 参数1,表示组件文本域
		// 参数2,垂直滚动条一直显示
		// 参数3,水平滚动条从不显示

		JPanel jpc = new JPanel(new GridLayout(1, 2));
		jpc.add(jsp);
		jpc.add(jsp2);
		//jpc.add(jsp3);
		add(jpc);

		JPanel jp = new JPanel();
		JButton jb = new JButton("刷新");
		jp.add(jb);

		JButton bt = new JButton("查看该表");
		JButton newbt = new JButton("new表");
		JButton delbt = new JButton("delete表");
		jp.add(bt);
		jp.add(newbt);
		jp.add(delbt);
 
		 bt.addActionListener(this);
		 newbt.addActionListener(this);
		 delbt.addActionListener(this);
		 jb.addActionListener(this);
		
		add(jp, BorderLayout.SOUTH);
		// 窗口属性的设置
		setTitle("表格窗口");// 标题
		setSize(750, 500);// 窗口大小
		setLocationRelativeTo(null);// 窗口居中
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
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
           // System.out.println("s"+filepath);
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
    
    
    
	public static void sql() throws ClassNotFoundException {

		// ArrayList<String> list = new ArrayList<String>();
		// 数据库的用户名与密码，需要根据自己的设置
		specify.removeAllChildren();
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		
		DB_URL=getValue("DB_URL",filepath);
		USER=getValue("USER",filepath);
		PASS=getValue("PASS",filepath);
		
		System.out.println("|---->load properties");
		//System.out.println(DB_URL);
		//System.out.println(USER);
		//System.out.println(PASS);
		;
		
		
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->login");
			//System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			
			// 执行查询
			//System.out.println("实例化Statement对象...");
			
			
			stmt2 = conn.createStatement();
			stmt = conn.createStatement();
			stmt3 = conn.createStatement();

			String sql;
			// sql = "SELECT id, name, url FROM websites";
			sql = "show databases";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				String id = rs.getString("Database");
				// String name = rs.getString("name");
				// String url = rs.getString("url");
				// if(!id.equals("test"))
				// continue;

				// 输出数据
				// System.out.println("ID: " + id);
				// list.add(id);
				String dsql1 = "use " + id;
				ResultSet rs2 = stmt2.executeQuery(dsql1);
				// rs2.last();
				String dsql2 = "show tables;";
				ResultSet rs3 = stmt3.executeQuery(dsql2);

				DefaultMutableTreeNode com_1 = new DefaultMutableTreeNode(id);
				
				while (rs3.next()) {
					String id3 = rs3.getString("Tables_in_" + id);
					com_1.add(new DefaultMutableTreeNode(id3));
				}
				rs3.last();

				specify.add(com_1);
				// System.out.print(", 站点名称: " + name);
				// System.out.print(", 站点 URL: " + url);
				// System.out.print(rs+"\n");
			}
			
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		}
		// System.out.println("Goodbye!");
		// return list;
		
	}
	static int cloumn;
	
	
	public static void main(String[] args) 
	{
	
		try {
			sql();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainEnterMysql().setVisible(true);// 创建窗口实例, 并让窗口可见
	}
	
	
	String s1;
	String s2;
	
	
	@Override
	public void valueChanged(TreeSelectionEvent e) 
	{
		
		// TODO Auto-generated method stub
		//tableModel.removeRow(0);
		tableModel.setRowCount(0);
		tableModel.fireTableDataChanged();
	
	   
	     // Object[][] student = new Object[30][3]; 
	      Object[] header = { "1", "2","3"};

		  s1 = (String) e.getPath().getPathComponent(1).toString();
		  s2= (String) e.getPath().getPathComponent(2).toString();
		 
	    
	    List<String> lsf = new ArrayList<String>();
		List<String> lstype = new ArrayList<String>();
		
		String obj = null;
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->select table");
			//System.out.println("连接数据库...");
			Connection conn = null;
			Statement stmt = null;

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// 执行查询
			//System.out.println("实例化Statement对象...");
			stmt = conn.createStatement();

			// System.out.println("ID: " + id);
			// list.add(id);
			String dsql1 = "use " + s1;
			ResultSet rs2 = stmt.executeQuery(dsql1);
			
			// rs2.last();
			String dsql2 = "desc " + s2;
			ResultSet rs3 = stmt.executeQuery(dsql2);

			// DefaultMutableTreeNode com_1 = new DefaultMutableTreeNode(id);
			while (rs3.next()) 
			{
				String id3 = rs3.getString("Field");
				 
				// com_1.add();
				lsf.add(id3);
				lstype.add(rs3.getString("Type"));
				
				// obj+=id3+",";
				//System.out.println(id3 + "\t");
			}
			// rs3.last();

			// specify.add(com_1);
			// System.out.print(", 站点名称: " + name);
			// System.out.print(", 站点 URL: " + url);
			// System.out.print(rs+"\n");
		}
		
		// 完成后关闭
		// rs.close();
		// stmt.close();
		// conn.close();
		catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// td.removeAll();

		//for (int i = 0; i < cloumn; i++) 
		{
			/*student[i][0] = null;
			student[i][1] = null;
			student[i][2] = null;*/
			//tableModel.removeRow(i);
			//tableModel.re
		}

		// header= ls.toArray();
		int i = 0;
		//tableModel.setRowCount(lsf.size());
		
		Object ojs[]=new Object[3];
		for (Object oj : lsf) 
		{
			//student[i][0] = i;
			//student[i][1] = oj;
			//student[i][2] =lstype.get(i);
			ojs[0]=i;
			ojs[1]=oj;
			ojs[2]=lstype.get(i);
			tableModel.addRow(ojs);;
			i++;
		}
		cloumn=i;
		tableModel.fireTableDataChanged();
		//tableModel.addRow(student);
		
		// JTableDemo jf= Class.forName("JTableDemo");

		// td.set
		// System.out.print(ls.toArray().toString()+"\n");
		// td.removeAll();

		
	}
	
	void newtable()
	{
		NewTableToDb td = new NewTableToDb();
		td.dbname=s1;
	    td.setBounds(400,50,800,600);
	    td.setVisible(true);
	    td.label.setText(s2);
	    td.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    
	}
	
	void deletetable()
	{
		Object[] options = {"delete","cancel"};
		int response=JOptionPane.showOptionDialog ( null, "删除"+s2,"消息",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
                null, options, options[0] ) ;
            if (response == 0)
             {}//JOptionPane.showMessageDialog(null,"delete");}
            else if(response == 1)
             {return;}
          //  else if(response == 2)
          //   {JOptionPane.showMessageDialog(null,"您按下了取款按 钮");}
          //  else if(response == 3)
           //  {JOptionPane.showMessageDialog(null,"您按下了退出按 钮");}
		
            
	/*	try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("连接数据库...");
			System.out.println("|---->deletetable");
			
			Connection conn = null;
			Statement stmt = null;

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// 执行查询
			//System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();

			// System.out.println("ID: " + id);
			// list.add(id);
			String dsql1 = "use " + s1;
			ResultSet rs2 = stmt.executeQuery(dsql1);
			// rs2.last();
			String dsql2 = "drop table " + s2;
			stmt.execute(dsql2);
		
		}
		catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
	}
	
	void refresh()
	{
		try {
			sql();
			//jtree.setmodel（treemodel）；
			
			jtree.updateUI();
			
			//jtree.validate();
			//jtree.setVisible(true);
			 
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	void showdata()
	{
		//int num = jtb.getColumnCount();
		 

		
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->show data");
			Connection conn = null;
			Statement stmt = null;

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// 执行查询
			//System.out.println("实例化Statement对象...");
			stmt = conn.createStatement();

			// System.out.println("ID: " + id);
			// list.add(id);
			String dsql1 = "use " + s1;
			ResultSet rs2 = stmt.executeQuery(dsql1);
			// rs2.last();
			String dsql2 = "select * from " + s2;
			ResultSet rs3 = stmt.executeQuery(dsql2);

			// DefaultMutableTreeNode com_1 = new DefaultMutableTreeNode(id);
			int index=0;
			JTSD.header=new Object[cloumn];
			//System.out.println("ID: " + cloumn);
			 for(int j=0;j<cloumn;j++)
			{
				JTSD.header[j]=""+j;
			} 
			
			JTSD.column=cloumn;
			JTSD.student=new Object[30][cloumn];
			
			while (rs3.next()) 
			{
				
				/*String id3 = rs3.getString(1);
				String id4 = rs3.getString(2);
				String id5 = rs3.getString(3);
				// com_1.add();
				//ls.add(id3);
				// obj+=id3+",";
				JTSD.student[index][0]=id3;
				JTSD.student[index][1]=id4;
				JTSD.student[index][2]=id5;*/
				
				for(int k=0;k<cloumn;k++)
					JTSD.student[index][k]=rs3.getString(k+1);
				
				index++;
				//System.out.println(id3 + "\t"+id4+ "\t"+id5);
			}
			
			// rs3.last();
			// specify.add(com_1);
			// System.out.print(", 站点名称: " + name);
			// System.out.print(", 站点 URL: " + url);
		
			// System.out.print(rs+"\n");
			JTSD td = new JTSD();
		    td.setBounds(400,50,800,600);
		    td.setVisible(true);
		    td.label.setText(s2);
		    td.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    
		}
		
		
		// 完成后关闭
		// rs.close();
		// stmt.close();
		// conn.close();
		catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// td.removeAll();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="查看该表")
			showdata();
		
		else if(e.getActionCommand()=="new表")
			newtable();
		
		else if(e.getActionCommand()=="delete表")
			deletetable();
		else  if(e.getActionCommand()=="刷新")
			refresh();
	}
}