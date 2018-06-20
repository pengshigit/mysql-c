

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
	public static final String NEW_LIEN = System.getProperty("line.separator");// �õ�ϵͳ�Ļ��з�

	
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

		// ��������Ӹ��˵���ע�⣺�˵��Ŀ�ݼ���ͬʱ����Alt������ĸ��������setMnemonic('F')�����ÿ�ݼ�ΪAlt +��
		JMenu menuFile = new JMenu("�ļ�(F)"), menuEdit = new JMenu("�༭(E)"), menuView = new JMenu("�鿴(V)");
		menuFile.setMnemonic('F');
		menuEdit.setMnemonic('E');
		menuView.setMnemonic('V');
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuView);

		JMenu itemOpen = new JMenu("��");
		itemOpen.setMnemonic('O');
		JMenuItem itemOpen1 = new JMenuItem("��x");
		JMenuItem itemOpen2 = new JMenuItem("��y");
		itemOpen.add(itemOpen1);
		itemOpen.add(itemOpen2);
		JMenuItem itemSave = new JMenuItem("ˢ��");
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuFile.add(itemOpen);
		menuFile.add(itemSave);

		// ��ӡ��༭���˵��ĸ��˵���
		JMenuItem itemCopy = new JMenuItem("����");
		itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuEdit.add(itemCopy);

		// ��ӡ��鿴���˵��ĸ��˵���
		JMenuItem itemStop = new JMenuItem("ֹͣ"), itemRefresh = new JMenuItem("ˢ��");
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
						//jtree.setmodel��treemodel����
						
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
		String[][] data = { { "����", "��ë��","123" }, { "����", "ƹ����","123"  } };
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
			jta.append(i + "��ǰ���¹�" + NEW_LIEN);
		}
		jta.setLineWrap(true);// �Զ��л�����һ��
		
		
		JScrollPane jsp2 = new JScrollPane(jtb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//JScrollPane jsp3 = new JScrollPane(jtb2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,	JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// ����1,��ʾ����ı���
		// ����2,��ֱ������һֱ��ʾ
		// ����3,ˮƽ�������Ӳ���ʾ

		JPanel jpc = new JPanel(new GridLayout(1, 2));
		jpc.add(jsp);
		jpc.add(jsp2);
		//jpc.add(jsp3);
		add(jpc);

		JPanel jp = new JPanel();
		JButton jb = new JButton("ˢ��");
		jp.add(jb);

		JButton bt = new JButton("�鿴�ñ�");
		JButton newbt = new JButton("new��");
		JButton delbt = new JButton("delete��");
		jp.add(bt);
		jp.add(newbt);
		jp.add(delbt);
 
		 bt.addActionListener(this);
		 newbt.addActionListener(this);
		 delbt.addActionListener(this);
		 jb.addActionListener(this);
		
		add(jp, BorderLayout.SOUTH);
		// �������Ե�����
		setTitle("��񴰿�");// ����
		setSize(750, 500);// ���ڴ�С
		setLocationRelativeTo(null);// ���ھ���
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	 public static String getValue(String k, String filepath){
         InputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(filepath));
            prop.load(in);     ///���������б�
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
     * ���ü�ֵ
     * @param filepath
     * @param map 
     */
    public static void setValue(String filepath, Map<String, String> map){
        ///�������Ե�b.properties�ļ�
        FileOutputStream oFile ;
        try {
           // System.out.println("s"+filepath);
            oFile = new FileOutputStream(filepath, false);
            //true��ʾ׷�Ӵ�
           // System.out.println(map.get("key")+",,,"+map.get("value"));
            prop.setProperty(map.get("key"), map.get("value"));
            //prop.put(map.get("key"), map.get("value"));
            prop.store(oFile, "The New properties file");
            oFile.close();
        } catch (Exception e) {
            // TODO �Զ����ɵ� catch ��
            e.printStackTrace();
        }
    }
    
    
    
	public static void sql() throws ClassNotFoundException {

		// ArrayList<String> list = new ArrayList<String>();
		// ���ݿ���û��������룬��Ҫ�����Լ�������
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
			// ע�� JDBC ����
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->login");
			//System.out.println("�������ݿ�...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			
			// ִ�в�ѯ
			//System.out.println("ʵ����Statement����...");
			
			
			stmt2 = conn.createStatement();
			stmt = conn.createStatement();
			stmt3 = conn.createStatement();

			String sql;
			// sql = "SELECT id, name, url FROM websites";
			sql = "show databases";
			ResultSet rs = stmt.executeQuery(sql);

			// չ����������ݿ�
			while (rs.next()) {
				// ͨ���ֶμ���
				String id = rs.getString("Database");
				// String name = rs.getString("name");
				// String url = rs.getString("url");
				// if(!id.equals("test"))
				// continue;

				// �������
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
				// System.out.print(", վ������: " + name);
				// System.out.print(", վ�� URL: " + url);
				// System.out.print(rs+"\n");
			}
			
			// ��ɺ�ر�
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException se) {
			// ���� JDBC ����
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
		new MainEnterMysql().setVisible(true);// ��������ʵ��, ���ô��ڿɼ�
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
			// ע�� JDBC ����
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->select table");
			//System.out.println("�������ݿ�...");
			Connection conn = null;
			Statement stmt = null;

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// ִ�в�ѯ
			//System.out.println("ʵ����Statement����...");
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
			// System.out.print(", վ������: " + name);
			// System.out.print(", վ�� URL: " + url);
			// System.out.print(rs+"\n");
		}
		
		// ��ɺ�ر�
		// rs.close();
		// stmt.close();
		// conn.close();
		catch (SQLException se) {
			// ���� JDBC ����
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
		int response=JOptionPane.showOptionDialog ( null, "ɾ��"+s2,"��Ϣ",JOptionPane.YES_OPTION ,JOptionPane.PLAIN_MESSAGE,
                null, options, options[0] ) ;
            if (response == 0)
             {}//JOptionPane.showMessageDialog(null,"delete");}
            else if(response == 1)
             {return;}
          //  else if(response == 2)
          //   {JOptionPane.showMessageDialog(null,"��������ȡ� ť");}
          //  else if(response == 3)
           //  {JOptionPane.showMessageDialog(null,"���������˳��� ť");}
		
            
	/*	try {
			// ע�� JDBC ����
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("�������ݿ�...");
			System.out.println("|---->deletetable");
			
			Connection conn = null;
			Statement stmt = null;

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// ִ�в�ѯ
			//System.out.println(" ʵ����Statement����...");
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
			// ���� JDBC ����
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
			//jtree.setmodel��treemodel����
			
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
			// ע�� JDBC ����
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("|---->show data");
			Connection conn = null;
			Statement stmt = null;

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// ִ�в�ѯ
			//System.out.println("ʵ����Statement����...");
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
			// System.out.print(", վ������: " + name);
			// System.out.print(", վ�� URL: " + url);
		
			// System.out.print(rs+"\n");
			JTSD td = new JTSD();
		    td.setBounds(400,50,800,600);
		    td.setVisible(true);
		    td.label.setText(s2);
		    td.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    
		}
		
		
		// ��ɺ�ر�
		// rs.close();
		// stmt.close();
		// conn.close();
		catch (SQLException se) {
			// ���� JDBC ����
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
		if(e.getActionCommand()=="�鿴�ñ�")
			showdata();
		
		else if(e.getActionCommand()=="new��")
			newtable();
		
		else if(e.getActionCommand()=="delete��")
			deletetable();
		else  if(e.getActionCommand()=="ˢ��")
			refresh();
	}
}