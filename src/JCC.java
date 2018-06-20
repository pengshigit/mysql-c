

import javax.swing.JComboBox;

public class JCC extends JComboBox 
{

	
	JCC()
	{
		this.addItem("int"); 
		this.addItem("varchar");
		this.addItem("bigint");
		
		this.addItem("float");
		
		this.addItem("double");
		
		this.addItem("date");
	
		this.addItem("char");
		this.addItem("text");
		
	}

	void set(int a, int b, int c, int d) 
	{
		this.setBounds(a, b, c, d);

	}
	JCC(int a, int b, int c, int d) 
	{
		this.setBounds(a, b, c, d);
	}

	void add(String n) 
	{
		this.addItem(n);
	}
	
	int  getItemList()
	{
		return this.getItemCount();
	}
	
	String getN() 
	{
		String h=this.getSelectedItem().toString();
		
		if(h=="varchar")
		return "varchar(10)";
		
		return h;
	}
}
