import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainDisplay1 implements ActionListener{

	JFrame iFrame;
	JPanel iPaneLbl, temp;
	JButton byPerson, byBook, byPolicy,  Borrow, Return, extProgram;
	JLabel iName;
	
	JTable bookTable, personTable;
	String category[] = {"Book name", "Author", "Borrower"};
	String category2[] = {"Name", "Age", "Number", "Position"};
	String category3[] = {"Student", "Faculty", "Guest", "Staff"};
	/*String category4[] = {"FULL", "HALF", "BASIC"};*/
	DefaultTableModel model, model2;
	JScrollPane tbl_sp, tbl_sp2;
	Person[] libPerson;
	int countPerson;
	Book[] libBook;
	int countBook;	
	public MainDisplay1(){		
		libPerson = new Person[100];
		libBook = new Book[100];		
		countPerson = countBook = 0;
		
		iFrame=new JFrame("Book Management");
		iFrame.setLayout(null);
		iFrame.setBounds(40, 40, 1135, 360);
		iFrame.setResizable(false);
	

		iPaneLbl=new JPanel(null);
		iPaneLbl.setBounds(10, 0, 400, 60);
		iPaneLbl.setBackground(Color.pink);
		iName=new JLabel("2018 Book Management ");
		iName.setBounds(95, 5, 400, 50);
		iName.setForeground(Color.white);
		iName.setFont(new Font("Helvitica", Font.BOLD, 20));
		iPaneLbl.add(iName);
		iFrame.add(iPaneLbl);

		byPerson=new JButton("Membership Information");
		byPerson.setBounds(130, 90, 190, 30);
		byPerson.setForeground(Color.white);
		byPerson.setBackground(Color.pink);
		byPerson.addActionListener(this);
		iFrame.add(byPerson);

		byBook=new JButton("Book  Information");
		byBook.setBounds(130, 140, 190, 30);
		byBook.setForeground(Color.white);
		byBook.setBackground(Color.pink);
		byBook.addActionListener(this);
		iFrame.add(byBook);

		/*byPolicy=new JButton("Manage by Policy");
		byPolicy.setBounds(140, 190, 190, 30);
		byPolicy.setForeground(Color.white);
		byPolicy.setBackground(Color.pink);
		byPolicy.addActionListener(this);
		iFrame.add(byPolicy);*/

		extProgram=new JButton("Exit the program");
		extProgram.setBounds(130, 250, 190, 30);
		extProgram.setForeground(Color.white);
		extProgram.setBackground(Color.pink);
		extProgram.addActionListener(this);
		iFrame.add(extProgram);
		
		Borrow=new JButton("Borrow");
		Borrow.setBounds(130, 190, 90, 30);
		Borrow.setForeground(Color.white);
		Borrow.setBackground(Color.pink);
		Borrow.addActionListener(this);
		iFrame.add(Borrow);
		
		Return=new JButton("Return");
		Return.setBounds(230, 190, 90, 30);
		Return.setForeground(Color.white);
		Return.setBackground(Color.pink);
		Return.addActionListener(this);
		iFrame.add(Return);
		
		model = new DefaultTableModel(category,0);
		bookTable = new JTable(model);		
		tbl_sp = new JScrollPane(bookTable);
		tbl_sp.setBounds(415, 0, 250, 400);
		iFrame.add(tbl_sp);
	
		model2 = new DefaultTableModel(category2, 0);
		personTable = new JTable(model2);
		tbl_sp2 = new JScrollPane(personTable);
		tbl_sp2.setBounds(670, 0, 440, 400);
		iFrame.add(tbl_sp2);		
		iFrame.setVisible(true);
	}
	private void load() {
		try {
			FileInputStream fis = new FileInputStream("library.txt");
			InputStreamReader isr = new InputStreamReader(fis, "MS949");
			BufferedReader br = new BufferedReader(isr);
			String temp;
			if(br.readLine().trim().equals("Book")){
				while(true){
					temp = br.readLine().trim();
					if(!temp.equals("Person")){
						libBook[countBook] = new Book();
						libBook[countBook].setName(temp);
						libBook[countBook].setAuth(br.readLine().trim());
						temp = br.readLine().trim();
						if(temp.equals("null"))
							libBook[countBook++].setPersonname(null);
						else
							libBook[countBook++].setPersonname(temp);
					}
					else{
						while(true){
						libPerson[countPerson] = new Person() {
						};
						libPerson[countPerson].setName(br.readLine().trim());
						libPerson[countPerson].setAge(Integer.parseInt(br.readLine().trim()));
						libPerson[countPerson].setAddress(br.readLine().trim());
						libPerson[countPerson].setStatus(br.readLine().trim());
						libPerson[countPerson++].setNumofbook(Integer.parseInt(br.readLine().trim()));
						}
					}
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (Exception e) {
			refresh();
			return;
		}
		refresh();
	}
	public void actionPerformed(ActionEvent iEvent) {
		if(iEvent.getSource()==byPerson) {
			String name = JOptionPane.showInputDialog("이름을 등록해주세요");
			int age = Integer.parseInt(JOptionPane.showInputDialog("나이를 등록해주세요"));
			String address = JOptionPane.showInputDialog("주소를 등록해주세요");
			int abc = JOptionPane.showOptionDialog(temp, "선택해주세요", " ", 0, 0, null, category3, 0);
			switch (abc) {
			case 0:
				libPerson[countPerson++] = new Student(name, age, address);
				refresh();
				break;
			case 1:
				libPerson[countPerson++] = new Faculty(name, age, address);
				refresh();
				break;
			case 2:
				String visit = JOptionPane.showInputDialog("방문 목적을 등록하세요"); // guest 클래스만 존재
				libPerson[countPerson++] = new guest(name, age, address, visit); // 테이블에 방문 목적은 출력 X
				refresh();
				break;
			case 3:
				libPerson[countPerson++] = new Staff(name, age, address);
				refresh();
				break;
			}
		}
		else if(iEvent.getSource()==byBook) {
			String name = JOptionPane.showInputDialog("책 제목을 등록해주세요");
			String auth = JOptionPane.showInputDialog("저자를 등록해주세요");			
			libBook[countBook++] = new Book(name, auth);
			refresh();
		}
		/*else if(iEvent.getSource()==byPolicy) {
			int select = JOptionPane.showOptionDialog(temp, "정책을 선택해주세요", "정책 선택", 0, 0, null, category4, 0);
			Policy.change(select); 
		} */
		else if(iEvent.getSource()==Borrow){
			int book = bookTable.getSelectedRow();
			int person = personTable.getSelectedRow();
			String bookname = (String) model.getValueAt(book, 0);
			String personname = (String) model2.getValueAt(person, 0);
			if(model.getValueAt(book, 2) !=null){
				JOptionPane.showMessageDialog(temp, " " + bookname +"\"은(는) 이미 대출된 도서입니다.");
				return;
			}
			borrow(bookname, personname);
		}
		else if(iEvent.getSource()==Return){
			int book = bookTable.getSelectedRow();
			if(model.getValueAt(book, 2)==null){
				JOptionPane.showMessageDialog(temp, "이미 반납된 도서입니다");
			}
			else{
				String personname = (String) model.getValueAt(book, 2);
				Return(personname, (String) model.getValueAt(book, 0));
				refresh();
			}			
		}
		else
		{
			try {
				FileOutputStream fos = new FileOutputStream("library.txt");
				OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write("Book\r\n");
				for(int i=0; i<countBook; i++){
					bw.write(libBook[i].getName()+"\r\n");
					bw.write(libBook[i].getAuth()+"\r\n");
					bw.write(libBook[i].getPersonname()+"\r\n");
				}
				bw.write("Person\r\n");
				for(int i=0; i<countPerson; i++){
					bw.write(libPerson[i].getName()+"\r\n");
					bw.write(libPerson[i].getAge()+"\r\n");
					bw.write(libPerson[i].getAddress()+"\r\n");
					bw.write(libPerson[i].getStatus()+"\r\n");
					bw.write(libPerson[i].getNumofbook()+"\r\n");
				}
				bw.flush();
				osw.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	private void Return(String personname, String bookname) {
		for (int i = 0; i < countPerson; i++) {
			if(libPerson[i].getName().equals(personname)){
				libPerson[i].setNumofbook(libPerson[i].getNumofbook()-1);
				break;
			}
		}
		for (int i = 0; i < countBook; i++) {
			if(libBook[i].getName().equals(bookname)){
				libBook[i].setPersonname(null);
				return;
			}
		}
	}
	private void borrow(String bookname, String personname) {
		for(int i=0; i<countPerson; i++){
			if(libPerson[i].getName().equals(personname)){
				libPerson[i].setNumofbook(libPerson[i].getNumofbook()+1);
				break;
			}
		}
		for(int i=0; i<countBook; i++){
			if(libBook[i].getName().equals(bookname)){
				libBook[i].setPersonname(personname);
				break;
			}
		}		
		refresh();
	}
	private void refresh() {
		model = new DefaultTableModel(category, 0);
		for(int i=0; i<countBook; i++){
			model.addRow(libBook[i].getall());
		}
		model2 = new DefaultTableModel(category2, 0);
		for(int i=0; i<countPerson; i++){
			model2.addRow(libPerson[i].getall());
		}
		bookTable.setModel(model);
		personTable.setModel(model2);
		iFrame.invalidate();
	}
	public static void main(String[] args) {
		MainDisplay1 MDisMDis=new MainDisplay1();
		MDisMDis.load();
	}
}