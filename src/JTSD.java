

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;;

public class JTSD extends JFrame implements ActionListener {

	JTable table;
	JTextArea text;
	int height = 600;
	JLabel label;
	String title;
	
	/*
	 * static Object[][] student = {{new Integer(101), "Tom","��",new
	 * Integer(20),"�����"}, {new Integer(102), "Tom","��",new Integer(20),"�����"},
	 * {new Integer(103), "ţţ","��",new Integer(21),"����"}, {new Integer(104),
	 * "����","Ů",new Integer(22),"ͨ��"} };
	 */
	static int column;
	static Object[][] student;
	static Object[] header;

	int width = 800;

	public static int getColumn() {
		return column;
	}

	public static void setColumn(int column) {
		JTSD.column = column;
	}

	public static Object[][] getStudent() {
		return student;
	}

	public static void setStudent(Object[][] student) {
		JTSD.student = student;
	}

	public static Object[] getHeader() {
		return header;
	}

	public static void setHeader(Object[] header) {
		JTSD.header = header;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void set(String str) {
		header = str.split(",");
	}

	public JTSD() 
	{
		
		label = new JLabel(getTitle(), JLabel.CENTER);
		table = new JTable(student, header);
		table.setPreferredScrollableViewportSize(new Dimension(width, height));
		
		JScrollPane scrollPane = new JScrollPane(table);
		Container ct = this.getContentPane();
		
		ct.add(label, BorderLayout.NORTH);
		ct.add(scrollPane, BorderLayout.CENTER);
		table.setSelectionBackground(Color.GREEN);
		
 
		
		JPanel pl = new JPanel();
		JButton bt = new JButton(":");
		
		text = new JTextArea();
		text.setEditable(false);
		
		pl.add(bt);
		pl.add(text);
		
		ct.add(pl, BorderLayout.SOUTH);
		bt.addActionListener(this);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			
		     public void mouseClicked(java.awt.event.MouseEvent e) 
		     { 
		      if(table.getValueAt(table.getSelectedRow(),0)!=null)
		      {
		       String s  = (String) table.getValueAt(table.getSelectedRow(),0);   //��ȡ��ѡ�е��еĵ�һ��λ�õ����ݣ���Ȼ��Ҳ����ָ������ĸ��еڼ���
		       System.out.println(s);
		      }
		      
		    }
		 });
		
	}

	/*
	 * public static void main(String[] args) { JTableDemo td = new
	 * JTableDemo(); td.setBounds(400,300,500,300); td.setVisible(true);
	 * td.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); }
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		int num = table.getColumnCount();
		text.setText("");
		text.append(String.valueOf(num));
		text.setForeground(Color.red);
		text.setEditable(true);

	}

}
